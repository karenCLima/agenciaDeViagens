package com.spring.agenciaDeViagens.service;


import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.spring.agenciaDeViagens.controller.dto.CustomerRequest;
import com.spring.agenciaDeViagens.controller.dto.CustomerResponse;
import com.spring.agenciaDeViagens.controller.dto.TicketResponse;
import com.spring.agenciaDeViagens.controller.exception.CpfNotFoundError;
import com.spring.agenciaDeViagens.controller.exception.PassportNumberValidationError;
import com.spring.agenciaDeViagens.controller.exception.PasswordValidationError;
import com.spring.agenciaDeViagens.model.Customer;
import com.spring.agenciaDeViagens.model.Ticket;
import com.spring.agenciaDeViagens.repository.CustomerRepository;
import com.spring.agenciaDeViagens.utils.CustomerConvert;

@ExtendWith(SpringExtension.class)
public class CustomerServiceUnitTest {
	
	@Mock
	CustomerRepository repository;
	
	@Mock
	PasswordEncoder passwordEncoder;
	
	@InjectMocks
	CustomerService customerService;
	
	private int page = 0; 
	private int size = 10; 
	private String direction = "ASC";
	PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(direction), "name");
	
	@BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
	
	@Test
	public void saveCostumer_should_ReturnCustomerResponse_when_ValidRequest() throws PasswordValidationError, PassportNumberValidationError {
		Mockito.when(passwordEncoder.encode(Mockito.anyString())).thenReturn("encondedPassword");
		
		CustomerRequest customer = new CustomerRequest();
		customer.setName("Name test");
		customer.setCpf("96592334002");
		customer.setEmail("test@gmail.com");
		customer.setPassportNumber("AZ123456");
		customer.setPassword("Password@1");
		
		Mockito.when(repository.save(Mockito.any())).thenReturn(CustomerConvert.toEntity(customer));
		CustomerResponse savedCustomerResponse = customerService.saveCustumer(customer);
		
		Assertions.assertNotNull(savedCustomerResponse);
		Assertions.assertEquals("Name test", savedCustomerResponse.getName());
		
	}
	
	@Test
	public void saveCustomer_should_ThrowsPasswordValidationError_when_InvalidPassword() throws PasswordValidationError, PassportNumberValidationError {
		Mockito.when(passwordEncoder.encode(Mockito.anyString())).thenReturn("encondedPassword");
		
		CustomerRequest customer = new CustomerRequest();
		customer.setName("Name test");
		customer.setCpf("96592334002");
		customer.setEmail("test@gmail.com");
		customer.setPassportNumber("AZ123456");
		customer.setPassword("InvalidPassword");
		
		Mockito.when(repository.save(Mockito.any())).thenReturn(CustomerConvert.toEntity(customer));
		
		Assertions.assertThrows(PasswordValidationError.class, ()-> customerService.saveCustumer(customer));
		
		
	}
	
	@Test
	public void saveCustomer_should_ThrowsPassportNumberValidationError_when_InvalidPassport() throws PasswordValidationError, PassportNumberValidationError {
		Mockito.when(passwordEncoder.encode(Mockito.anyString())).thenReturn("encondedPassword");
		
		CustomerRequest customer = new CustomerRequest();
		customer.setName("Name test");
		customer.setCpf("96592334002");
		customer.setEmail("test@gmail.com");
		customer.setPassportNumber("invalidPassportNumber");
		customer.setPassword("Password@1");
		
		Mockito.when(repository.save(Mockito.any())).thenReturn(CustomerConvert.toEntity(customer));
		
		Assertions.assertThrows(PassportNumberValidationError.class, ()-> customerService.saveCustumer(customer));
		
		
	}
	
	@Test
	public void getCustomerByCpf_should_ReturnCustomerResponse_when_IdExist() throws CpfNotFoundError {
		Customer customer = new Customer();
		customer.setId(1);
		customer.setName("Name test");
		customer.setCpf("96592334002");
		customer.setEmail("test@gmail.com");
		customer.setPassportNumber("AZ123456");
		customer.setPassword("Password@1");
		customer.setActivate(true);
		
		Mockito.when(repository.findByCpf(Mockito.any())).thenReturn(customer);
		
		CustomerResponse result = customerService.getCustomerByCpf("96592334002");
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals("test@gmail.com", result.getEmail());
		
	}
	
	@Test
	public void getCustomerByCpf_should_ThrowsCpfNotFoundError_when_IdNotExist() {
		Mockito.when(repository.findByCpf(Mockito.any())).thenReturn(null);
		
		Assertions.assertThrows(CpfNotFoundError.class, ()-> customerService.getCustomerByCpf("96592334002"));
	}
	
	@Test
	public void getCustomerByCpf_should_ThrowsCpfNotFoundError_when_ActivateIsFalse() {
		Customer customer = new Customer();
		customer.setId(1);
		customer.setName("Name test");
		customer.setCpf("96592334002");
		customer.setEmail("test@gmail.com");
		customer.setPassportNumber("AZ123456");
		customer.setPassword("Password@1");
		customer.setActivate(false);
		
		Mockito.when(repository.findByCpf(Mockito.any())).thenReturn(null);
		
		Assertions.assertThrows(CpfNotFoundError.class, ()-> customerService.getCustomerByCpf("96592334002"));
	}
	
	@Test
	public void updateCostumer_should_ReturnCustomerResponse_when_ValidRequest() throws PasswordValidationError, PassportNumberValidationError {
		Mockito.when(passwordEncoder.encode(Mockito.anyString())).thenReturn("encondedPassword");
		Customer originalcustomer = new Customer();
		originalcustomer.setId(1);
		originalcustomer.setName("Name test original");
		originalcustomer.setCpf("96592334002");
		originalcustomer.setEmail("testOriginal@gmail.com");
		originalcustomer.setPassportNumber("AZ123456");
		originalcustomer.setPassword("Password@1");
		
		CustomerRequest customer = new CustomerRequest();
		customer.setName("Name test");
		customer.setCpf("96592334002");
		customer.setEmail("test@gmail.com");
		customer.setPassportNumber("AZ123456");
		customer.setPassword("Password@1");
		
		Mockito.when(repository.save(Mockito.any())).thenReturn(CustomerConvert.toEntity(customer));
		CustomerResponse savedCustomerResponse = customerService.updateCustomer(customer,1);
		
		Assertions.assertNotNull(savedCustomerResponse);
		Assertions.assertNotEquals(originalcustomer.getName(), savedCustomerResponse.getName());
		Assertions.assertNotEquals(originalcustomer.getEmail(), savedCustomerResponse.getEmail());
	}
	
	@Test
	public void updateCustomer_should_ThrowsPasswordValidationError_when_InvalidPassword() throws PasswordValidationError, PassportNumberValidationError {
		Mockito.when(passwordEncoder.encode(Mockito.anyString())).thenReturn("encondedPassword");
		
		Customer originalcustomer = new Customer();
		originalcustomer.setId(1);
		originalcustomer.setName("Name test original");
		originalcustomer.setCpf("96592334002");
		originalcustomer.setEmail("testOriginal@gmail.com");
		originalcustomer.setPassportNumber("AZ123456");
		originalcustomer.setPassword("Password@1");
		
		CustomerRequest customer = new CustomerRequest();
		customer.setName("Name test");
		customer.setCpf("96592334002");
		customer.setEmail("test@gmail.com");
		customer.setPassportNumber("AZ123456");
		customer.setPassword("InvalidPassword");
		
		Mockito.when(repository.save(Mockito.any())).thenReturn(CustomerConvert.toEntity(customer));
		
		Assertions.assertThrows(PasswordValidationError.class, ()-> customerService.updateCustomer(customer,1));
		
		
	}
	
	@Test
	public void updateCustomer_should_ThrowsPassportNumberValidationError_when_InvalidPassport() throws PasswordValidationError, PassportNumberValidationError {
		Mockito.when(passwordEncoder.encode(Mockito.anyString())).thenReturn("encondedPassword");
		Customer originalcustomer = new Customer();
		originalcustomer.setId(1);
		originalcustomer.setName("Name test original");
		originalcustomer.setCpf("96592334002");
		originalcustomer.setEmail("testOriginal@gmail.com");
		originalcustomer.setPassportNumber("AZ123456");
		originalcustomer.setPassword("Password@1");
		
		CustomerRequest customer = new CustomerRequest();
		customer.setName("Name test");
		customer.setCpf("96592334002");
		customer.setEmail("test@gmail.com");
		customer.setPassportNumber("invalidPassportNumber");
		customer.setPassword("Password@1");
		
		Mockito.when(repository.save(Mockito.any())).thenReturn(CustomerConvert.toEntity(customer));
		
		Assertions.assertThrows(PassportNumberValidationError.class, ()-> customerService.updateCustomer(customer,1));
		
		
	}
	
	@Test
	public void getCostumerById_should_ReturnCustomerResponse_when_ValidRequest() {
		Mockito.when(passwordEncoder.encode(Mockito.anyString())).thenReturn("encondedPassword");
		Customer originalcustomer = new Customer();
		originalcustomer.setId(1);
		originalcustomer.setName("Name test original");
		originalcustomer.setCpf("96592334002");
		originalcustomer.setEmail("testOriginal@gmail.com");
		originalcustomer.setPassportNumber("AZ123456");
		originalcustomer.setPassword("Password@1");
		
		Mockito.when(repository.findById(Mockito.anyInt())).thenReturn(Optional.of(originalcustomer));
		
		CustomerResponse savedCustomerResponse = customerService.getCustomerById(1);
		
		Assertions.assertNotNull(savedCustomerResponse);
		Assertions.assertEquals(originalcustomer.getName(),savedCustomerResponse.getName() );
		Assertions.assertEquals(originalcustomer.getEmail(), savedCustomerResponse.getEmail());
		
	}
	
	@Test
	public void getAllCustomers_should_ReturnCustomerResponse_when_ValidRequest() {
		Mockito.when(passwordEncoder.encode(Mockito.anyString())).thenReturn("encondedPassword");
		Customer customer1 = new Customer();
		customer1.setId(1);
		customer1.setName("Name test original");
		customer1.setCpf("96592334002");
		customer1.setEmail("testOriginal@gmail.com");
		customer1.setPassportNumber("AZ123456");
		customer1.setPassword("Password@1");
		
		Customer customer2 = new Customer();
		customer2.setId(2);
		customer2.setName("Name test original");
		customer2.setCpf("18080050074");
		customer2.setEmail("test@gmail.com");
		customer2.setPassportNumber("AO123456");
		customer2.setPassword("Password@1");
		
		List<Customer> customers = List.of(customer1, customer2);
        Page <Customer> customersImpl = new PageImpl<>(customers);
        
        Mockito.when(repository.findAll(pageRequest)).thenReturn(customersImpl);
        
        Page<CustomerResponse> result = customerService.getAllCustomers(0, 10, "ASC");
        
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.getSize());
	}
	

}
