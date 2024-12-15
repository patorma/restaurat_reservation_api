package com.edteam.restaurant_reservation.service;

import org.springframework.stereotype.Service;

import com.edteam.restaurant_reservation.domain.entity.Reservation;
import com.edteam.restaurant_reservation.dto.paypal.OrderCaptureResponse;
import com.edteam.restaurant_reservation.dto.paypal.OrderResponse;
import com.edteam.restaurant_reservation.dto.response.PaypalCaptureResponse;
import com.edteam.restaurant_reservation.dto.response.PaypalOrderResponse;

import lombok.AllArgsConstructor;


//es el proceso  o flujo que uno controla a nivel de backend
@AllArgsConstructor
@Service
public class CheckoutService {

	//se hace inyeccion de dependencias
	//primero se inyecta la dependencia de PaypalService
	//donde se encuentra la logica de integracion con la api de paypal
	private final PaypalService paypalService;
	//tambien se necesita el service de la reserva
	private final ReservationService reservationService;
	
	//se necesita ReservationService porque hay que recordar cuando se realizo
	//la reserva se genero con estado pendiente  tendria qwue pasar a pagado cuando
	// se captura la orden y se procesa esa orden mediante el captcha
	//tendria que pasar a pagado cuando se captura la orden
	//y se procesa esa orden mediante el captcher
	//que fue definido el paypalservice
	//en ese momento el estado de la reserva pasa de pendiente a 
//metodo para crear un pago utilizando paypal
	public PaypalOrderResponse createPaypalPaymentUrl(Long reservationId,
													  String returnUrl,String cancelUrl) {
		OrderResponse orderResponse = paypalService.createOrder(
				
				reservationId,
				returnUrl,
				cancelUrl
		);
		
		//Con la respuesta anterior se arma el paypalUrl
		String paypalUrl =  orderResponse
				 .getLinks()
				 .stream()
				 .filter(link -> link.getRel().equals("approve"))
				 .findFirst()
				 .orElseThrow(()-> new RuntimeException(""))
				 .getHref();
		
		//el PaypalOrderResponse retorna el formato anterior de la variable paypalUrl
	   return new PaypalOrderResponse(paypalUrl);
	}
	
	//se agrega lo de 
//una vez que se aprobo el pago recibe el id de la orden
//
	public PaypalCaptureResponse capturePaypalPayment(String orderId) {
        OrderCaptureResponse orderCaptureResponse = paypalService.captureOrder(orderId);
       //verifica si la creacion de esa orden fue completada
        //tomando como dato el id de la orden
        boolean completed = orderCaptureResponse.getStatus().equals("COMPLETED");

        PaypalCaptureResponse paypalCaptureResponse = new PaypalCaptureResponse();
        paypalCaptureResponse.setCompleted(completed);

        if (completed) {
        	//purchaseIdStr  es el id de la reserva
        	String purchaseIdStr = orderCaptureResponse.getPurchaseUnits().getFirst().getReferenceId();
            Reservation reservation = reservationService.confirmReservationPayment(Long.parseLong(purchaseIdStr));
           //se setea el valor para que pase de  pendiente a pagado la reserva
            paypalCaptureResponse.setReservationId(reservation.getId());
        }
        return paypalCaptureResponse;
    }
}
