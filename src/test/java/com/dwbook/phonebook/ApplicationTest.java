package com.dwbook.phonebook;

import io.dropwizard.testing.junit.DropwizardAppRule;

import javax.ws.rs.core.MediaType;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import com.dwbook.phonebook.representations.Contact;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

public class ApplicationTest{

	private Client client;
	private String LOCAL_URL = "http://localhost:9000/contact";
	private Contact contact = new Contact(0,"nijesh","chandran","89876888");
	
	@ClassRule
	public static final DropwizardAppRule<PhonebookConfiguration> RULE =
		new DropwizardAppRule<PhonebookConfiguration>(App.class, "config.yaml");
	
	@Before
	public void setUp(){
		client = new Client();
		client.addFilter(new HTTPBasicAuthFilter("nijesh", "password"));
	}
	
	@Test
	public void createAndRetrieveContact(){
		WebResource contactResource = client.resource(LOCAL_URL);
		ClientResponse response = contactResource
				.type(MediaType.APPLICATION_JSON)
				.post(ClientResponse.class,contact);
		Assert.assertEquals(201, response.getStatus());
		
		WebResource newContactResource = client.resource(response.getHeaders().getFirst("Location"));
		Contact newResponse = newContactResource.get(Contact.class);
		System.out.println(newResponse.getId());
		Assert.assertNotNull(newResponse.getId());
	}
	
}
