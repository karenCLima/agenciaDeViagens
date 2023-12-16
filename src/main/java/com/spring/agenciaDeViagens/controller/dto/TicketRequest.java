package com.spring.agenciaDeViagens.controller.dto;

import java.time.LocalDateTime;

import com.spring.agenciaDeViagens.model.TicketType;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class TicketRequest {
	
	@NotNull
	private Integer customer_Id;
	private LocalDateTime bookedDate;
	private LocalDateTime travelDate;
	private TicketType ticketType;
	private Long flightNumber;
	@NotNull
	private Integer travelPackage_Id;

}
