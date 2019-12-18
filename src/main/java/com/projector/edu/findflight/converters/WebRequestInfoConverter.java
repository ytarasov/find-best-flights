package com.projector.edu.findflight.converters;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.projector.edu.findflight.config.SearchFlightsConfiguration;
import com.projector.edu.findflight.utils.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WebRequestInfoConverter {

	@Autowired
	private DateTimeUtil dateTimeUtil;

	public SearchFlightsConfiguration convertRequestInfoToConfiguration(String originAirportValue,
																		String destinationAirportValue,
																		String dateFromValue, String timeFromValue,
																		String travelDurationHourValue) {
		String originAirport = extractCode(originAirportValue);
		String destinationAirport = extractCode(destinationAirportValue);
		LocalDateTime fromFlightDateTime = dateTimeUtil.getLocalDateTime(dateFromValue, timeFromValue);
		
		int maxRouteDurationHours = Integer.parseInt(travelDurationHourValue);
		
		SearchFlightsConfiguration requestSearchFlightsConfiguration =
				createSearchFlightsRequestConfiguration(originAirport, destinationAirport,
														fromFlightDateTime,
													    maxRouteDurationHours);
		
		return requestSearchFlightsConfiguration;
	}
	
	private String extractCode(String spotValue) {
		int lastIndex = spotValue.lastIndexOf(")");
		return spotValue.substring(lastIndex - 3, lastIndex);
	}
	
	private SearchFlightsConfiguration createSearchFlightsRequestConfiguration(String originAirport, String destinationAirport,
																			   LocalDateTime fromFlightDateTime,
																			   int maxRouteDurationHours) {
		SearchFlightsConfiguration requestSearchFlightsConfiguration = new SearchFlightsConfiguration();

		requestSearchFlightsConfiguration.setOriginAirport(originAirport);
		requestSearchFlightsConfiguration.setDestinationAirport(destinationAirport);
		requestSearchFlightsConfiguration.setFromJorneyDateTime(fromFlightDateTime);
		requestSearchFlightsConfiguration.setMaxFlightsDurationHours(maxRouteDurationHours);
		
		return requestSearchFlightsConfiguration;
	}

}
