package com.projector.edu.findflight.model.builders;

import com.projector.edu.findflight.model.FlightRoute;
import com.projector.edu.findflight.model.IndexedFlight;
import com.projector.edu.findflight.converters.FlightIndexConverter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class FlightRouteBuilder {
	
	private FlightIndexConverter flightIndexConverter = new FlightIndexConverter();

	public FlightRoute createFlightRoute(FlightRoute actualFlightRoute,
										 IndexedFlight flight,
										 int transferNumberMax) {

		int transferNumber = actualFlightRoute.getTransferNumber();
		int newPrice = flight.getPrice() + actualFlightRoute.getPrice();
		int newArrivalDateTime = flight.getArrivalDateTime();
		
		short newDurationMinutes = getDurationMinutes(actualFlightRoute, flight, transferNumber);
		int newTransferNumber = transferNumber + 1;
		int maxFlightNumber = transferNumberMax + 1;

		IndexedFlight[] segmentJorneys = new IndexedFlight[maxFlightNumber];
		short[] newTransferSpots = new short[maxFlightNumber];
		
		System.arraycopy(actualFlightRoute.getSegmentFlights(), 0, segmentJorneys, 0, maxFlightNumber);
		System.arraycopy(actualFlightRoute.getTransferAirports(), 0, newTransferSpots, 0, maxFlightNumber);
		
		segmentJorneys[newTransferNumber] = flight;
		newTransferSpots[newTransferNumber] = flight.getToAirportId();

		FlightRoute newFlightRoute = createFlightRoute(newPrice, newTransferNumber,
													   newArrivalDateTime,
													   newDurationMinutes,
													   segmentJorneys,
													   newTransferSpots);
		
		return newFlightRoute;
	}

	public FlightRoute createInitFlightRoute(LocalDateTime originDateTime, int maxFlightsNumber) {
		int originDateTimeIndex = flightIndexConverter.convertLocalDateTimeToDateTimeIndex(originDateTime);
		return createFlightRoute(0, -1, originDateTimeIndex, (short) 0,
				new IndexedFlight[maxFlightsNumber], new short[maxFlightsNumber]);
	}
	
	private FlightRoute createFlightRoute(int price, int transferNumber,
										  int newArrivalDateTime,
			   							  short durationMinutes,
										  IndexedFlight[] flights,
			   							  short[] transferAirports) {
		FlightRoute flightRoute = new FlightRoute();
		flightRoute.setPrice(price);
		flightRoute.setTransferNumber(transferNumber);
		flightRoute.setArrivalDateTime(newArrivalDateTime);
		flightRoute.setDuration(durationMinutes);
		flightRoute.setSegmentFlights(flights);
		flightRoute.setTransferAirports(transferAirports);
		return flightRoute;
	}

	private short getDurationMinutes(FlightRoute actualFlightRoute, IndexedFlight flight, int transferNumber) {
		short oldDurationMinutes = actualFlightRoute.getDuration();
		int waitingTimeBetweenFlights = 0;
		if (transferNumber != -1) {
			waitingTimeBetweenFlights = flight.getDepartureDateTime() - actualFlightRoute.getArrivalDateTime();
		}
		short flightDuration = flight.getDuration();
		short newDurationMinutes = (short)(oldDurationMinutes + waitingTimeBetweenFlights + flightDuration);
		return newDurationMinutes;
	}

}