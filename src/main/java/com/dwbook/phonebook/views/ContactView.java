package com.dwbook.phonebook.views;

import io.dropwizard.views.View;

import com.dwbook.phonebook.representations.Contact;

public class ContactView extends View {
	private final Contact contact;

	public ContactView(Contact contact) {
		super("/views/contact.mustache");
		this.contact = contact;
	}

	public Contact getContact() {
		return contact;
	}
}
