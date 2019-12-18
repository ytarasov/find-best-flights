package com.projector.edu.findflight.converters.data;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalTimeToStringConverter implements Converter<LocalTime, String>{

	@Override
	public String convert(LocalTime date) {
		return date.format(DateTimeFormatter.ISO_LOCAL_TIME);
	}

}
