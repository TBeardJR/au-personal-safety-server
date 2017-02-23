package com.au.personal.safety.database;

import java.sql.Timestamp;
//the Date class is the parent class of Timestamp
import java.util.Date;
import java.net.URISyntaxException;
//import com.mysql.cj.api.jdbc.Statement;
import java.sql.*;


public class Location {
	/** class variables */
	private int LocationID; //IMPORTANT: the database autogenerates this field, this will be changed from Null when getting/reading a location
	private Timestamp timeRecorded;
	private double longitude;
	private double latitude;
	private int userID;
	
	/** constructor */
  
  public Location() {
	    
		
		
	}
  
  
  
	/** methods */
	//note: the connection will already be established, these functions assume the connection is established
	
  public void setLocationID(int locID_in)
  {
  	LocationID = locID_in;
  }
  public void setTime(Timestamp time_in)
  {
  	timeRecorded = time_in;
  }
  public void setLong(double long_in)
  {
  	longitude = long_in;
  }
  public void setLat(double lat_in)
  {
  	latitude = lat_in;
  }
  public void setUserID(int userID_in)
  {
  	userID = userID_in;
  }
  
  public int getLocationID()
  {
  	return LocationID;
  }
  public Timestamp getTime()
  {
  	return timeRecorded;
  }
  public double getLong()
  {
  	return longitude;
  }
  public double getLat()
  {
  	return latitude;
  }
  public int getUserID()
  {
  	return userID;
  }
  
	/*
  website for structuring Java Query commands
  https://docs.oracle.com/javase/tutorial/jdbc/basics/processingsqlstatements.html
  */
	
	/**
	The below function generates a new entry in the Location table if an entry with matching UserID does not already exist
	If an entry already exists, the entry will be updated with the new longitude, latitude, and time
	
	The function assumes that there is only one location entry for each UserID
	*/
	
	
  public void saveNewLocation(double long_in, double lat_in, Timestamp timestamp_in, int userID_in) throws SQLException
  {
		int locationUID = -1;
      Statement stmt = null;
      String selectQry = "SELECT LocationID FROM Location WHERE UserID = " + userID_in + ";";
      String insertQry = "INSERT INTO Location (Longitude, Latitude, Time, UserID) VALUES (" + long_in + ", " 
              + lat_in + ", " + timestamp_in + ", " + userID_in + ");";
      String updateQry  = "UPDATE Location SET Longitude = " + long_in + " , Latitude = " + lat_in 
      		+ " , Time = " + timestamp_in + " WHERE LocationID = " + locationUID + " ;";
      	
      try {
      	//use a function to get the connection info
      	Connection conn = DatabaseConnectionSingleton.getInstance().getConnection();
      	stmt = conn.createStatement();
      	ResultSet rs01 = stmt.executeQuery(selectQry);
      	
      	//see if entry exists
	        if (!rs01.next())
	        {
	        	//there is no existing entry
	        	//insert a new entry
	        	stmt.executeUpdate(insertQry);
	        	
	        }
      	
	        // else, there is an entry
	        else
	        {
	            locationUID = rs01.getInt("LocationID");
	            //update the entry with the new lat, long, and time
	            stmt.executeUpdate(updateQry);
	            
	        }
	        
	        
      } catch (SQLException e) {
      	JDBCTutorialUtilities.printSQLException(e);
      	
      } catch (URISyntaxException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} finally {
      	/**
      	we do not close the connection, so do NOT do the below
      	
      	if (stmt != null) {
      		stmt.close();
      	}
      	
      	*/
      }
	    
      
  }
	
	/**
	The below function returns True if a location entry exists for the entered UserID
	Else the function returns False
	*/
	public boolean userHasAnEntry(int userID_in)
	{
	    boolean result = false;
		
		
		return result;
	}
	
	/**
	The below function returns a Location object with the location information for the inputted UserID
	This function assumes that a Location entry exists for the UserID
	*/
	public Location getLocationForUserID(int userID_in)
	{
	    Location thisLoc = new Location();
		
	    //get the values and set the values to parameters of thisLoc
	    
	    
		return thisLoc;
	}
	
	

}
