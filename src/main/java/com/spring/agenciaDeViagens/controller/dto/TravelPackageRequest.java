package com.spring.agenciaDeViagens.controller.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class TravelPackageRequest {
	@NotBlank
	private String country;
	@NotBlank
	private String place;
	@Positive
	private Integer travelDurationInDays;
	@DecimalMin(value = "0.0", inclusive = false)
	private BigDecimal price;

}
