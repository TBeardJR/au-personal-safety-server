package com.au.personal.safety.contacts;

public class Contact {
	private String FirstName;
	private String LastName;
	private String ContactEmail;
	private String ContactPhone;
	private int UserID;
	
	public Contact() {
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

	public int getUserID() {
		return UserID;
	}

	public void setUserID(int UserID) {
		this.UserID = UserID;	
	}

}

