package com.edteam.restaurant_reservation.dto.response;

import lombok.Data;
/*Respuesta que se va a obtener una vez que el usuario tenga un
 * inicio de sesion exitoso*/
@Data
public class AuthResponseDTO {

	private String token;
	
	private UserProfileResponseDTO user;
}
