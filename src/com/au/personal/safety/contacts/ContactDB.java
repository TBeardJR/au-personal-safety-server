package com.au.personal.safety.contacts;

import java.net.URISyntaxException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import com.au.personal.safety.database.DatabaseConnectionSingleton;
import com.au.personal.safety.constants.ContactConstants;
import com.au.personal.safety.validator.ContactResourceValidator;

public class ContactDB {

	private Contact contact;
	private String contactQuery;
	private boolean contactOkay;
	
	public ContactDB(Contact contact){
		this.contact = contact;
	}
	
	public Contact getContact() {
		return contact;
	}
	
	public String getContactQuery() {
		return contactQuery;
	}
	
	/**
	The below function creates the value for the class' variable contactQuery
	
	The function assumes that there is a non-null value for the class' variable contact
	*/	
	public void buildInsert(){
		contactQuery = "INSERT INTO Contacts (FirstName, LastName, PhoneNumber, ContactCarrier, Email, UserID) "
				+ "VALUES (\"" + contact.getFirstName()
				+ "\", \"" + contact.getLastName() + "\", \"" + contact.getContactPhone() 
				+ "\", \"" + contact.getContactCarrier() + "\", \""
				+ contact.getContactEmail() + "\", " + contact.getUserID() + ");";
	}
	
	/**
	The below function assumes that when a Contact entry in the database need to be updated, that
	 the UserID for the corresponding entry never changes
	*/
	public Response sendContact(){
		//if the contact is missing phone AND email or missing the carrier this will return to the app with an error
		ContactResourceValidator crv = new ContactResourceValidator(contact);
		boolean validateResult = crv.validate(); //this will return false if the contact is invalid 
		if(!validateResult){
			return crv.getResponse(); //this will let user know what exactly is wrong with the entered contact
			//return Response.status(Response.Status.OK).entity(ContactConstants.CONTACT_MISSING).build();
		}
		
		//else it is checked that the contact does not already exist and then updates or adds contact accordingly
		Statement stmt = null;
		int contactUID = -1;
		try {
			Connection conn = DatabaseConnectionSingleton.getInstance().getConnection();
			stmt = conn.createStatement();
			String selectQry = "SELECT ContactID FROM Contacts WHERE UserID = " + contact.getUserID() + ";";
			ResultSet rs01 = stmt.executeQuery(selectQry);
			
			if (!rs01.next()){
				buildInsert();
				stmt.executeUpdate(contactQuery);
			}
			
			else{
				contactUID = rs01.getInt("ContactID");
				String updateQry  = "UPDATE Contacts SET FirstName = " + contact.getFirstName() + " , LastName = " + contact.getLastName() 
					+ " , PhoneNumber = " + contact.getContactPhone() + " , Email = " + contact.getContactEmail()
					+ " , ContactCarrier = " + contact.getContactCarrier()
	              		+ " WHERE ContactID = " + contactUID + ";";
	            stmt.executeUpdate(updateQry);
			}
			
		    return Response.status(Response.Status.OK).entity(ContactConstants.CONTACT_WAS_SUCCESSFULLY_ADDED).build();
		    
		} catch (URISyntaxException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ContactConstants.CONTACT_COULD_NOT_BE_ADDED).build();
		}
	      
	}
	
	public Response deleteContact(){
		int contactID = contact.getContactID();
		String deleteContact = "DELETE FROM Contacts WHERE ContactID =" + contactID;
		
		Statement stmt = null;
		
		try {
			Connection conn = DatabaseConnectionSingleton.getInstance().getConnection();
			stmt = conn.createStatement();
			stmt.executeUpdate(deleteContact);
		    return Response.status(Response.Status.OK).entity(ContactConstants.CONTACT_WAS_SUCCESSFULLY_DELETED).build();
		} catch (URISyntaxException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ContactConstants.CONTACT_COULD_NOT_BE_DELETED).build();
		}
		
	}
	
}
