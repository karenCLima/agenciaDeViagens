package com.spring.agenciaDeViagens.controller;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import com.querydsl.core.types.Predicate;
import com.spring.agenciaDeViagens.controller.dto.TravelPackageRequest;
import com.spring.agenciaDeViagens.controller.dto.TravelPackageResponse;
import com.spring.agenciaDeViagens.service.TravelPackageService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/travel-package")
public class TravelPackageController {
	
	@Autowired
	TravelPackageService travelPackageService;
	
	@GetMapping
	public Page<TravelPackageResponse> getTravelPackages(Pageable pageable){
		return travelPackageService.getAllTravelPackageResponses(pageable);
	}
	
	@PostMapping
	public ResponseEntity<TravelPackageResponse> saveTravelPackage(@Valid @RequestBody TravelPackageRequest travelPackageRequest) {
		TravelPackageResponse travelPackageResponse = travelPackageService.saveTravelPackage(travelPackageRequest);
		return ResponseEntity.created(URI.create("/travel-package/" + travelPackageResponse.getId())).body(travelPackageResponse);
	}
	
	@GetMapping("/{id}")
	public TravelPackageResponse getById(@PathVariable Integer id) {
		return travelPackageService.getById(id);
	}
	
	@GetMapping("/country")
	public Page<TravelPackageResponse> getByCountry(@RequestParam(value = "country") String country, Pageable pageable){
		return travelPackageService.getByCountry(country, pageable);
	}
	
	@GetMapping("/price")
	public ResponseEntity<List<TravelPackageResponse>> getByPrice(
			@RequestParam(value = "minValue", required = false, defaultValue = "0") BigDecimal minValue, 
			@RequestParam(value = "maxValue") BigDecimal maxValue ){
		return ResponseEntity.ok(travelPackageService.getByPrice(minValue, maxValue));
	}
	
	@DeleteMapping("/{id}")
	public void deleteTravelPackage(@PathVariable Integer id) {
		travelPackageService.deleteTravelPackage(id);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<TravelPackageResponse> updateTravelPackage(
			@RequestBody TravelPackageRequest travelPackageRequest,
			@PathVariable Integer id){
		return ResponseEntity.ok(travelPackageService.updateTravelPackage(travelPackageRequest, id));
	}
	
}
