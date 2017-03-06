package com.au.personal.safety.test.contacts;

import static org.junit.Assert.*;

import java.net.URISyntaxException;

import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.junit.Test;

import com.au.personal.safety.contacts.Contact;
import com.au.personal.safety.contacts.ContactDB;
import com.au.personal.safety.database.DatabaseConnectionSingleton;

//Sorts by methods by ascending name so that tests run in this desired order
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class ContactDBTests {

	/* methods to be called within tests */
	//contact with all attributes set to non null values
	public Contact createContact1() {
		Contact result = new Contact();
		result.setFirstName("test");
		result.setLastName("01");
		result.setUserID(1);
		result.setContactEmail("test01@email");
		result.setContactPhone("111-1111");
		return result;
	}
	//contact has a null email
	public Contact createContact2() {
		Contact result = new Contact();
		result.setFirstName("test");
		result.setLastName("02");
		result.setUserID(2);
		result.setContactPhone("222-2222");
		return result;
	}
	//contact has a null phone
	public Contact createContact3() {
		Contact result = new Contact();
		result.setFirstName("test");
		result.setLastName("03");
		result.setUserID(3);
		result.setContactEmail("test03@email");
		return result;
	}
		
	/* Test constructor 
	 * Check that (1) instance was created and (2) buildInsert created the correct value for
	 *  string variable contactQuery
	 * */
	@Test
	public void test01_01_instanceCreated() {
		
		//create a Contact object with all attributes set
		Contact contact1 = createContact1();
		
		//initialized ContactDB object with contact1
		ContactDB contactDB1 = new ContactDB(contact1);
		
		//check that the object was created
		assertNotNull("test01_01: contactDB1 is null\n", contactDB1);
		//check that the contactQuery value is correct
		String expectedCQ = "INSERT INTO Contacts (FirstName, LastName, PhoneNumber, Email, UserID) "
				+ "VALUES (\"" + "test"
				+ "\", \"" + "01"+ "\", \"" + "111-1111" + "\", \"" 
				+ "test01@email" + "\", " + "1" + ");";
		assertTrue("test01_01: contactDB1.contactQuery is: " + contactDB1.getContactQuery() + "\n should be: "
				+ expectedCQ + "\n", contactDB1.getContactQuery().equals(expectedCQ));
	}
	
	
	/* Test constructor INVALID Test
	 * Setting Entered Contract value is null 
	 * Result: NullObject? error raised?
	 * */
	@Test
	public void test01_02_invalidNullInput() {
		
		//create a Contact object set to null
		Contact contactNull = null;
		
		try {
			//initialized ContactDB object with contact1
		    ContactDB contactDB1 = new ContactDB(contactNull);
		    //if gets to here, exception was not raised, test fails
		    fail("test01_02: NullPointerException not raised");
		}
		//NullPointerException should be raised
		catch (Exception NullPointerException) {
			//test passes
		}
	}
	
	/* Test buildInsert()
	 * Setting: ContactDB.contact has non-null values for FirstName, LastName, ContactEmail, ContactPhone, and UserID
	 * Result: contactDB.contactQuery has correct string value
	 * */
	@Test
	public void test02_01_buildInsertWithAllNonNullValues() {
		//create Contact object with all attributes set
		Contact contact1 = createContact1();
		
		//since buildInsert() is run in ContactDB constructor and is based on constructor input,
		// going to call constructor to test buildInsert()
		ContactDB contactDB2 = new ContactDB(contact1);
		
		//check that contactQuery value is correct
		String expectedCQ = "INSERT INTO Contacts (FirstName, LastName, PhoneNumber, Email, UserID) "
				+ "VALUES (\"" + "test"
				+ "\", \"" + "01"+ "\", \"" + "111-1111" + "\", \"" 
				+ "test01@email" + "\", " + "1" + ");";
		assertTrue("test02_01: contactDB1.contactQuery is: " + contactDB2.getContactQuery() + "\n should be: "
				+ expectedCQ + "\n", contactDB2.getContactQuery().equals(expectedCQ));
	}
	
	
	/* Test buildInsert()
	 * Setting: ContactDB.contact has non-null values for FirstName, LastName, ContactPhone, and UserID
	 *  ContactDB.contact has a null value for ContactEmail
	 * Result: contactDB.contactQuery has correct string value
	 * */
	@Test
	public void test02_02_buildInsertWithNullEmail() {
		//create Contact object with null email
		Contact contact2 = createContact2();
				
		//since buildInsert() is run in ContactDB constructor and is based on constructor input,
		// going to call constructor to test buildInsert()
		ContactDB contactDB2 = new ContactDB(contact2);
				
		//check that contactQuery value is correct
		String expectedCQ = "INSERT INTO Contacts (FirstName, LastName, PhoneNumber, Email, UserID) "
				+ "VALUES (\"" + "test"
				+ "\", \"" + "02"+ "\", \"" + "222-2222" + "\", \"" 
				+ null + "\", " + "2" + ");";
		assertTrue("test02_01: contactDB1.contactQuery is: " + contactDB2.getContactQuery() + "\n should be: "
				+ expectedCQ + "\n", contactDB2.getContactQuery().equals(expectedCQ));
	}
	
	/* Test buildInsert()
	 * Setting: ContactDB.contact has non-null values for FirstName, LastName, ContactEmail, and UserID
	 *  ContactDB.contact has a null value for ContactPhone
	 * Result: contactDB.contactQuery has correct string value
	 * */
	@Test
	public void test02_03_buildInsertWithNullPhonel() {
		//create Contact object with null phone
		Contact contact3 = createContact3();
						
		//since buildInsert() is run in ContactDB constructor and is based on constructor input,
		// going to call constructor to test buildInsert()
		ContactDB contactDB3 = new ContactDB(contact3);
						
		//check that contactQuery value is correct
		String expectedCQ = "INSERT INTO Contacts (FirstName, LastName, PhoneNumber, Email, UserID) "
				+ "VALUES (\"" + "test"
				+ "\", \"" + "03"+ "\", \"" + null + "\", \"" 
				+ "test03@email" + "\", " + "3" + ");";
		assertTrue("test02_01: contactDB1.contactQuery is: " + contactDB3.getContactQuery() + "\n should be: "
				+ expectedCQ + "\n", contactDB3.getContactQuery().equals(expectedCQ));
	}
	
	/* Test sendContact() Add Contact
	 * Setting: A new contact is created and wants to be put into the database
	 *  The contact has a zero null values
	 * Result: The new contact is in the Contact table with the correct values
	 * */
	@Test
	public void test03_01_sendContactAddWithAllNonNullValues() {
		fail("not implemented yet");
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
	
	/* Test sendContact() Add Contact
	 * Setting: A new contact is created and wants to be put into the database
	 *  The contact has a null value for ContactEmail
	 * Result: The new contact is in the Contact table with the correct values
	 * */
	@Test
	public void test03_02_sendContactAddWithNullEmail() {
		fail("not implemented yet");
	}
	
	/* Test sendContact() Add Contact
	 * Setting: A new contact is created and wants to be put into the database
	 *  The contact has a null value for ContactPhone
	 * Result: The new contact is in the Contact table with the correct values
	 * */
	@Test
	public void test03_03_sendContactAddWithNullPhone() {
		fail("not implemented yet");
	}
	
	/* Test sendContact() Add Contact INVALID RESPONSE RAISED
	 * Setting: A new contact is created and wants to be put into the database
	 *  The contact has null values for ContactPhone and ContactEmail
	 * Result: The new contact is not added and an Invalid Add Response Raised
	 * */
	@Test
	public void test03_04_sendContactAddInvalidContactWithNoEmailNoPhone() {
		fail("not implemented yet");
	}
	
	/* Test sendContact() Modify Contact
	 * Setting: A contact is updated with new values
	 *  The contact previous had zero null values
	 *  The contact will now have a new FirstName, LastName, ContactEmail, and ContactPhone
	 * Result: The new contact is in the Contact table with the correct values
	 * */
	@Test
	public void test04_01_sendContactModifyAllValues() {
		fail("not implemented yet");
	}
	
	/* Test sendContact() Modify Contact
	 * Setting: A contact is updated with new values
	 *  The contact previous had zero null values
	 *  The contact will now have a new FirstName
	 * Result: The new contact is in the Contact table with the correct values
	 * */
	@Test
	public void test04_02_sendContactModifyFirstName() {
		fail("not implemented yet");
	}
	
	/* Test sendContact() Modify Contact
	 * Setting: A contact is updated with new values
	 *  The contact previous had zero null values
	 *  The contact will now have a new LastName
	 * Result: The new contact is in the Contact table with the correct values
	 * */
	@Test
	public void test04_03_sendContactModifyLastName() {
		fail("not implemented yet");
	}
	
	/* Test sendContact() Modify Contact
	 * Setting: A contact is updated with new values
	 *  The contact previous had zero null values
	 *  The contact will now have a new ContactEmail
	 * Result: The new contact is in the Contact table with the correct values
	 * */
	@Test
	public void test04_04_sendContactModifyEmail() {
		fail("not implemented yet");
	}
	
	/* Test sendContact() Modify Contact
	 * Setting: A contact is updated with new values
	 *  The contact previous had zero null values
	 *  The contact will now have a new ContactPhone
	 * Result: The new contact is in the Contact table with the correct values
	 * */
	@Test
	public void test04_05_sendContactModifyPhone() {
		fail("not implemented yet");
	}
	
	/* Test sendContact() Modify Contact
	 * Setting: A contact is updated with new values
	 *  The contact previous had zero null values
	 *  The contact will now have a null ContactEmail
	 * Result: The new contact is in the Contact table with the correct values
	 * */
	@Test
	public void test04_06_sendContactModifyEmailToNull() {
		fail("not implemented yet");
	}
	
	/* Test sendContact() Modify Contact
	 * Setting: A contact is updated with new values
	 *  The contact previous had zero null values
	 *  The contact will now have a null ContactPhone
	 * Result: The new contact is in the Contact table with the correct values
	 * */
	@Test
	public void test04_07_sendContactModifyPhoneToNull() {
		fail("not implemented yet");
	}
	
	/* Test sendContact() Modify Contact
	 * Setting: A contact is updated with new values
	 *  The contact previous had a null ContactEmail
	 *  The contact will now have a ContactEmail, thus no null values
	 * Result: The new contact is in the Contact table with the correct values
	 * */
	@Test
	public void test04_08_sendContactModifyNullEmail() {
		fail("not implemented yet");
	}
	
	/* Test sendContact() Modify Contact
	 * Setting: A contact is updated with new values
	 *  The contact previous had a null ContactPhone
	 *  The contact will now have a ContactPhone, thus no null values
	 * Result: The new contact is in the Contact table with the correct values
	 * */
	@Test
	public void test04_09_sendContactModifyNullPhone() {
		fail("not implemented yet");
	}
	
	/* Test sendContact() Modify Contact
	 * Setting: A contact is updated with new values
	 *  The contact previous had a null ContactPhone
	 *  The contact will now have a ContactPhone and null ContactEmail
	 * Result: The new contact is in the Contact table with the correct values
	 * */
	@Test
	public void test04_10_sendContactModifyNullPhonePlusEmailToNull() {
		fail("not implemented yet");
	}
	
	/* Test sendContact() Modify Contact
	 * Setting: A contact is updated with new values
	 *  The contact previous had a null ContactEmail
	 *  The contact will now have a ContactEmail and null ContactPhone
	 * Result: The new contact is in the Contact table with the correct values
	 * */
	@Test
	public void test04_11_sendContactModifyNullEmailPlusPhoneToNull() {
		fail("not implemented yet");
	}
	
	/* Test sendContact() Modify Contact INVALID RESPONSE RAISED
	 * Setting: A contact is updated with new values
	 *  The contact previous had zero null values
	 *  The desired update has null ContactEmail and ContactPhone values
	 * Result: The current entry is unchanged and and Invalid Update Response Raised
	 * */
	@Test
	public void test04_11_sendContactModifyInvalidContactWithNoEmailNoPhone() {
		fail("not implemented yet");
	}
	
	/* Test sendContact() Modify Contact INVALID RESPONSE RAISED
	 * Setting: A contact is updated with new values
	 *  The contact previous had a null ContactPhone
	 *  The desired update has null ContactEmail and ContactPhone values
	 * Result: The current entry is unchanged and and Invalid Update Response Raised
	 * */
	@Test
	public void test04_12_sendContactModifyInvalidContactWithNoEmailNoPhone() {
		fail("not implemented yet");
	}
	
	/* Test sendContact() Modify Contact INVALID RESPONSE RAISED
	 * Setting: A contact is updated with new values
	 *  The contact previous had a null ContactEmail
	 *  The desired update has null ContactEmail and ContactPhone values
	 * Result: The current entry is unchanged and and Invalid Update Response Raised
	 * */
	@Test
	public void test04_13_sendContactModifyInvalidContactWithNoEmailNoPhone() {
		fail("not implemented yet");
	}

}
