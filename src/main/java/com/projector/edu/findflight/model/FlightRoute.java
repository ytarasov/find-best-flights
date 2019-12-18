package com.projector.edu.findflight.model;

public class FlightRoute {
	
	private int price;
	
	private int arrivalDateTime;
	
	private short duration;
	
	private int transferNumber;
	
	private short[] transferAirports;
	
	private IndexedFlight[] segmentFlights;
		
	public int getTransferNumber() {
		return transferNumber;
	}
	
	public void setTransferNumber(int transferNumber) {
		this.transferNumber = transferNumber;
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

	public short[] getTransferAirports() {
		return transferAirports;
	}

	public void setTransferAirports(short[] transferAirports) {
		this.transferAirports = transferAirports;
	}

	public int getArrivalDateTime() {
		return arrivalDateTime;
	}

	public void setArrivalDateTime(int arrivalDateTime) {
		this.arrivalDateTime = arrivalDateTime;
	}

	public IndexedFlight[] getSegmentFlights() {
		return segmentFlights;
	}

	public void setSegmentFlights(IndexedFlight[] segmentFlights) {
		this.segmentFlights = segmentFlights;
	}

}
