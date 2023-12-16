package com.spring.agenciaDeViagens.controller;

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

import com.spring.agenciaDeViagens.controller.dto.CustomerRequest;
import com.spring.agenciaDeViagens.controller.exception.CpfNotFoundError;
import com.spring.agenciaDeViagens.model.Customer;
import com.spring.agenciaDeViagens.repository.CustomerRepository;
import com.spring.agenciaDeViagens.service.CustomerService;
import com.spring.agenciaDeViagens.utils.CustomerConvert;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class CustomerControllerIntegrationTest {
	
	@MockBean
	CustomerService customerService;
	
	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
	CustomerRepository repository;
	
	@Test
	public void post_should_ReturnBadRequest_when_NameIsBlank() throws Exception {
		mockMvc.perform(
                MockMvcRequestBuilders.post("/customer")
                        .content("""
                                {
                                    "name":"",
									"cpf": "18080050074",
									"email": "julia@teste.com",
									"passportNumber": "CS152369",
									"password": "Teste#123"
                                }
                                """)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(
                MockMvcResultHandlers.print()
        ).andExpect(//andExpect é um assert dessa forma de teste
                MockMvcResultMatchers.status().isBadRequest()
        );
	}
	
	@Test
	public void post_should_ReturnBadRequest_when_NameIsNull() throws Exception {
		mockMvc.perform(
                MockMvcRequestBuilders.post("/customer")
                        .content("""
                                {
                                    
									"cpf": "18080050074",
									"email": "julia@teste.com",
									"passportNumber": "CS152369",
									"password": "Teste#123"
                                }
                                """)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(
                MockMvcResultHandlers.print()
        ).andExpect(//andExpect é um assert dessa forma de teste
                MockMvcResultMatchers.status().isBadRequest()
        );
	}
	
	@Test
	public void post_should_ReturnBadRequest_when_NameIsShorterThan3Char() throws Exception {
		mockMvc.perform(
                MockMvcRequestBuilders.post("/customer")
                        .content("""
                                {
                                    "name":"Ju",
									"cpf": "18080050074",
									"email": "julia@teste.com",
									"passportNumber": "CS152369",
									"password": "Teste#123"
                                }
                                """)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(
                MockMvcResultHandlers.print()
        ).andExpect(//andExpect é um assert dessa forma de teste
                MockMvcResultMatchers.status().isBadRequest()
        );
	}
	
	@Test
	public void post_should_ReturnBadRequest_when_InvalidCpf() throws Exception {
		mockMvc.perform(
                MockMvcRequestBuilders.post("/customer")
                        .content("""
                                {
                                    "name":"Julia",
									"cpf": "180800500",
									"email": "julia@teste.com",
									"passportNumber": "CS152369",
									"password": "Teste#123"
                                }
                                """)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(
                MockMvcResultHandlers.print()
        ).andExpect(//andExpect é um assert dessa forma de teste
                MockMvcResultMatchers.status().isBadRequest()
        );
	}
	
	@Test
	public void post_should_ReturnBadRequest_when_InvalidEmail() throws Exception {
		mockMvc.perform(
                MockMvcRequestBuilders.post("/customer")
                        .content("""
                                {
                                    "name":"Julia",
									"cpf": "18080050074",
									"email": "julia_teste.com",
									"passportNumber": "CS152369",
									"password": "Teste#123"
                                }
                                """)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(
                MockMvcResultHandlers.print()
        ).andExpect(//andExpect é um assert dessa forma de teste
                MockMvcResultMatchers.status().isBadRequest()
        );
	}
	
	@Test
	public void post_should_ReturnOk_when_ValidRequest() throws Exception {
	
            Customer customer = new Customer();
            customer.setId(1);
            customer.setName("Julia");
    		customer.setCpf("18080050074");
    		customer.setEmail("julia@teste.com");
    		customer.setPassportNumber("CS152369");
    		customer.setPassword("Teste#123");
    		customer.setActivate(true);
          
        Mockito.when(customerService.saveCustumer(Mockito.any())).thenReturn(CustomerConvert.toResponse(customer));
		mockMvc.perform(
                MockMvcRequestBuilders.post("/customer")
                        .content("""
                                {
                                    "name":"Julia",
									"cpf": "18080050074",
									"email": "julia@teste.com",
									"passportNumber": "CS152369",
									"password": "Teste#123"
                                }
                                """)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(
                MockMvcResultHandlers.print()
        ).andExpect(//andExpect é um assert dessa forma de teste
                MockMvcResultMatchers.status().isCreated()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id")
                .exists()
        );
	}
	
	@Test
	public void getCustomerByCpf_should_ReturnCustomerResponse_when_CpfExist() throws Exception {
		Integer id = 1;
	    Customer customer = new Customer();
	    customer.setId(id);
	    customer.setName("Julia");
	    customer.setCpf("18080050074");
		customer.setEmail("julia@teste.com");
		customer.setPassportNumber("CS152369");
		customer.setPassword("Teste#123");
		customer.setActivate(true);

	    Mockito.when(customerService.getCustomerByCpf("18080050074")).thenReturn(CustomerConvert.toResponse(customer));

	    // Execução do teste
	    mockMvc.perform(
	    		MockMvcRequestBuilders.get("/customer/cpf/{cpf}", "18080050074")
	            	.contentType(MediaType.APPLICATION_JSON)
	            	.accept(MediaType.APPLICATION_JSON)
	            
	    ).andDo(
                MockMvcResultHandlers.print()
        ).andExpect(
        		MockMvcResultMatchers.status().is2xxSuccessful()
        ).andExpect(
        		MockMvcResultMatchers.jsonPath("$.id").exists()
        ).andExpect(
        		MockMvcResultMatchers.jsonPath("$.name").value("Julia")
        );
	}
	
	@Test
	public void getCustomerByCpf_should_ReturnNotFound_when_CpfNotExist() throws Exception {
		Mockito.when(customerService.getCustomerByCpf(Mockito.anyString()))
        .thenThrow(new CpfNotFoundError("CPF not found"));
		
		mockMvc.perform(
	    		MockMvcRequestBuilders.get("/customer/cpf/{cpf}", "32643701070")
	            	.contentType(MediaType.APPLICATION_JSON)
	            	.accept(MediaType.APPLICATION_JSON)
	            
	    ).andDo(
                MockMvcResultHandlers.print()
        ).andExpect(
        		MockMvcResultMatchers.status().isNotFound()
        ).andExpect(
        		MockMvcResultMatchers.content().string("CPF not found")
        );
	
	}
	
	@Test
	public void getCustomerById_should_ReturnCustomerResponse_when_IdExist() throws Exception {
		Integer id = 1;
	    Customer customer = new Customer();
	    customer.setId(id);
	    customer.setName("Julia");
	    customer.setCpf("18080050074");
		customer.setEmail("julia@teste.com");
		customer.setPassportNumber("CS152369");
		customer.setPassword("Teste#123");
		customer.setActivate(true);

	    Mockito.when(customerService.getCustomerById(id)).thenReturn(CustomerConvert.toResponse(customer));

	    // Execução do teste
	    mockMvc.perform(
	    		MockMvcRequestBuilders.get("/customer/{id}", 1)
	            	.contentType(MediaType.APPLICATION_JSON)
	            	.accept(MediaType.APPLICATION_JSON)
	            
	    ).andDo(
                MockMvcResultHandlers.print()
        ).andExpect(
        		MockMvcResultMatchers.status().is2xxSuccessful()
        ).andExpect(
        		MockMvcResultMatchers.jsonPath("$.id").exists()
        ).andExpect(
        		MockMvcResultMatchers.jsonPath("$.name").value("Julia")
        );
	}
	
	@Test
	public void updateCustomer_should_ReturnCustomerResponse_when_ValidRequest() throws Exception {
		Customer originalcustomer = new Customer();
		originalcustomer.setId(1);
		originalcustomer.setName("Name test original");
		originalcustomer.setCpf("96592334002");
		originalcustomer.setEmail("testOriginal@gmail.com");
		originalcustomer.setPassportNumber("AZ123456");
		originalcustomer.setPassword("Password@1");
		
		Customer customer = new Customer();
		customer.setName("Julia");
		customer.setCpf("18080050074");
		customer.setEmail("julia@gmail.com");
		customer.setPassportNumber("CS152369");
		customer.setPassword("Password@1");
        
        Mockito.when(customerService.updateCustomer(Mockito.any(), Mockito.eq(1))).thenReturn(CustomerConvert.toResponse(customer));
        
        mockMvc.perform(
                MockMvcRequestBuilders.put("/customer/{id}",1)
                        .content("""
                                {
                                    "name":"Julia",
									"cpf": "18080050074",
									"email": "julia@teste.com",
									"passportNumber": "CS152369",
									"password": "Password@1"
                                }
                                """)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(
                MockMvcResultHandlers.print()
        ).andExpect(//andExpect é um assert dessa forma de teste
                MockMvcResultMatchers.status().is2xxSuccessful()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name")
                .value("Julia")
        );
	}

}
