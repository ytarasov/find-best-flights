package com.projector.edu.findflight.converters.data;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class LocalDateTimeConverter {


	public static LocalDateTime localDateTimeFromLong(long dateTime) {
		int year = (int) (dateTime / 100000000.0);
		long monthStarting = dateTime - (year * 100000000L);
		int month = (int) (monthStarting / 1000000.0);
		long dayStarting = monthStarting - (month * 1000000L);
		int day = (int) (dayStarting / 10000.0);
		long hourStarting = dayStarting - (day * 10000L);
		int hour = (int) (hourStarting / 100L);
		int min = (int) (hourStarting - (hour * 100L));
		return LocalDateTime.of(year, month, day, hour, min);
	}

	public static long toLong(LocalDateTime dateTime) {
		long year = dateTime.getYear() * 100000000L;
		long month = dateTime.getMonthValue() * 1000000L;
		long day = dateTime.getDayOfMonth() * 10000L;
		long hour = dateTime.getHour() * 100L;
		long min = dateTime.getMinute();
		return (year + month + day + hour + min);

	}

	//20161205 = YYYmmDD
	public static LocalDate localDateFromInt(int date) {
		int year = (int) (date / 10000.0);
		int monthStarting = date - (year * 10000);
		int month = (int) (monthStarting / 100.0);
		int day = monthStarting - (month * 100);
		return LocalDate.of(year, month, day);
	}

	public static int toInt(LocalDate date) {
		int year = date.getYear() * 10000;
		int month = date.getMonthValue() * 100;
		int day = date.getDayOfMonth();
		return (year + month + day);
	}

}
