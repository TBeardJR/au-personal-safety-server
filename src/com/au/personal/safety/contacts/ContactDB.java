package com.au.personal.safety.contacts;

import java.net.URISyntaxException;
import java.sql.*;

import javax.ws.rs.core.Response;

import com.au.personal.safety.database.DatabaseConnectionSingleton;
import com.au.personal.safety.constants.ContactConstants;

public class ContactDB {

	private Contact contact;
	private String contactQuery;
	
	/**
	The below constructor assumes that the entered Contact contact is valid
	
	We need to create a validator class for Contact class
	*/
	public ContactDB(Contact contact){
		this.contact = contact;
		buildInsert();
	}
	
	/**
	The below function creates the value for the class' variable contactQuery
	
	The function assumes that there is a non-null value for the class' variable contact
	*/
	private void buildInsert(){
		contactQuery = "INSERT INTO Contacts (FirstName, LastName, PhoneNumber, Email, UserID) "
				+ "VALUES (\"" + contact.getFirstName()
				+ "\", \"" + contact.getLastName()+ "\", \"" + contact.getContactPhone() + "\", \"" 
				+ contact.getContactEmail() + "\", " + contact.getUserID() + ");";
	}
	
	/**
	The below function assumes that when a Contact entry in the database need to be updated, that
	 the UserID for the corresponding entry never changes
	*/
	
	public Response sendContact(){
		Statement stmt = null;
		
		try {
			Connection conn = DatabaseConnectionSingleton.getInstance().getConnection();
			stmt = conn.createStatement();
			stmt.executeUpdate(contactQuery);
		    return Response.status(Response.Status.OK).entity(ContactConstants.CONTACT_WAS_SUCCESSFULLY_ADDED).build();
		} catch (URISyntaxException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ContactConstants.CONTACT_COULD_NOT_BE_ADDED).build();
		}
		

	      
	}
}
