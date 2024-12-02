package com.edteam.restaurant_reservation.dto.paypal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class PurchaseUnit {
	 @JsonProperty("reference_id")
	  private String referenceId;
	  private Amount amount;
}
