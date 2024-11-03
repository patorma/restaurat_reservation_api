package com.edteam.restaurant_reservation.service;

import org.springframework.stereotype.Service;

import com.edteam.restaurant_reservation.domain.entity.District;
import com.edteam.restaurant_reservation.dto.response.DistrictResponseDTO;
import com.edteam.restaurant_reservation.mapper.DistrictMapper;
import com.edteam.restaurant_reservation.repository.DistrictRepository;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import java.util.List;

//@RequiredArgsConstructor esto debido a que 
//los servicios van a necesitar las dependencias del repository
//donde se implementa las query a trave

@RequiredArgsConstructor
@Service
public class DistrictService {
	
	private final DistrictRepository districtRepository;
	
	private final DistrictMapper districtMapper;
	
	@Transactional(readOnly=true)
	public List<DistrictResponseDTO> getAllDistricts(){
		
		List<District> districts = districtRepository.findAll();
		/*como necesitamos que retorne DistrictResponseDTO
		 * acá entra lo de mappeer */
		return districtMapper.toResponseDtoList(districts);
	}
	
	

}
