package com.spring.agenciaDeViagens.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.spring.agenciaDeViagens.model.Customer;
import com.spring.agenciaDeViagens.model.Ticket;
import com.spring.agenciaDeViagens.model.TicketType;
import com.spring.agenciaDeViagens.model.TravelPackage;
import com.spring.agenciaDeViagens.utils.LocalDateTimeConvert;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TicketRepositoryIntegrationTest {
	
	@Autowired
	TicketRepository ticketRepository;
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	TravelPackageRepository travelPackageRepository;
	
	private LocalDateTime bookedDate = LocalDateTimeConvert.toDate("2023-10-02T14:30:00.000");
	private LocalDateTime travelDate = LocalDateTimeConvert.toDate("2024-10-02T14:30:00.000");
	private TicketType ticketType = TicketType.ECONOMICA;
	private Long flightNumber = 1452642572565L;
	private int page = 0; 
	private int size = 10; 
	private String direction = "ASC";
	PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(direction), "customer");
	
	@Test
    @Order(1)
	public void should_saveATicket() {
		Customer customer = new Customer();
		customer.setId(1);
		customer.setName("Name test");
		customer.setCpf("96592334002");
		customer.setEmail("test@gmail.com");
		customer.setPassportNumber("AZ123456");
		customer.setPassword("Password@1");
		customer.setActivate(true);
		customerRepository.save(customer);
		TravelPackage travelPackage = new TravelPackage();
		travelPackage.setId(1);
		travelPackage.setCountry("Brasil");
        travelPackage.setPlace("Rio de Janeiro");
        travelPackage.setTravelDurationInDays(4);
        travelPackage.setPrice(BigDecimal.valueOf(1200.00));
        travelPackage.setActive(true);
		travelPackageRepository.save(travelPackage);
		Ticket Ticket = new Ticket();
		Ticket.setId(1);
		Ticket.setCustomer(customer);
		Ticket.setTravelPackage(travelPackage);
		Ticket.setBookedDate(bookedDate);
		Ticket.setTravelDate(travelDate);
		Ticket.setTicketType(TicketType.PRIMEIRA_CLASSE);
		Ticket.setFlightNumber(flightNumber);
		Ticket.setActive(true);		
		ticketRepository.save(Ticket);
	}
	
	@Test
    @Order(2)
	public void should_findAllTicketsByCustomer() {
		List<Ticket> foundList = ticketRepository.findAllByCustomer(1);
		Assertions.assertNotNull(foundList);
		Assertions.assertEquals(1, foundList.size());
	}
	
	@Test
    @Order(3)
	public void should_findAllTickets() {
		Page<Ticket> foundList = ticketRepository.findAll(pageRequest);
		Assertions.assertNotNull(foundList);
		Assertions.assertEquals(1, foundList.getNumberOfElements());
	}
	
	@Test
    @Order(3)
	public void should_findTicketById() {
		Ticket found = ticketRepository.findById(1).get();
		Assertions.assertNotNull(found);
		Assertions.assertEquals(1, found.getId());
	}

}
