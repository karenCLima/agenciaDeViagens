package com.spring.agenciaDeViagens.service;


import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import com.spring.agenciaDeViagens.model.TravelPackage;
import com.spring.agenciaDeViagens.repository.TravelPackageRepository;
import com.spring.agenciaDeViagens.utils.TravelPackageConvert;
import com.spring.agenciaDeViagens.controller.dto.TravelPackageRequest;
import com.spring.agenciaDeViagens.controller.dto.TravelPackageResponse;
import com.spring.agenciaDeViagens.controller.exception.NotValidParamError;

@ExtendWith(SpringExtension.class)
public class TravelPackageServiceUnitTest {
	
	@Mock
	TravelPackageRepository travelPackageRepository;
	
	@InjectMocks
	TravelPackageService service;
	
	

    @Test
    public void getAllTravel_should_ReturnExpectedPage_when_PackagesExist() {
        
    	TravelPackage travelPackage = new TravelPackage();
        travelPackage.setId(1);
        travelPackage.setCountry("Brasil");
        travelPackage.setPlace("Rio de Janeiro");
        travelPackage.setTravelDurationInDays(4);
        travelPackage.setPrice(BigDecimal.valueOf(1200.00));
        TravelPackage travelPackage2 = new TravelPackage();
        travelPackage2.setId(2);
        travelPackage2.setCountry("Brasil");
        travelPackage2.setPlace("Rio Branco");
        travelPackage2.setTravelDurationInDays(3);
        travelPackage2.setPrice(BigDecimal.valueOf(2200.00));
        Pageable pageable = Pageable.unpaged();
        List<TravelPackage> travelPackages = List.of(travelPackage, travelPackage2);
        Page <TravelPackage> traImpl = new PageImpl<>(travelPackages);
        
        Mockito.when(travelPackageRepository.findAll(pageable)).thenReturn(traImpl);
        
        Page<TravelPackageResponse> result = service.getAllTravelPackageResponses(pageable);
        
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.getSize());
 
    }
    
    @Test
    public void saveTravelPackage_should_ReturnResponse_when_ValidRequest() throws NotValidParamError {
    	TravelPackageRequest travelPackage = new TravelPackageRequest();
        travelPackage.setCountry("Brasil");
        travelPackage.setPlace("Rio de Janeiro");
        travelPackage.setTravelDurationInDays(4);
        travelPackage.setPrice(BigDecimal.valueOf(1200.00));
        TravelPackage travelPackage2 = TravelPackageConvert.toEntity(travelPackage);
        
        Mockito.when(travelPackageRepository.save(Mockito.any())).thenReturn(travelPackage2);
        
        TravelPackageResponse result = service.saveTravelPackage(travelPackage);
        
        Assertions.assertNotNull(result);
        Assertions.assertEquals(travelPackage.getCountry(), result.getCountry());
        
        
    }
    
    @Test
    public void saveTravelPackage_should_ThrowsNotValidParam_when_PriceIsLessThan100() throws NotValidParamError {
    	TravelPackageRequest travelPackage = new TravelPackageRequest();
        travelPackage.setCountry("Brasil");
        travelPackage.setPlace("Rio de Janeiro");
        travelPackage.setTravelDurationInDays(4);
        travelPackage.setPrice(BigDecimal.valueOf(99.00));
        
        Mockito.when(travelPackageRepository.save(Mockito.any())).thenReturn(null);
        
        Assertions.assertThrows(NotValidParamError.class, () -> service.saveTravelPackage(travelPackage));
    }
    
    
    
    @Test
    public void saveTravelPackage_should_ThrowsNotValidParam_when_TravelDurationLessThan2() throws NotValidParamError {
    	TravelPackageRequest travelPackage = new TravelPackageRequest();
        travelPackage.setCountry("Brasil");
        travelPackage.setPlace("Rio de Janeiro");
        travelPackage.setTravelDurationInDays(1);
        travelPackage.setPrice(BigDecimal.valueOf(1200.00));
        
        Mockito.when(travelPackageRepository.save(Mockito.any())).thenReturn(null);
        
        Assertions.assertThrows(NotValidParamError.class, () -> service.saveTravelPackage(travelPackage));
    }
    
    @Test
    public void updateTravelPackage_should_ReturnResponse_when_ValidRequest() throws NotValidParamError {
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
        
        Mockito.when(travelPackageRepository.save(Mockito.any())).thenReturn(updatedTravelPackage);
        
        TravelPackageResponse result = service.updateTravelPackage(travelPackage,1);
        
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.getId());
        Assertions.assertNotEquals(originaltravelPackage.getPlace(), result.getPlace());
        Assertions.assertNotEquals(originaltravelPackage.getPrice(), result.getPrice());
        
    }
    
    @Test
    public void updateTravelPackage_should_ThrowsNotValidParam_when_PriceIsLessThan100() throws NotValidParamError {
    	TravelPackage originaltravelPackage = new TravelPackage();
    	originaltravelPackage.setId(1);
    	originaltravelPackage.setCountry("Brasil");
    	originaltravelPackage.setPlace("Rio de Janeiro");
    	originaltravelPackage.setTravelDurationInDays(4);
    	originaltravelPackage.setPrice(BigDecimal.valueOf(1200.00));
    	originaltravelPackage.setActive(true);
    	
    	TravelPackageRequest travelPackage = new TravelPackageRequest();
        travelPackage.setCountry("Brasil");
        travelPackage.setCountry("Brasil");
        travelPackage.setPlace("Minas Gerais");
        travelPackage.setTravelDurationInDays(10);
        travelPackage.setPrice(BigDecimal.valueOf(99.00));
        
        Mockito.when(travelPackageRepository.save(Mockito.any())).thenReturn(null);
        
        Assertions.assertThrows(NotValidParamError.class, () -> service.updateTravelPackage(travelPackage, 1));
    }
    
    
    @Test
    public void updateTravelPackage_should_ThrowsNotValidParam_when_TravelDurationIsLessThan2() throws NotValidParamError {
    	TravelPackage originaltravelPackage = new TravelPackage();
    	originaltravelPackage.setId(1);
    	originaltravelPackage.setCountry("Brasil");
    	originaltravelPackage.setPlace("Rio de Janeiro");
    	originaltravelPackage.setTravelDurationInDays(1);
    	originaltravelPackage.setPrice(BigDecimal.valueOf(1200.00));
    	originaltravelPackage.setActive(true);
    	
    	TravelPackageRequest travelPackage = new TravelPackageRequest();
        travelPackage.setCountry("Brasil");
        travelPackage.setCountry("Brasil");
        travelPackage.setPlace("Minas Gerais");
        travelPackage.setTravelDurationInDays(0);
        travelPackage.setPrice(BigDecimal.valueOf(3200.00));
        
        Mockito.when(travelPackageRepository.save(Mockito.any())).thenReturn(null);
        
        Assertions.assertThrows(NotValidParamError.class, () -> service.updateTravelPackage(travelPackage, 1));
    }
    
    @Test
    public void getById_should_ReturnResponse_when_idExist() {
    	TravelPackage travelPackage = new TravelPackage();
        travelPackage.setId(1);
        travelPackage.setCountry("Brasil");
        travelPackage.setPlace("Rio de Janeiro");
        travelPackage.setTravelDurationInDays(4);
        travelPackage.setPrice(BigDecimal.valueOf(1200.00));
        
        Mockito.when(travelPackageRepository.findById(Mockito.any())).thenReturn(Optional.of(travelPackage));
        
        TravelPackageResponse result = service.getById(1);
        
        Assertions.assertNotNull(result);
        Assertions.assertEquals(travelPackage.getCountry(), result.getCountry());
    }
    
   
}
