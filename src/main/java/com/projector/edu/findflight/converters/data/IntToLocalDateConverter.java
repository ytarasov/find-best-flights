package com.projector.edu.findflight.converters.data;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;

public class IntToLocalDateConverter implements Converter<Integer,LocalDate>{

	@Override
	public LocalDate convert(Integer date) {
		return LocalDateTimeConverter.localDateFromInt(date);
	}

}
