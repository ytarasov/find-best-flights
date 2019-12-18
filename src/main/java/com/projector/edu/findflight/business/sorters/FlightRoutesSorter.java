package com.projector.edu.findflight.business.sorters;

import com.projector.edu.findflight.model.FlightRoute;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FlightRoutesSorter {

	public List<FlightRoute> sortFlightRouteByPrice(List<FlightRoute> flightRoute) {
		return flightRoute.stream()
				.sorted((previousFlightRoute, nextFlightRoute) -> compareByPrice(previousFlightRoute, nextFlightRoute))
				.collect(Collectors.toList());		
	}
	
	private int compareByPrice(FlightRoute previousFlightRoute, FlightRoute nextFlightRoute) {
		int previousFlightRoutePrice = previousFlightRoute.getPrice();
		int nextFlightRoutePrice = nextFlightRoute.getPrice();
		if (previousFlightRoutePrice == nextFlightRoutePrice) {
			return 0;
		}
		if (previousFlightRoutePrice < nextFlightRoutePrice) {
			return -1;
		} else {
			return 1;
		}	
	}
	
}