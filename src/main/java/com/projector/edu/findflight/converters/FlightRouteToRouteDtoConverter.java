package com.projector.edu.findflight.converters;

import com.projector.edu.findflight.cache.GlobalCache;
import com.projector.edu.findflight.dto.RouteDto;
import com.projector.edu.findflight.dto.SearchResultDto;
import com.projector.edu.findflight.dto.SpotDto;
import com.projector.edu.findflight.dto.TransportTypeDto;
import com.projector.edu.findflight.dto.builders.RouteDtoBuilder;
import com.projector.edu.findflight.graph.FlightGraph;
import com.projector.edu.findflight.model.FlightRoute;
import com.projector.edu.findflight.model.IndexedFlight;
import com.projector.edu.findflight.converters.FlightIndexConverter;
import com.projector.edu.persistence.domain.Spot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Component
public class FlightRouteToRouteDtoConverter {
	
	@Autowired
	private GlobalCache globalCache;
	
	@Autowired
	private RouteDtoBuilder routeDtoBuilder;

	private FlightIndexConverter flightIndexConverter = new FlightIndexConverter();

	public SearchResultDto convertToDto(List<FlightRoute> flightRoutes) {
		SearchResultDto searchResultDto = new SearchResultDto();		
		List<RouteDto> routeDtoList = convertToRouteDtos(flightRoutes);
		searchResultDto.setRouteTracks(routeDtoList);
		return searchResultDto;		
	}

	private List<RouteDto> convertToRouteDtos(List<FlightRoute> flightRoutes) {
		List<RouteDto> routeDtos = new LinkedList<RouteDto>();
		for(FlightRoute flightRoute : flightRoutes) {
			RouteDto routeDto = convertToRouteDto(flightRoute);
			routeDtos.add(routeDto);
		}
		return routeDtos;
	}	
	
	private RouteDto convertToRouteDto(FlightRoute flightRoute) {
		List<RouteDto> segmentRouteDtos = new LinkedList<>();
		IndexedFlight[] segmentFlights = flightRoute.getSegmentFlights();
		for (IndexedFlight segmentFlight : segmentFlights) {
			if (segmentFlight != null) {
				RouteDto segmentRouteDto = convertFlightRouteToRouteDto(segmentFlight);
				segmentRouteDtos.add(segmentRouteDto);
			}
		}		
		
		RouteDto firstRouteDto = segmentRouteDtos.get(0);
		RouteDto lastRouteDto = segmentRouteDtos.get(segmentRouteDtos.size() - 1);
		
		SpotDto originRouteSpot = firstRouteDto.getOriginSpot();
		SpotDto destinationRouteSpot = lastRouteDto.getDestinationSpot();
		
		int price = flightRoute.getPrice();
		LocalDateTime departureDateTime = LocalDateTime.of(firstRouteDto.getDepartureDate(), firstRouteDto.getDepartureTime());
		LocalDateTime arrivalDateTime = LocalDateTime.of(lastRouteDto.getArrivalDate(), lastRouteDto.getArrivalTime());
		
		int durationHours = (int) flightRoute.getDuration() / 60;
		int durationMinutes = (int) flightRoute.getDuration() - durationHours * 60;
		
		List<TransportTypeDto> allRouteTypes = getAllRouteTypes(segmentRouteDtos);
		List<String> transfers = getTransfers(segmentRouteDtos);
		List<String> carriers = getCarriers(segmentRouteDtos);
		
		RouteDto routeDto = routeDtoBuilder.createCompositeRouteDto(originRouteSpot, destinationRouteSpot,
										   							durationHours, durationMinutes,
										   							departureDateTime,
										   							arrivalDateTime,				   
										   							price,
										   							allRouteTypes,
										   							transfers,
										   							carriers);
		routeDto.setSegments(segmentRouteDtos);
		
		return routeDto;		
	}

	private RouteDto convertFlightRouteToRouteDto(IndexedFlight flight) {
		SpotDto originRouteSpot = convertToRouteSpot(flight.getFromAirportId());
		SpotDto destinationRouteSpot = convertToRouteSpot(flight.getToAirportId());
		
		int durationHours = (int) flight.getDuration() / 60;
		int durationMinutes = (int) flight.getDuration() - durationHours * 60;
		
		LocalDateTime departureDateTime = flightIndexConverter.convertDateTimeIndexToLocalDateTime(flight.getDepartureDateTime());
		LocalDateTime arrivalDateTime = flightIndexConverter.convertDateTimeIndexToLocalDateTime(flight.getArrivalDateTime());

		RouteDto routeDto = routeDtoBuilder.createRouteDto(originRouteSpot, destinationRouteSpot,
										    			   durationHours, durationMinutes,
										    			   departureDateTime, arrivalDateTime,
														   flight.getPrice(),
														   TransportTypeDto.AIR,
				                                            "");
	
		return routeDto;
	}	
	
	private SpotDto convertToRouteSpot(short airportId) {
		FlightGraph flightGraph = globalCache.getFlightGraph();
		Spot airport = flightGraph.getAirportById(airportId);
		SpotDto spotDto = convertToRouteSpot(airport);
		return spotDto;
	}	
	
	private SpotDto convertToRouteSpot(Spot spot) {
		SpotDto spotDto = new SpotDto();		
		spotDto.setAmadeusId(spot.getCode());
		spotDto.setLat(spot.getLat());
		spotDto.setLon(spot.getLon());
		spotDto.setName(spot.getName());
		spotDto.setParentAreaSpotId(spot.getParentAreaSpot());
		return spotDto;
	}
	
	private List<String> getTransfers(List<RouteDto> routeDtos) {
		List<String> transfers = new LinkedList<String>();
		for (RouteDto routeDto : routeDtos) {
			transfers.add(routeDto.getOriginSpot().getAmadeusId());
		}
		if (transfers.size() > 0) {
			transfers.remove(0);
		}
		return transfers;
	}
	
	private List<String> getCarriers(List<RouteDto> routeDtos) {
		List<String> carriers = new LinkedList<String>();
		for (RouteDto routeDto : routeDtos) {
			carriers.add(routeDto.getCarrier());
		}		
		return carriers;
	}
	
	private List<TransportTypeDto> getAllRouteTypes(List<RouteDto> routeDtos) {
		List<TransportTypeDto> allRouteTypes = new LinkedList<TransportTypeDto>();
		for (RouteDto routeDto : routeDtos) {
			TransportTypeDto routeType = routeDto.getJorneyType();
			allRouteTypes.add(routeType);
		}
		return allRouteTypes;
	}

}