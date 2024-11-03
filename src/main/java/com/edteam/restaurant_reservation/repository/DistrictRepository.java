package com.edteam.restaurant_reservation.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edteam.restaurant_reservation.domain.entity.District;

//el JpaRepository es una interface que tiene metodos de persistensia
//pide dos datos de entrada uno es la entidad que va aplicar
//esos datos de persistencia y el siguiente atributo es el identificador
public interface DistrictRepository extends JpaRepository<District, Long> {

	//query personalizadas
	
	Optional<District> findByName(String name);
}
