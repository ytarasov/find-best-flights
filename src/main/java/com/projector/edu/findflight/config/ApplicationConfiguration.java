package com.projector.edu.findflight.config;

import javax.annotation.PostConstruct;

import com.projector.edu.findflight.cache.GlobalCacheBuilder;
import com.projector.edu.findflight.graph.FlightGraphBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({ "com.projector.edu.findflight" })
public class ApplicationConfiguration {
	
	@Autowired
	private GlobalCacheBuilder globalCacheBuilder;

	@PostConstruct
	public void init() {
		globalCacheBuilder.buildGlobalCache();
	}

}
