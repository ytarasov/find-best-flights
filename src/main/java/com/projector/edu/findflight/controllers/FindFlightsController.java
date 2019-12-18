package com.projector.edu.findflight.controllers;

import java.util.LinkedList;
import java.util.List;

import com.projector.edu.findflight.config.SearchFlightsConfiguration;
import com.projector.edu.findflight.config.properties.ConfigPropertiesReader;
import com.projector.edu.findflight.dto.SearchResultDto;
import com.projector.edu.findflight.converters.FlightRouteToRouteDtoConverter;
import com.projector.edu.findflight.converters.WebRequestInfoConverter;
import com.projector.edu.findflight.model.FlightOptions;
import com.projector.edu.findflight.model.FlightRoute;
import com.projector.edu.findflight.service.FindFlightsService;
import com.projector.edu.findflight.utils.TimeProfileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FindFlightsController {

	@Autowired
	private FindFlightsService findFlightsService;
	
	@Autowired
	private WebRequestInfoConverter webRequestInfoConverter;
	
	@Autowired
	private ConfigPropertiesReader configPropertiesReader;

	@Autowired
	private FlightRouteToRouteDtoConverter flightRouteToRouteDtoConverter;
	
	@Autowired
	private TimeProfileUtil timeProfileUtil;

	private static final int MINUTES_IN_DAY_NUMBER = 1440;

	private static final int MINUTES_IN_HOUR_NUMBER = 60;
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/get-all-airports", method = RequestMethod.GET)
	public List<String> getAllAirports() {
		return findFlightsService.getAllAirports();
	}	
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/get-best-flights", method = RequestMethod.GET)
	public SearchResultDto getBestFlights(@RequestParam("originCode") String originCode,
										  @RequestParam("destinationCode") String destinationCode,
										  @RequestParam("originDate") String originDate,
										  @RequestParam("timeFrom") String timeFrom,
										  @RequestParam("duration") String duration) {
		
		List<String> profiledMethodNames = new LinkedList<String>();
		profiledMethodNames.add("getBestFlights");
		
		timeProfileUtil.initMethodProfileMap(profiledMethodNames);
		
		long nano0 = System.nanoTime();
		
		SearchFlightsConfiguration requestSearchFlightsConfiguration =
				webRequestInfoConverter.convertRequestInfoToConfiguration(originCode, destinationCode,
															   			  originDate, timeFrom, duration);
		
		SearchFlightsConfiguration fileSearchFlightsConfiguration = (SearchFlightsConfiguration)
				configPropertiesReader.readPropertiesFile("config", new SearchFlightsConfiguration());
		
		SearchFlightsConfiguration searchFlightsConfiguration = (SearchFlightsConfiguration)
				configPropertiesReader.mergeConfigurations(fileSearchFlightsConfiguration, requestSearchFlightsConfiguration);

		FlightOptions flightOptions = createFlightOptions(searchFlightsConfiguration);
		List<FlightRoute> bestFlightRoutes = findFlightsService.getBestFlightRoutes(flightOptions);
		SearchResultDto searchResultDto = flightRouteToRouteDtoConverter.convertToDto(bestFlightRoutes);
		
		timeProfileUtil.profileMethod(nano0, "getBestFlights");
		timeProfileUtil.finithProfiling();

		return searchResultDto;
	}

	private FlightOptions createFlightOptions(SearchFlightsConfiguration searchFlightsConfiguration) {
		FlightOptions flightOptions = new FlightOptions();
		flightOptions.setOriginSpot(searchFlightsConfiguration.getOriginAirport());
		flightOptions.setDestinationSpot(searchFlightsConfiguration.getDestinationAirport());
		flightOptions.setRemainOriginDateTimeInMinutes(MINUTES_IN_DAY_NUMBER - (searchFlightsConfiguration.getFromJorneyDateTime().getHour() * MINUTES_IN_HOUR_NUMBER + searchFlightsConfiguration.getFromJorneyDateTime().getMinute()));
		flightOptions.setMaxFlightRouteDurationInMinutes(searchFlightsConfiguration.getMaxFlightsDurationHours() * MINUTES_IN_HOUR_NUMBER);
		flightOptions.setMaxPossibleTransfersNumber(searchFlightsConfiguration.getTransferNumber());
		flightOptions.setMaxWaitForNewFlightTimeInMinutes(searchFlightsConfiguration.getFlightsSearchWaitNewTransferHours() * MINUTES_IN_HOUR_NUMBER);
		flightOptions.setMinNeededTimeForTransferInMinutes(searchFlightsConfiguration.getFlightsSearchWaitAfterArrivalMinutes());
		flightOptions.setSearchedFlightsLimitNumber(searchFlightsConfiguration.getSearchedFlightsLimitNumber());
		flightOptions.setOriginDateTime(searchFlightsConfiguration.getFromJorneyDateTime());
		return flightOptions;
	}

}