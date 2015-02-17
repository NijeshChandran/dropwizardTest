package com.dwbook.phonebook;
import io.dropwizard.Application;
import io.dropwizard.auth.CachingAuthenticator;
import io.dropwizard.auth.basic.BasicAuthProvider;
import io.dropwizard.auth.basic.BasicCredentials;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;

import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dwbook.phonebook.healthcheck.NewContactHealthCheck;
import com.dwbook.phonebook.resources.ClientResource;
import com.dwbook.phonebook.resources.ContactResource;
import com.google.common.cache.CacheBuilderSpec;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

public class App extends Application <PhonebookConfiguration>
{	
	
	private static final Logger LOGGER =LoggerFactory.getLogger(App.class);
   
    
    @Override
    public void initialize(Bootstrap<PhonebookConfiguration> b) {
    	 b.addBundle(new ViewBundle());
    	
    }
   
    
    
    @Override
    public void run(PhonebookConfiguration c, Environment e) throws Exception {
    	
	    LOGGER.info("Method App#run() called");

	  // Create a DBI factory and build a JDBI instance
	   final DBIFactory factory = new DBIFactory();
	   final DBI jdbi = factory.build(e, c.getDataSourceFactory(), "mysql");
	   // Add the resource to the environment
	   e.jersey().register(new ContactResource(jdbi));
	   
	   final Client client = new  JerseyClientBuilder(e).build("REST Client");
	   client.addFilter(new HTTPBasicAuthFilter("nijesh", "password"));
	   e.jersey().register(new ClientResource(client));
	   
	   // Register the authenticator with the environment
//	   e.jersey().register(new BasicAuthProvider<Boolean>( new PhonebookAuthenticator(jdbi), "Web Service Realm"));
	   CachingAuthenticator<BasicCredentials, Boolean> authenticator = 
		   new CachingAuthenticator<BasicCredentials, Boolean>(e.metrics(),
		   new PhonebookAuthenticator(jdbi),
		   CacheBuilderSpec.parse("maximumSize=10000, expireAfterAccess=10m"));
	   // Register the authenticator with the environment
	   e.jersey().register(new BasicAuthProvider<Boolean>( authenticator, "Web Service Realm"));
	
	   // Add health checks
	   e.healthChecks().register("New Contact health check", new NewContactHealthCheck(client));
    }
    
    public static void main( String[] args ) throws Exception
    {	
    	new App().run(args);
    }
    
}
