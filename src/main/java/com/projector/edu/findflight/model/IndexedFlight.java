package com.projector.edu.findflight.model;

public class IndexedFlight {
	
	private short fromAirportId;
	
	private short toAirportId;
	
	private int price;
	
	private short departureDate;
	
	private short departureTime;
	
	private int departureDateTime;
	
	private int arrivalDateTime;	
	
	private short duration;
	
	public short getFromAirportId() {
		return fromAirportId;
	}

	public void setFromAirportId(short fromAirportId) {
		this.fromAirportId = fromAirportId;
	}

	public short getToAirportId() {
		return toAirportId;
	}

	public void setToAirportId(short toAirportId) {
		this.toAirportId = toAirportId;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}	

	public short getDuration() {
		return duration;
	}

	public void setDuration(short duration) {
		this.duration = duration;
	}

	public int getDepartureDateTime() {
		return departureDateTime;
	}

	public void setDepartureDateTime(int departureDateTime) {
		this.departureDateTime = departureDateTime;
	}

	public int getArrivalDateTime() {
		return arrivalDateTime;
	}

	public void setArrivalDateTime(int arrivalDateTime) {
		this.arrivalDateTime = arrivalDateTime;
	}

	public short getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(short departureDate) {
		this.departureDate = departureDate;
	}

	public short getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(short departureTime) {
		this.departureTime = departureTime;
	}
	
}
