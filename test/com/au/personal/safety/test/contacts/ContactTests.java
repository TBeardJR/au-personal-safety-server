package com.au.personal.safety.test.contacts;

import static org.junit.Assert.*;

import java.net.URISyntaxException;

import org.junit.Test;

import com.au.personal.safety.contacts.Contact;
import com.au.personal.safety.contacts.ContactDB;
import com.au.personal.safety.database.DatabaseConnectionSingleton;

public class ContactTests {

	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	/* Test constructor */
	@Test
	public void test01_01_instanceCreated() {
		
	}
	
	/*
	 * Test Getters
	 * */
	
	/*
	 * Test Setters
	 * */
	
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
