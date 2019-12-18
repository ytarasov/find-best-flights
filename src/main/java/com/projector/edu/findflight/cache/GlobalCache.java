package com.projector.edu.findflight.cache;

import com.projector.edu.findflight.graph.FlightGraph;
import org.springframework.stereotype.Component;

@Component
public class GlobalCache {

	private FlightGraph flightGraph;

	public FlightGraph getFlightGraph() {
		return flightGraph;
	}

	public void setFlightGraph(FlightGraph flightGraph) {
		this.flightGraph = flightGraph;
	}

}
