package com.projector.edu.findflight.business.searchers;

import com.projector.edu.findflight.graph.FlightGraph;
import com.projector.edu.findflight.model.FlightOptions;
import com.projector.edu.findflight.model.FlightRoute;
import com.projector.edu.findflight.model.IndexedFlight;
import com.projector.edu.findflight.model.builders.FlightRouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class DfsFlightRoutesSearcher {
	
	@Autowired
	private FlightRouteBuilder flightRouteBuilder;

	public List<FlightRoute> getBestFlightRoutes(FlightGraph flightGraph, FlightOptions flightOptions) {

		short originAirportId = flightGraph.getAirportIdByIataCode(flightOptions.getOriginSpot());
		LocalDateTime originDateTime = flightOptions.getOriginDateTime();
		int maxFlightsNumber = flightOptions.getSearchedFlightsLimitNumber();

		BestFlightRoutesHolder bestFlightRoutesHolder = new BestFlightRoutesHolder(maxFlightsNumber);
		FlightRoute initFlightRoute = flightRouteBuilder.createInitFlightRoute(originDateTime, maxFlightsNumber + 1);

		searchFlights(flightGraph, flightOptions,
					  initFlightRoute,
					  bestFlightRoutesHolder,
					  originAirportId);
		
		return bestFlightRoutesHolder.getSortedFlightRoutes();
	}

	private void searchFlights(FlightGraph flightGraph, FlightOptions flightOptions,
							   FlightRoute actualFlightRoute,
							   BestFlightRoutesHolder bestFlightRoutesHolder,
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
							  bestFlightRoutesHolder,
				              availableFlights);
	}
	
	private void searchNextSpotFlights(FlightGraph flightGraph, FlightOptions flightOptions,
									   FlightRoute actualFlightRoute,
									   BestFlightRoutesHolder bestFlightRoutesHolder,
									   IndexedFlight[] nextAirportFlights) {
		for (IndexedFlight nextAirportFlight : nextAirportFlights) {
			if (nextAirportFlight != null) {
				FlightRoute newFlightRoute = addFlightRoute(flightGraph,
														    actualFlightRoute,
															nextAirportFlight,
															bestFlightRoutesHolder,
															flightOptions);
				if (newFlightRoute != null) {
					searchFlights(flightGraph, flightOptions,
							      newFlightRoute,
							      bestFlightRoutesHolder,
							      nextAirportFlight.getToAirportId());
				}
			}			
		}		
	}
	
	private FlightRoute addFlightRoute(FlightGraph flightGraph,
									   FlightRoute actualFlightRoute,
									   IndexedFlight actualFlight,
									   BestFlightRoutesHolder bestFlightRoutesHolder,
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
			bestFlightRoutesHolder.addFlightRoute(newFlightRoute);
			return null;
		} else {
			if (bestFlightRoutesHolder.getCurrentRouteNumber() == flightsLimitNumber) {
				int newFlightPrice = actualFlightRoute.getPrice() + actualFlight.getPrice();
				if (newFlightPrice > bestFlightRoutesHolder.getMaxPrice()){
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
