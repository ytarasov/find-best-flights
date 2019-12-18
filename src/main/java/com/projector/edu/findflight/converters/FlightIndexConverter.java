package com.projector.edu.findflight.converters;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class FlightIndexConverter {
	
	public int convertLocalDateTimeToDateTimeIndex(LocalDateTime localDateTime) {		
		return convertLocalDateAndTimeToDateTimeIndex(localDateTime.toLocalDate(), localDateTime.toLocalTime());
	}

	public int convertLocalDateAndTimeToDateTimeIndex(LocalDate localDate, LocalTime localTime) {		
		int dayOfYear = localDate.getDayOfYear();
		int minuteOfDay = localTime.getHour() * 60 + localTime.getMinute();
		int resultDateIndex = dayOfYear * 1440 + minuteOfDay;
		return resultDateIndex;
	}
	
	public short convertLocalDateToDateIndex(LocalDate localDate) {		
		int dayOfYear = localDate.getDayOfYear();		
		return (short) dayOfYear;
	}
	
	public short convertLocalTimeToTimeIndex(LocalTime localTime) {		
		int minuteOfDay = localTime.getHour() * 60 + localTime.getMinute();		
		return (short) minuteOfDay;
	}
	
	public LocalDateTime convertDateTimeIndexToLocalDateTime(int dateTimeIndex) {
		int dayOfYear = dateTimeIndex / 1440 ;
		int minuteOfDay = dateTimeIndex - dayOfYear * 1440;
		LocalDate localDate = LocalDate.ofYearDay(2016, dayOfYear);
		LocalTime localTime = LocalTime.ofSecondOfDay(minuteOfDay * 60);
		return LocalDateTime.of(localDate, localTime);
	}

	public short convertDurationToDurationIndex(Duration duration) {		 
		return (short) duration.toMinutes();
	}
	
	public short convertDateTimeIndexToDateIndex(int dateTimeIndex) {
		int dayOfYear = dateTimeIndex / 1440 ;
		return (short) dayOfYear; 
	}
	
}
