package com.edteam.restaurant_reservation.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edteam.restaurant_reservation.domain.entity.Restaurant;
import com.edteam.restaurant_reservation.dto.response.RestaurantResponseDTO;
import com.edteam.restaurant_reservation.exception.ResourceNotFoundException;
import com.edteam.restaurant_reservation.mapper.RestaurantMapper;
import com.edteam.restaurant_reservation.repository.RestaurantRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
@Service
public class RestaurantService {

	private final RestaurantRepository restaurantRepository;
	
	private final RestaurantMapper restaurantMapper;
	
	// se debe mostrar la lista de  en formato de pagina
	@Transactional(readOnly = true)
	public Page<RestaurantResponseDTO> getAllRestaurants(Pageable pageable){
		Page<Restaurant> restaurants =restaurantRepository.findAll(pageable);
		//se toma esta pagina Page<Restaurant> restaurants
		//y cada elmento lo transforma a RestaurantResponseDTO
		return restaurants.map(restaurantMapper::toResponseDto);
	}
	//cuantos restaurantes habran en la pagina
	@Transactional(readOnly = true)
	public Page<RestaurantResponseDTO> getRestaurantsByDistrictName(String districtName,Pageable pageable){
		Page<Restaurant> restaurants =restaurantRepository.findByDistrictName(districtName,pageable);
		return restaurants.map(restaurantMapper::toResponseDto);
	}
	
	//metodo para recuperar el detalle de un restaurante 
	public RestaurantResponseDTO getRestaurantById(Long id) {
		Restaurant restaurant = restaurantRepository.findById(id)
					.orElseThrow(() ->  new ResourceNotFoundException("Restaurant not found with id: " + id));
		return restaurantMapper.toResponseDto(restaurant);
	}
}
