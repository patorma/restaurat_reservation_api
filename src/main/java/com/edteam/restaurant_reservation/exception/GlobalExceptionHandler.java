package com.edteam.restaurant_reservation.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

//clase que gestiona estas excepciones 
//se da la excepcion y capturo la excepcion y
//delego dependiendo del tipo de excepcion
//cual es el error que debo mostrar
//lo que va hacer @ControllerAdvice es un pull de metodos 
//simplemente de section hadler osea cada vez que se de una excepcion
/*cada vez que se de una excepcion osea si hay un metodo que esta haciendo
 * una busqueda por un id y ese id no existe */
@ControllerAdvice
public class GlobalExceptionHandler {

	 @ExceptionHandler(ResourceNotFoundException.class)
	    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
	        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
	        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	    }
	 
	 @ExceptionHandler(BadRequestException.class)
	    public ResponseEntity<ErrorDetails> handleBadRequestException(BadRequestException ex, WebRequest request) {
	        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
	        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	    }

	    @ExceptionHandler(Exception.class)
	    public ResponseEntity<ErrorDetails> handleGlobalException(Exception ex, WebRequest request) {
	        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
	        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	    }

}
