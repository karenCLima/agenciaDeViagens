package com.spring.agenciaDeViagens.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.types.Predicate;
import com.spring.agenciaDeViagens.controller.dto.TicketRequest;
import com.spring.agenciaDeViagens.controller.dto.TicketResponse;
import com.spring.agenciaDeViagens.model.Ticket;
import com.spring.agenciaDeViagens.model.TicketType;
import com.spring.agenciaDeViagens.service.TicketService;

@RestController
@RequestMapping("/ticket")
public class TicketController {
	
	@Autowired
	TicketService ticketService;
	
	@RequestMapping
	public ResponseEntity<Page<TicketResponse>> getTickets(
			@RequestParam(value = "page", required = false,defaultValue = "0") Integer page, 	
			@RequestParam(value = "size", required = false,defaultValue = "10") Integer size, 
			@RequestParam(value = "direction", required = false,defaultValue = "ASC") String direction){
		return ResponseEntity.ok(ticketService.getAllTickets(page, size, direction));
	}
	
	@PostMapping
	public ResponseEntity<TicketResponse> saveTicket(@RequestBody TicketRequest ticketRequest) {
		TicketResponse ticketResponse =  ticketService.saveTicket(ticketRequest);
		return ResponseEntity.created(URI.create("/ticket/" + ticketResponse.getId())).body(ticketResponse);
	}
	
	@GetMapping("/{id}")
	public TicketResponse getById(@PathVariable Integer id) {
		return ticketService.getTicketById(id);
	}
	
	@GetMapping("/ticket_type")
	public ResponseEntity<Page<TicketResponse>> getByTicketType(
			@QuerydslPredicate(root = Ticket.class) Predicate predicate, 
			@PageableDefault(page = 0, size = 10) Pageable pageable){
		return ResponseEntity.ok(ticketService.getTicketsByTicketType(predicate, pageable));
	}
	
	@GetMapping("/customer/{customer_Id}")
	public List<TicketResponse> getByCustomerId(@PathVariable Integer customer_Id){
		return ticketService.getTicketByCustomerId(customer_Id);
	}
	
	@DeleteMapping("/{id}")
	public void deleteTicket(@PathVariable Integer id) {
		ticketService.deleteTicket(id);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<TicketResponse> updateTicket(@RequestBody TicketRequest ticketRequest, @PathVariable Integer id){
		return ResponseEntity.ok(ticketService.updateTicket(ticketRequest, id));
	}

}
