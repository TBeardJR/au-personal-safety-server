package com.au.personal.safety.contacts;

import static org.junit.Assert.assertEquals;
import com.au.personal.safety.database.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import javax.xml.ws.Response;

import org.junit.Test;

import com.au.personal.safety.database.DatabaseConnectionSingleton;
import com.au.personal.safety.contacts.*;

public class ContactTests {
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testSend(){
		
		Contact contact1 = new Contact();
		String First= "Sally";
		String Last = "Smith";
		String Email = "sallyS@gmail.com";
		String Phone = "251-555-4786";
		int ID = 2;
		
		contact1.setContactEmail(Email);
		contact1.setContactPhone(Phone);
		contact1.setFirstName(First);
		contact1.setLastName(Last);
		contact1.setUserID(ID);
		
		ContactDB contactDB = new ContactDB(contact1);
		
		try {
			DatabaseConnectionSingleton conn = DatabaseConnectionSingleton.getInstance();
			conn.openConnection();
			contactDB.sendContact();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		assertFalse(false);
		
	}
	
	

}
