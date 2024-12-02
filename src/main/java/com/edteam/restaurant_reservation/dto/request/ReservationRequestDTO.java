package com.edteam.restaurant_reservation.dto.request;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ReservationRequestDTO {

	private Long id;
	
	@NotNull(message = "Restaurant ID is mandatory")
	private Long restaurantId;
	
	@NotNull(message = "Reservation Date is mandatory")
	private LocalDateTime reservationDate;
	
	@Positive(message = "Number of peolple must positive")
	private int numberOfPeople;
	
	private String additionalInfo;
}
