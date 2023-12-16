package com.spring.agenciaDeViagens.repository;

import java.util.List;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import com.spring.agenciaDeViagens.model.Customer;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CustomerRepositoryIntegrationTest {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Test
    @Order(1)
	public void should_saveACustomer() {
		Customer customer = new Customer();
		customer.setId(1);
		customer.setName("Name test");
		customer.setCpf("96592334002");
		customer.setEmail("test@gmail.com");
		customer.setPassportNumber("AZ123456");
		customer.setPassword("Password@1");
		customer.setActivate(true);
		
		customerRepository.save(customer);
	}
	
	@Test
    @Order(2)
	public void should_findCustomerById() {
		Customer foundCustomer = customerRepository.findById(1).get();
		
		Assertions.assertNotNull(foundCustomer);
	}
	
	@Test
    @Order(3)
	public void should_findCustomerByCpf() {
		Customer foundCustomer = customerRepository.findByCpf("96592334002");
		
		Assertions.assertNotNull(foundCustomer);
	}
	
	@Test
    @Order(4)
	public void should_findCustomersByEmail() {
		UserDetails foundCustomer = customerRepository.findByEmail("test@gmail.com");
		Assertions.assertNotNull(foundCustomer);
	}
	
	@Test
    @Order(5)
	public void should_findAllCustomers() {
		List<Customer> foundCustomer = customerRepository.findAll();
		Assertions.assertNotNull(foundCustomer);
		Assertions.assertEquals(1, foundCustomer.size());
	}
	

}
