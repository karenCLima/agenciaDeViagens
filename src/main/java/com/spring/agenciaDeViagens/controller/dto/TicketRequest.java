package com.spring.agenciaDeViagens.controller.dto;

import java.time.LocalDateTime;

import com.spring.agenciaDeViagens.model.TicketType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class TicketRequest {
	
	private Integer customer_Id;
	private LocalDateTime bookedDate;
	private LocalDateTime travelDate;
	private TicketType ticketType;
	private Long flightNumber;
	private Integer travelPackage_Id;

}
