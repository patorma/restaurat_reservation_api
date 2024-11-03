package com.edteam.restaurant_reservation.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "restaurants")
public class Restaurant {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name",nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String address;
	
	@Column(name = "phone_number",nullable=false)
	private String phoneNumber;
	
	@Column(name = "price_per_person",nullable = false)
	private double pricePerPerson;
	
	@Column(nullable = false)
	private int capacity;
	
	@ManyToOne
	@JoinColumn(name = "district_id",nullable = false)
	@JsonIgnoreProperties({"hibernateLazyInitializer","hadler"})
	private District district;
}
