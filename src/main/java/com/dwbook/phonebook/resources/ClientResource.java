package com.dwbook.phonebook.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.dwbook.phonebook.representations.Contact;
import com.dwbook.phonebook.views.ContactView;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@Produces(MediaType.TEXT_HTML )
@Path("/client/")
public class ClientResource {
	private Client client;
	
	private String LOCAL_URL = "http://localhost:9000/contact";
	
	public ClientResource(Client client) {
		this.client = client;
	}

	@GET
	@Path("showContact")
	public ContactView showContact(@QueryParam("id") int id) {
		WebResource contactResource = client.resource(LOCAL_URL+"/" + id);
		Contact c = contactResource.get(Contact.class);
		return new ContactView(c);
	}

	@GET
	@Path("newContact")
	public Response newContact(
			@QueryParam("firstName") String firstName,
			@QueryParam("lastName") String lastName,
			@QueryParam("phone") String phone) {
		
		WebResource contactResource = client.resource(LOCAL_URL);
		ClientResponse response = contactResource
				.type(MediaType.APPLICATION_JSON)
				.post(ClientResponse.class,new Contact(0, firstName, lastName, phone));
		if (response.getStatus() == 201) {
			// Created
			return Response
					.status(302)
					.entity("The contact was created successfully! The new contact can be found at "
							+ response.getHeaders().getFirst("Location")).build();
		} else {
			// Other Status code, indicates an error
			return Response.status(422).entity(response.getEntity(String.class)).build();
		}
	}

	@GET
	@Path("updateContact")
	public Response updateContact(@QueryParam("id") int id,
			@QueryParam("firstName") String firstName,
			@QueryParam("lastName") String lastName,
			@QueryParam("phone") String phone) {
		WebResource contactResource = client.resource(LOCAL_URL+"/" + id);
		ClientResponse response = contactResource.type(
				MediaType.APPLICATION_JSON).put(ClientResponse.class,
				new Contact(id, firstName, lastName, phone));
		if (response.getStatus() == 200) {
			// Created
			return Response.status(302).entity("The contact was updated successfully!").build();
		} else {
			// Other Status code, indicates an error
			return Response.status(422).entity(response.getEntity(String.class)).build();
		}
	}

	@GET
	@Path("deleteContact")
	public Response deleteContact(@QueryParam("id") int id) {
		WebResource contactResource = client.resource(LOCAL_URL+"/" + id);
		contactResource.delete();
		return Response.noContent().entity("Contact was deleted!").build();
	}
	

}