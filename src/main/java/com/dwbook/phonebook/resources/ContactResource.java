package com.dwbook.phonebook.resources;

import io.dropwizard.auth.Auth;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.skife.jdbi.v2.DBI;

import com.dwbook.phonebook.dao.ContactDAO;
import com.dwbook.phonebook.representations.Contact;

@Path("/contact")
@Produces(MediaType.APPLICATION_JSON)
public class ContactResource {
	
	private final ContactDAO contactDao;
	
	public ContactResource(DBI jdbi) {
		contactDao = jdbi.onDemand(ContactDAO.class);
	}
	
	@GET
	@Path("/{id}")
	public Response getContact(@PathParam("id") int id, @Auth Boolean isAuthenticated) {
		// retrieve information about the contact with the	provided id
		Contact contact = contactDao.getContactById(id);
		return Response.ok(contact).build();
	}
	
	
	@POST
	public Response createContact(Contact c, @Auth Boolean isAuthenticated) throws URISyntaxException{
		int newContactId = contactDao.createContact(c.getFirstName(),c.getLastName(),c.getPhone());
		
//		Contact c = new Contact(1010,firstName,lastName,phone);
//		return Response.created(null).build();
		return Response.created(new URI(String.valueOf(newContactId))).build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response deleteContact(@PathParam("id") int id, @Auth Boolean isAuthenticated) {
		System.out.println("deleteContact....");
		contactDao.deleteContact(id);
		return Response.noContent().build();
	
	}
	
	@PUT
	@Path("/{id}")
	public Response updateContact(
		@PathParam("id") int id,Contact c, @Auth Boolean isAuthenticated) {
		
		System.out.println("updateContact......."+id+c.getFirstName());
		contactDao.updateContact(id,c.getFirstName(),c.getLastName(),c.getPhone());
	return Response.ok(c) .build();
	}
	
}
