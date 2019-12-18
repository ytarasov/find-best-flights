package com.projector.edu.findflight.graph;

import com.projector.edu.findflight.dto.FlightDto;
import com.projector.edu.findflight.model.IndexedFlight;
import com.projector.edu.findflight.model.builders.IndexedFlightBuilder;
import com.projector.edu.findflight.converters.FlightIndexConverter;
import com.projector.edu.persistence.domain.Spot;

import java.util.*;

public class FlightGraph {

	private static final int MINUTES_IN_DAY_NUMBER = 1440;

	private static final int MAX_NUMBER_OF_FLIGHTS = 2000;

	private IndexedFlightBuilder indexedFlightBuilder = new IndexedFlightBuilder();

	private FlightIndexConverter flightIndexConverter = new FlightIndexConverter();

	private Map<Short, Spot> airportIndexedByIdMap = new HashMap<>();

	private Map<String, Short> airportCodeIndexedByIdMap = new HashMap<String, Short>();

	private Map<Short, Map<Short, IndexedFlight[]>> flightMap;

	public FlightGraph(List<Spot> airports) {
		short airportId = 0;
		for (Spot airport : airports) {
			this.airportIndexedByIdMap.put(airportId, airport);
			this.airportCodeIndexedByIdMap.put(airport.getCode(), airportId);
			airportId++;
		}
		this.flightMap = new HashMap<>();
	}

	public Spot getAirportById(short airportId) {
		return airportIndexedByIdMap.get(airportId);
	}

	public short getAirportIdByIataCode(String iataCode) {
		return airportCodeIndexedByIdMap.get(iataCode);
	}

	public void addFlight(FlightDto flightDto) {
		IndexedFlight flight = createIndexedFlight(flightDto);

		short fromAirportId = flight.getFromAirportId();
		short departureDateIndex = (short) (flight.getDepartureDateTime() / MINUTES_IN_DAY_NUMBER);

		Map<Short, IndexedFlight[]> forAirportFlightsMap = flightMap.get(fromAirportId);
		if (forAirportFlightsMap == null) {
			forAirportFlightsMap = new HashMap<>();
			flightMap.put(fromAirportId, forAirportFlightsMap);
		}
		IndexedFlight[] forAirportForDateFlights = forAirportFlightsMap.get(departureDateIndex);
		if (forAirportForDateFlights == null) {
			forAirportForDateFlights = new IndexedFlight[MAX_NUMBER_OF_FLIGHTS];
			forAirportFlightsMap.put(departureDateIndex, forAirportForDateFlights);
		}
		for (int i = 0; i < forAirportForDateFlights.length; i++) {
			if (forAirportForDateFlights[i] == null) {
				forAirportForDateFlights[i] = flight;
				break;
			}
		}
	}

	//TODO: Refactor this !!!!
	public IndexedFlight[] getAvailableFlights(short flightAirportId, int flightFromDateTime, int flightToDateTime) {
		IndexedFlight[] availableFlights = new IndexedFlight[0];

		Map<Short, IndexedFlight[]> forAirportDayFlightsMap = flightMap.get(flightAirportId);
		if (forAirportDayFlightsMap == null) {
			return new IndexedFlight[0];
		}

		short flightFromDate = (short) (flightFromDateTime / MINUTES_IN_DAY_NUMBER);
		short flightFromTime = (short) (flightFromDateTime - flightFromDate * MINUTES_IN_DAY_NUMBER);

		short flightToDate = (short) (flightToDateTime / MINUTES_IN_DAY_NUMBER);
		short flightToTime = (short) (flightToDateTime - flightToDate * MINUTES_IN_DAY_NUMBER);

		if (flightFromDate == flightToDate) {
			IndexedFlight[] todayFlights = forAirportDayFlightsMap.get(flightFromDate);
			if (todayFlights == null) {
				return new IndexedFlight[0];
			}
			int firstIndex = -1;
			int lastIndex = -1;
			for (int i = 0; i < todayFlights.length; i++) {
				IndexedFlight todayFlight = todayFlights[i];
				if (todayFlight.getDepartureTime() >= flightFromTime && todayFlight.getDepartureTime() <= flightToTime) {
					if (firstIndex == -1) {
						firstIndex = i;
					}
					lastIndex = i;
				}
				if (todayFlight.getDepartureTime() > flightToTime) {
					break;
				}
			}
			if (firstIndex != -1) {
				int todayNumber = lastIndex - firstIndex + 1;
				availableFlights = new IndexedFlight[todayNumber];
				System.arraycopy(todayFlights, firstIndex, availableFlights, 0, todayNumber);
			}
		} else {
			IndexedFlight[] todayFlights = forAirportDayFlightsMap.get(flightFromDate);
			int firstIndex = -1;
			if (todayFlights != null) {
				for (int i = 0; i < todayFlights.length; i++) {
					IndexedFlight todayFlight = todayFlights[i];
					if (todayFlight.getDepartureTime() >= flightFromTime) {
						firstIndex = i;
						break;
					}
				}
			}

			IndexedFlight[] nextDayFlights = forAirportDayFlightsMap.get(flightToDate);
			int lastIndex = -1;
			if (nextDayFlights != null) {
				for (int i = 0; i < nextDayFlights.length; i++) {
					IndexedFlight nextDayFlight = nextDayFlights[i];
					if (nextDayFlight.getDepartureTime() <= flightToTime) {
						lastIndex = i;
					}
					if (nextDayFlight.getDepartureTime() > flightToTime) {
						break;
					}
				}
				int todayNumber = firstIndex != -1 ? todayFlights.length - firstIndex : 0;
				int nextDayNumber = lastIndex != -1 ? lastIndex + 1 : 0;
				int number = todayNumber + nextDayNumber;
				availableFlights = new IndexedFlight[number];

				if (firstIndex != -1) {
					System.arraycopy(todayFlights, firstIndex, availableFlights, 0, todayNumber);
				}
				if (lastIndex != -1) {
					System.arraycopy(nextDayFlights, 0, availableFlights, todayNumber, nextDayNumber);
				}
			}
		}
		return availableFlights;
	}

	//TODO: Refactor this !!!!
	public void sortFlightsByDepartureTime() {
		for(Map.Entry<Short, Map<Short, IndexedFlight[]>> el: flightMap.entrySet()){
			for( Map.Entry<Short, IndexedFlight[]> el2: el.getValue().entrySet()){
				IndexedFlight[] array = el2.getValue();
				for(int i=0; i<array.length; i++){
					if(array[i]==null){
						IndexedFlight[] cuttedArray = Arrays.copyOf(array, i);
						Arrays.sort(cuttedArray, new Comparator<IndexedFlight>() {
							public int compare(IndexedFlight sj1, IndexedFlight sj2) {
								int time1 = sj1.getDepartureTime();
								int time2 = sj2.getDepartureTime();
								return time1 - time2;
							}
						});
						el2.setValue(cuttedArray);
						break;
					}
				}
			}
		}
	}

	private IndexedFlight createIndexedFlight(FlightDto flightDto) {
		short fromAirportId = getAirportIdByIataCode(flightDto.getOriginSpot());
		short toAirportId = getAirportIdByIataCode(flightDto.getDestinationSpot());
		int fromDateTimeId = flightIndexConverter.convertLocalDateAndTimeToDateTimeIndex(flightDto.getDepartureDate(),
				flightDto.getDepartureTime());
		int toDateTimeId = flightIndexConverter.convertLocalDateAndTimeToDateTimeIndex(flightDto.getArrivalDate(),
				flightDto.getArrivalTime());
		short fromDateId = flightIndexConverter.convertLocalDateToDateIndex(flightDto.getDepartureDate());
		short fromTimeId = flightIndexConverter.convertLocalTimeToTimeIndex(flightDto.getDepartureTime());
		short durationId = flightIndexConverter.convertDurationToDurationIndex(flightDto.getDuration());
		int price = flightDto.getPrice();

		return indexedFlightBuilder.build(fromAirportId, toAirportId,
										  fromDateId, fromTimeId,
										  fromDateTimeId, toDateTimeId,
										  durationId, price);
	}

}
