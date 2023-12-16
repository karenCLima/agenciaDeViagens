package com.spring.agenciaDeViagens.repository;

import java.math.BigDecimal;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import com.spring.agenciaDeViagens.model.TravelPackage;

import jakarta.transaction.Transactional;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
@TestPropertySource(properties = "spring.datasource.url=jdbc:h2:mem:db;DB_CLOSE_ON_EXIT=FALSE")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TravelPackageRepositoryIntegrationTest {

	@Autowired
	private TravelPackageRepository travelPackageRepository;

	@Test
    @Order(1)
	public void should_saveATravelPackage() {
		TravelPackage travelPackage = new TravelPackage();
        travelPackage.setCountry("Brasil");
        travelPackage.setPlace("Rio de Janeiro");
        travelPackage.setTravelDurationInDays(4);
        travelPackage.setPrice(BigDecimal.valueOf(1200.00));
        
        travelPackageRepository.save(travelPackage);
	}
	
	@Test
    @Order(2)
	public void should_findTravelPackageById() {
		TravelPackage travelPackage = new TravelPackage();
		travelPackage.setId(1);
        travelPackage.setCountry("Brasil");
        travelPackage.setPlace("Rio de Janeiro");
        travelPackage.setTravelDurationInDays(4);
        travelPackage.setPrice(BigDecimal.valueOf(1200.00));
        
        TravelPackage travelPackageSaved = new TravelPackage();
        travelPackageSaved = travelPackageRepository.save(travelPackage);
        Integer id = travelPackageSaved.getId();
		
		TravelPackage found = travelPackageRepository.findById(id).get();
		Assertions.assertNotNull(found);
	}
	
	
	@Test
    @Order(3)
	public void should_findAllTravelPackagers() {
		Pageable pageable = Pageable.unpaged();
		TravelPackage travelPackage = new TravelPackage();
		travelPackage.setId(1);
        travelPackage.setCountry("Brasil");
        travelPackage.setPlace("Rio de Janeiro");
        travelPackage.setTravelDurationInDays(4);
        travelPackage.setPrice(BigDecimal.valueOf(1200.00));
        travelPackage.setActive(true);
        travelPackageRepository.save(travelPackage);
		
		Page<TravelPackage> found = travelPackageRepository.findAll(pageable);
		System.out.println(found.hasContent());
		Assertions.assertNotNull(found);
		Assertions.assertEquals(1, found.getNumberOfElements());
	}
}
