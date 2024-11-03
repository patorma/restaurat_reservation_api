package com.edteam.restaurant_reservation.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edteam.restaurant_reservation.domain.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	//Buscar usuario por correo
	Optional<User> findOneByEmail(String email);
	
	//Verificar si el usuario existe mediante un correo
	boolean existsByEmail(String email); 
}
