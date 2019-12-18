package com.projector.edu.findflight.config;

import java.time.LocalDateTime;

import com.projector.edu.findflight.config.properties.FileProperty;
import com.projector.edu.findflight.config.properties.WebRequestProperty;
import org.springframework.stereotype.Component;

@Component
public class SearchFlightsConfiguration {
	
	@WebRequestProperty
	private String originAirport;
	
	@WebRequestProperty
	private String destinationAirport;
	
	@FileProperty(propertyName="searchedFlightsLimitNumber")
	private int searchedFlightsLimitNumber;
	
	@FileProperty(propertyName="transfer_number")
	private int transferNumber;
	
	@WebRequestProperty
	@FileProperty(propertyName="max_flights_duration_hours")
	private int maxFlightsDurationHours;
	
	@FileProperty(propertyName="flights_search_wait_new_transfer_hours")
	private int flightsSearchWaitNewTransferHours;
	
	@FileProperty(propertyName="flights_search_wait_after_arrival_minutes")
	private int flightsSearchWaitAfterArrivalMinutes;
	
	@WebRequestProperty
	private LocalDateTime fromJorneyDateTime;

	private LocalDateTime toJorneyDateTime;

	public int getTransferNumber() {
		return transferNumber;
	}

	public void setTransferNumber(int transferNumber) {
		this.transferNumber = transferNumber;
	}

	public LocalDateTime getFromJorneyDateTime() {
		return fromJorneyDateTime;
	}

	public void setFromJorneyDateTime(LocalDateTime fromJorneyDateTime) {
		this.fromJorneyDateTime = fromJorneyDateTime;
	}

	public LocalDateTime getToJorneyDateTime() {
		return toJorneyDateTime;
	}

	public void setToJorneyDateTime(LocalDateTime toJorneyDateTime) {
		this.toJorneyDateTime = toJorneyDateTime;
	}

	public int getSearchedFlightsLimitNumber() {
		return searchedFlightsLimitNumber;
	}

	public void setSearchedFlightsLimitNumber(int searchedFlightsLimitNumber) {
		this.searchedFlightsLimitNumber = searchedFlightsLimitNumber;
	}

	public int getMaxFlightsDurationHours() {
		return maxFlightsDurationHours;
	}

	public void setMaxFlightsDurationHours(int maxFlightsDurationHours) {
		this.maxFlightsDurationHours = maxFlightsDurationHours;
	}

	public int getFlightsSearchWaitNewTransferHours() {
		return flightsSearchWaitNewTransferHours;
	}

	public void setFlightsSearchWaitNewTransferHours(int flightsSearchWaitNewTransferHours) {
		this.flightsSearchWaitNewTransferHours = flightsSearchWaitNewTransferHours;
	}

	public int getFlightsSearchWaitAfterArrivalMinutes() {
		return flightsSearchWaitAfterArrivalMinutes;
	}

	public void setFlightsSearchWaitAfterArrivalMinutes(int flightsSearchWaitAfterArrivalMinutes) {
		this.flightsSearchWaitAfterArrivalMinutes = flightsSearchWaitAfterArrivalMinutes;
	}

	public String getOriginAirport() {
		return originAirport;
	}

	public void setOriginAirport(String originAirport) {
		this.originAirport = originAirport;
	}

	public String getDestinationAirport() {
		return destinationAirport;
	}

	public void setDestinationAirport(String destinationAirport) {
		this.destinationAirport = destinationAirport;
	}
}