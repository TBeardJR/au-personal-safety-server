package com.au.personal.safety.users;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import com.au.personal.safety.constants.ContactConstants;
import com.au.personal.safety.contacts.Contact;
import com.au.personal.safety.database.DatabaseConnectionSingleton;

public class UserDB {

	private User user;
	private boolean pinVerified;
	private boolean passwordVerified;
	
	public UserDB(User user){
		this.user = user;
	}
	
	public User getUser(){
		return user;
	}
	
	/*
	public Response createNewUser(User user){
		Statement stmt = null;
		int userUID = -1;
		try {
			Connection conn = DatabaseConnectionSingleton.getInstance().getConnection();
			stmt = conn.createStatement();
			String selectQry = "SELECT UserID FROM Users WHERE UserEmail = " + user.getUserEmail() + ";";
			ResultSet rs01 = stmt.executeQuery(selectQry);
			
			String userQuery = "INSERT INTO Users (FirstName, LastName, UserName, Email, PhoneNumber, UserPin) "
					+ "VALUES (\"" + user.getFirstName()
					+ "\", \"" + user.getLastName() + "\", \"" + user.getUserName() + "\", \"" 
					+ "\", \"" + user.getUserEmail() + "\", \""
					+ user.getUserPhone() + "\", " + user.getUserPin() + ");";;
			if (!rs01.next()){
				stmt.executeUpdate(userQuery);
			    return Response.status(Response.Status.OK).entity(UserConstants.USER_WAS_SUCCESSFULLY_CREATED).build();
			}
			
			else{
				return Response.status(Response.Status.OK).entity(UserConstants.USER_ALREADY_EXISTS).build();
			}
			

		    
		} catch (URISyntaxException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(UserConstants.USER_COULD_NOT_BE_CREATED).build();
		}
	}
	
	public Response verifyPin(User user){
		Statement stmt = null;
		int userUID = -1;
		try {
			Connection conn = DatabaseConnectionSingleton.getInstance().getConnection();
			stmt = conn.createStatement();
			String selectQry = "SELECT Pin FROM Users WHERE UserID = " + user.getUserID() + ";";
			ResultSet rs01 = stmt.executeQuery(selectQry);
			
			
			if (!rs01.next()){
			    return Response.status(Response.Status.OK).entity(UserConstants.PIN_MATCHED).build();
			}
			
			else{
				return Response.status(Response.Status.OK).entity(UserConstants.PIN_DID_NOT_MATCH).build();
			}
			

		    
		} catch (URISyntaxException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(UserConstants.PIN_COULD_NOT_BE_VERIFIED).build();
		}
	}
	
	public Response verifyPassword(User user){
		Statement stmt = null;
		int userUID = -1;
		try {
			Connection conn = DatabaseConnectionSingleton.getInstance().getConnection();
			stmt = conn.createStatement();
			String selectQry = "SELECT Pin FROM Users WHERE UserID = " + user.getUserID() + ";";
			ResultSet rs01 = stmt.executeQuery(selectQry);
			
			
			if (!rs01.next()){
			    return Response.status(Response.Status.OK).entity(UserConstants.PIN_MATCHED).build();
			}
			
			else{
				return Response.status(Response.Status.OK).entity(UserConstants.PIN_DID_NOT_MATCH).build();
			}
			

		    
		} catch (URISyntaxException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(UserConstants.PIN_COULD_NOT_BE_VERIFIED).build();
		}
	}
	
	public List<Contact> getContacts(int UserID){
		String selectQuery = "SELECT * FROM Contacts WHERE UserID = " + UserID +";";
		List<Contact> emergencyContacts = new ArrayList<Contact>();
		Statement stmt = null;
		Contact contact = new Contact();
		try{
			Connection conn = DatabaseConnectionSingleton.getInstance().getConnection();
			stmt = conn.createStatement();
			ResultSet rs01 = stmt.executeQuery(selectQuery);
			
			while(rs01.next()){
				contact.setFirstName(rs01.getString("FirstName"));
				contact.setLastName(rs01.getString("LastName"));
				contact.setContactEmail(rs01.getString("Email"));
				contact.setContactPhone(rs01.getString("PhoneNumber"));
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
	*/
}
