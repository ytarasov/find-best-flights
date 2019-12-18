package com.projector.edu.findflight.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.projector.edu.persistence.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.mongodb.BasicDBObject;
import com.mongodb.Cursor;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

@Component
public class MongoDatabaseService {
	
	@Autowired
	private MongoOperations operations;	
	
	public List<Spot> getAllAirports(){
		Query queryAirports = new Query();
		queryAirports.addCriteria(Criteria.where(Spot.SPOT_TYPE).is(SpotType.AIRPORT));
		return getOnlyEuropeAirports(operations.find(queryAirports, Spot.class));
	}
	
	private List<Spot> getOnlyEuropeAirports(List<Spot> airports){
		String [] europeanCountries = new String[]{"AL","AD","AT","BY","BE","BA","BG","HR","CY","CZ","DK","EE","FO","FI","FR","DE","GI","GR","HU","IS","IE","IT","LV","LI","LT","LU","MK","MT","MD","MC","NL","NO","PL","PT","RO","RU","SM","RS","SK","SI","ES","SE","CH","UA","GB","VA","RS","IM","RS","ME","TR"};
		Set<String> countriesSet =new HashSet<>(Arrays.asList(europeanCountries));
		 
		List<Spot> europeanAirport = new LinkedList<>();
		for(Spot airport: airports){
			 if(airport.getSpotType()!=SpotType.AIRPORT) continue;
			 String parentSpotId = airport.getParentAreaSpot();
			 String parentSpotCountry = parentSpotId.substring(parentSpotId.length() - 2);
			 if(countriesSet.contains(parentSpotCountry)){
				  europeanAirport.add(airport);
			 }
		}
		return europeanAirport;
	}

	public Cursor getAllFlightsCursor(List<Integer> dates) {
		DBCollection collection = operations.getCollection("jorney");

		Set<String> allAirports = getAllAirports().stream().map(a -> a.getCode()).collect(Collectors.toSet());

		DBObject query = new BasicDBObject(Jorney.ORIGIN_SPOT, new BasicDBObject("$in", allAirports))
				.append(Jorney.DESTINATION_SPOT, new BasicDBObject("$in", allAirports))
				.append(Jorney.DEPARTURE_DATE, new BasicDBObject("$in", dates));

		DBObject projection = new BasicDBObject(Jorney.ORIGIN_SPOT, 1)
				.append(Jorney.DESTINATION_SPOT, 1)
				.append(Jorney.DEPARTURE_DATE, 1)
				.append(Jorney.DEPARTURE_TIME, 1)
				.append(Jorney.ARRIVAL_DATE, 1)
				.append(Jorney.ARRIVAL_TIME, 1)
				.append(Jorney.DURATION, 1)
				.append(Jorney.NET_FARE, 1)
				.append(Jorney.MARKUP, 1)
				.append(Jorney.CARRIER, 1);

		Cursor cursor = collection.find(query, projection);
		return cursor;
	}	
		
}
