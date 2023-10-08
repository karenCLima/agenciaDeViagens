package com.spring.agenciaDeViagens.controller.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TravelPackageResponse {
	
	private Integer id;
	private String country;
	private String place;
	private Integer travelDurationInDays;
	private BigDecimal price;

}
