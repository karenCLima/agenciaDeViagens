package com.spring.agenciaDeViagens.controller.dto;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class CustomerRequest {
	
	@NotBlank()
	@Length(min = 3)
	private String name;
	
	@CPF
	private String cpf;
	
	@Email
	private String email;
	
	private String passportNumber;
	
	private String password;

}
