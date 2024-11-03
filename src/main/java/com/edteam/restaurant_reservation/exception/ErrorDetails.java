package com.edteam.restaurant_reservation.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

//se debe tener una clase tipo DTO Para enviarle cierta informacion
// al frontend detallada del error

@Data
@AllArgsConstructor
public class ErrorDetails {
	//la fecha y hora  donde se esta dando el error
	private LocalDateTime timestamp;
	
	//mensaje del error
	private String message;
	
	//Detalle del error 
	private String details;
	
	

}
