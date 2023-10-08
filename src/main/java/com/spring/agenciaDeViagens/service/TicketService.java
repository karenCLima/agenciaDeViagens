package com.spring.agenciaDeViagens.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;
import com.spring.agenciaDeViagens.controller.dto.TicketRequest;
import com.spring.agenciaDeViagens.controller.dto.TicketResponse;
import com.spring.agenciaDeViagens.model.Customer;
import com.spring.agenciaDeViagens.model.Ticket;
import com.spring.agenciaDeViagens.model.TicketType;
import com.spring.agenciaDeViagens.model.TravelPackage;
import com.spring.agenciaDeViagens.repository.CustomerRepository;
import com.spring.agenciaDeViagens.repository.TicketRepository;
import com.spring.agenciaDeViagens.repository.TravelPackageRepository;
import com.spring.agenciaDeViagens.utils.TicketConvert;

@Service
public class TicketService {
	
	@Autowired
	TicketRepository ticketRepository;
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	TravelPackageRepository travelPackageRepository;
	
	public Page<TicketResponse> getAllTickets(int page, int size, String direction){
		PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(direction), "customer");
		Page<Ticket> tickets = ticketRepository.findAll(pageRequest);
		return TicketConvert.toResponsePage(tickets);
	}
	
	public TicketResponse saveTicket(TicketRequest ticketRequest) {
		Customer customer = customerRepository.findById(ticketRequest.getCustomer_Id()).get();
		TravelPackage travelPackage = travelPackageRepository.findById(ticketRequest.getTravelPackage_Id()).get();
		Ticket ticket = TicketConvert.toEntity(ticketRequest, customer, travelPackage);
		ticket.setActive(true);
		return TicketConvert.toResponse(ticketRepository.save(ticket));
	}
	
	public TicketResponse getTicketById(Integer id) {
		return TicketConvert.toResponse(ticketRepository.findById(id).get());
	}
	
	public Page<TicketResponse> getTicketsByTicketType(Predicate predicate, Pageable pageable){
		Page<Ticket> tickets =  ticketRepository.findAll(predicate, pageable);
		return TicketConvert.toResponsePage(tickets);
	}
	
	public List<TicketResponse> getTicketByCustomerId(Integer customer_Id){
		return TicketConvert.toResponseList(ticketRepository.findAllByCustomer(customer_Id));
	}
	
	public void deleteTicket(Integer id) {
		Ticket ticket = ticketRepository.findById(id).orElseThrow();
		ticket.setActive(false);
		ticketRepository.save(ticket);
	}
	
	
	public TicketResponse updateTicket(TicketRequest ticketRequest, Integer id) {
		Customer customer = customerRepository.findById(ticketRequest.getCustomer_Id()).get();
		TravelPackage travelPackage = travelPackageRepository.findById(ticketRequest.getTravelPackage_Id()).get();
		Ticket ticket = TicketConvert.toEntity(ticketRequest,customer, travelPackage);
		ticket.setId(id);
		ticket.setActive(true);
		return TicketConvert.toResponse(ticketRepository.save(ticket));
	}
	
	

}
