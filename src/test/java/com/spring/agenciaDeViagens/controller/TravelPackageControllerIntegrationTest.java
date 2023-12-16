package com.spring.agenciaDeViagens.controller;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.spring.agenciaDeViagens.controller.dto.TravelPackageRequest;
import com.spring.agenciaDeViagens.controller.exception.NotValidParamError;
import com.spring.agenciaDeViagens.model.TravelPackage;
import com.spring.agenciaDeViagens.repository.TravelPackageRepository;
import com.spring.agenciaDeViagens.service.TravelPackageService;
import com.spring.agenciaDeViagens.utils.TravelPackageConvert;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class TravelPackageControllerIntegrationTest {

	@MockBean
	TravelPackageService service;
	
	@Autowired
	MockMvc mockMvc;
	
	@Test
	public void saveTravelPackage_should_ReturnBadRequest_when_CountryIsBlank() throws Exception {
		mockMvc.perform(
                MockMvcRequestBuilders.post("/travel-package")
                        .content("""
                                {
                                    "country":"",
									"place":"Rio de Janeiro",
									"travelDurationInDays": 4,
									"price":1200.00
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
	public void saveTravelPackage_should_ReturnBadRequest_when_CountryIsNull() throws Exception {
		mockMvc.perform(
                MockMvcRequestBuilders.post("/travel-package")
                        .content("""
                                {
									"place":"Rio de Janeiro",
									"travelDurationInDays": 4,
									"price":1200.00
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
	public void saveTravelPackage_should_ReturnBadRequest_when_PlaceIsBlank() throws Exception {
		mockMvc.perform(
                MockMvcRequestBuilders.post("/travel-package")
                        .content("""
                                {
                                    "country":"Brasil",
									"place":"",
									"travelDurationInDays": 4,
									"price":1200.00
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
	public void saveTravelPackage_should_ReturnBadRequest_when_PlaceIsNull() throws Exception {
		mockMvc.perform(
                MockMvcRequestBuilders.post("/travel-package")
                        .content("""
                                {
									"country":"Brasil",
									"travelDurationInDays": 4,
									"price":1200.00
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
	public void saveTravelPackage_should_ReturnBadRequest_when_PlaceAndCountryIsBlank() throws Exception {
		mockMvc.perform(
                MockMvcRequestBuilders.post("/travel-package")
                        .content("""
                                {
                                    "country":"",
									"place":"",
									"travelDurationInDays": 4,
									"price":1200.00
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
	public void saveTravelPackage_should_ReturnBadRequest_when_PlaceAndCountryIsNull() throws Exception {
		mockMvc.perform(
                MockMvcRequestBuilders.post("/travel-package")
                        .content("""
                                {
                                   
									"travelDurationInDays": 4,
									"price":1200.00
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
	public void saveTravelPackage_should_ReturnSucessuful_when_ValidRequest() throws Exception {
		TravelPackage travelPackage = new TravelPackage();
		travelPackage.setId(1);
		travelPackage.setCountry("Brasil");
		travelPackage.setPlace("Rio de Janeiro");
		travelPackage.setTravelDurationInDays(4);
		travelPackage.setPrice(BigDecimal.valueOf(1200.00));
		travelPackage.setActive(true);
		
		Mockito.when(service.saveTravelPackage(Mockito.any())).thenReturn(TravelPackageConvert.toResponse(travelPackage));
		
		mockMvc.perform(
                MockMvcRequestBuilders.post("/travel-package")
                        .content("""
                                {
                                    "country":"Brasil",
									"place":"Rio de Janeiro",
									"travelDurationInDays": 4,
									"price":1200.00
                                }
                                """)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(
                MockMvcResultHandlers.print()
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        ).andExpect(
        		MockMvcResultMatchers.jsonPath("$.id").exists()
        ).andExpect(
        		MockMvcResultMatchers.jsonPath("$.country").value("Brasil")
        );
	}
	
	@Test
	public void getByCountry_should_ReturnBadRequest_when_countryInRequestParamIsInvalid() throws Exception {
		TravelPackage travelPackage = new TravelPackage();
		travelPackage.setId(1);
		travelPackage.setCountry("Brasil");
		travelPackage.setPlace("Rio de Janeiro");
		travelPackage.setTravelDurationInDays(4);
		travelPackage.setPrice(BigDecimal.valueOf(1200.00));
		travelPackage.setActive(true);
		Pageable pageable = Pageable.unpaged();
		Page<TravelPackage> travelPage = new PageImpl<>(List.of(travelPackage));
		
		Mockito.when(service.getByCountry(Mockito.any(),Mockito.eq(pageable))).thenReturn(TravelPackageConvert.toResponsePage(travelPage));
		
		mockMvc.perform(
	    		MockMvcRequestBuilders.get("/travel-package/country?invalidParam=" + "Brasil")
	            	.contentType(MediaType.APPLICATION_JSON)
	            	.accept(MediaType.APPLICATION_JSON)
	            
	    ).andDo(
                MockMvcResultHandlers.print()
        ).andExpect(
        		MockMvcResultMatchers.status().isBadRequest()
        );
	
	}
	
	@Test
	public void getByCountry_should_ReturnStatusOk_when_ValidRequest() throws Exception {
		TravelPackage travelPackage = new TravelPackage();
		travelPackage.setId(1);
		travelPackage.setCountry("Brasil");
		travelPackage.setPlace("Rio de Janeiro");
		travelPackage.setTravelDurationInDays(4);
		travelPackage.setPrice(BigDecimal.valueOf(1200.00));
		travelPackage.setActive(true);
		Pageable pageable = Pageable.unpaged();
		Page<TravelPackage> travelPage = new PageImpl<>(List.of(travelPackage));
		
		Mockito.when(service.getByCountry(Mockito.any(),Mockito.eq(pageable))).thenReturn(TravelPackageConvert.toResponsePage(travelPage));
		
		mockMvc.perform(
	    		MockMvcRequestBuilders.get("/travel-package/country?country=" + "Brasil")
	            	.contentType(MediaType.APPLICATION_JSON)
	            	.accept(MediaType.APPLICATION_JSON)
	            
	    ).andDo(
                MockMvcResultHandlers.print()
        ).andExpect(
        		MockMvcResultMatchers.status().is2xxSuccessful()
        );
	
	}
	
	@Test
	public void saveTravelPackage_should_ReturnBadRequest_when_PriceIsNegative() throws Exception {
		mockMvc.perform(
                MockMvcRequestBuilders.post("/travel-package")
                        .content("""
                                {
                                    "country":"Brasil",
									"place":"Rio de Janeiro",
									"travelDurationInDays": 4,
									"price":-1200.00
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
	public void saveTravelPackage_should_ReturnBadRequest_when_PriceIsZero() throws Exception {
		mockMvc.perform(
                MockMvcRequestBuilders.post("/travel-package")
                        .content("""
                                {
                                    "country":"Brasil",
									"place":"Rio de Janeiro",
									"travelDurationInDays": 4,
									"price":0
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
	public void saveTravelPackage_should_ReturnBadRequest_when_TravelDurationIsZero() throws Exception {
		mockMvc.perform(
                MockMvcRequestBuilders.post("/travel-package")
                        .content("""
                                {
                                    "country":"Brasil",
									"place":"Rio de Janeiro",
									"travelDurationInDays": 0,
									"price":1000.00
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
	public void saveTravelPackage_should_ReturnBadRequest_when_TravelDurationIsNegative() throws Exception {
		mockMvc.perform(
                MockMvcRequestBuilders.post("/travel-package")
                        .content("""
                                {
                                    "country":"Brasil",
									"place":"Rio de Janeiro",
									"travelDurationInDays": -4,
									"price":1000.00
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
	public void updateTravelPackage_should_ReturnBadRequest_when_PriceIsNegative() throws Exception {
		TravelPackage travelPackage = new TravelPackage();
		travelPackage.setId(1);
		travelPackage.setCountry("Brasil");
		travelPackage.setPlace("Rio de Janeiro");
		travelPackage.setTravelDurationInDays(4);
		travelPackage.setPrice(BigDecimal.valueOf(1200.00));
		travelPackage.setActive(true);
		
		mockMvc.perform(
                MockMvcRequestBuilders.put("/travel-package/1")
                        .content("""
                                {
                                    "country":"Brasil",
									"place":"Rio de Janeiro",
									"travelDurationInDays": 4,
									"price":-1200.00
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
	public void updateTravelPackage_should_ReturnBadRequest_when_PriceIsZero() throws Exception {
		TravelPackage travelPackage = new TravelPackage();
		travelPackage.setId(1);
		travelPackage.setCountry("Brasil");
		travelPackage.setPlace("Rio de Janeiro");
		travelPackage.setTravelDurationInDays(4);
		travelPackage.setPrice(BigDecimal.valueOf(1200.00));
		travelPackage.setActive(true);
		
		mockMvc.perform(
                MockMvcRequestBuilders.put("/travel-package/1")
                        .content("""
                                {
                                    "country":"Brasil",
									"place":"Rio de Janeiro",
									"travelDurationInDays": 4,
									"price":0
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
	public void updateTravelPackage_should_ReturnBadRequest_when_TravelDurationIsZero() throws Exception {
		TravelPackage travelPackage = new TravelPackage();
		travelPackage.setId(1);
		travelPackage.setCountry("Brasil");
		travelPackage.setPlace("Rio de Janeiro");
		travelPackage.setTravelDurationInDays(4);
		travelPackage.setPrice(BigDecimal.valueOf(1200.00));
		travelPackage.setActive(true);
		
		mockMvc.perform(
                MockMvcRequestBuilders.put("/travel-package/1")
                        .content("""
                                {
                                    "country":"Brasil",
									"place":"Rio de Janeiro",
									"travelDurationInDays": 0,
									"price":1200.00
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
	public void updateTravelPackage_should_ReturnBadRequest_when_TravelDurationIsNegative() throws Exception {
		TravelPackage travelPackage = new TravelPackage();
		travelPackage.setId(1);
		travelPackage.setCountry("Brasil");
		travelPackage.setPlace("Rio de Janeiro");
		travelPackage.setTravelDurationInDays(4);
		travelPackage.setPrice(BigDecimal.valueOf(1200.00));
		travelPackage.setActive(true);
		
		mockMvc.perform(
                MockMvcRequestBuilders.put("/travel-package/1")
                        .content("""
                                {
                                    "country":"Brasil",
									"place":"Rio de Janeiro",
									"travelDurationInDays": -1,
									"price":1200.00
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
	public void updateTravelPackage_should_ReturnResponse_when_ArgumentIsValid() throws Exception {
		TravelPackage originaltravelPackage = new TravelPackage();
    	originaltravelPackage.setId(1);
    	originaltravelPackage.setCountry("Brasil");
    	originaltravelPackage.setPlace("Rio de Janeiro");
    	originaltravelPackage.setTravelDurationInDays(4);
    	originaltravelPackage.setPrice(BigDecimal.valueOf(1200.00));
    	originaltravelPackage.setActive(true);
    	
    	TravelPackageRequest travelPackage = new TravelPackageRequest();
        travelPackage.setCountry("Brasil");
        travelPackage.setPlace("Minas Gerais");
        travelPackage.setTravelDurationInDays(10);
        travelPackage.setPrice(BigDecimal.valueOf(3200.00));
        TravelPackage updatedTravelPackage = TravelPackageConvert.toEntity(travelPackage);
        updatedTravelPackage.setId(1);
		
		Mockito.when(service.updateTravelPackage(Mockito.any(), Mockito.eq(1))).thenReturn(TravelPackageConvert.toResponse(updatedTravelPackage));
		
		mockMvc.perform(
                MockMvcRequestBuilders.put("/travel-package/1")
                        .content("""
                                {
                                    "country":"Brasil",
									"place":"Minas Gerais",
									"travelDurationInDays": 10,
									"price":3200.00
                                }
                                """)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(
                MockMvcResultHandlers.print()
        ).andExpect(
                MockMvcResultMatchers.status().is2xxSuccessful()
        ).andExpect(
        		MockMvcResultMatchers.jsonPath("$.price").value(3200.00)
        ).andExpect(
        		MockMvcResultMatchers.jsonPath("$.place").value("Minas Gerais")
        );
	}
	
	@Test
	public void getTravelPackageById_should_ReturnSucessuful_when_ValidRequest() throws Exception {
		TravelPackage travelPackage = new TravelPackage();
		travelPackage.setId(1);
		travelPackage.setCountry("Brasil");
		travelPackage.setPlace("Rio de Janeiro");
		travelPackage.setTravelDurationInDays(4);
		travelPackage.setPrice(BigDecimal.valueOf(1200.00));
		travelPackage.setActive(true);
		
		Mockito.when(service.getById(Mockito.eq(1))).thenReturn(TravelPackageConvert.toResponse(travelPackage));
		
		mockMvc.perform(
                MockMvcRequestBuilders.get("/travel-package/{id}",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(
                MockMvcResultHandlers.print()
        ).andExpect(
                MockMvcResultMatchers.status().is2xxSuccessful()
        ).andExpect(
        		MockMvcResultMatchers.jsonPath("$.id").exists()
        ).andExpect(
        		MockMvcResultMatchers.jsonPath("$.country").value("Brasil")
        );
	}
	
}
