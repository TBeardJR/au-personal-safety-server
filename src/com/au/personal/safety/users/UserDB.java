package com.au.personal.safety.users;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.ws.rs.core.Response;
//local
import com.au.personal.safety.constants.UserConstants;
import com.au.personal.safety.database.DatabaseConnectionSingleton;

//Class
public class UserDB {
	
	private User user;
	
	public UserDB(User user){
		this.user = user;
	}
	
	public User getUser(){
		return user;
	}
	
	////********** CREATE USER ********** 
	public Response createNewUser(){
		Statement stmt = null;
		
		//Create Query Statements
		String selectQuery = "SELECT UserID FROM User WHERE UserName = '" + user.getUserName() + "';";
		String insertQuery = "INSERT INTO User (UserName) " + "VALUES ('" + user.getUserName() + "');";
		
		try {
			//Connect to Database
			Connection conn = DatabaseConnectionSingleton.getInstance().getConnection();
			stmt = conn.createStatement();
			
			//Execute Select Query.
			//rs01 will have values if a user with this userName already exists
			//rs01 will be null if there is not a user with this userName
			ResultSet rs01 = stmt.executeQuery(selectQuery);
			
			//If rs01 does not have values then create the user. Return a success Response.
			if (!rs01.next()){
				stmt.executeUpdate(insertQuery);
			    return Response.status(Response.Status.OK).entity(UserConstants.USER_WAS_SUCCESSFULLY_CREATED).build();
			}
			
			//If rs01 does have values then we return that there is already a user with this username in our database.
			else{
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(UserConstants.USER_ALREADY_EXISTS).build();
			}
		}	
		//If we had a URIException return URI error response
		catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(UserConstants.USER_COULD_NOT_BE_CREATED_URI).build();
		}
		
		//If we had a SQLException return SQL error response
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(UserConstants.USER_COULD_NOT_BE_CREATED_SQL).build();
		}
		
	}
	
	//********** SET PIN ********** 
	public Response setUserPin(){
		Statement stmt = null;
		//Create Query Statements
		String selectQuery = "SELECT UserID FROM User WHERE UserName = '" + user.getUserName() + "';";
		String updateQuery = "UPDATE User SET UserPin = '" + user.getUserPin() + "' WHERE UserName = '" + user.getUserName() + "';";
		
		try{
			//Connect to Database
			Connection conn = DatabaseConnectionSingleton.getInstance().getConnection();
			stmt = conn.createStatement();
			
			//Execute Select Query.
			//rs01 will have values if a user with this userName already exists
			//rs01 will be null if there is not a user with this userName
			ResultSet rs01 = stmt.executeQuery(selectQuery);
			
			//If rs01 does not have values then return an error response. 
			if (!rs01.next()){
			    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(UserConstants.USER_DOES_NOT_EXIST).build();
			}
			
			//If rs01 does have values then we set the pin. Return a successfully set response
			else{
				stmt.executeUpdate(updateQuery);
				return Response.status(Response.Status.OK).entity(UserConstants.PIN_CREATED).build();
			}
			
		} 
		//If we had a URIException return URI error response
		catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(UserConstants.PIN_COULD_NOT_BE_CREATED_URI).build();
		}
		
		//If we had a SQLException return SQL error response
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(UserConstants.PIN_COULD_NOT_BE_CREATED_SQL).build();
		}

	}
	
	//********** GET PIN ********** 
	public String getUserPin(){
		Statement stmt = null;
		String pin;
		
		//Create Query Statements
		String selectQry = "SELECT * FROM User WHERE UserName = '" + user.getUserName() + "';";

		try{
			//Connect to Database
			Connection conn = DatabaseConnectionSingleton.getInstance().getConnection();
			stmt = conn.createStatement();
			
			//Execute Select Query.
			//rs01 will have values if a user with this userName exits
			//rs01 will be null if there is not a user with this userName
			ResultSet rs01 = stmt.executeQuery(selectQry);
			
			//If rs01 does not have values then return pin as an error. 
			if (!rs01.next()){
			    return "ERROR noUSER";
			}
			
			//If rs01 does have values then we get the pin. Return the pin.
			else{
				pin = rs01.getString("UserPin");
				return pin;
			}
			
		} 
		//If we had a URIException return URI error response
		catch (URISyntaxException e) {
			e.printStackTrace();
			return "ERROR URI";
		}
				
		//If we had a SQLException return SQL error response
		catch (SQLException e) {
			e.printStackTrace();
			return "ERROR SQL";
		}

	}
	
	public int getUserID(){
		Statement stmt = null;
		int userid;
		
		//Create Query Statements
		String selectQry = "SELECT * FROM User WHERE UserName = '" + user.getUserName() + "';";

		try{
			//Connect to Database
			Connection conn = DatabaseConnectionSingleton.getInstance().getConnection();
			stmt = conn.createStatement();
			
			//Execute Select Query.
			//rs01 will have values if a user with this userName exits
			//rs01 will be null if there is not a user with this userName
			ResultSet rs01 = stmt.executeQuery(selectQry);
			
			//If rs01 does not have values then return pin as an error. 
			if (!rs01.next()){
			    return -1;
			}
			
			//If rs01 does have values then we get the pin. Return the pin.
			else{
				userid = rs01.getInt("UserID");
				return userid;
			}
			
		} 
		//If we had a URIException return URI error response
		catch (URISyntaxException e) {
			e.printStackTrace();
			return -1;
		}
				
		//If we had a SQLException return SQL error response
		catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}

	}
}
