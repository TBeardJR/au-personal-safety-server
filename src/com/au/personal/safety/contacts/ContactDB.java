package com.au.personal.safety.contacts;

import java.net.URISyntaxException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Response;
//local
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
		
		//Create a validator
		ContactResourceValidator crv = new ContactResourceValidator(contact);
		
		//Validate the contact information
		//validateResult will be true if contact email, phone and carrier are okay
		//validateResult will be false if contact, email or carrier are not okay
		boolean validateResult = crv.validate(); 
		
		//If validateResult is false, contact data has an error. Return the Response from the validator class. 
		if(!validateResult){
			return crv.getResponse(); 
		}
		
		//If validateResult is true, contact data is okay. Continue as normal.
		Statement stmt = null;
		int contactUID = -1;
		
		//Create Query Statements
		String selectQuery = "SELECT ContactID FROM Contacts WHERE ContactID = " + contact.getContactID() + ";";
		
		String insertQuery = "INSERT INTO Contacts (FirstName, LastName, PhoneNumber, ContactCarrier, Email, UserID) "
				+ "VALUES ('" + contact.getFirstName()
				+ "', '" + contact.getLastName() + "', '" + contact.getContactPhone() 
				+ "', '" + contact.getContactCarrier() + "', '"
				+ contact.getContactEmail() + "', " + contact.getUserID() + ");";
		
		String updateQuery  = "UPDATE Contacts SET FirstName = '" + contact.getFirstName() + "', LastName = '" + contact.getLastName() 
				+ "' , PhoneNumber = '" + contact.getContactPhone() + "' , Email = '" + contact.getContactEmail()
				+ "' , ContactCarrier = '" + contact.getContactCarrier();
				
		
		try {
			//Connect to Database
			Connection conn = DatabaseConnectionSingleton.getInstance().getConnection();
			stmt = conn.createStatement();
			
			//Execute Select Query.
			//rs01 will have values if this same contact with this userName already exists
			//rs01 will be null if there is this userName does not have this same contact
			ResultSet rs01 = stmt.executeQuery(selectQuery);
			
			//if this contact does exist NOT for this username insert the contact
			if (!rs01.next()){
				//execute the insertQuery
				stmt.executeUpdate(insertQuery);
				return Response.status(Response.Status.OK).entity(ContactConstants.CONTACT_WAS_SUCCESSFULLY_ADDED).build();
			}
			//if this contact does exist for this username, update the contact
			else{
				//Get contactID from the result
				contactUID = rs01.getInt("ContactID");
				
				//add the where condition to the udpate query 
				updateQuery = updateQuery + "' WHERE ContactID = " + contactUID + ";";
				
				//execute the updateQuery 
	            stmt.executeUpdate(updateQuery);
	            return Response.status(Response.Status.OK).entity(ContactConstants.CONTACT_WAS_SUCCESSFULLY_UPDATED).build();
			}
			    
		} 
		//If we had a URIException return URI error response
		catch (URISyntaxException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ContactConstants.CONTACT_COULD_NOT_BE_CREATED_URI).build();
		}
				
		//If we had a SQLException return SQL error response
		catch (SQLException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ContactConstants.CONTACT_COULD_NOT_BE_CREATED_SQL).build();
		}
	      
	}
	
	//********** DELETE CONTACT **********
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
	
	public List<Contact> getContacts(int UserID){
		String selectQuery = "SELECT * FROM Contacts WHERE UserID = " + UserID +";";
		List<Contact> emergencyContacts = new ArrayList<Contact>();
		Statement stmt = null;
		
		try{
			Connection conn = DatabaseConnectionSingleton.getInstance().getConnection();
			stmt = conn.createStatement();
			ResultSet rs01 = stmt.executeQuery(selectQuery);
			
			while(rs01.next()){
				Contact contact = new Contact();
				contact.setFirstName(rs01.getString("FirstName"));
				contact.setLastName(rs01.getString("LastName"));
				contact.setContactEmail(rs01.getString("Email"));
				contact.setContactPhone(rs01.getString("PhoneNumber"));
				contact.setContactCarrier(rs01.getString("ContactCarrier"));
				contact.setContactID(rs01.getInt("ContactID"));
				contact.setUserID(UserID);
				emergencyContacts.add(contact);
			}
		} catch (URISyntaxException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return emergencyContacts;	
	}
	
}
