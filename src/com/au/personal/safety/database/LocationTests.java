package com.au.personal.safety.database;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import org.junit.Test;

public class LocationTests {

	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	/* Test Constructor */
	public void test01_instanceCreated() {
		Location loc01 = new Location();
		assertFalse("test01_instanceCreated error: loc01 is null\n", loc01 == null);
	}
	
	/* Test variable setters and getters */
	public void test02_locationSettersWork() {
		
	}
	
	public void test02_locationGettersWork() {
		
	}
	
	/* Test saveNewLocation(double long_in, double lat_in, Timestamp timestamp_in, int userID_in) throws SQLException */
	public void test03_throwsExceptionIfDatabaseNotConnection() {
		Location loc01 = new Location();
		boolean exceptionThrown = false;
		double long_in = 0.0;
		double lat_in = 0.0;
		Timestamp timestamp_in = new Timestamp(2017, 1, 1, 1, 1, 0, 0);
		int userID_in = 0;
		
		try {
		    loc01.saveNewLocation(long_in, lat_in, timestamp_in, userID_in);
		}
		catch (Exception e) {
			exceptionThrown = true;
		}
		assertTrue("test03_throwsExceptionIfDatabaseNotConnection error\n", exceptionThrown);
	}
	
	public void test03_noEntriesInLocationTable() {
		String errorString = "";
		boolean errorExists = false;
		
		Location loc01 = new Location();
		int resultLocactionID;
		Timestamp resultTime;
		double resultLat;
		double resultLong;
		int resultUserID;
		
		boolean exceptionThrown = false;
		double long_in = 0.0;
		double lat_in = 0.0;
		int userID_in = 0;
		DatabaseConnectionSingleton conn;
		Statement stmt = null;
		String selectEntry = "SELECT * FROM Location WHERE UserID = " + userID_in + " ;";
		
		try {
		    //initialize connection
			conn = DatabaseConnectionSingleton.getInstance();
			conn.openConnection();
			
			//USE MOCKITO INSTEAD OF CONNECTING TO ACTUAL DATABASE (INSTEAD OF PRIOR 2 LINES)
			
			loc01.saveNewLocation(long_in, lat_in, userID_in);
			
			//make sure entry was created
			stmt = conn.getConnection().createStatement();
			ResultSet rs01 = stmt.executeQuery(selectEntry);
			
			if (!rs01.next())
	        {
	        	//there is no existing entry
	        	//ERROR
				errorString = "the entry was not added to the database\n";
	        	errorExists = true;
	        	
	        }
      	
	        // else, there is an entry
	        else
	        {
	            //set the entry's info to loc01 attributes
	        	loc01.setLocationID(rs01.getInt("LocationID"));
	        	loc01.setTime(rs01.getTimestamp("Time"));
	            loc01.setLat(rs01.getDouble("Latitude"));
	            loc01.setLong(rs01.getDouble("Longitude"));
	            loc01.setUserID(rs01.getInt("UserID"));
	            
	            
	            //make sure entry's info matches input
	            
	        }
			
			
			
			
			conn.closeConnection();
		}
		catch (Exception e) {
			exceptionThrown = true;
			//if (conn != null) {
			//    conn.closeConnection();
			//}
		}
		
		assertFalse("test03_noEntriesInLocationTable error: " + errorString, errorExists);
	}
	
	public void test03_updateNeededForLocationTable() {
		
	}

}