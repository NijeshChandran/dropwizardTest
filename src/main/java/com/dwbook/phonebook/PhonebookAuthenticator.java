package com.dwbook.phonebook;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

import org.skife.jdbi.v2.DBI;

import com.dwbook.phonebook.dao.UserDAO;
import com.google.common.base.Optional;

public class PhonebookAuthenticator implements Authenticator<BasicCredentials, Boolean> {
	
	private UserDAO userDao;
	
	public PhonebookAuthenticator(DBI jdbi) {
		userDao = jdbi.onDemand(UserDAO.class);
	}
	
	public Optional<Boolean> authenticate(BasicCredentials c) throws AuthenticationException {
		
		System.out.println("----------Authenticate..........[-]");
		if (userDao.getUsers(c.getUsername(), c.getPassword()) > 0) {
			System.out.println("----------Authenticate..........[+]");
			return Optional.of(true);
		}
		return Optional.absent();
	}
}
