package com.spring.agenciaDeViagens.controller.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class ValidationError {
    private String field;
    private String message;
}
