package com.spring.agenciaDeViagens.utils;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;

import com.spring.agenciaDeViagens.controller.dto.TravelPackageRequest;
import com.spring.agenciaDeViagens.controller.dto.TravelPackageResponse;
import com.spring.agenciaDeViagens.model.TravelPackage;

public class TravelPackageConvert {
	
	public static TravelPackage toEntity(TravelPackageRequest travelPackageRequest) {
		TravelPackage travelPackage = new TravelPackage();
		travelPackage.setCountry(travelPackageRequest.getCountry());
		travelPackage.setPlace(travelPackageRequest.getPlace());
		travelPackage.setTravelDurationInDays(travelPackageRequest.getTravelDurationInDays());
		travelPackage.setPrice(travelPackageRequest.getPrice());
		return travelPackage;
	}
	
	public static TravelPackageResponse toResponse(TravelPackage travelPackage) {
		TravelPackageResponse travelPackageResponse = new TravelPackageResponse();
		travelPackageResponse.setId(travelPackage.getId());
		travelPackageResponse.setCountry(travelPackage.getCountry());
		travelPackageResponse.setPlace(travelPackage.getPlace());
		travelPackageResponse.setTravelDurationInDays(travelPackage.getTravelDurationInDays());
		travelPackageResponse.setPrice(travelPackage.getPrice());
		return travelPackageResponse;
	}
	
	public static List<TravelPackageResponse> toResponseList(List<TravelPackage> travelPackages){
		List<TravelPackageResponse> travelPackageResponses = new ArrayList<>();
		for (TravelPackage travelPackage : travelPackages) {
			TravelPackageResponse travelPackageResponse = TravelPackageConvert.toResponse(travelPackage);
			travelPackageResponses.add(travelPackageResponse);
		}
		return travelPackageResponses;
	}
	
	public static Page<TravelPackageResponse> toResponsePage(Page<TravelPackage> travelPackages){
		List<TravelPackageResponse> travelPackageResponses = new ArrayList<>();
		for (TravelPackage travelPackage : travelPackages) {
			TravelPackageResponse travelPackageResponse = TravelPackageConvert.toResponse(travelPackage);
			travelPackageResponses.add(travelPackageResponse);
		}
		return new PageImpl<>( travelPackageResponses);
	}
}
