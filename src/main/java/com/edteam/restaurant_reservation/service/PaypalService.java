package com.edteam.restaurant_reservation.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.edteam.restaurant_reservation.domain.entity.Reservation;
import com.edteam.restaurant_reservation.dto.paypal.Amount;
import com.edteam.restaurant_reservation.dto.paypal.ApplicationContext;
import com.edteam.restaurant_reservation.dto.paypal.OrderCaptureResponse;
import com.edteam.restaurant_reservation.dto.paypal.OrderRequest;
import com.edteam.restaurant_reservation.dto.paypal.OrderResponse;
import com.edteam.restaurant_reservation.dto.paypal.PurchaseUnit;
import com.edteam.restaurant_reservation.dto.paypal.TokenResponse;
import com.edteam.restaurant_reservation.exception.ResourceNotFoundException;
import com.edteam.restaurant_reservation.repository.ReservationRepository;

import jakarta.annotation.PostConstruct;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.util.LinkedMultiValueMap;

import java.util.Base64;
import java.util.Collections;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class PaypalService {

/* se le indica con que credenciales conectarse a la api
 * de paypal al backend*/ 
    @Value("${paypal.client-id}")
    private String clientId;

    @Value("${paypal.client-secret}")
    private String clientSecret;

    @Value("${paypal.api-base}")
    private String apiBase;
//fin de declaracion de credenciales para conexion de api paypal
/*cuando se va a crear la orden de pago  se necesita decir
 * que en esa orden de pago se necesita tener la ionformacion
 * de la reserva porque la reserva es la que uno va a pagar
 *  */
    @NonNull
    private ReservationRepository reservationRepository;
/*El  RestClient  es una interfaz que me permite comunicarme con api
 * externas en este ejemplo es paypal*/
    private RestClient paypalClient;

    @PostConstruct 
    public void init() {
        paypalClient = RestClient
                .builder()
                .baseUrl(apiBase)
                .build();
    }
// se configura obtencion de token ojo son los token que exige paypal
    //para acceder a los endpoint de ellos 
    private String getAccessToken() {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "client_credentials");

        return Objects.requireNonNull(paypalClient.post()
                        .uri("/v1/oauth2/token")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes()))
                        .body(body)
                        .retrieve()
                        .toEntity(TokenResponse.class)
                        .getBody())
                .getAccessToken();
    }

    public OrderResponse createOrder(Long reservationId, String returnUrl, String cancelUrl) {
        Reservation reservation = reservationRepository
                .findById(reservationId)
                .orElseThrow(ResourceNotFoundException::new);

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setIntent("CAPTURE");

        Amount amount = new Amount();
        amount.setCurrencyCode("USD");
        amount.setValue(reservation.getTotalAmount().toString());

        PurchaseUnit purchaseUnit = new PurchaseUnit();
        purchaseUnit.setReferenceId(reservation.getId().toString());
        purchaseUnit.setAmount(amount);

        orderRequest.setPurchaseUnits(Collections.singletonList(purchaseUnit));

        ApplicationContext applicationContext = ApplicationContext
                .builder()
                .brandName("EDteamReserve")//nombre de la aplicacion que se genera
                .returnURL(returnUrl)
                .cancelURL(cancelUrl)
                .build();

        orderRequest.setApplicationContext(applicationContext);

        return paypalClient.post()
                .uri("/v2/checkout/orders")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .body(orderRequest)
                .retrieve()
                .toEntity(OrderResponse.class)
                .getBody();
    }
//Una vez que se autoriza el pagop o confirma el cliente que quiere pagar
    //el capture toma esos datos  verifica la order 
    
    public OrderCaptureResponse captureOrder(String orderId) {
        return paypalClient.post()
                .uri("v2/checkout/orders/{order_id}/capture", orderId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(OrderCaptureResponse.class)
                .getBody();
    }
}
