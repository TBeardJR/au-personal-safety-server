package com.au.personal.safety.users;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import com.au.personal.safety.constants.UserConstants;
import com.au.personal.safety.contacts.Contact;
import com.au.personal.safety.database.DatabaseConnectionSingleton;

public class UserDB {

	private User user;
	
	public UserDB(User user){
		this.user = user;
	}
	
	public User getUser(){
		return user;
	}
	
	
	public Response createNewUser(){
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
	
	
	public int getUserPin(String userName){
		String selectQuery = "SELECT * FROM Users WHERE UserName = " + userName +";";
		int pin = 0;
		Statement stmt = null;
		
		try{
			Connection conn = DatabaseConnectionSingleton.getInstance().getConnection();
			stmt = conn.createStatement();
			ResultSet rs01 = stmt.executeQuery(selectQuery);
			pin = rs01.getInt("UserPin");
		} catch (URISyntaxException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pin;
	}
	
}
