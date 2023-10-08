package com.spring.agenciaDeViagens.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.DialectOverride.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@Table(name = "tickets")
@org.hibernate.annotations.Where(clause = "active is true")
public class Ticket {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	private Customer customer;
	
	@Column(nullable = false)
	private LocalDateTime bookedDate;
	
	@Column(nullable = false)
	private LocalDateTime travelDate;
	
	@Column(nullable = false)
	private TicketType ticketType;
	
	@Column(nullable = false)
	private Long flightNumber;
	
	@ManyToOne
	private TravelPackage travelPackage;
	
	private Boolean active;
}
