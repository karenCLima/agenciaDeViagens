package com.spring.agenciaDeViagens.model;

import java.math.BigDecimal;

import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@Table(name = "travel-packages")
@Where(clause ="active is true")
public class TravelPackage {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(nullable = false)
	private String country;
	@Column(nullable = false)
	private String place;
	@Column(nullable = false)
	private Integer travelDurationInDays;
	@Column(nullable = false)
	private BigDecimal price;
	
	private Boolean active;

}
