package com.au.personal.safety.contacts;

import java.net.URISyntaxException;
import java.sql.*;

import javax.ws.rs.core.Response;

import com.au.personal.safety.database.DatabaseConnectionSingleton;
import com.au.personal.safety.constants.ContactConstants;

public class ContactDB {

	private Contact contact;
	private String contactQuery;
	
	
	public ContactDB(Contact contact){
		this.contact = contact;
		buildInsert();
	}
	
	private void buildInsert(){
		contactQuery = "INSERT INTO Contacts (FirstName, LastName, PhoneNumber, Email, UserID) "
				+ "VALUES (?, ?, ?, ?, ?)";
	}
	
	public Response sendContact(){
		Connection conn = null;
		
		try {
			conn = DatabaseConnectionSingleton.getInstance().getConnection();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		PreparedStatement preparedStmt;
		
		try {
			preparedStmt = conn.prepareStatement(contactQuery);
			preparedStmt.setString(1, contact.getFirstName());
		    preparedStmt.setString(2, contact.getLastName());
		    preparedStmt.setString(3, contact.getContactPhone());
		    preparedStmt.setString(4, contact.getContactEmail());
		    preparedStmt.setInt(5, contact.getUserID());

		      // execute the preparedstatement
		    preparedStmt.execute();
		    return Response.status(Response.Status.OK).entity(ContactConstants.CONTACT_WAS_SUCCESSFULLY_ADDED).build();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ContactConstants.CONTACT_COULD_NOT_BE_ADDED).build();
		}
	      
	}
}
