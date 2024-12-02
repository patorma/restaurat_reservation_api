package com.edteam.restaurant_reservation.domain.entity;

import java.time.LocalDateTime;

import com.edteam.restaurant_reservation.domain.enums.ReservationStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name="reservations")
public class Reservation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "restaurant_id",nullable = false)
	private Restaurant restaurant;
	
	@ManyToOne
	@JoinColumn(name = "client_id",nullable = false)
	private User client;
	
	@Column(name = "reservation_date",nullable = false)
	private LocalDateTime reservationDate;
	
	//número de personas ose para cuantas personas 
	//quiero la reserva
	
	@Column(name = "number_of_people",nullable = false)
	private int numberOfPeople;
	
	
	 @Enumerated(EnumType.STRING)
	 @Column(name = "status",nullable = false)
	 private ReservationStatus status;
	 
	
	@Column(name = "additional_info")
	private String additionalInfo;
	
	@Column(name = "total_amount",nullable = false)
	private Double totalAmount; 
	
	public void calculateTotalAmount() {
		this.totalAmount = this.numberOfPeople * restaurant.getPricePerPerson();
	}
}
