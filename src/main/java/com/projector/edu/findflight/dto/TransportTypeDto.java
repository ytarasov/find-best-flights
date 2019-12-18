package com.projector.edu.findflight.dto;

public enum TransportTypeDto {
	
	AIR("AIR", true), LOCAL("LOCAL", false), ALL("ALL", true);
	
	private String name;
	
	private boolean isDateTimeRelated;
	
	private TransportTypeDto(String name, boolean isDateTimeRelated)	{
		this.name = name;
		this.isDateTimeRelated = isDateTimeRelated;
	}

	public String getName() {
		return name;
	}
	
	public boolean isDateTimeRelated() {
		return isDateTimeRelated;
	}
	
}
