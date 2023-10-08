package com.spring.agenciaDeViagens.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.spring.agenciaDeViagens.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer>{
	
	Customer findByCpf(String cpf);
	
	UserDetails findByEmail(String email);

}
