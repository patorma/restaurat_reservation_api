package com.edteam.restaurant_reservation.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edteam.restaurant_reservation.dto.response.PaypalCaptureResponse;
import com.edteam.restaurant_reservation.dto.response.PaypalOrderResponse;
import com.edteam.restaurant_reservation.service.CheckoutService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/checkout")
public class CheckoutController {

	//Interactua con el checkoutService
	//y el checkout service es el que interactua con el paypal servie
	//y el paypal service es el que se comunica usan restclient 
	//con el api de paypal
	private final CheckoutService checkoutService;
	
	// se implementa los metodos relacionados con los endpoit
	
	@PostMapping("/paypal/create")
	public PaypalOrderResponse createPaypalOrder(
			
			@RequestParam Long reservationId,
			@RequestParam String returnUrl,
			@RequestParam String cancelUrl
			
			) {
		
		return checkoutService.createPaypalPaymentUrl(reservationId, returnUrl, cancelUrl);
	}
	//el metodo siguiente tiene que ver con el captcha
	//una vez que se haya aprobado la solicitud de pago
	//por intermediuo del usuario y de paypal tambien 
	//viene la captura 
	@PostMapping("/paypal/capture")
	public PaypalCaptureResponse capturePaypalOrder(
			
			@RequestParam String orderId
			) {
		
		return  checkoutService.capturePaypalPayment(orderId);
	}
}
