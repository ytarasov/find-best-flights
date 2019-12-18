package com.projector.edu.findflight.cache;

import com.projector.edu.findflight.graph.FlightGraph;
import com.projector.edu.findflight.graph.FlightGraphBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
@PropertySource("classpath:global.properties")
public class GlobalCacheBuilder {
	
	@Autowired
	private FlightGraphBuilder flightGraphBuilder;
	
	@Autowired
	private GlobalCache globalCache;

	@Value("#{'${dates}'.split(',')}")
	private List<Integer> dates;

	public void buildGlobalCache() {
		FlightGraph flightGraph = flightGraphBuilder.build(dates);
		globalCache.setFlightGraph(flightGraph);
	}
	
}
