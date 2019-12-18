package com.projector.edu.findflight.model.builders;

import com.projector.edu.findflight.model.IndexedFlight;

public class IndexedFlightBuilder {
	
	public IndexedFlight build(short fromAirportId, short toAirportId,
                               int fromDateTimeId, int toDateTimeId,
                               short durationId, int price) {
		IndexedFlight flight = new IndexedFlight();
		flight.setFromAirportId(fromAirportId);
		flight.setToAirportId(toAirportId);
		flight.setDepartureDateTime(fromDateTimeId);
		flight.setArrivalDateTime(toDateTimeId);
		flight.setDuration(durationId);
		flight.setPrice(price);
		return flight;
	}
	
	public IndexedFlight build(short fromAirportId, short toAirportId,
							   short fromDateId, short fromTimeId,
			  				   int fromDateTimeId, int toDateTimeId,
			  				   short durationId, int price) {
		IndexedFlight indexedFlight = new IndexedFlight();
		indexedFlight.setFromAirportId(fromAirportId);
		indexedFlight.setToAirportId(toAirportId);
		indexedFlight.setDepartureDateTime(fromDateTimeId);
		indexedFlight.setArrivalDateTime(toDateTimeId);
		indexedFlight.setDuration(durationId);
		indexedFlight.setPrice(price);
		indexedFlight.setDepartureDate(fromDateId);
		indexedFlight.setDepartureTime(fromTimeId);
		return indexedFlight;
	}
	
}