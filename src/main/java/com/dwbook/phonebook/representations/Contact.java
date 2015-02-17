package com.dwbook.phonebook.representations;

public class Contact {
	
	private  int id;
	private  String firstName;
	private  String lastName;
	private  String phone;
	
	public Contact() {
		this.id = 0;
		this.firstName = null;
		this.lastName = null;
		this.phone = null;
	}
	
	public Contact(int id, String firstName, String lastName, String phone) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
	}

	public int getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getPhone() {
		return phone;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
