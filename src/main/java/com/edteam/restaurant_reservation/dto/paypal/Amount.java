package com.edteam.restaurant_reservation.dto.paypal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Amount {
//estructura del 

	//se indica con que nombre quiero que aparezca en el 
	//encabezado de json
	//@Column solo se utiliza cuando las clases son entity
	@JsonProperty("currency_code")
	private String currencyCode;
	
	private String value;
}
