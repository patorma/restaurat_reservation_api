package com.edteam.restaurant_reservation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

//es la respuesta cuando la solicitud de crear una orden de pago
//se va a generar y en ese momento loq ue se va a recinir es
//como respuesta es simplemente una url que me entrega paypal

@Data
@AllArgsConstructor
public class PaypalOrderResponse {

	private String paypalUrl;
}
