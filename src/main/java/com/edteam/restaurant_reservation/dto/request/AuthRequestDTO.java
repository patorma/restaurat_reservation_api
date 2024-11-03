package com.edteam.restaurant_reservation.dto.request;

import lombok.Data;

//Representa inicio de sesion y se van a enviar estos dos datos
@Data
public class AuthRequestDTO {

	private String email;
	private String password;
}
