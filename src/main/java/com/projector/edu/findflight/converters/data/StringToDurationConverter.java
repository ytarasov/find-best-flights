package com.projector.edu.findflight.converters.data;

import org.springframework.core.convert.converter.Converter;

import java.time.Duration;

public class StringToDurationConverter implements Converter<String, Duration>{

	@Override
	public Duration convert(String arg0) {
		String timestampStr = arg0;
		String[] tokens = timestampStr.split(":");
		int hours = Integer.parseInt(tokens[0]);
		int minutes = Integer.parseInt(tokens[1]);
		int duration = 60 * hours + minutes;
		return Duration.ofMinutes(duration);
	}
	
	public static Duration getDurationFromString(String timestampStr) {		
		String[] tokens = timestampStr.split(":");
		int hours = Integer.parseInt(tokens[0]);
		int minutes = Integer.parseInt(tokens[1]);
		int duration = 60 * hours + minutes;
		return Duration.ofMinutes(duration);
	}

}
