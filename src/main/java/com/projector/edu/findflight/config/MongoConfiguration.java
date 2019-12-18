package com.projector.edu.findflight.config;

import java.util.Arrays;

import com.projector.edu.findflight.converters.data.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.convert.CustomConversions;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
@Configuration
@EnableMongoRepositories(basePackages="com.projector.edu.persistence.repositories")
public class MongoConfiguration extends AbstractMongoConfiguration {

	@Bean
	public MappingMongoConverter mappingMongoConverter() throws Exception {

		DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDbFactory());

		MappingMongoConverter converter = new MappingMongoConverter(
				dbRefResolver, mongoMappingContext());

		converter.setCustomConversions(new CustomConversions(Arrays.asList(
									   new DurationToStringConverter(),
									   new StringToDurationConverter(),
									   new IntToLocalDateConverter(),
									   new LocalDateToIntConverter(),
									   new StringToLocalTimeConverter(),
									   new LocalTimeToStringConverter()
		)));

		converter.setTypeMapper(new DefaultMongoTypeMapper(null));

		return converter;
	}

	@Override
	protected String getDatabaseName() {
		return "outrunner";
	}

	@Override
	public Mongo mongo() throws Exception {
		return new MongoClient("127.0.0.1", 27017);
	}

	@Override
	protected String getMappingBasePackage() {
		return "com.projector.edu.persistence.domain";
	}

}
