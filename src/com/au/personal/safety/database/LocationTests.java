package com.au.personal.safety.database;

import static org.junit.Assert.*;

import java.sql.SQLException;
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
		Location loc01 = new Location();
		boolean exceptionThrown = false;
		double long_in = 0.0;
		double lat_in = 0.0;
		Timestamp timestamp_in = new Timestamp(2017, 1, 1, 1, 1, 0, 0);
		int userID_in = 0;
		DatabaseConnectionSingleton conn;
		
		try {
		    //initialize connection
			conn = DatabaseConnectionSingleton.getInstance();
			conn.openConnection();
			
			//USE MOCKITO INSTEAD OF CONNECTING TO ACTUAL DATABASE (INSTEAD OF PRIOR 2 LINES)
			
			loc01.saveNewLocation(long_in, lat_in, timestamp_in, userID_in);
			
			//make sure entry was created
			
			
			//make sure entry's info matches input
			
			
			
			conn.closeConnection();
		}
		catch (Exception e) {
			exceptionThrown = true;
			//if (conn != null) {
			//    conn.closeConnection();
			//}
		}
		
	}
	
	public void test03_updateNeededForLocationTable() {
		
	}

}