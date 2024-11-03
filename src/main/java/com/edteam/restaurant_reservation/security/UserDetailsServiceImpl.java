package com.edteam.restaurant_reservation.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.edteam.restaurant_reservation.domain.entity.User;
import com.edteam.restaurant_reservation.repository.UserRepository;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	private final UserRepository userRepository;
// aunque sea un servicio de spring security se debe anotar con @Service
	// La clase
	//tiene como propisito en un inicio de sesion cargar la informacion 
	//del usuario por el username 
	//luego se asigna la informacion a la entidad User pero de spring security
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		// Se busca informacion de nuestro usuario
	    // Busca al usuario en la base de datos por correo electrónico
         //y lanza una excepción si no se encuentra.
		
		User user = userRepository.findOneByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException(username));
		
		//con la informacion que recupero de userRepository
		//contruyo el userd e spring
		//el user sirve para mapear la informacion del usuario
		return org.springframework.security.core.userdetails.User
				.withUsername(user.getEmail())
				.password(user.getPassword())
				.roles(user.getRole().name())
				.build();
	}

}
