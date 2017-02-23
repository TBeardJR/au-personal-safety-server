package com.au.personal.safety.contacts;

import java.net.URISyntaxException;
import java.sql.*;
import com.au.personal.safety.database.DatabaseConnectionSingleton;

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
	
	private void sendContact(){
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
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      
	}
}
