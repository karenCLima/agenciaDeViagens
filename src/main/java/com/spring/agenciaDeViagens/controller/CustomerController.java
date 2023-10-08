package com.spring.agenciaDeViagens.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

import com.spring.agenciaDeViagens.controller.dto.CustomerRequest;
import com.spring.agenciaDeViagens.controller.dto.CustomerResponse;
import com.spring.agenciaDeViagens.controller.exception.PassportNumberValidationError;
import com.spring.agenciaDeViagens.controller.exception.PasswordValidationError;
import com.spring.agenciaDeViagens.service.CustomerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/customer")
public class CustomerController {
	
	@Autowired
	CustomerService customerService;
	
	@GetMapping
	public ResponseEntity<Page<CustomerResponse>> getCustomers(
			@RequestParam(value = "page", required = false,defaultValue = "0") Integer page, 	
			@RequestParam(value = "size", required = false,defaultValue = "10") Integer size, 
			@RequestParam(value = "direction", required = false,defaultValue = "ASC") String direction){
		return ResponseEntity.ok(customerService.getAllCustomers(page, size, direction));
	}
	
	@PostMapping
	public ResponseEntity<CustomerResponse> saveCustomer( @Valid @RequestBody CustomerRequest customerRequest) throws PasswordValidationError, PassportNumberValidationError {
		CustomerResponse customerResponse = customerService.saveCustumer(customerRequest);
		return ResponseEntity.created(URI.create("/customer/" + customerResponse.getId())).body(customerResponse);
	}
	
	@GetMapping("/{id}")
	public CustomerResponse getCustomerById(@PathVariable Integer id) {
		return customerService.getCustomerById(id);
	}
	
	@GetMapping("/cpf/{cpf}")
	public CustomerResponse getCustomerByCpf(@PathVariable String cpf) {
		return customerService.getCustomerByCpf(cpf);
		
	}
	
	@DeleteMapping("/{id}")
	public void deleteCustomer(@PathVariable Integer id) {
		customerService.deleteCustomer(id);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<CustomerResponse> updateCustomer(@RequestBody CustomerRequest customerRequest, @PathVariable Integer id){
		return ResponseEntity.ok(customerService.updateCustomer(customerRequest, id));
	}

}
