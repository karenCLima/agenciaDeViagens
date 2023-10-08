package com.spring.agenciaDeViagens.utils;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;

import com.spring.agenciaDeViagens.controller.dto.CustomerRequest;
import com.spring.agenciaDeViagens.controller.dto.CustomerResponse;
import com.spring.agenciaDeViagens.model.Customer;

public class CustomerConvert {
	
	public static Customer toEntity(CustomerRequest customerRequest) {
		Customer customer = new Customer();	
		customer.setName(customerRequest.getName());
		customer.setEmail(customerRequest.getEmail());
		customer.setCpf(customerRequest.getCpf());
		customer.setPassportNumber(customerRequest.getPassportNumber());
		customer.setPassword(customerRequest.getPassword());
		return customer;
	}
	
	public static CustomerResponse toResponse(Customer customer) {
		CustomerResponse customerResponse = new CustomerResponse();
		customerResponse.setId(customer.getId());
		customerResponse.setName(customer.getName());
		customerResponse.setEmail(customer.getEmail());
		return customerResponse;
	}
	
	public static List<CustomerResponse> toResponseList(List<Customer> customers) {
		List<CustomerResponse> customerResponses = new ArrayList<>();
		for(Customer customer : customers) {
			CustomerResponse customerResponse = CustomerConvert.toResponse(customer);
			customerResponses.add(customerResponse);
		}
		return customerResponses;
		
	}
	
	public static Page<CustomerResponse> toResponsePage(Page<Customer> customers) {
		List<CustomerResponse> customerResponses = new ArrayList<>();
		for(Customer customer : customers) {
			CustomerResponse customerResponse = CustomerConvert.toResponse(customer);
			customerResponses.add(customerResponse);
		}
		return new PageImpl<>(customerResponses);
		
	}

}
