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
	public Contact(String firstName_IN, String lastName_IN, String contactEmail_IN, String contactPhone_IN,
		String contactCarrier_IN, int userID_IN)
	{
		FirstName = firstName_IN;
		LastName = lastName_IN;
		ContactEmail = contactEmail_IN;
		ContactPhone = contactPhone_IN;
		ContactCarrier = contactCarrier_IN;
		UserID = userID_IN;
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
		this.ContactPhone = ContactCarrier;
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
	
	public void setContactID(int id_IN) {
		this.ContactID = id_IN;
	}
	
	/*
	 * We do not need the below function, use "ContactResourceValidator" class to check that the contact is valid
	 */
	
	/*
	public boolean checkValues(){
		if(ContactEmail == null && ContactPhone == null){
			return false;
		}
		if(ContactCarrier == null){
			return false;
		}
		return true;
	}
	*/
}

