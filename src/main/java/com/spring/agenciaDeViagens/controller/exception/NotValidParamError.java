package com.spring.agenciaDeViagens.controller.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter 
@AllArgsConstructor 
public class NotValidParamError extends Exception {
	private String description;
}
