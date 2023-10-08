package com.spring.agenciaDeViagens.utils;

public class Validator {
	public static Boolean passwordValidate(String password){
        return password.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*\\W).{8,}$");
    }
	
	public static Boolean passportValidate(String passportNumber){
        return passportNumber.matches("^(?=.*[A-Z]).{2}(?=.*\\d).{6}$");
    }

}
