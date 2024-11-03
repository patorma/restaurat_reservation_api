package com.edteam.restaurant_reservation.exception;

//el BadRequestException si trato de buscar un recurso y por algun
//motivo no lo encuentro
public class ResourceNotFoundException extends RuntimeException {

	public ResourceNotFoundException() {
		super();
	}
	
	public ResourceNotFoundException(String message) {
		super(message);
	}
}
