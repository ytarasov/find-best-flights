package com.projector.edu.findflight.business.searchers;

import com.projector.edu.findflight.graph.*;
import com.projector.edu.findflight.model.FlightOptions;
import com.projector.edu.findflight.model.FlightRoute;
import com.projector.edu.findflight.model.IndexedFlight;
import com.projector.edu.findflight.model.builders.FlightRouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Component
public class DfsFlightRoutesSearcherOptimized {
	
	@Autowired
	private FlightRouteBuilder flightRouteBuilder;

	public List<FlightRoute> getBestFlightRoutes(FlightGraph flightGraph, FlightOptions flightOptions) {
		List<FlightRoute> bestBestFlightRoutes = new LinkedList<>();

		short originAirportId = flightGraph.getAirportIdByIataCode(flightOptions.getOriginSpot());
		LocalDateTime originDateTime = flightOptions.getOriginDateTime();
		int maxFlightsNumber = flightOptions.getSearchedFlightsLimitNumber();
		FlightRoute initFlightRoute = flightRouteBuilder.createInitFlightRoute(originDateTime, maxFlightsNumber + 1);

		searchFlights(flightGraph, flightOptions,
					  initFlightRoute,
				      bestBestFlightRoutes,
					  originAirportId);
		
		return bestBestFlightRoutes;
	}

	private void searchFlights(FlightGraph flightGraph, FlightOptions flightOptions,
							   FlightRoute actualFlightRoute,
							   List<FlightRoute> bestBestFlightRoutes,
							   short fromAirportId) {
		int actualTransferNumber = actualFlightRoute.getTransferNumber();
		int waitTransferMinutes = actualTransferNumber != -1 ? flightOptions.getMaxWaitForNewFlightTimeInMinutes()
				: flightOptions.getRemainOriginDateTimeInMinutes();
		int waitAfterArrivalMinutes = actualTransferNumber != -1 ? flightOptions.getMinNeededTimeForTransferInMinutes()
				: 0;
		
		int flightFromDateTime = actualFlightRoute.getArrivalDateTime();
		int flightToDateTime = flightFromDateTime + waitTransferMinutes;

		IndexedFlight[] availableFlights = flightGraph.getAvailableFlights(fromAirportId,
				flightFromDateTime + waitAfterArrivalMinutes, flightToDateTime);
		
		searchNextSpotFlights(flightGraph, flightOptions,
							  actualFlightRoute,
							  bestBestFlightRoutes,
				              availableFlights);
	}
	
	private void searchNextSpotFlights(FlightGraph flightGraph, FlightOptions flightOptions,
									   FlightRoute actualFlightRoute,
								       List<FlightRoute> bestBestFlightRoutes,
									   IndexedFlight[] nextAirportFlights) {
		for (IndexedFlight nextAirportFlight : nextAirportFlights) {
			if (nextAirportFlight != null) {
				FlightRoute newFlightRoute = addFlightRoute(flightGraph,
														    actualFlightRoute,
															nextAirportFlight,
															bestBestFlightRoutes,
															flightOptions);
				if (newFlightRoute != null) {
					searchFlights(flightGraph, flightOptions,
							      newFlightRoute,
							      bestBestFlightRoutes,
							      nextAirportFlight.getToAirportId());
				}
			}			
		}		
	}
	
	private FlightRoute addFlightRoute(FlightGraph flightGraph,
									   FlightRoute actualFlightRoute,
									   IndexedFlight actualFlight,
									   List<FlightRoute> bestBestFlightRoutes,
									   FlightOptions flightOptions) {
		short nextAirportId = actualFlight.getToAirportId();
		int flightsLimitNumber = flightOptions.getSearchedFlightsLimitNumber();
		int maxTransferNumber = flightOptions.getMaxPossibleTransfersNumber();
		
		if (isAirportAlreadyPassed(nextAirportId, actualFlightRoute.getTransferAirports())) {
			return null;
		}

		FlightRoute newFlightRoute = flightRouteBuilder
				.createFlightRoute(actualFlightRoute, actualFlight, maxTransferNumber);

		if (newFlightRoute.getDuration() > flightOptions.getMaxFlightRouteDurationInMinutes()) {
			return null;
		}		
		
		if (nextAirportId == flightGraph.getAirportIdByIataCode(flightOptions.getDestinationSpot())) {
			if (bestBestFlightRoutes.size() < flightsLimitNumber) {
				bestBestFlightRoutes.add(newFlightRoute);
				if (bestBestFlightRoutes.size() == flightsLimitNumber - 1) {
					Collections.sort(bestBestFlightRoutes, (flight1, flight2) -> flight1.getPrice() - flight2.getPrice());
				}			
			} else {
				int lastFlightIndex = flightsLimitNumber - 1;
				FlightRoute lastFlightRoute = bestBestFlightRoutes.get(lastFlightIndex);
				int newFlightPrice = actualFlightRoute.getPrice() + actualFlight.getPrice();
				if (newFlightPrice < lastFlightRoute.getPrice()) {
					bestBestFlightRoutes.set(lastFlightIndex, newFlightRoute);
					Collections.sort(bestBestFlightRoutes, (flight1, flight2) -> flight1.getPrice() - flight2.getPrice());
				}
			}
			return null;
		} else {
			if (bestBestFlightRoutes.size() == flightsLimitNumber) {
				FlightRoute mostExpensive = bestBestFlightRoutes.get(0);
				int newFlightPrice = actualFlightRoute.getPrice() + actualFlight.getPrice();
				if (newFlightPrice > mostExpensive.getPrice()){
					return null;
				}
			}
		}
		
		if (newFlightRoute.getTransferNumber() >= maxTransferNumber) {
			return null;
		}		

		return newFlightRoute;
	}
	
	private boolean isAirportAlreadyPassed(short airportId, short[] passedAirportIds) {
		for (short passedAirportId : passedAirportIds) {
			if (passedAirportId == 0) {
				return false;
			}
			if (passedAirportId != 0 && passedAirportId == airportId) {
				return true;
			}
		}
		return false;
	}
	
}
