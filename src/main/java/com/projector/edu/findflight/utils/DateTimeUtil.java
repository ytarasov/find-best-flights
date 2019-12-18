package com.projector.edu.findflight.utils;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.stereotype.Component;

@Component
public class DateTimeUtil {

	private DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
	
	public LocalDateTime getLocalDateTime(String dateValue, String timeValue) {
		LocalDate localDate = getLocalDate(dateValue);
		LocalTime localTime = getLocalTime(timeValue);
		LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
		return localDateTime;
	}

	public LocalDate getLocalDate(String dateValue) {
		LocalDate localDate = LocalDate.parse(dateValue, dateformatter);
		return localDate;
	}

	public LocalTime getLocalTime(String timeValue) {
		LocalTime localTime = LocalTime.parse(timeValue, timeFormatter);
		return localTime;
	}

	public static XMLGregorianCalendar getGregorianDate(LocalDate date) throws DatatypeConfigurationException {
		GregorianCalendar gc = (GregorianCalendar) GregorianCalendar.getInstance();
		gc.set(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth());
		return DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
	}
	
	public Duration getDurationFromHours(int parseInt) {		
		return Duration.ofHours(parseInt);
	}
	
	public List<LocalDate> getPeriodLocalDates(LocalDateTime fromLocalDateTime, LocalDateTime toLocalDateTime) {
		LocalDate fromLocalDate = fromLocalDateTime.toLocalDate();
		LocalDate toLocalDate = toLocalDateTime.toLocalDate();
		return getPeriodLocalDates(fromLocalDate, toLocalDate);
	}
	
	public List<LocalDate> getPeriodLocalDates(LocalDate fromLocalDate, LocalDate toLocalDate) {
		List<LocalDate> periodLocalDates = new LinkedList<LocalDate>();
		LocalDate tempLocalDate = LocalDate.from(fromLocalDate);
		while(!tempLocalDate.isAfter(toLocalDate)) {
			periodLocalDates.add(tempLocalDate);
			tempLocalDate = tempLocalDate.plusDays(1);			
		}
		return periodLocalDates;
	}	

}
