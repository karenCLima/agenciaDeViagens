package com.spring.agenciaDeViagens.controller.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PassportNumberValidationError extends Exception{
	private String description;

}
