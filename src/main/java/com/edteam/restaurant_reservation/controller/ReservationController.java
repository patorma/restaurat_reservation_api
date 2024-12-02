package com.edteam.restaurant_reservation.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edteam.restaurant_reservation.dto.request.ReservationRequestDTO;
import com.edteam.restaurant_reservation.dto.response.ReservationResponseDTO;
import com.edteam.restaurant_reservation.service.ReservationService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/reservations")
public class ReservationController {

	//se inyecta dependencia
//los controller reciben dependencias de sus servicios
//un controller no puede recibir la inyeccion de dependencias
	// del repository
	private final ReservationService reservationService;
	
	// este método inicia la creacion de la reserva
	@PostMapping
	public ResponseEntity<ReservationResponseDTO> createReservation(
			//recupera la informacion que viene en la peticion
			@RequestBody @Validated ReservationRequestDTO reservationRequestDTO){
		ReservationResponseDTO reservationResponseDTO	= reservationService
				.createReservation(reservationRequestDTO);
		return new ResponseEntity<>(reservationResponseDTO,HttpStatus.CREATED);
	}
	
	//se listan todas las reservas de un cliente
	@GetMapping("/my-reservations")
	public ResponseEntity<List<ReservationResponseDTO>> getMyReservations(){
		List<ReservationResponseDTO> reservations =  reservationService
						.getReservationsByClientId();
		return new ResponseEntity<>(reservations,HttpStatus.OK);
	}
	//recupera el detalle de una reserva por id
	@GetMapping("/{id}")
	public ResponseEntity<ReservationResponseDTO> getReservationById(
			@PathVariable Long id){
		   ReservationResponseDTO reservation =  reservationService
						.getReservationById(id);
		return new ResponseEntity<>(reservation,HttpStatus.OK);
	}
	
	// nose coloca el metodo de confirmacion ya que va en el
	//modulo del pago
	
}
