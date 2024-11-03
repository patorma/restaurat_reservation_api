package com.edteam.restaurant_reservation.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

//registro de usuario lo que se va enviar
@Data
public class SignupRequestDTO {
//@NotBlank indica que el atributo es obligatorio
	@NotBlank
	private String firstName;
	
	@NotBlank(message = "last name is mandatory")
	private String lastName;
	
	@NotBlank(message = "Email is mandatory")
	@Email(message = "Invalid email")
	private String email;
	
	@NotNull(message = "Password is mandatory" )
	@Size(min = 4)
	private String password;
}
