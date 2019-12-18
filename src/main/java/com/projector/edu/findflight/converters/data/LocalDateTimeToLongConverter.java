package com.projector.edu.findflight.converters.data;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class LocalDateTimeToLongConverter implements Converter<LocalDateTime, Long> {

	@Override
	public Long convert(LocalDateTime localDateTime) {
		ZoneId zoneId = ZoneId.systemDefault();
		return localDateTime.atZone(zoneId).toEpochSecond();
	}

}
