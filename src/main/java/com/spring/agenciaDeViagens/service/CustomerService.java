package com.spring.agenciaDeViagens.service;


import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.agenciaDeViagens.controller.dto.CustomerRequest;
import com.spring.agenciaDeViagens.controller.dto.CustomerResponse;
import com.spring.agenciaDeViagens.controller.exception.CpfNotFoundError;
import com.spring.agenciaDeViagens.controller.exception.PassportNumberValidationError;
import com.spring.agenciaDeViagens.controller.exception.PasswordValidationError;
import com.spring.agenciaDeViagens.model.Customer;
import com.spring.agenciaDeViagens.repository.CustomerRepository;
import com.spring.agenciaDeViagens.utils.CustomerConvert;
import com.spring.agenciaDeViagens.utils.Validator;

@Service
public class CustomerService {
	
	
	CustomerRepository customerRepository;
	
	PasswordEncoder passwordEncoder;
	
	@Autowired
	public CustomerService(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
		super();
		this.customerRepository = customerRepository;
		this.passwordEncoder = passwordEncoder;
	}

	
	
	
	public Page<CustomerResponse> getAllCustomers(int page, int size, String direction){
		PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(direction), "name");
		Page<Customer>customers = customerRepository.findAll(pageRequest);
		return CustomerConvert.toResponsePage(customers);
	}
	
	public CustomerResponse saveCustumer(CustomerRequest customerRequest) throws PasswordValidationError, PassportNumberValidationError {
		Customer customer = CustomerConvert.toEntity(customerRequest);
		
		if(!Validator.passwordValidate(customer.getPassword())) {
			throw new PasswordValidationError("Senha deve seguir o padrao:\n -1 Letra  maíuscula,\n -1 letra minuscula,\n -1 numero,\n -1 caractere especial,\n -tamanho mínimo de 8 carcteres");
		}else if (!Validator.passportValidate(customer.getPassportNumber())) {
			throw new PassportNumberValidationError("Número de Passaporte inválido!");
		}
		
		String encodePassword = passwordEncoder.encode(customer.getPassword());
        customer.setPassword(encodePassword);
		
		customer.setActivate(true);
		
		Customer customerEntity = customerRepository.save(customer);
		return CustomerConvert.toResponse(customerEntity);
	}
	
	public CustomerResponse getCustomerById(Integer id) {
		return CustomerConvert.toResponse(customerRepository.findById(id).get());
	}
	
	public CustomerResponse getCustomerByCpf(String cpf) throws CpfNotFoundError {
		Customer foundCustomer = customerRepository.findByCpf(cpf);
		if(foundCustomer == null) throw new CpfNotFoundError("CPF not found");
		return CustomerConvert.toResponse(foundCustomer);
	}
	
	public void deleteCustomer(Integer id) {
		Customer customer = customerRepository.findById(id).get();
		customer.setActivate(false);
		customerRepository.save(customer);
	}
	
	public CustomerResponse updateCustomer(CustomerRequest customerRequest, Integer id) throws PasswordValidationError, PassportNumberValidationError {
		Customer customer = CustomerConvert.toEntity(customerRequest);
		
		if(!Validator.passwordValidate(customer.getPassword())) {
			throw new PasswordValidationError("Senha deve seguir o padrao:\n -1 Letra  maíuscula,\n -1 letra minuscula,\n -1 numero,\n -1 caractere especial,\n -tamanho mínimo de 8 carcteres");
		}else if (!Validator.passportValidate(customer.getPassportNumber())) {
			throw new PassportNumberValidationError("Número de Passaporte inválido!");
		}
		
		String encodePassword = passwordEncoder.encode(customer.getPassword());
        customer.setPassword(encodePassword);
		
		customer.setActivate(true);
		
		customer.setId(id);
		customer.setActivate(true);
		return CustomerConvert.toResponse(customerRepository.save(customer));
	}
}
