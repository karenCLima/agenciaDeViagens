package com.spring.agenciaDeViagens.controller.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PasswordValidationError extends Exception {
    private String description;
}
