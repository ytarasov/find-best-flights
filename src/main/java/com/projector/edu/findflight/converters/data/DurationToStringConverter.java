package com.projector.edu.findflight.converters.data;

import org.springframework.core.convert.converter.Converter;

import java.time.Duration;

public class DurationToStringConverter implements Converter<Duration, String> {

	@Override
	public String convert(Duration arg0) {
		long min = arg0.toMinutes();
		return String.format("%d:%02d", min / 60, (min % 60));
	}

}
