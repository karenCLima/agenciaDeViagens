package com.spring.agenciaDeViagens.utils;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;

import com.spring.agenciaDeViagens.controller.dto.TicketRequest;
import com.spring.agenciaDeViagens.controller.dto.TicketResponse;
import com.spring.agenciaDeViagens.model.Customer;
import com.spring.agenciaDeViagens.model.Ticket;
import com.spring.agenciaDeViagens.model.TravelPackage;

public class TicketConvert {
	
	public static Ticket toEntity(TicketRequest ticketRequest, Customer customer, TravelPackage travelPackage) {
		Ticket ticket = new Ticket();
		ticket.setCustomer(customer);
		ticket.setBookedDate(ticketRequest.getBookedDate());
		ticket.setTravelDate(ticketRequest.getTravelDate());
		ticket.setFlightNumber(ticketRequest.getFlightNumber());
		ticket.setTicketType(ticketRequest.getTicketType());
		ticket.setTravelPackage(travelPackage);
		return ticket;
	}
	
	public static TicketResponse toResponse(Ticket ticket) {
		TicketResponse ticketResponse = new TicketResponse();
		ticketResponse.setId(ticket.getId());
		ticketResponse.setBookedDate(ticket.getBookedDate());
		ticketResponse.setCustomer(ticket.getCustomer());
		ticketResponse.setFlightNumber(ticket.getFlightNumber());
		ticketResponse.setTicketType(ticket.getTicketType());
		ticketResponse.setTravelDate(ticket.getTravelDate());
		ticketResponse.setTravelPackage(ticket.getTravelPackage());
		return ticketResponse;
	}
	
	public static List<TicketResponse> toResponseList(List<Ticket> tickets){
		List<TicketResponse> ticketResponses = new ArrayList<>();
		for(Ticket ticket: tickets) {
			TicketResponse ticketResponse = TicketConvert.toResponse(ticket);
			ticketResponses.add(ticketResponse);
		}
		return ticketResponses;
	}
	
	public static Page<TicketResponse> toResponsePage(Page<Ticket> tickets){
		List<TicketResponse> ticketResponses = new ArrayList<>();
		for(Ticket ticket: tickets) {
			TicketResponse ticketResponse = TicketConvert.toResponse(ticket);
			ticketResponses.add(ticketResponse);
		}
		return new PageImpl<>(ticketResponses);
	}

}
