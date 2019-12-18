package com.projector.edu.findflight.graph;

import com.mongodb.Cursor;
import com.mongodb.DBObject;
import com.projector.edu.findflight.converters.data.LocalDateTimeConverter;
import com.projector.edu.findflight.converters.data.StringToDurationConverter;
import com.projector.edu.findflight.converters.data.StringToLocalTimeConverter;
import com.projector.edu.findflight.dto.FlightDto;
import com.projector.edu.findflight.service.MongoDatabaseService;
import com.projector.edu.persistence.domain.Jorney;
import com.projector.edu.persistence.domain.Spot;
import com.projector.edu.persistence.domain.TransportType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Component
public class FlightGraphBuilder {
	
	@Autowired
	private MongoDatabaseService mongoDatabaseService;

	public FlightGraph build(List<Integer> dates) {
		List<Spot> allAirports = mongoDatabaseService.getAllAirports();
		FlightGraph flightGraph = new FlightGraph(allAirports);

		Cursor allFlightsCursor = mongoDatabaseService.getAllFlightsCursor(dates);
		while (allFlightsCursor.hasNext()) {
			DBObject dbObject = allFlightsCursor.next();
			FlightDto flightDto = createFlightDto(dbObject);
			flightGraph.addFlight(flightDto);
		}

		flightGraph.sortFlightsByDepartureTime();

		return flightGraph;
	}

	private FlightDto createFlightDto(DBObject dbObject) {
		FlightDto flightDto = new FlightDto();
		String originSpot = (String) dbObject.get(Jorney.ORIGIN_SPOT);
		String destinationSpot = (String) dbObject.get(Jorney.DESTINATION_SPOT);
		LocalDate departureDate = LocalDateTimeConverter.localDateFromInt((Integer) dbObject.get(Jorney.DEPARTURE_DATE));
		LocalTime departureTime = StringToLocalTimeConverter.getLocalTimeFromString((String) dbObject.get(Jorney.DEPARTURE_TIME));
		LocalDate arrivalDate = LocalDateTimeConverter.localDateFromInt((Integer) dbObject.get(Jorney.ARRIVAL_DATE));
		LocalTime arrivalTime = StringToLocalTimeConverter.getLocalTimeFromString((String) dbObject.get(Jorney.ARRIVAL_TIME));
		int netFare = (int) dbObject.get(Jorney.NET_FARE);
		int markup = (int) dbObject.get(Jorney.MARKUP);
		Duration duration = StringToDurationConverter.getDurationFromString((String) dbObject.get(Jorney.DURATION));

		flightDto.setOriginSpot(originSpot);
		flightDto.setDestinationSpot(destinationSpot);
		flightDto.setDepartureDate(departureDate);
		flightDto.setDepartureTime(departureTime);
		flightDto.setArrivalDate(arrivalDate);
		flightDto.setArrivalTime(arrivalTime);
		flightDto.setDuration(duration);
		flightDto.setPrice(netFare + markup);

		return flightDto;
	}
	
}
