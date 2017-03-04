package com.au.personal.safety.test.contacts;

import static org.junit.Assert.*;

import java.net.URISyntaxException;

import org.junit.Test;

import com.au.personal.safety.contacts.Contact;
import com.au.personal.safety.contacts.ContactDB;
import com.au.personal.safety.database.DatabaseConnectionSingleton;

public class ContactDBTests {

	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	/* Test constructor 
	 * Check that (1) instance was created and (2) buildInsert created the correct value for
	 *  string variable contactQuery
	 * */
	
	/* Test buildInsert()
	 * Setting: ContactDB.contact has non-null values for FirstName, LastName, ContactEmail, ContactPhone, and UserID
	 * Result: contactDB.contactQuery has correct string value
	 * */
	
	/* Test buildInsert()
	 * Setting: ContactDB.contact has non-null values for FirstName, LastName, ContactPhone, and UserID
	 *  ContactDB.contact has a null value for ContactEmail
	 * Result: contactDB.contactQuery has correct string value
	 * */
	
	/* Test buildInsert()
	 * Setting: ContactDB.contact has non-null values for FirstName, LastName, ContactEmail, and UserID
	 *  ContactDB.contact has a null value for ContactPhone
	 * Result: contactDB.contactQuery has correct string value
	 * */
	
	/* Test sendContact() Add Contact
	 * Setting: A new contact is created and wants to be put into the database
	 *  The contact has a zero null values
	 * Result: The new contact is in the Contact table with the correct values
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
	
	/* Test sendContact() Add Contact
	 * Setting: A new contact is created and wants to be put into the database
	 *  The contact has a null value for ContactEmail
	 * Result: The new contact is in the Contact table with the correct values
	 * */
	
	/* Test sendContact() Add Contact
	 * Setting: A new contact is created and wants to be put into the database
	 *  The contact has a null value for ContactPhone
	 * Result: The new contact is in the Contact table with the correct values
	 * */
	
	/* Test sendContact() Add Contact INVALID RESPONSE RAISED
	 * Setting: A new contact is created and wants to be put into the database
	 *  The contact has null values for ContactPhone and ContactEmail
	 * Result: The new contact is not added and an Invalid Add Response Raised
	 * */
	
	
	/* Test sendContact() Modify Contact
	 * Setting: A contact is updated with new values
	 *  The contact previous had zero null values
	 *  The contact will now have a new FirstName, LastName, ContactEmail, and ContactPhone
	 * Result: The new contact is in the Contact table with the correct values
	 * */
	
	/* Test sendContact() Modify Contact
	 * Setting: A contact is updated with new values
	 *  The contact previous had zero null values
	 *  The contact will now have a new FirstName
	 * Result: The new contact is in the Contact table with the correct values
	 * */
	
	/* Test sendContact() Modify Contact
	 * Setting: A contact is updated with new values
	 *  The contact previous had zero null values
	 *  The contact will now have a new LastName
	 * Result: The new contact is in the Contact table with the correct values
	 * */
	
	/* Test sendContact() Modify Contact
	 * Setting: A contact is updated with new values
	 *  The contact previous had zero null values
	 *  The contact will now have a new ContactEmail
	 * Result: The new contact is in the Contact table with the correct values
	 * */
	
	/* Test sendContact() Modify Contact
	 * Setting: A contact is updated with new values
	 *  The contact previous had zero null values
	 *  The contact will now have a new ContactPhone
	 * Result: The new contact is in the Contact table with the correct values
	 * */
	
	/* Test sendContact() Modify Contact
	 * Setting: A contact is updated with new values
	 *  The contact previous had zero null values
	 *  The contact will now have a null ContactEmail
	 * Result: The new contact is in the Contact table with the correct values
	 * */
	
	/* Test sendContact() Modify Contact
	 * Setting: A contact is updated with new values
	 *  The contact previous had zero null values
	 *  The contact will now have a null ContactPhone
	 * Result: The new contact is in the Contact table with the correct values
	 * */
	
	/* Test sendContact() Modify Contact
	 * Setting: A contact is updated with new values
	 *  The contact previous had a null ContactEmail
	 *  The contact will now have a ContactEmail, thus no null values
	 * Result: The new contact is in the Contact table with the correct values
	 * */
	
	/* Test sendContact() Modify Contact
	 * Setting: A contact is updated with new values
	 *  The contact previous had a null ContactPhone
	 *  The contact will now have a ContactPhone, thus no null values
	 * Result: The new contact is in the Contact table with the correct values
	 * */
	
	/* Test sendContact() Modify Contact
	 * Setting: A contact is updated with new values
	 *  The contact previous had a null ContactPhone
	 *  The contact will now have a ContactPhone and null ContactEmail
	 * Result: The new contact is in the Contact table with the correct values
	 * */
	
	/* Test sendContact() Modify Contact
	 * Setting: A contact is updated with new values
	 *  The contact previous had a null ContactEmail
	 *  The contact will now have a ContactEmail and null ContactPhone
	 * Result: The new contact is in the Contact table with the correct values
	 * */
	
	
	/* Test sendContact() Modify Contact INVALID RESPONSE RAISED
	 * Setting: A contact is updated with new values
	 *  The contact previous had zero null values
	 *  The desired update has null ContactEmail and ContactPhone values
	 * Result: The current entry is unchanged and and Invalid Update Response Raised
	 * */
	
	/* Test sendContact() Modify Contact INVALID RESPONSE RAISED
	 * Setting: A contact is updated with new values
	 *  The contact previous had a null ContactPhone
	 *  The desired update has null ContactEmail and ContactPhone values
	 * Result: The current entry is unchanged and and Invalid Update Response Raised
	 * */
	
	/* Test sendContact() Modify Contact INVALID RESPONSE RAISED
	 * Setting: A contact is updated with new values
	 *  The contact previous had a null ContactEmail
	 *  The desired update has null ContactEmail and ContactPhone values
	 * Result: The current entry is unchanged and and Invalid Update Response Raised
	 * */

}
