package com.projector.edu.findflight.service;

import java.util.List;
import java.util.stream.Collectors;

import com.projector.edu.findflight.business.searchers.DfsFlightRoutesSearcher;
import com.projector.edu.findflight.business.searchers.DfsFlightRoutesSearcherOptimized;
import com.projector.edu.findflight.business.sorters.FlightRoutesSorter;
import com.projector.edu.findflight.cache.GlobalCache;
import com.projector.edu.findflight.graph.FlightGraph;
import com.projector.edu.findflight.model.FlightOptions;
import com.projector.edu.findflight.model.FlightRoute;
import com.projector.edu.persistence.domain.Spot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FindFlightsService {
	
	@Autowired
	private MongoDatabaseService mongoDatabaseService;

	@Autowired
	private FlightRoutesSorter flightRoutesSorter;

	@Autowired
	private DfsFlightRoutesSearcherOptimized dfsFlightRoutesSearcher;

	@Autowired
	private GlobalCache globalCache;

	public List<String> getAllAirports() {
		List<Spot> airports = mongoDatabaseService.getAllAirports();
		return airports.stream().map(a -> a.getName() + " (" + a.getCode() + ")").collect(Collectors.toList());
	}

	public List<FlightRoute> getBestFlightRoutes(FlightOptions flightOptions) {

		FlightGraph flightGraph = globalCache.getFlightGraph();

		List<FlightRoute> bestFlightRoutes = dfsFlightRoutesSearcher.getBestFlightRoutes(flightGraph, flightOptions);

		//bestFlightRoutes = flightRoutesSorter.sortFlightRouteByPrice(bestFlightRoutes);

		return bestFlightRoutes;
	}

}