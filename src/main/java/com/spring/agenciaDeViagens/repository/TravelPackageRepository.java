package com.spring.agenciaDeViagens.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.querydsl.core.types.Predicate;
import com.spring.agenciaDeViagens.model.TravelPackage;

public interface TravelPackageRepository extends JpaRepository<TravelPackage, Integer>, QuerydslPredicateExecutor<TravelPackage> {
	
	Page<TravelPackage> findAll(Predicate predicate, Pageable pageable);

}
