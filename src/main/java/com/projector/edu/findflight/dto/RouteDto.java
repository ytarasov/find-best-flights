package com.projector.edu.findflight.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class RouteDto {

	private SpotDto originSpot;

	private SpotDto destinationSpot;

	private Integer interestFactor;

	private LocalDate departureDate;

	private LocalDate arrivalDate;

	private LocalTime departureTime;

	private LocalTime arrivalTime;

	private int durationMinutes;

	private int durationHours;
	
	private TransportTypeDto jorneyType;
	
	private String carrier;

	private List<String> transfers;

	private List<TransportTypeDto> segmentJorneyTypes;

	private List<RouteDto> segments;
	
	private List<String> carriers;

	public SpotDto getOriginSpot() {
		return originSpot;
	}

	public void setOriginSpot(SpotDto originSpot) {
		this.originSpot = originSpot;
	}

	public SpotDto getDestinationSpot() {
		return destinationSpot;
	}

	public void setDestinationSpot(SpotDto destinationSpot) {
		this.destinationSpot = destinationSpot;
	}

	public List<String> getTransfers() {
		return transfers;
	}

	public void setTransfers(List<String> transfers) {
		this.transfers = transfers;
	}

	public Integer getInterestFactor() {
		return interestFactor;
	}

	public void setInterestFactor(Integer interestFactor) {
		this.interestFactor = interestFactor;
	}

	public List<RouteDto> getSegments() {
		return segments;
	}

	public void setSegments(List<RouteDto> segments) {
		this.segments = segments;
	}

	public LocalDate getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(LocalDate departureDate) {
		this.departureDate = departureDate;
	}

	public LocalTime getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(LocalTime departureTime) {
		this.departureTime = departureTime;
	}

	public LocalTime getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(LocalTime arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public LocalDate getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(LocalDate arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public int getDurationMinutes() {
		return durationMinutes;
	}

	public void setDurationMinutes(int durationMinutes) {
		this.durationMinutes = durationMinutes;
	}

	public int getDurationHours() {
		return durationHours;
	}

	public void setDurationHours(int durationHours) {
		this.durationHours = durationHours;
	}

	public TransportTypeDto getJorneyType() {
		return jorneyType;
	}

	public void setJorneyType(TransportTypeDto сarrierType) {
		this.jorneyType = сarrierType;
	}

	public List<TransportTypeDto> getSegmentJorneyTypes() {
		return segmentJorneyTypes;
	}

	public void setSegmentJorneyTypes(List<TransportTypeDto> segmentJorneyTypes) {
		this.segmentJorneyTypes = segmentJorneyTypes;
	}

	public List<String> getCarriers() {
		return carriers;
	}

	public void setCarriers(List<String> carriers) {
		this.carriers = carriers;
	}

	public String getCarrier() {
		return carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

}
