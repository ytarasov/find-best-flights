package com.projector.edu.findflight.dto;

public class SearchRequestDto {

	private String originCode;

	private String destinationCode;

	private String originDate;

	private String originTimeFrom;

	private String maxTravelDurationHours;

	public String getOriginCode() {
		return originCode;
	}

	public void setOriginCode(String originCode) {
		this.originCode = originCode;
	}

	public String getDestinationCode() {
		return destinationCode;
	}

	public void setDestinationCode(String destinationCode) {
		this.destinationCode = destinationCode;
	}

	public String getOriginDate() {
		return originDate;
	}

	public void setOriginDate(String originDate) {
		this.originDate = originDate;
	}

	public String getOriginTimeFrom() {
		return originTimeFrom;
	}

	public void setOriginTimeFrom(String originTimeFrom) {
		this.originTimeFrom = originTimeFrom;
	}

	public String getMaxTravelDurationHours() {
		return maxTravelDurationHours;
	}

	public void setMaxTravelDurationHours(String maxTravelDurationHours) {
		this.maxTravelDurationHours = maxTravelDurationHours;
	}
	
}
