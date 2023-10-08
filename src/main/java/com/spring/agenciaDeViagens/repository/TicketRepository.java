package com.spring.agenciaDeViagens.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.querydsl.core.types.Predicate;
import com.spring.agenciaDeViagens.model.Customer;
import com.spring.agenciaDeViagens.model.Ticket;
import com.spring.agenciaDeViagens.model.TicketType;

public interface TicketRepository extends JpaRepository<Ticket, Integer>, QuerydslPredicateExecutor<Ticket> {
	
//	@Query(value = "SELECT * FROM TICKETS WHERE TicketType = :ticketType", nativeQuery = true)
//	Page<Ticket> findByTicketType(TicketType ticketType, Pageable pageable);
	
	Page<Ticket> findAll(Predicate predicate, Pageable pageable);
	
	@Query(value = "SELECT * FROM TICKETS WHERE CUSTOMER_ID = :customer_Id", nativeQuery = true)
	List<Ticket> findAllByCustomer(Integer customer_Id);

}
