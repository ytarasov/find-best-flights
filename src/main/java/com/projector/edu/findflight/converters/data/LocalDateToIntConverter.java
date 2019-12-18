package com.projector.edu.findflight.converters.data;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;

public class LocalDateToIntConverter implements Converter<LocalDate, Integer>{

	@Override
	public Integer convert(LocalDate date) {
		return LocalDateTimeConverter.toInt(date);
	}

}
