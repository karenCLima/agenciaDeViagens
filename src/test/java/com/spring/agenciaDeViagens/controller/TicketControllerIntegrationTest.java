package com.spring.agenciaDeViagens.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.spring.agenciaDeViagens.model.Customer;
import com.spring.agenciaDeViagens.model.Ticket;
import com.spring.agenciaDeViagens.model.TicketType;
import com.spring.agenciaDeViagens.model.TravelPackage;
import com.spring.agenciaDeViagens.service.TicketService;
import com.spring.agenciaDeViagens.utils.LocalDateTimeConvert;
import com.spring.agenciaDeViagens.utils.TicketConvert;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class TicketControllerIntegrationTest {
	
	@MockBean
	TicketService service;
	
	@Autowired
	MockMvc mockMvc;
	
	private int page = 0; 
	private int size = 10; 
	private String direction = "ASC";
	PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(direction), "customer");
	
	@Test
	public void saveTicket_should_ReturnBadRequest_when_CustomerIdIsEmpty() throws Exception {
		mockMvc.perform(
                MockMvcRequestBuilders.post("/ticket")
                        .content("""
                                {
                                    "customer_Id":,
									"bookedDate":"2023-10-02T14:30:00.000",
									"travelDate":"2024-01-25T07:30:00.000",
									"ticketType":"PRIMEIRA_CLASSE",
									"flightNumber":525652521555522,
									"travelPackage_Id":1
                                }
                                """)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(
                MockMvcResultHandlers.print()
        ).andExpect(
                MockMvcResultMatchers.status().isBadRequest()
        );
	}
	
	@Test
	public void saveTicket_should_ReturnBadRequest_when_CustomerIdIsNull() throws Exception {
		mockMvc.perform(
                MockMvcRequestBuilders.post("/ticket")
                        .content("""
                                {
                                    
									"bookedDate":"2023-10-02T14:30:00.000",
									"travelDate":"2024-01-25T07:30:00.000",
									"ticketType":"PRIMEIRA_CLASSE",
									"flightNumber":525652521555522,
									"travelPackage_Id":1
                                }
                                """)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(
                MockMvcResultHandlers.print()
        ).andExpect(
                MockMvcResultMatchers.status().isBadRequest()
        );
	}
	
	@Test
	public void saveTicket_should_ReturnBadRequest_when_TravelPackageIdIsEmpty() throws Exception {
		mockMvc.perform(
                MockMvcRequestBuilders.post("/ticket")
                        .content("""
                                {
                                    "customer_Id":1,
									"bookedDate":"2023-10-02T14:30:00.000",
									"travelDate":"2024-01-25T07:30:00.000",
									"ticketType":"PRIMEIRA_CLASSE",
									"flightNumber":525652521555522,
									"travelPackage_Id":
                                }
                                """)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(
                MockMvcResultHandlers.print()
        ).andExpect(
                MockMvcResultMatchers.status().isBadRequest()
        );
	}
	
	@Test
	public void saveTicket_should_ReturnBadRequest_when_TravelPackageIdIsNull() throws Exception {
		mockMvc.perform(
                MockMvcRequestBuilders.post("/ticket")
                        .content("""
                                {
                                    "customer_Id":1,
									"bookedDate":"2023-10-02T14:30:00.000",
									"travelDate":"2024-01-25T07:30:00.000",
									"ticketType":"PRIMEIRA_CLASSE",
									"flightNumber":525652521555522
                                }
                                """)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(
                MockMvcResultHandlers.print()
        ).andExpect(
                MockMvcResultMatchers.status().isBadRequest()
        );
	}
	
	@Test
	public void saveTicket_should_ReturnSuccessuful_when_ValidRequest() throws Exception {
		Customer customer = new Customer();
		customer.setId(1);
		TravelPackage travelPackage = new TravelPackage();
		travelPackage.setId(1);
		Ticket Ticket = new Ticket();
		Ticket.setId(1);
		Ticket.setCustomer(customer);
		Ticket.setTravelPackage(travelPackage);
		Ticket.setBookedDate(LocalDateTimeConvert.toDate("2023-10-02T14:30:00.000"));
		Ticket.setTravelDate(LocalDateTimeConvert.toDate("2024-01-25T07:30:00.000"));
		Ticket.setTicketType(TicketType.PRIMEIRA_CLASSE);
		Ticket.setFlightNumber(525652521555522L);
		Ticket.setActive(true);	
		Mockito.when(service.saveTicket(Mockito.any())).thenReturn(TicketConvert.toResponse(Ticket));
		
		mockMvc.perform(
                MockMvcRequestBuilders.post("/ticket")
                        .content("""
                                {
                                    "customer_Id":1,
									"bookedDate":"2023-10-02T14:30:00.000",
									"travelDate":"2024-01-25T07:30:00.000",
									"ticketType":"PRIMEIRA_CLASSE",
									"flightNumber":525652521555522,
									"travelPackage_Id":1
                                }
                                """)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(
                MockMvcResultHandlers.print()
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id")
                .exists()
        ).andExpect(
        		MockMvcResultMatchers.jsonPath("$.flightNumber").value(525652521555522L)
        );
	}
	
	@Test
	public void getTicketById_should_ReturnSuccessuful_when_IdExist() throws Exception {
		Customer customer = new Customer();
		customer.setId(1);
		TravelPackage travelPackage = new TravelPackage();
		travelPackage.setId(1);
		Ticket Ticket = new Ticket();
		Ticket.setId(1);
		Ticket.setCustomer(customer);
		Ticket.setTravelPackage(travelPackage);
		Ticket.setBookedDate(LocalDateTimeConvert.toDate("2023-10-02T14:30:00.000"));
		Ticket.setTravelDate(LocalDateTimeConvert.toDate("2024-01-25T07:30:00.000"));
		Ticket.setTicketType(TicketType.PRIMEIRA_CLASSE);
		Ticket.setFlightNumber(525652521555522L);
		Ticket.setActive(true);	
		Mockito.when(service.getTicketById(Mockito.eq(1))).thenReturn(TicketConvert.toResponse(Ticket));
		
		mockMvc.perform(
                MockMvcRequestBuilders.get("/ticket/{id}",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(
                MockMvcResultHandlers.print()
        ).andExpect(
                MockMvcResultMatchers.status().is2xxSuccessful()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id")
                .exists()
        ).andExpect(
        		MockMvcResultMatchers.jsonPath("$.flightNumber").value(525652521555522L)
        );
	}
	
	@Test
	public void getTicketByCustomerId_should_ReturnSuccessuful_when_IdExist() throws Exception {
		Customer customer = new Customer();
		customer.setId(1);
		TravelPackage travelPackage = new TravelPackage();
		travelPackage.setId(1);
		Ticket Ticket = new Ticket();
		Ticket.setId(1);
		Ticket.setCustomer(customer);
		Ticket.setTravelPackage(travelPackage);
		Ticket.setBookedDate(LocalDateTimeConvert.toDate("2023-10-02T14:30:00.000"));
		Ticket.setTravelDate(LocalDateTimeConvert.toDate("2024-01-25T07:30:00.000"));
		Ticket.setTicketType(TicketType.PRIMEIRA_CLASSE);
		Ticket.setFlightNumber(525652521555522L);
		Ticket.setActive(true);	
		List<Ticket> tickets = List.of(Ticket);
		
		Mockito.when(service.getTicketByCustomerId(Mockito.eq(1))).thenReturn(TicketConvert.toResponseList(tickets));
		
		mockMvc.perform(
                MockMvcRequestBuilders.get("/ticket/customer/{customer_Id}",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(
                MockMvcResultHandlers.print()
        ).andExpect(
                MockMvcResultMatchers.status().is2xxSuccessful()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id")
                .exists()
        ).andExpect(
        		MockMvcResultMatchers.jsonPath("$[0].flightNumber").value(525652521555522L)
        );
	}
	
	@Test
	public void getTickets_should_ReturnSuccessuful_when_IdExist() throws Exception {
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
        
        Mockito.when(service.getAllTickets(page, size, direction)).thenReturn(TicketConvert.toResponsePage(tiImpl));
        
        mockMvc.perform(
	    		MockMvcRequestBuilders.get("/ticket")
	            	.contentType(MediaType.APPLICATION_JSON)
	            	.accept(MediaType.APPLICATION_JSON)
	            
	    ).andDo(
                MockMvcResultHandlers.print()
        ).andExpect(
        		MockMvcResultMatchers.status().is2xxSuccessful()
        );
	}

}
