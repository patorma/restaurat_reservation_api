package com.edteam.restaurant_reservation.dto.response;

import com.edteam.restaurant_reservation.domain.enums.Role;

import lombok.Data;

@Data
public class UserProfileResponseDTO {

	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private Role role; 
}
