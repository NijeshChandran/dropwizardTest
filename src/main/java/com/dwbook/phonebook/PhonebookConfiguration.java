package com.dwbook.phonebook;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jetty.HttpConnectorFactory;
import io.dropwizard.server.DefaultServerFactory;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PhonebookConfiguration extends Configuration {
	
	
	@JsonProperty
	private DataSourceFactory database = new DataSourceFactory();

	public PhonebookConfiguration() {
		super();
//	    // The following is to make sure it runs with a random port. parallel tests clash otherwise
//	    ((HttpConnectorFactory) ((DefaultServerFactory) getServerFactory()).getApplicationConnectors().get(0)).setPort(0);
//	    // this is for admin port
//	    ((HttpConnectorFactory) ((DefaultServerFactory) getServerFactory()).getAdminConnectors().get(0)).setPort(0);  
	
	}

	public DataSourceFactory getDataSourceFactory() {
		return database;
	}

}