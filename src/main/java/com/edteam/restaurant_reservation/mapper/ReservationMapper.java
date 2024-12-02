package com.edteam.restaurant_reservation.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import java.util.List;

import com.edteam.restaurant_reservation.domain.entity.Reservation;
import com.edteam.restaurant_reservation.dto.request.ReservationRequestDTO;
import com.edteam.restaurant_reservation.dto.response.ReservationResponseDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class ReservationMapper {

	private final ModelMapper modelMapper;
	
	public Reservation toEntity(ReservationRequestDTO reservationRequestDTO) {
		return modelMapper.map(reservationRequestDTO, Reservation.class);
	}
	
	public ReservationResponseDTO toResponseDto(Reservation reservation) {
		return modelMapper.map(reservation, ReservationResponseDTO.class);
	}
	
	public List<ReservationResponseDTO> toResponseDtoList(List<Reservation> reservations){
		return reservations.stream()
				.map(this::toResponseDto)
				.toList();
	}
	
}
