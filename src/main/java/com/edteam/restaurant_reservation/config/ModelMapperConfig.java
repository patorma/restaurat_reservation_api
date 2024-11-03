package com.edteam.restaurant_reservation.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
// esto se registra con @Bean cuando un objeto no pertenece al
//ambito de spring
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
