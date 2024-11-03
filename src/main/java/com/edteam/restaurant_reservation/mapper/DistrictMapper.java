package com.edteam.restaurant_reservation.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.edteam.restaurant_reservation.domain.entity.District;
import com.edteam.restaurant_reservation.dto.response.DistrictResponseDTO;

import lombok.RequiredArgsConstructor;
import java.util.List;
//se le dice a spring que esta clase es componente y lo registre en 
//este contexto
@RequiredArgsConstructor
@Component
public class DistrictMapper {

	private final ModelMapper modelMapper;
	
	//este metodo recibe una entidad 
	//transforma la entidad District a la estructura del 
	//DistrictResponseDTO
	//este metodo toma la estructura de la entidad como dato
	//de entrada y mapearlo para obtener un objeto con la estructura
	//de DistrictResponseDTO
	//trabaja de uno a uno  osea un objeto a un objeto resultante
	public DistrictResponseDTO toResponseDto(District district) {
		return modelMapper.map(district,DistrictResponseDTO.class);
	}
	
	//pero a veces se va q querer pasar una lista de entidades a una lista
	//de entidades DTO
	
	//en este metodo se hace lo mismo que lo anterior 
	//pero como es una lista recorro esa lista 
	//trasnformandola en un flujo de datos mediante un stream
	public List<DistrictResponseDTO> toResponseDtoList(List<District> districts){
		return districts.stream()
				.map(this::toResponseDto)//se llama al metodo que hace la trasnformacion por cada objeto
				.toList();
	}
}
