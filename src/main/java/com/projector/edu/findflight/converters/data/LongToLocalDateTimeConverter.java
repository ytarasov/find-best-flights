package com.projector.edu.findflight.converters.data;

import org.springframework.core.convert.converter.Converter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

public class LongToLocalDateTimeConverter implements Converter<Long, LocalDateTime> {
	
	@Override
	public LocalDateTime convert(Long timestamp) {
		return LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), TimeZone.getDefault().toZoneId());
	}

}
