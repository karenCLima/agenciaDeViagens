package com.spring.agenciaDeViagens.controller.dto;

import java.time.LocalDateTime;

import com.spring.agenciaDeViagens.model.Customer;
import com.spring.agenciaDeViagens.model.TicketType;
import com.spring.agenciaDeViagens.model.TravelPackage;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TicketResponse {
	
	private Integer id;
	private Customer customer;
	private LocalDateTime bookedDate;
	private LocalDateTime travelDate;
	private TicketType ticketType;
	private Long flightNumber;
	private TravelPackage travelPackage;

}
