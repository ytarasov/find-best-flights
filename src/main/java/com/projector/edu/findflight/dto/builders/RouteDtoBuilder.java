package com.projector.edu.findflight.dto.builders;

import java.time.LocalDateTime;
import java.util.List;

import com.projector.edu.findflight.dto.RouteDto;
import com.projector.edu.findflight.dto.SpotDto;
import com.projector.edu.findflight.dto.TransportTypeDto;
import org.springframework.stereotype.Component;

@Component
public class RouteDtoBuilder {

	public RouteDto createCompositeRouteDto(SpotDto originRouteSpot, SpotDto destinationRouteSpot,
											int durationHours, int durationMinutes,
											LocalDateTime departureDateTime,
											LocalDateTime arrivalDateTime,
											Integer price,
											List<TransportTypeDto> segmentTransportTypes,
											List<String> transfers,
											List<String> carriers) {
		RouteDto routeDto = new RouteDto();
		routeDto.setOriginSpot(originRouteSpot);
		routeDto.setDestinationSpot(destinationRouteSpot);
		routeDto.setDurationHours(durationHours);
		routeDto.setDurationMinutes(durationMinutes);
		routeDto.setDepartureDate(departureDateTime.toLocalDate());
		routeDto.setDepartureTime(departureDateTime.toLocalTime());
		routeDto.setArrivalDate(arrivalDateTime.toLocalDate());
		routeDto.setArrivalTime(arrivalDateTime.toLocalTime());
		routeDto.setInterestFactor(price);
		routeDto.setSegmentJorneyTypes(segmentTransportTypes);
		routeDto.setTransfers(transfers);
		routeDto.setCarriers(carriers);
		return routeDto;
	}
	
	public RouteDto createRouteDto(SpotDto originRouteSpot, SpotDto destinationRouteSpot,
			  					   int durationHours, int durationMinutes,
			  					   LocalDateTime departureDateTime,
			  					   LocalDateTime arrivalDateTime,
			  					   Integer price,
			  					   TransportTypeDto transportTypeModel,
			  					   String carrier) {
		RouteDto routeDto = new RouteDto();
		routeDto.setOriginSpot(originRouteSpot);
		routeDto.setDestinationSpot(destinationRouteSpot);
		routeDto.setDurationHours(durationHours);
		routeDto.setDurationMinutes(durationMinutes);
		routeDto.setDepartureDate(departureDateTime.toLocalDate());
		routeDto.setDepartureTime(departureDateTime.toLocalTime());
		routeDto.setArrivalDate(arrivalDateTime.toLocalDate());
		routeDto.setArrivalTime(arrivalDateTime.toLocalTime());
		routeDto.setInterestFactor(price);
		routeDto.setJorneyType(transportTypeModel);
		routeDto.setCarrier(carrier);
		return routeDto;
	}
	
	public RouteDto createJorneyRouteDto(SpotDto originRouteSpot, SpotDto destinationRouteSpot,
								         int durationHours, int durationMinutes,
								 		 LocalDateTime departureDateTime,
										 LocalDateTime arrivalDateTime,
										 Integer price) {
		RouteDto routeDto = new RouteDto();
		routeDto.setOriginSpot(originRouteSpot);
		routeDto.setDestinationSpot(destinationRouteSpot);
		routeDto.setDurationHours(durationHours);
		routeDto.setDurationMinutes(durationMinutes);
		routeDto.setDepartureDate(departureDateTime.toLocalDate());
		routeDto.setDepartureTime(departureDateTime.toLocalTime());
		routeDto.setArrivalDate(arrivalDateTime.toLocalDate());
		routeDto.setArrivalTime(arrivalDateTime.toLocalTime());
		routeDto.setInterestFactor(price);		
		return routeDto;
	}
	
}
