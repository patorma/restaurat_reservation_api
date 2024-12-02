package com.edteam.restaurant_reservation.dto.response;

import lombok.Data;

//y cuando se procesa la captura de la orden 
//se emite esta respuesta 
@Data
public class PaypalCaptureResponse {
	private boolean completed;
	//id de la reserva que se comleto
    private Long reservationId;
}
