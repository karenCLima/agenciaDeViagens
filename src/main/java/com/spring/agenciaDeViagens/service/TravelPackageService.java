package com.spring.agenciaDeViagens.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.spring.agenciaDeViagens.controller.dto.TravelPackageRequest;
import com.spring.agenciaDeViagens.controller.dto.TravelPackageResponse;
import com.spring.agenciaDeViagens.model.QTravelPackage;
import com.spring.agenciaDeViagens.model.TravelPackage;
import com.spring.agenciaDeViagens.repository.TravelPackageRepository;
import com.spring.agenciaDeViagens.utils.TravelPackageConvert;

import jakarta.persistence.EntityManager;

@Service
public class TravelPackageService {
	
	@Autowired
	TravelPackageRepository travelPackageRepository;
	
	@Autowired
	EntityManager entityManager;
	
	public Page<TravelPackageResponse> getAllTravelPackageResponses(Pageable pageable){
		return TravelPackageConvert.toResponsePage(travelPackageRepository.findAll(pageable));
	}
	
	public TravelPackageResponse saveTravelPackage(TravelPackageRequest travelPackageRequest) {
		TravelPackage travelPackage = TravelPackageConvert.toEntity(travelPackageRequest);
		travelPackage.setActive(true);
		return TravelPackageConvert.toResponse(travelPackageRepository.save(travelPackage));
	}
	
	public TravelPackageResponse getById(Integer id) {
		return TravelPackageConvert.toResponse(travelPackageRepository.findById(id).get());
	}
	
	public Page<TravelPackageResponse> getByCountry(String country, Pageable pageable) {
		JPAQuery<TravelPackage> query = new JPAQuery<>(entityManager);
		QTravelPackage qTravelPackage = QTravelPackage.travelPackage;
		List<TravelPackage> travelPackage =query.from(qTravelPackage).where(qTravelPackage.country.eq(country)).fetch();
		return TravelPackageConvert.toResponsePage(new PageImpl<>(travelPackage));
	}
	
	public List<TravelPackageResponse>  getByPrice(BigDecimal minValue, BigDecimal maxValue){
		JPAQuery<TravelPackage> query = new JPAQuery<>(entityManager);
		QTravelPackage qTravelPackage = QTravelPackage.travelPackage;
		List<TravelPackage> travelPackages = query.from(qTravelPackage).where(qTravelPackage.price.between(minValue, maxValue)).fetch();
		return TravelPackageConvert.toResponseList(travelPackages);
	}
	
	public void deleteTravelPackage(Integer id) {
		TravelPackage travelPackage = travelPackageRepository.findById(id).orElseThrow();
		travelPackage.setActive(false);
		travelPackageRepository.save(travelPackage);
	}
	
	public TravelPackageResponse updateTravelPackage(TravelPackageRequest travelPackageRequest, Integer id) {
		TravelPackage travelPackage = TravelPackageConvert.toEntity(travelPackageRequest);
		travelPackage.setId(id);
		travelPackage.setActive(true);
		return TravelPackageConvert.toResponse(travelPackageRepository.save(travelPackage));
	}

}
