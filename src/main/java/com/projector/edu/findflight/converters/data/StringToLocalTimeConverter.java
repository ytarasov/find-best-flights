package com.projector.edu.findflight.converters.data;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class StringToLocalTimeConverter implements Converter<String,LocalTime>{

	@Override
	public LocalTime convert(String date) {
		return LocalTime.parse(date, DateTimeFormatter.ISO_LOCAL_TIME);
	}
	
	public static LocalTime getLocalTimeFromString(String time) {
		return LocalTime.parse(time, DateTimeFormatter.ISO_LOCAL_TIME);
	}

}
