package com.spring.agenciaDeViagens.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.spring.agenciaDeViagens.controller.dto.TicketRequest;
import com.spring.agenciaDeViagens.controller.dto.TicketResponse;
import com.spring.agenciaDeViagens.model.Customer;
import com.spring.agenciaDeViagens.model.Ticket;
import com.spring.agenciaDeViagens.model.TicketType;
import com.spring.agenciaDeViagens.model.TravelPackage;
import com.spring.agenciaDeViagens.repository.CustomerRepository;
import com.spring.agenciaDeViagens.repository.TicketRepository;
import com.spring.agenciaDeViagens.repository.TravelPackageRepository;
import com.spring.agenciaDeViagens.utils.LocalDateTimeConvert;
import com.spring.agenciaDeViagens.utils.TicketConvert;

@ExtendWith(SpringExtension.class)
public class TicketServiceUnitTest {
	
	@Mock
	TicketRepository ticketRepository;
	@Mock
	CustomerRepository customerRepository;
	@Mock
	TravelPackageRepository travelPackageRepository;
	
	@InjectMocks
	TicketService ticketService;
	
	private LocalDateTime bookedDate = LocalDateTimeConvert.toDate("2023-10-02T14:30:00.000");
	private LocalDateTime travelDate = LocalDateTimeConvert.toDate("2024-10-02T14:30:00.000");
	private TicketType ticketType = TicketType.ECONOMICA;
	private Long flightNumber = 1452642572565L;
	private int page = 0; 
	private int size = 10; 
	private String direction = "ASC";
	PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(direction), "customer");
	
	@Test
	public void updateTicket_should_ReturnUpdated_When_ValidRequest() {
		Customer customer = new Customer();
		customer.setId(1);
		TravelPackage travelPackage = new TravelPackage();
		travelPackage.setId(1);
		Ticket Ticket = new Ticket();
		Ticket.setId(1);
		Ticket.setCustomer(customer);
		Ticket.setTravelPackage(travelPackage);
		Ticket.setBookedDate(bookedDate);
		Ticket.setTravelDate(travelDate);
		Ticket.setTicketType(TicketType.PRIMEIRA_CLASSE);
		Ticket.setFlightNumber(flightNumber);
		Ticket.setActive(true);		
		Mockito.when(customerRepository.findById(Mockito.any())).thenReturn(Optional.of(customer));
		Mockito.when(travelPackageRepository.findById(Mockito.any())).thenReturn(Optional.of(travelPackage));
	
		TicketRequest ticketRequest = new TicketRequest();
		ticketRequest.setCustomer_Id(customer.getId());
		ticketRequest.setTravelPackage_Id(travelPackage.getId());
		ticketRequest.setBookedDate(bookedDate);
		ticketRequest.setTravelDate(travelDate);
		ticketRequest.setTicketType(ticketType);
		ticketRequest.setFlightNumber(flightNumber);
		Ticket updatedTicket = TicketConvert.toEntity(ticketRequest, customer, travelPackage);
		updatedTicket.setId(1);
		
		Mockito.when(ticketRepository.save(Mockito.any())).thenReturn(updatedTicket);
		TicketResponse result = ticketService.updateTicket(ticketRequest, 1);
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(1, result.getId());
		Assertions.assertNotEquals(Ticket.getTicketType(), result.getTicketType());
		
	}
	
	@Test
	public void updateTicket_should_ThrowsNoSuchElementException_When_CustomerNotExist() {
		Customer customer = new Customer();
		customer.setId(1);
		TravelPackage travelPackage = new TravelPackage();
		travelPackage.setId(1);
		Ticket Ticket = new Ticket();
		Ticket.setId(1);
		Ticket.setCustomer(customer);
		Ticket.setTravelPackage(travelPackage);
		Ticket.setActive(true);		
		Mockito.when(customerRepository.findById(Mockito.any())).thenReturn(Optional.empty());
		Mockito.when(travelPackageRepository.findById(Mockito.any())).thenReturn(Optional.of(travelPackage));
		
		TicketRequest ticketRequest = new TicketRequest();
		ticketRequest.setCustomer_Id(2);
		ticketRequest.setTravelPackage_Id(travelPackage.getId());
		Ticket updatedTicket = TicketConvert.toEntity(ticketRequest, customer, travelPackage);
		updatedTicket.setId(1);
		
		Mockito.when(ticketRepository.save(Mockito.any())).thenReturn(updatedTicket);
		
		Assertions.assertThrows(NoSuchElementException.class, () -> ticketService.updateTicket(ticketRequest, 1));
		Assertions.assertThrowsExactly(NoSuchElementException.class, () -> ticketService.updateTicket(ticketRequest, 1));
	}
	
	@Test
	public void updateTicket_should_ThrowsNoSuchElementException_When_TravelPackageNotExist() {
		Customer customer = new Customer();
		customer.setId(1);
		TravelPackage travelPackage = new TravelPackage();
		travelPackage.setId(1);
		Ticket Ticket = new Ticket();
		Ticket.setId(1);
		Ticket.setCustomer(customer);
		Ticket.setTravelPackage(travelPackage);
		Ticket.setActive(true);		
		Mockito.when(customerRepository.findById(Mockito.any())).thenReturn(Optional.of(customer));
		Mockito.when(travelPackageRepository.findById(Mockito.any())).thenReturn(Optional.empty());
		
		TicketRequest ticketRequest = new TicketRequest();
		ticketRequest.setCustomer_Id(customer.getId());
		ticketRequest.setTravelPackage_Id(2);
		Ticket updatedTicket = TicketConvert.toEntity(ticketRequest, customer, travelPackage);
		updatedTicket.setId(1);
		
		Mockito.when(ticketRepository.save(Mockito.any())).thenReturn(updatedTicket);
		
		Assertions.assertThrows(NoSuchElementException.class, () -> ticketService.updateTicket(ticketRequest, 1));
	}
	
	@Test
	public void saveTicket_should_ReturnResponse_When_ValidRequest() {
		Customer customer = new Customer();
		customer.setId(1);
		TravelPackage travelPackage = new TravelPackage();
		travelPackage.setId(1);
			
		Mockito.when(customerRepository.findById(Mockito.any())).thenReturn(Optional.of(customer));
		Mockito.when(travelPackageRepository.findById(Mockito.any())).thenReturn(Optional.of(travelPackage));
	
		TicketRequest ticketRequest = new TicketRequest();
		ticketRequest.setCustomer_Id(customer.getId());
		ticketRequest.setTravelPackage_Id(travelPackage.getId());
		ticketRequest.setBookedDate(bookedDate);
		ticketRequest.setTravelDate(travelDate);
		ticketRequest.setTicketType(ticketType);
		ticketRequest.setFlightNumber(flightNumber);
		Ticket convertedTicket = TicketConvert.toEntity(ticketRequest, customer, travelPackage);
		convertedTicket.setId(1);
		
		Mockito.when(ticketRepository.save(Mockito.any())).thenReturn(convertedTicket);
		TicketResponse result = ticketService.saveTicket(ticketRequest);
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(1, result.getId());
		Assertions.assertEquals(ticketRequest.getTicketType(), result.getTicketType());
		
	}
	
	@Test
	public void saveTicket_should_ThrowsNoSuchElementException_When_TravelPackageNotExist() {
		Customer customer = new Customer();
		customer.setId(1);
		TravelPackage travelPackage = new TravelPackage();
		travelPackage.setId(1);
		Ticket Ticket = new Ticket();
		Ticket.setId(1);
		Ticket.setCustomer(customer);
		Ticket.setTravelPackage(travelPackage);
		Ticket.setActive(true);		
		Mockito.when(customerRepository.findById(Mockito.any())).thenReturn(Optional.of(customer));
		Mockito.when(travelPackageRepository.findById(Mockito.any())).thenReturn(Optional.empty());
		
		TicketRequest ticketRequest = new TicketRequest();
		ticketRequest.setCustomer_Id(customer.getId());
		ticketRequest.setTravelPackage_Id(2);
		Ticket updatedTicket = TicketConvert.toEntity(ticketRequest, customer, travelPackage);
		updatedTicket.setId(1);
		
		Mockito.when(ticketRepository.save(Mockito.any())).thenReturn(updatedTicket);
		
		Assertions.assertThrows(NoSuchElementException.class, () -> ticketService.saveTicket(ticketRequest));
	}
	
	@Test
	public void saveTicket_should_ThrowsNoSuchElementException_When_CustomerNotExist() {
		Customer customer = new Customer();
		customer.setId(1);
		TravelPackage travelPackage = new TravelPackage();
		travelPackage.setId(1);
		Ticket Ticket = new Ticket();
		Ticket.setId(1);
		Ticket.setCustomer(customer);
		Ticket.setTravelPackage(travelPackage);
		Ticket.setActive(true);		
		Mockito.when(customerRepository.findById(Mockito.any())).thenReturn(Optional.empty());
		Mockito.when(travelPackageRepository.findById(Mockito.any())).thenReturn(Optional.of(travelPackage));
		
		TicketRequest ticketRequest = new TicketRequest();
		ticketRequest.setCustomer_Id(2);
		ticketRequest.setTravelPackage_Id(travelPackage.getId());
		Ticket updatedTicket = TicketConvert.toEntity(ticketRequest, customer, travelPackage);
		updatedTicket.setId(1);
		
		Mockito.when(ticketRepository.save(Mockito.any())).thenReturn(updatedTicket);
		
		Assertions.assertThrows(NoSuchElementException.class, () -> ticketService.saveTicket(ticketRequest));
	}
	
	@Test
    public void getAllTickets_should_ReturnExpectedPage_when_PackagesExist() {
        
		Customer customer = new Customer();
		customer.setId(1);
		TravelPackage travelPackage = new TravelPackage();
		travelPackage.setId(1);
		Customer customer2 = new Customer();
		customer.setId(2);
		TravelPackage travelPackage2 = new TravelPackage();
		travelPackage.setId(2);
		Ticket Ticket = new Ticket();
		Ticket.setId(1);
		Ticket.setCustomer(customer);
		Ticket.setTravelPackage(travelPackage);
		Ticket.setTicketType(TicketType.EXECUTIVA);
		Ticket.setActive(true);
		Ticket Ticket2 = new Ticket();
		Ticket2.setId(2);
		Ticket2.setCustomer(customer2);
		Ticket2.setTravelPackage(travelPackage2);
		Ticket2.setTicketType(TicketType.PRIMEIRA_CLASSE);
		Ticket2.setActive(true);
       
        List<Ticket> tickets = List.of(Ticket, Ticket2);
        Page <Ticket> tiImpl = new PageImpl<>(tickets);
        
        Mockito.when(ticketRepository.findAll(pageRequest)).thenReturn(tiImpl);
        
        Page<TicketResponse> result = ticketService.getAllTickets(0, 10, "ASC");
        
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.getSize());
 
    }
	
	@Test
	public void getTicketByID_should_ReturnResponse_When_ValidRequest() {
		Customer customer = new Customer();
		customer.setId(1);
		TravelPackage travelPackage = new TravelPackage();
		travelPackage.setId(1);
			
		Mockito.when(customerRepository.findById(Mockito.any())).thenReturn(Optional.of(customer));
		Mockito.when(travelPackageRepository.findById(Mockito.any())).thenReturn(Optional.of(travelPackage));
	
		TicketRequest ticketRequest = new TicketRequest();
		ticketRequest.setCustomer_Id(customer.getId());
		ticketRequest.setTravelPackage_Id(travelPackage.getId());
		ticketRequest.setBookedDate(bookedDate);
		ticketRequest.setTravelDate(travelDate);
		ticketRequest.setTicketType(ticketType);
		ticketRequest.setFlightNumber(flightNumber);
		Ticket convertedTicket = TicketConvert.toEntity(ticketRequest, customer, travelPackage);
		convertedTicket.setId(1);
		
		Mockito.when(ticketRepository.findById(Mockito.eq(1))).thenReturn(Optional.of(convertedTicket));
		TicketResponse result = ticketService.getTicketById(1);
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(1, result.getId());
		Assertions.assertEquals(ticketRequest.getTicketType(), result.getTicketType());
		
	}
	
	@Test
	public void getTicketByCpf_should_ReturnResponse_When_ValidRequest() {
		Customer customer = new Customer();
		customer.setId(1);
		TravelPackage travelPackage = new TravelPackage();
		travelPackage.setId(1);
			
		Mockito.when(customerRepository.findById(Mockito.any())).thenReturn(Optional.of(customer));
		Mockito.when(travelPackageRepository.findById(Mockito.any())).thenReturn(Optional.of(travelPackage));
	
		TicketRequest ticketRequest = new TicketRequest();
		ticketRequest.setCustomer_Id(customer.getId());
		ticketRequest.setTravelPackage_Id(travelPackage.getId());
		ticketRequest.setBookedDate(bookedDate);
		ticketRequest.setTravelDate(travelDate);
		ticketRequest.setTicketType(ticketType);
		ticketRequest.setFlightNumber(flightNumber);
		List<Ticket> convertedTicket = List.of(TicketConvert.toEntity(ticketRequest, customer, travelPackage));
		
		Mockito.when(ticketRepository.findAllByCustomer(Mockito.eq(1))).thenReturn(convertedTicket);
		List<TicketResponse> result = ticketService.getTicketByCustomerId(1);
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(1, result.size());
		
	}

}
