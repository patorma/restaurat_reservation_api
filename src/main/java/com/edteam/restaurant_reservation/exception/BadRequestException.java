package com.edteam.restaurant_reservation.exception;

//Excepciones personalizadas
//En caso que este mal estructurada la cabecera
//el BadRequestException  es cuando envien una peticion incorrecta
//o mal formada
public class BadRequestException extends RuntimeException{

	public BadRequestException() {
		super();
	}
	public BadRequestException(String message) {
		super(message);
	}
}
