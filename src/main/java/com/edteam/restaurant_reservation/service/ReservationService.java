package com.edteam.restaurant_reservation.service;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.edteam.restaurant_reservation.domain.entity.Reservation;
import com.edteam.restaurant_reservation.domain.entity.Restaurant;
import com.edteam.restaurant_reservation.domain.entity.User;
import com.edteam.restaurant_reservation.domain.enums.ReservationStatus;
import com.edteam.restaurant_reservation.dto.request.ReservationRequestDTO;
import com.edteam.restaurant_reservation.dto.response.ReservationResponseDTO;
import com.edteam.restaurant_reservation.exception.ResourceNotFoundException;
import com.edteam.restaurant_reservation.mapper.ReservationMapper;
import com.edteam.restaurant_reservation.repository.ReservationRepository;
import com.edteam.restaurant_reservation.repository.RestaurantRepository;
import com.edteam.restaurant_reservation.repository.UserRepository;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service  
public class ReservationService {
//esta clase tiene la escencia de una reserva
 //van las dependencias necesarias para esta logica 
	//cada servicio su primera dependencia es su repository
  private final ReservationRepository reservationRepository;
  
  //si se quiere hacer una reserva , cuya reserva es en un restaurante
  //va haber un momento en el proceso de reserva
  //hay que recuperar la informacion del restaurante 
  //para asociarlo a esa reserva
  //esa informacion se recupera de la base de datos
  //por eso se necesita la dependencia del restaurant repository
  private final RestaurantRepository restaurantRepository;
  //repository del usuario persona que va a realizar proceso de reserva
  private final UserRepository userRepository;
  
  //luego viene mapper ya que en los servicios vien un proceso
  //recibe una entidad y se expone un DTO y viceversa
  private final ReservationMapper reservationMapper;
  
  //este metodo recibe un DTO osea recibe la informacion de la reserva
  
  @Transactional
  public ReservationResponseDTO createReservation
  						(ReservationRequestDTO reservationRequestDTO) {
	  //se recupera informacion del usuario que inicio sesion
	  //se debe acceder al contexto de spring security para obtener esa informacion
	  Authentication authentication = SecurityContextHolder.getContext()
			  				.getAuthentication();
	  
	  User user = userRepository.findOneByEmail(authentication.getName())
			  			.orElseThrow(()-> new ResourceNotFoundException("user not found by username"));
	  //busco un restaurante con el id que viene en la reserva
	  Restaurant restaurant = restaurantRepository.
			  				findById(reservationRequestDTO.getRestaurantId())
			  				.orElseThrow(()-> new ResourceNotFoundException("restaurant not found by id"));
	  
	  // si las anteriores búsquedas resultaron bien se realiza la reserva
	  // en el mapper tomo la informacion 
	  Reservation reservation = reservationMapper.toEntity(reservationRequestDTO);
	  //luego se asigna los valores a la entidad
	  reservation.setRestaurant(restaurant);
	  reservation.setClient(user);
	  reservation.setStatus(ReservationStatus.PENDING);
	  reservation.calculateTotalAmount();
	  reservation = reservationRepository.save(reservation);
	  
	  return reservationMapper.toResponseDto(reservation);
	  
  }
  
  //Permite buscar la lista de  reservas por el id de cliente
// es una query
  
  @Transactional(readOnly = true)
  public List<ReservationResponseDTO> getReservationsByClientId() {

	  // se recupera informacion de usuario que inicia sesion
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    User user = userRepository.findOneByEmail(authentication.getName())
        .orElseThrow(() -> new ResourceNotFoundException("User not found"));

    List<Reservation> reservations = reservationRepository.findByClientId(user.getId());
    return reservationMapper.toResponseDtoList(reservations);
  }
  
  // es una query o metodo  que permite recuperar la informacion de una reserva
  //por id es importante para obtener el detalle de la reserva por id
  @Transactional(readOnly = true)
  public ReservationResponseDTO getReservationById(Long reservationId) {
    Reservation reservation = reservationRepository.findById(reservationId)
            .orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));

    return reservationMapper.toResponseDto(reservation);
  }

  // este metodo va a permitir recuperar o confirmar la resreva es decir
  //cambia el estado de la reserva de pendiente a pagar se utiliza en un proceso de pago
  // se recupera por un id los datos de una reserva y cambiarle el status 
  @Transactional
  public Reservation confirmReservationPayment(Long reservationId) {
    Reservation reservation = reservationRepository
            .findById(reservationId)
            .orElseThrow(ResourceNotFoundException::new);

    reservation.setStatus(ReservationStatus.PAID);
    return reservationRepository.save(reservation);
  }

  
  
}
