package com.spring.agenciaDeViagens.controller.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class TravelPackageRequest {
	@NotBlank
	private String country;
	@NotBlank
	private String place;
	private Integer travelDurationInDays;
	private BigDecimal price;

}
