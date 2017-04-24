package com.au.personal.safety.contacts;

public class Contact {
	private String FirstName;
	private String LastName;
	private String ContactEmail;
	private String ContactPhone;
	private String ContactCarrier;
	private int UserID;
	private int ContactID;
	
	public Contact() {
	}
	
	// another constructor
	public Contact(String FirstName, String LastName, String ContactEmail, String ContactPhone, String ContactCarrier, int UserID) {
		this.FirstName = FirstName;
		this.LastName = LastName;
		this.ContactEmail = ContactEmail;
		this.ContactPhone = ContactPhone;
		this.ContactCarrier = ContactCarrier;
		this.UserID = UserID;
	}

	public String getFirstName() {
		return FirstName;
	}

	public void setFirstName(String FirstName) {
		this.FirstName = FirstName;
	}

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String LastName) {
		this.LastName = LastName;
	}

	public String getContactEmail() {
		return ContactEmail;
	}

	public void setContactEmail(String ContactEmail) {
		this.ContactEmail = ContactEmail;
	}
	
	public String getContactPhone() {
		return ContactPhone;
	}

	public void setContactPhone(String ContactPhone) {
		this.ContactPhone = ContactPhone;
	}
	
	public String getContactCarrier() {
		return ContactCarrier;
	}

	public void setContactCarrier(String ContactCarrier) {
		this.ContactCarrier = ContactCarrier;
	}

	public int getUserID() {
		return UserID;
	}

	public void setUserID(int UserID) {
		this.UserID = UserID;	
	}
	
	public int getContactID() {
		return ContactID;
	}
	
	public void setContactID(Integer id_IN) {
		this.ContactID = id_IN;
	}
	
	
}

