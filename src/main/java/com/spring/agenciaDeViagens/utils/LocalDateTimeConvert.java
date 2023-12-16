package com.spring.agenciaDeViagens.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeConvert {
	
	public static LocalDateTime toDate(String dataString) {
       
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");

        LocalDateTime localDateTime = LocalDateTime.parse(dataString, formatter);

        return localDateTime;
    }
}
