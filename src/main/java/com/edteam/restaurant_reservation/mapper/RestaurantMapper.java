package com.edteam.restaurant_reservation.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.edteam.restaurant_reservation.domain.entity.Restaurant;
import com.edteam.restaurant_reservation.dto.response.RestaurantResponseDTO;

import lombok.RequiredArgsConstructor;
import java.util.List;

@RequiredArgsConstructor
@Component
public class RestaurantMapper {

	private final ModelMapper modelMapper;
	
	public RestaurantResponseDTO toResponseDto(Restaurant restaurant) {
		return modelMapper.map(restaurant,  RestaurantResponseDTO.class);
	}
	
	public List<RestaurantResponseDTO> toResponseDtoList(List<Restaurant> restaurants){
		return restaurants.stream()
			   .map(this::toResponseDto)
			   .toList();
	}
}
