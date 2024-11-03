package com.edteam.restaurant_reservation.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.edteam.restaurant_reservation.domain.entity.User;
import com.edteam.restaurant_reservation.dto.request.SignupRequestDTO;
import com.edteam.restaurant_reservation.dto.response.AuthResponseDTO;
import com.edteam.restaurant_reservation.dto.response.UserProfileResponseDTO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserMapper {
	
	private final ModelMapper modelMapper;
	
	public User toUser(SignupRequestDTO signupRequestDTO) {
		return modelMapper.map(signupRequestDTO, User.class);
	}
	
	public UserProfileResponseDTO toUserProfileResponseDTO(User user) {
		return modelMapper.map(user, UserProfileResponseDTO.class);
	}
	
	public AuthResponseDTO toAuthResponseDTO (String token,UserProfileResponseDTO userProfile) {
		AuthResponseDTO authResponseDTO = new AuthResponseDTO();
		authResponseDTO.setToken(token);
		authResponseDTO.setUser(userProfile);
		
		return authResponseDTO;
	}

}
