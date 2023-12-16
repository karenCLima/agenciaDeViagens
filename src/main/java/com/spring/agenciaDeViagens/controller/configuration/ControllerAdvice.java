package com.spring.agenciaDeViagens.controller.configuration;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.spring.agenciaDeViagens.controller.exception.CpfNotFoundError;
import com.spring.agenciaDeViagens.controller.exception.NotValidParamError;
import com.spring.agenciaDeViagens.controller.exception.PassportNumberValidationError;
import com.spring.agenciaDeViagens.controller.exception.PasswordValidationError;
import com.spring.agenciaDeViagens.controller.exception.ValidationError;

@RestControllerAdvice
public class ControllerAdvice {
    @Autowired
    private MessageSource messageSource;

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ValidationError> handler(MethodArgumentNotValidException exception){
         List<ValidationError> errors = new ArrayList<>();
         List<FieldError> fieldErros = exception.getBindingResult().getFieldErrors();

         fieldErros.forEach( e -> {
             String message = messageSource.getMessage(e, LocaleContextHolder.getLocale());
             ValidationError validationError =  new ValidationError(e.getField(), message);
             errors.add(validationError);
         });

         return errors;
    }


    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PasswordValidationError.class)
    public String handlerPassword(PasswordValidationError exception){
        return exception.getDescription();
    }
    
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PassportNumberValidationError.class)
    public String handlerPassport(PassportNumberValidationError exception){
        return exception.getDescription();
    }
    
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(CpfNotFoundError.class)
    public String handlerCpf(CpfNotFoundError exception){
        return exception.getDescription();
    }
    
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public String handleNoSuchElementException(NoSuchElementException e) {
        return e.getMessage(); 
    }
    
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NotValidParamError.class)
    public String handleNotValidParam(NotValidParamError e) {
        return e.getDescription(); 
    }
}
