package com.au.personal.safety.test.contacts;

import org.junit.*; //imports the @Before, etc.
import static org.junit.Assert.*; //import the asserts

import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.ws.rs.core.Response;

import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.junit.Test;

import com.au.personal.safety.constants.ContactConstants;
import com.au.personal.safety.constants.HttpResponseConstants;
import com.au.personal.safety.contacts.Contact;
import com.au.personal.safety.contacts.ContactDB;
import com.au.personal.safety.database.DatabaseConnectionSingleton;

//Sorts by methods by ascending name so that tests run in this desired order
@FixMethodOrder(MethodSorters.NAME_ASCENDING)


/* IMPORTANT:
 *  This class assumes that the Contact entered for ContactDB class is always valid, please look
 *  at "ContactResourceValidator" class for the definition of a valid contact.
 *  Note: The ContactResourceValidator class is in charge of checking that the Contact is valid.
 */

/* IMPORTNAT2"
 *  Will need to alter and add tests for new column "PhoneCarrier" after the column is added to DB
 *  and know valid values for this field
 */

public class ContactDBTests {
	
	DatabaseConnectionSingleton conn;
	Statement stmt;
	String databaseNullValue = "null";  //the database saves nulls as "null"
	
	/* variable to be called within test */
	/* desired values that have been saved in the database for contacts 1, 2, and 3 */
	// IMPORTANT: we want empty strings saved in database, not Null values, b/c this is easier to implement
	int contact1UserID = 1;
	String contact1FirstName = "test";
	String contact1LastName = "01";
	String contact1Email = "test01@email";
	String contact1Phone = "1111111111";
	String contact1Carrier = "@vtext.com";
	
	String selectContact1Qry = "SELECT * FROM Contacts WHERE FirstName = \"" + contact1FirstName + "\" AND " 
	    + "LastName = \"" + contact1LastName + "\" AND UserID = " + contact1UserID 
	    + " AND Email = \"" + contact1Email + "\" AND PhoneNumber = \"" + contact1Phone + "\" AND ContactCarrier = \""
		+ contact1Carrier + "\" ORDER BY ContactID ;";
	
	int contact2UserID = 2;
	String contact2FirstName = "test";
	String contact2LastName = "02";
	String contact2Email = null;
	String contact2Phone = "2222222222";
	String contact2Carrier = "@vtext.com";
	
	String selectContact2Qry = "SELECT * FROM Contacts WHERE FirstName = \"" + contact2FirstName + "\" AND " 
	    + "LastName = \"" + contact2LastName + "\" " + "AND UserID = " + contact2UserID 
	    + " AND Email = \"" + contact2Email + "\" AND PhoneNumber = \"" + contact2Phone + "\" AND ContactCarrier = \""
		+ contact2Carrier + "\" ORDER BY ContactID ;";
	
	int contact3UserID = 3;
	String contact3FirstName = "test";
	String contact3LastName = "03";
	String contact3Email = "test03@email";
	String contact3Phone = null;
	String contact3Carrier = null;
	
	String selectContact3Qry = "SELECT * FROM Contacts WHERE FirstName = \"" + contact3FirstName + "\" AND " 
		    + "LastName = \"" + contact3LastName + "\" " + "AND UserID = " + contact3UserID 
		    + " AND Email = \"" + contact3Email + "\" AND PhoneNumber = \"" + contact3Phone + "\" AND ContactCarrier = \""
		+ contact3Carrier + "\" ORDER BY ContactID ;";
	
	//note the below contact is a second emergency contact for user with UserID = 1
	int contact4UserID = 1;
	String contact4FirstName = "test";
	String contact4LastName = "04";
	String contact4Email = "test04@email";
	String contact4Phone = "4444444444";
	String contact4Carrier = "@vtext.com";
	
	String selectContact4Qry = "SELECT * FROM Contacts WHERE FirstName = \"" + contact4FirstName + "\" AND " 
	    + "LastName = \"" + contact4LastName + "\" AND UserID = " + contact4UserID 
	    + " AND Email = \"" + contact4Email + "\" AND PhoneNumber = \"" + contact4Phone + "\" AND ContactCarrier = \""
		+ contact4Carrier + "\" ORDER BY ContactID ;";
	
	
	String selectIvContactQry = "SELECT * FROM Contacts WHERE FirstName = \"" + null + "\" AND " 
		    + "LastName = \"" + null + "\" " + "AND UserID = " + "-1"
		    + " AND Email = \"" + null + "\" AND PhoneNumber = \"" + null + "\" AND ContactCarrier = \""
		    + null + "\" ORDER BY ContactID ;";
	/* Set connection variables to test database */
	
	
	
	/* methods to be called within tests */
	//contact with all attributes set to non null values
	public Contact createContact1() {
		Contact result = new Contact(contact1FirstName, contact1LastName, contact1Email, contact1Phone,
		    contact1Carrier, contact1UserID);
		return result;
	}
	//contact has a null email
	public Contact createContact2() {
		Contact result = new Contact(contact2FirstName, contact2LastName, contact2Email, contact2Phone,
		    contact2Carrier, contact2UserID);
		return result;
	}
	//contact has a null phone
	public Contact createContact3() {
		Contact result = new Contact(contact3FirstName, contact3LastName, contact3Email, contact3Phone,
		    contact3Carrier, contact3UserID);
		return result;
	}
	//contact has same UserID as contact1
		public Contact createContact4() {
			Contact result = new Contact(contact4FirstName, contact4LastName, contact4Email, contact4Phone,
			    contact4Carrier, contact4UserID);
			return result;
		}
	//contact has a null Email and a null Phone Number
	public Contact createInvalidContact() {
		Contact result = new Contact(null, null, null, null, null, -1);
		return result;
	}
	
	/* Before each test:
	 *  - connect to the database
	 *  - clear the Contacts table
	 */
	@Before
	public void beforeEachTest() {
		
		//initialize connection variables
		//DatabaseConnectionSingleton conn;
		conn = null;
		stmt = null;
		
		try {
			//open connection
			conn = DatabaseConnectionSingleton.getInstance();
			conn.openConnection();
			stmt = conn.getConnection().createStatement();
			//clear database
			String sqlString = "DELETE FROM Contacts;";
			stmt.executeUpdate(sqlString);
			//close connection
			conn.closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	/* Test constructor 
	 * Check that (1) instance was created and (2) buildInsert created the correct value for
	 *  string variable contactQuery
	 * */
	/*
	@Test
	public void test01_01_instanceCreated() {
		
		//create a Contact object with all attributes set
		Contact contact1 = createContact1();
		
		//initialized ContactDB object with contact1
		ContactDB contactDB1 = new ContactDB(contact1);
		
		//check that the object was created
		assertNotNull("test01_01: contactDB1 is null\n", contactDB1);
		
		
	}
	
	*/
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
	 * 
	 * */
	/*
	
	Hmm, "buildInsert()" is not used anywhere in the code, I think we can comment this function out
	
	*/
	
	
	/* Test sendContact()
	 * Setting: contact is valid (contact1), the Contacts table is empty
	 * Result: the contact has been added
	 * 
	*/
	@Test
	public void test03_01_sendContactAddContact1() {
		//initialized error variables
		String errorString = "";
		boolean errorExists = false;
		boolean exceptionThrown = false;
		Contact resultingContact = new Contact();
		Response resultingResponse = null;
		Response expectedResponse = Response.status(Response.Status.OK).entity(ContactConstants.CONTACT_WAS_SUCCESSFULLY_ADDED).build();
		//initialize connection variables
		DatabaseConnectionSingleton conn;
		Statement stmt = null;
		
		//create Contact object with all attributes set
		Contact contact1 = createContact1();
		
		//since buildInsert() is run in ContactDB constructor and is based on constructor input,
		// going to call constructor to test buildInsert()
		ContactDB contactDB = new ContactDB(contact1);
		
		try {
			//open connection
			conn = DatabaseConnectionSingleton.getInstance();
			conn.openConnection();
			
			//call function that will add the contact to database
			resultingResponse = contactDB.sendContact();
			
			//make sure entry was created and added correctly
			String selectQry = selectContact1Qry;
			
			try {
				stmt = conn.getConnection().createStatement();
				ResultSet rs01 = stmt.executeQuery(selectQry);
				
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
		        	resultingContact.setContactID(rs01.getInt("ContactID"));
		        	resultingContact.setFirstName(rs01.getString("FirstName"));
		        	resultingContact.setLastName(rs01.getString("LastName"));
		        	resultingContact.setContactEmail(rs01.getString("Email"));
		        	resultingContact.setContactPhone(rs01.getString("PhoneNumber"));
		        	resultingContact.setContactCarrier(rs01.getString("ContactCarrier"));
		        	resultingContact.setUserID(rs01.getInt("UserID"));
		        }
				
			} catch (Exception e) {
				exceptionThrown = true;
				e.printStackTrace();
			}
			
			//close connection
			conn.closeConnection();
			
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			exceptionThrown = true;
			e.printStackTrace();
		}
		
		//Test with asserts
		//make sure no exception was thrown
		assertFalse("test03_01 error: exception thrown\n", exceptionThrown);
		//make sure entry was save to the database
		assertFalse("test03_01 error: " + errorString, errorExists);
		//make sure entry's info matches input
		assertNotNull("test03_01 error: Contact is null\n", resultingContact.getContactID());
		assertEquals("test03_01 error: UserID incorrect, have: " + resultingContact.getUserID() 
				+ "\nwanted: "+ contact1UserID +"\n", contact1UserID, resultingContact.getUserID());
		assertTrue("test03_01 error: FirstName incorrect have: " + resultingContact.getFirstName()
				+ "\nwanted: " + contact1FirstName + "\n", resultingContact.getFirstName().equals(contact1FirstName));
		assertTrue("test03_01 error: LastName incorrect have: " + resultingContact.getLastName()
				+ "\nwanted: "+ contact1LastName +"\n", resultingContact.getLastName().equals(contact1LastName));
		assertTrue("test03_01 error: Email incorrect have: " + resultingContact.getContactEmail()
				+ "\nwanted: "+ contact1Email +"\n", resultingContact.getContactEmail().equals(contact1Email));
		assertTrue("test03_01 error: PhoneNumber incorrect have: " + resultingContact.getContactPhone()
				+ "\nwanted: "+ contact1Phone +"\n", resultingContact.getContactPhone().equals(contact1Phone));
		assertTrue("test03_01 error: ContactCarrier incorrect have: " + resultingContact.getContactCarrier()
		        + "\nwanted: "+ contact1Carrier +"\n", resultingContact.getContactCarrier().equals(contact1Carrier));
		assertTrue("test03_01 error: Response incorrect have: " + resultingResponse.getEntity().toString()
		        + "\nwanted: "+ expectedResponse.getEntity().toString() +"\n" , resultingResponse.getEntity().equals(expectedResponse.getEntity()));
		
	}
	
	
	/* Test sendContact()
	 * Setting: contact is valid (contact2), the Contacts table is empty
	 * Result: the contact has been added
	 * 
	*/
	@Test
	public void test03_02_sendContactAddContact2() {
		//initialized error variables
		String errorString = "";
		boolean errorExists = false;
		boolean exceptionThrown = false;
		Contact resultingContact = new Contact();
		Response resultingResponse = null;
		Response expectedResponse = Response.status(Response.Status.OK).entity(ContactConstants.CONTACT_WAS_SUCCESSFULLY_ADDED).build();
		
		//initialize connection variables
		DatabaseConnectionSingleton conn;
		Statement stmt = null;
		
		//create Contact object with all attributes set
		Contact contact2 = createContact2();
		
		//since buildInsert() is run in ContactDB constructor and is based on constructor input,
		// going to call constructor to test buildInsert()
		ContactDB contactDB = new ContactDB(contact2);
		
		try {
			//open connection
			conn = DatabaseConnectionSingleton.getInstance();
			conn.openConnection();
			
			//call function that will add the contact to database
			resultingResponse = contactDB.sendContact();
			
			//make sure entry was created and added correctly
			String selectQry = selectContact2Qry;
			
			try {
				stmt = conn.getConnection().createStatement();
				ResultSet rs01 = stmt.executeQuery(selectQry);
				
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
		        	resultingContact.setContactID(rs01.getInt("ContactID"));
		        	resultingContact.setFirstName(rs01.getString("FirstName"));
		        	resultingContact.setLastName(rs01.getString("LastName"));
		        	resultingContact.setContactEmail(rs01.getString("Email"));
		        	resultingContact.setContactPhone(rs01.getString("PhoneNumber")); 
		        	resultingContact.setContactCarrier(rs01.getString("ContactCarrier")); 
		        	resultingContact.setUserID(rs01.getInt("UserID"));
		        }
				
			} catch (Exception e) {
				exceptionThrown = true;
				e.printStackTrace();
			}
			
			//close connection
			conn.closeConnection();
			
		} catch (URISyntaxException e) {
		    // TODO Auto-generated catch block
			exceptionThrown = true;
			e.printStackTrace();
		}
		
		//Test with asserts
		//Test with asserts
		//make sure no exception was thrown
		assertFalse("test03_02 error: exception thrown\n", exceptionThrown);
		//make sure entry was save to the database
		assertFalse("test03_02 error: " + errorString, errorExists);
		//make sure entry's info matches input
		assertNotNull("test03_02 error: Contact is null\n", resultingContact.getContactID());
		assertEquals("test03_02 error: UserID incorrect, have: " + resultingContact.getUserID() 
				+ "\nwanted: "+ contact1UserID +"\n", contact2UserID, resultingContact.getUserID());
		assertTrue("test03_02 error: FirstName incorrect have: " + resultingContact.getFirstName()
				+ "\nwanted: " + contact2FirstName + "\n", resultingContact.getFirstName().equals(contact2FirstName));
		assertTrue("test03_02 error: LastName incorrect have: " + resultingContact.getLastName()
				+ "\nwanted: "+ contact2LastName +"\n", resultingContact.getLastName().equals(contact2LastName));
		//assert that the email is "null"
		assertTrue("test03_02 error: Email incorrect have: " + resultingContact.getContactEmail()
		        + "\nwanted: "+ databaseNullValue +"\n", resultingContact.getContactEmail().equals(databaseNullValue));
		assertTrue("test03_02 error: PhoneNumber incorrect have: " + resultingContact.getContactPhone()
				+ "\nwanted: "+ contact2Phone +"\n", resultingContact.getContactPhone().equals(contact2Phone));
		assertTrue("test03_02 error: ContactCarrier incorrect have: " + resultingContact.getContactCarrier()
				+ "\nwanted: "+ contact2Carrier +"\n", resultingContact.getContactCarrier().equals(contact2Carrier));
		assertTrue("test03_01 error: Response incorrect have: " + resultingResponse.getEntity().toString()
                + "\nwanted: "+ expectedResponse.getEntity().toString() +"\n" , resultingResponse.getEntity().equals(expectedResponse.getEntity()));
	}
	
	
	/* Test sendContact()
	 * Setting: contact is valid (contact3), the Contacts table is empty
	 * Result: the contact has been added
	 * 
	*/
	@Test
	public void test03_03_sendContactAddContact3() {
		//initialized error variables
		String errorString = "";
		boolean errorExists = false;
		boolean exceptionThrown = false;
		Contact resultingContact = new Contact();
		Response resultingResponse = null;
		Response expectedResponse = Response.status(Response.Status.OK).entity(ContactConstants.CONTACT_WAS_SUCCESSFULLY_ADDED).build();
		
		//initialize connection variables
		DatabaseConnectionSingleton conn;
		Statement stmt = null;
		
		//create Contact object with all attributes set
		Contact contact3 = createContact3();
		
		//since buildInsert() is run in ContactDB constructor and is based on constructor input,
		// going to call constructor to test buildInsert()
		ContactDB contactDB = new ContactDB(contact3);
		
		try {
			//open connection
			conn = DatabaseConnectionSingleton.getInstance();
			conn.openConnection();
			
			//call function that will add the contact to database
			resultingResponse = contactDB.sendContact();
			
			//make sure entry was created and added correctly
			String selectQry = selectContact3Qry;
			
			try {
				stmt = conn.getConnection().createStatement();
				ResultSet rs01 = stmt.executeQuery(selectQry);
				
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
		        	resultingContact.setContactID(rs01.getInt("ContactID"));
		        	resultingContact.setFirstName(rs01.getString("FirstName"));
		        	resultingContact.setLastName(rs01.getString("LastName"));
		        	resultingContact.setContactEmail(rs01.getString("Email"));
		        	resultingContact.setContactPhone(rs01.getString("PhoneNumber")); 
		        	resultingContact.setContactCarrier(rs01.getString("ContactCarrier")); 
		        	resultingContact.setUserID(rs01.getInt("UserID"));
		        }
				
			} catch (Exception e) {
				exceptionThrown = true;
				e.printStackTrace();
			}
			
			//close connection
			conn.closeConnection();
			
		} catch (URISyntaxException e) {
		    // TODO Auto-generated catch block
			exceptionThrown = true;
			e.printStackTrace();
		}
		
		//Test with asserts
		//Test with asserts
		//make sure no exception was thrown
		assertFalse("test03_03 error: exception thrown\n", exceptionThrown);
		//make sure entry was save to the database
		assertFalse("test03_03 error: " + errorString, errorExists);
		//make sure entry's info matches input
		assertNotNull("test03_03 error: Contact is null\n", resultingContact.getContactID());
		assertEquals("test03_03 error: UserID incorrect, have: " + resultingContact.getUserID() 
				+ "\nwanted: "+ contact1UserID +"\n", contact3UserID, resultingContact.getUserID());
		assertTrue("test03_03 error: FirstName incorrect have: " + resultingContact.getFirstName()
				+ "\nwanted: " + contact3FirstName + "\n", resultingContact.getFirstName().equals(contact3FirstName));
		assertTrue("test03_03 error: LastName incorrect have: " + resultingContact.getLastName()
				+ "\nwanted: "+ contact3LastName +"\n", resultingContact.getLastName().equals(contact3LastName));
		assertTrue("test03_03 error: Email incorrect have: " + resultingContact.getContactEmail()
				+ "\nwanted: "+ contact3Email +"\n", resultingContact.getContactEmail().equals(contact3Email));
		//assert that the phone number and carrier are "null"
		assertTrue("test03_02 error: Email incorrect have: " + resultingContact.getContactPhone()
                + "\nwanted: "+ databaseNullValue +"\n", resultingContact.getContactPhone().equals(databaseNullValue));
		assertTrue("test03_02 error: Email incorrect have: " + resultingContact.getContactCarrier()
                + "\nwanted: "+ databaseNullValue +"\n", resultingContact.getContactCarrier().equals(databaseNullValue));
		assertTrue("test03_01 error: Response incorrect have: " + resultingResponse.getEntity().toString()
                + "\nwanted: "+ expectedResponse.getEntity().toString() +"\n" , resultingResponse.getEntity().equals(expectedResponse.getEntity()));
	}
	
	/* Test sendContact()
	 * Setting: contact is valid (invalidContact), the Contacts table is empty
	 * Result: the following response has been raised and the table is unchanged         
	     (Response.status(Response.Status.BAD_REQUEST).entity(HttpResponseConstants.INVALID_CONTACT).build())
	 * 
	*/
	@Test
	public void test03_04_sendContactAddInvalidContact() {
		//initialized error variables
		String entryErrorString = "";
		boolean entryExists = false;
		boolean exceptionThrown = false;
		//Contact resultingContact = new Contact();
		Response resultingResponse = null;
		Response expectedResponse = Response.status(Response.Status.BAD_REQUEST).entity(HttpResponseConstants.INVALID_CONTACT).build();
		
		//initialize connection variables
		DatabaseConnectionSingleton conn;
		Statement stmt = null;
		
		//create Contact object with all attributes set
		Contact contactIv = createInvalidContact();
		
		//since buildInsert() is run in ContactDB constructor and is based on constructor input,
		// going to call constructor to test buildInsert()
		ContactDB contactDB = new ContactDB(contactIv);
		
		try {
			//open connection
			conn = DatabaseConnectionSingleton.getInstance();
			conn.openConnection();
			
			//call function that will add the contact to database
			resultingResponse = contactDB.sendContact();
			
			//make sure entry was created and added correctly
			String selectQry = selectContact3Qry;
			
			try {
				stmt = conn.getConnection().createStatement();
				ResultSet rs01 = stmt.executeQuery(selectQry);
				
				if (!rs01.next())
		        {
		        	//the entry was added to the database
		        	//ERROR
					entryErrorString = "the entry was not added to the database\n";
		        	entryExists = false;
		        }
				
				// else, there is an entry
		        else
		        {
		        	//the entry was added to the database
		        	//ERROR
		        	entryErrorString = "the entry was added to the database\n";
		        	entryExists = true;
		        }
				
			} catch (Exception e) {
				exceptionThrown = true;
				e.printStackTrace();
			}
			
			//close connection
			conn.closeConnection();
			
		} catch (URISyntaxException e) {
		    // TODO Auto-generated catch block
			exceptionThrown = true;
			e.printStackTrace();
		}
		
		//Test with asserts
		//Test with asserts
		//make sure no exception was thrown
		assertFalse("test03_04 error: exception thrown\n", exceptionThrown);
		//make sure entry was save to the database
		assertFalse("test03_04 error: " + entryErrorString, entryExists);
		assertTrue("test03_04 error: Response incorrect have: " + resultingResponse.getEntity().toString()
                + "\nwanted: "+ expectedResponse.getEntity().toString() +"\n" , resultingResponse.getEntity().equals(expectedResponse.getEntity()));
	}
	
	
	/* Test sendContact() modify
	 * Setting: contact1 is added to the empty Contacts table; then the contact1 is modified to have a new phoneNumber
	 * Result: contact1 has been added and has the new phoneNumber after the modification
	 * 
	*/
	@Test
	public void test03_05_sendContactModifyContact1Phone() {
		//initialized error variables
		String errorString = "";
		boolean errorExists = false;
		String dupEntryErrorString = "";
		boolean dupEntryError = false;
		
		boolean exceptionThrown = false;
		Contact resultingContact = new Contact();
		Response resultingResponse = null;
		Response expectedResponse = Response.status(Response.Status.OK).entity(ContactConstants.CONTACT_WAS_SUCCESSFULLY_UPDATED).build();
		String modifiedPhoneNum = "1000000000";
		
		//initialize connection variables
		DatabaseConnectionSingleton conn;
		Statement stmt = null;
		
		//create Contact object with all attributes set
		Contact contact1 = createContact1();
		
		//since buildInsert() is run in ContactDB constructor and is based on constructor input,
		// going to call constructor to test buildInsert()
		ContactDB contactDB = new ContactDB(contact1);
		
		try {
			//open connection
			conn = DatabaseConnectionSingleton.getInstance();
			conn.openConnection();
			
			//call function that will add the contact to database
			resultingResponse = contactDB.sendContact();
			
			//make sure entry was created and added correctly
			String selectQry = selectContact1Qry;
			
			try {
				stmt = conn.getConnection().createStatement();
				ResultSet rs01 = stmt.executeQuery(selectQry);
				
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
		            //all we want is the contactID, so we can add this value to our Contact object with a modified phoneNum
		        	contact1.setContactID(rs01.getInt("ContactID"));
		        }
				
				//modify the phoneNumber or contact1 and then re-send the contact into the database
				contact1.setContactPhone(modifiedPhoneNum);
				contactDB = new ContactDB(contact1);
				resultingResponse = contactDB.sendContact();
				
				String selectModified = "SELECT * FROM Contacts WHERE FirstName = \"" + contact1FirstName + "\" AND " 
					    + "LastName = \"" + contact1LastName + "\" AND UserID = " + contact1UserID 
					    + " AND Email = \"" + contact1Email + "\" AND PhoneNumber = \"" + modifiedPhoneNum + "\" AND ContactCarrier = \""
						+ contact1Carrier + "\" ORDER BY ContactID ;";
				
				rs01 = stmt.executeQuery(selectModified);
				
				if (!rs01.next())
		        {
		        	//there is no existing entry
		        	//ERROR
					errorString = "the entry was not modified in the database\n";
		        	errorExists = true;
		        }
				
				// else, there is an entry
		        else
		        {
		            //set values to resultingContact
		        	resultingContact.setContactID(rs01.getInt("ContactID"));
		        	resultingContact.setFirstName(rs01.getString("FirstName"));
		        	resultingContact.setLastName(rs01.getString("LastName"));
		        	resultingContact.setContactEmail(rs01.getString("Email"));
		        	resultingContact.setContactPhone(rs01.getString("PhoneNumber")); 
		        	resultingContact.setContactCarrier(rs01.getString("ContactCarrier")); 
		        	resultingContact.setUserID(rs01.getInt("UserID"));
		        }
				
				//if there is another entry in the database, then error (the sendContact() added a 2nd entry)
				if (rs01.next()) {
					dupEntryErrorString = "The sendContact() added a 2nd entry when it should have modified\n";
					dupEntryError = true;
				}
				
			} catch (Exception e) {
				exceptionThrown = true;
				e.printStackTrace();
			}
			
			//close connection
			conn.closeConnection();
			
		} catch (URISyntaxException e) {
		    // TODO Auto-generated catch block
			exceptionThrown = true;
			e.printStackTrace();
		}
		
		//Test with asserts
		//make sure no exception was thrown
		assertFalse("test03_05 error: exception thrown\n", exceptionThrown);
		//make sure entry was save to the database
		assertFalse("test03_05 error: " + errorString, errorExists);
		//make sure no 2nd entry was added
		assertFalse("test03_05 error: " + dupEntryErrorString, dupEntryError);
		//make sure entry's info matches input
		assertNotNull("test03_05 error: Contact is null\n", resultingContact.getContactID());
		assertEquals("test03_05 error: UserID incorrect, have: " + resultingContact.getUserID() 
				+ "\nwanted: "+ contact1UserID +"\n", contact1UserID, resultingContact.getUserID());
		assertTrue("test03_05 error: FirstName incorrect have: " + resultingContact.getFirstName()
				+ "\nwanted: " + contact1FirstName + "\n", resultingContact.getFirstName().equals(contact1FirstName));
		assertTrue("test03_05 error: LastName incorrect have: " + resultingContact.getLastName()
				+ "\nwanted: "+ contact1LastName +"\n", resultingContact.getLastName().equals(contact1LastName));
		assertTrue("test03_05 error: Email incorrect have: " + resultingContact.getContactEmail()
				+ "\nwanted: "+ contact1Email +"\n", resultingContact.getContactEmail().equals(contact1Email));
		assertTrue("test03_05 error: PhoneNumber incorrect have: " + resultingContact.getContactPhone()
				+ "\nwanted: "+ modifiedPhoneNum +"\n", resultingContact.getContactPhone().equals(modifiedPhoneNum));
		assertTrue("test03_05 error: ContactCarrier incorrect have: " + resultingContact.getContactCarrier()
				+ "\nwanted: "+ contact1Carrier +"\n", resultingContact.getContactCarrier().equals(contact1Carrier));
		assertTrue("test03_05 error: Response incorrect have: " + resultingResponse.getEntity().toString()
                + "\nwanted: "+ expectedResponse.getEntity().toString() +"\n" , resultingResponse.getEntity().equals(expectedResponse.getEntity()));
	}
	
	/* Test sendContact() modify
	 * Setting: contact1 is added to the empty Contacts table; then the contact1 is modified to have a new contactCarrier
	 * Result: contact1 has been added and has the new contactCarrier after the modification
	 * 
	*/
	@Test
	public void test03_06_sendContactModifyContact1Carrier() {
		//initialized error variables
		String errorString = "";
		boolean errorExists = false;
		String dupEntryErrorString = "";
		boolean dupEntryError = false;
		
		boolean exceptionThrown = false;
		Contact resultingContact = new Contact();
		Response resultingResponse = null;
		Response expectedResponse = Response.status(Response.Status.OK).entity(ContactConstants.CONTACT_WAS_SUCCESSFULLY_UPDATED).build();
		String modifiedCarrier = "@txt.att.net";
		
		//initialize connection variables
		DatabaseConnectionSingleton conn;
		Statement stmt = null;
		
		//create Contact object with all attributes set
		Contact contact1 = createContact1();
		
		//since buildInsert() is run in ContactDB constructor and is based on constructor input,
		// going to call constructor to test buildInsert()
		ContactDB contactDB = new ContactDB(contact1);
		
		try {
			//open connection
			conn = DatabaseConnectionSingleton.getInstance();
			conn.openConnection();
			
			//call function that will add the contact to database
			resultingResponse = contactDB.sendContact();
			
			//make sure entry was created and added correctly
			String selectQry = selectContact1Qry;
			
			try {
				stmt = conn.getConnection().createStatement();
				ResultSet rs01 = stmt.executeQuery(selectQry);
				
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
		            //all we want is the contactID, so we can add this value to our Contact object with a modified phoneNum
		        	contact1.setContactID(rs01.getInt("ContactID"));
		        }
				
				//modify the phoneNumber or contact1 and then re-send the contact into the database
				contact1.setContactCarrier(modifiedCarrier);
				contactDB = new ContactDB(contact1);
				resultingResponse = contactDB.sendContact();
				
				String selectModified = "SELECT * FROM Contacts WHERE FirstName = \"" + contact1FirstName + "\" AND " 
					    + "LastName = \"" + contact1LastName + "\" AND UserID = " + contact1UserID 
					    + " AND Email = \"" + contact1Email + "\" AND PhoneNumber = \"" + contact1Phone + "\" AND ContactCarrier = \""
						+ modifiedCarrier + "\" ORDER BY ContactID ;";
				
				rs01 = stmt.executeQuery(selectModified);
				
				if (!rs01.next())
		        {
		        	//there is no existing entry
		        	//ERROR
					errorString = "the entry was not modified in the database\n";
		        	errorExists = true;
		        }
				
				// else, there is an entry
		        else
		        {
		            //set values to resultingContact
		        	resultingContact.setContactID(rs01.getInt("ContactID"));
		        	resultingContact.setFirstName(rs01.getString("FirstName"));
		        	resultingContact.setLastName(rs01.getString("LastName"));
		        	resultingContact.setContactEmail(rs01.getString("Email"));
		        	resultingContact.setContactPhone(rs01.getString("PhoneNumber")); 
		        	resultingContact.setContactCarrier(rs01.getString("ContactCarrier")); 
		        	resultingContact.setUserID(rs01.getInt("UserID"));
		        }
				
				//if there is another entry in the database, then error (the sendContact() added a 2nd entry)
				if (rs01.next()) {
					dupEntryErrorString = "The sendContact() added a 2nd entry when it should have modified\n";
					dupEntryError = true;
				}
				
			} catch (Exception e) {
				exceptionThrown = true;
				e.printStackTrace();
			}
			
			//close connection
			conn.closeConnection();
			
		} catch (URISyntaxException e) {
		    // TODO Auto-generated catch block
			exceptionThrown = true;
			e.printStackTrace();
		}
		
		//Test with asserts
		//make sure no exception was thrown
		assertFalse("test03_06 error: exception thrown\n", exceptionThrown);
		//make sure entry was save to the database
		assertFalse("test03_06 error: " + errorString, errorExists);
		//make sure no 2nd entry was added
		assertFalse("test03_06 error: " + dupEntryErrorString, dupEntryError);
		//make sure entry's info matches input
		assertNotNull("test03_06 error: Contact is null\n", resultingContact.getContactID());
		assertEquals("test03_06 error: UserID incorrect, have: " + resultingContact.getUserID() 
				+ "\nwanted: "+ contact1UserID +"\n", contact1UserID, resultingContact.getUserID());
		assertTrue("test03_06 error: FirstName incorrect have: " + resultingContact.getFirstName()
				+ "\nwanted: " + contact1FirstName + "\n", resultingContact.getFirstName().equals(contact1FirstName));
		assertTrue("test03_06 error: LastName incorrect have: " + resultingContact.getLastName()
				+ "\nwanted: "+ contact1LastName +"\n", resultingContact.getLastName().equals(contact1LastName));
		assertTrue("test03_06 error: Email incorrect have: " + resultingContact.getContactEmail()
				+ "\nwanted: "+ contact1Email +"\n", resultingContact.getContactEmail().equals(contact1Email));
		assertTrue("test03_06 error: PhoneNumber incorrect have: " + resultingContact.getContactPhone()
				+ "\nwanted: "+ contact1Phone +"\n", resultingContact.getContactPhone().equals(contact1Phone));
		assertTrue("test03_06 error: ContactCarrier incorrect have: " + resultingContact.getContactCarrier()
				+ "\nwanted: "+ modifiedCarrier +"\n", resultingContact.getContactCarrier().equals(modifiedCarrier));
		assertTrue("test03_06 error: Response incorrect have: " + resultingResponse.getEntity().toString()
                + "\nwanted: "+ expectedResponse.getEntity().toString() +"\n" , resultingResponse.getEntity().equals(expectedResponse.getEntity()));
	}
	
	/* Test sendContact() modify
	 * Setting: contact1 is added to the empty Contacts table; then the contact1 is modified to have a new email
	 * Result: contact1 has been added and has the new email after the modification
	 * 
	*/
	@Test
	public void test03_07_sendContactModifyContact1Email() {
		//initialized error variables
		String errorString = "";
		boolean errorExists = false;
		String dupEntryErrorString = "";
		boolean dupEntryError = false;
		
		boolean exceptionThrown = false;
		Contact resultingContact = new Contact();
		Response resultingResponse = null;
		Response expectedResponse = Response.status(Response.Status.OK).entity(ContactConstants.CONTACT_WAS_SUCCESSFULLY_UPDATED).build();
		String modifiedEmail = "modtest01@email.com";
		
		//initialize connection variables
		DatabaseConnectionSingleton conn;
		Statement stmt = null;
		
		//create Contact object with all attributes set
		Contact contact1 = createContact1();
		
		//since buildInsert() is run in ContactDB constructor and is based on constructor input,
		// going to call constructor to test buildInsert()
		ContactDB contactDB = new ContactDB(contact1);
		
		try {
			//open connection
			conn = DatabaseConnectionSingleton.getInstance();
			conn.openConnection();
			
			//call function that will add the contact to database
			resultingResponse = contactDB.sendContact();
			
			//make sure entry was created and added correctly
			String selectQry = selectContact1Qry;
			
			try {
				stmt = conn.getConnection().createStatement();
				ResultSet rs01 = stmt.executeQuery(selectQry);
				
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
		            //all we want is the contactID, so we can add this value to our Contact object with a modified phoneNum
		        	contact1.setContactID(rs01.getInt("ContactID"));
		        }
				
				//modify the phoneNumber or contact1 and then re-send the contact into the database
				contact1.setContactEmail(modifiedEmail);
				contactDB = new ContactDB(contact1);
				resultingResponse = contactDB.sendContact();
				
				String selectModified = "SELECT * FROM Contacts WHERE FirstName = \"" + contact1FirstName + "\" AND " 
					    + "LastName = \"" + contact1LastName + "\" AND UserID = " + contact1UserID 
					    + " AND Email = \"" + modifiedEmail + "\" AND PhoneNumber = \"" + contact1Phone + "\" AND ContactCarrier = \""
						+ contact1Carrier + "\" ORDER BY ContactID ;";
				
				rs01 = stmt.executeQuery(selectModified);
				
				if (!rs01.next())
		        {
		        	//there is no existing entry
		        	//ERROR
					errorString = "the entry was not modified in the database\n";
		        	errorExists = true;
		        }
				
				// else, there is an entry
		        else
		        {
		            //set values to resultingContact
		        	resultingContact.setContactID(rs01.getInt("ContactID"));
		        	resultingContact.setFirstName(rs01.getString("FirstName"));
		        	resultingContact.setLastName(rs01.getString("LastName"));
		        	resultingContact.setContactEmail(rs01.getString("Email"));
		        	resultingContact.setContactPhone(rs01.getString("PhoneNumber")); 
		        	resultingContact.setContactCarrier(rs01.getString("ContactCarrier")); 
		        	resultingContact.setUserID(rs01.getInt("UserID"));
		        }
				
				//if there is another entry in the database, then error (the sendContact() added a 2nd entry)
				if (rs01.next()) {
					dupEntryErrorString = "The sendContact() added a 2nd entry when it should have modified\n";
					dupEntryError = true;
				}
				
			} catch (Exception e) {
				exceptionThrown = true;
				e.printStackTrace();
			}
			
			//close connection
			conn.closeConnection();
			
		} catch (URISyntaxException e) {
		    // TODO Auto-generated catch block
			exceptionThrown = true;
			e.printStackTrace();
		}
		
		//Test with asserts
		//make sure no exception was thrown
		assertFalse("test03_07 error: exception thrown\n", exceptionThrown);
		//make sure entry was save to the database
		assertFalse("test03_07 error: " + errorString, errorExists);
		//make sure no 2nd entry was added
		assertFalse("test03_07 error: " + dupEntryErrorString, dupEntryError);
		//make sure entry's info matches input
		assertNotNull("test03_07 error: Contact is null\n", resultingContact.getContactID());
		assertEquals("test03_07 error: UserID incorrect, have: " + resultingContact.getUserID() 
				+ "\nwanted: "+ contact1UserID +"\n", contact1UserID, resultingContact.getUserID());
		assertTrue("test03_07 error: FirstName incorrect have: " + resultingContact.getFirstName()
				+ "\nwanted: " + contact1FirstName + "\n", resultingContact.getFirstName().equals(contact1FirstName));
		assertTrue("test03_07 error: LastName incorrect have: " + resultingContact.getLastName()
				+ "\nwanted: "+ contact1LastName +"\n", resultingContact.getLastName().equals(contact1LastName));
		assertTrue("test03_07 error: Email incorrect have: " + resultingContact.getContactEmail()
				+ "\nwanted: "+ modifiedEmail +"\n", resultingContact.getContactEmail().equals(modifiedEmail));
		assertTrue("test03_07 error: PhoneNumber incorrect have: " + resultingContact.getContactPhone()
				+ "\nwanted: "+ contact1Phone +"\n", resultingContact.getContactPhone().equals(contact1Phone));
		assertTrue("test03_07 error: ContactCarrier incorrect have: " + resultingContact.getContactCarrier()
				+ "\nwanted: "+ contact1Carrier +"\n", resultingContact.getContactCarrier().equals(contact1Carrier));
		assertTrue("test03_07 error: Response incorrect have: " + resultingResponse.getEntity().toString()
                + "\nwanted: "+ expectedResponse.getEntity().toString() +"\n" , resultingResponse.getEntity().equals(expectedResponse.getEntity()));
	}
	
	/* Test sendContact() modify
	 * Setting: contact1 is added to the empty Contacts table; then the contact1 is modified to have a new userID
	 * Result: contact1 has been added and has the new userID after the modification
	 * 
	*/
	
	/*
	 * HOWEVER, the database and app are set up in a way that ASSUMES the user will not be editing the UserID, so we do not need to test the above
	 */
	
	
	/* Test sendContact() modify
	 * Setting: contact1 is added to the empty Contacts table; then the contact1 is modified to have a null phoneNumber and null contactCarrier
	 * Result: contact1 has been added and has the new phoneNumber after the modification
	 * 
	*/
	@Test
	public void test03_09_sendContactModifyContact1PhoneNull() {
		//initialized error variables
		String errorString = "";
		boolean errorExists = false;
		String dupEntryErrorString = "";
		boolean dupEntryError = false;
		
		boolean exceptionThrown = false;
		Contact resultingContact = new Contact();
		Response resultingResponse = null;
		Response expectedResponse = Response.status(Response.Status.OK).entity(ContactConstants.CONTACT_WAS_SUCCESSFULLY_UPDATED).build();
		String modifiedPhoneNum = null;
		String modifiedCarrier = null;
		
		//initialize connection variables
		DatabaseConnectionSingleton conn;
		Statement stmt = null;
		
		//create Contact object with all attributes set
		Contact contact1 = createContact1();
		
		//since buildInsert() is run in ContactDB constructor and is based on constructor input,
		// going to call constructor to test buildInsert()
		ContactDB contactDB = new ContactDB(contact1);
		
		try {
			//open connection
			conn = DatabaseConnectionSingleton.getInstance();
			conn.openConnection();
			
			//call function that will add the contact to database
			resultingResponse = contactDB.sendContact();
			
			//make sure entry was created and added correctly
			String selectQry = selectContact1Qry;
			
			try {
				stmt = conn.getConnection().createStatement();
				ResultSet rs01 = stmt.executeQuery(selectQry);
				
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
		            //all we want is the contactID, so we can add this value to our Contact object with a modified phoneNum
		        	contact1.setContactID(rs01.getInt("ContactID"));
		        }
				
				//modify the phoneNumber or contact1 and then re-send the contact into the database
				contact1.setContactPhone(modifiedPhoneNum);
				contact1.setContactCarrier(modifiedCarrier);
				contactDB = new ContactDB(contact1);
				resultingResponse = contactDB.sendContact();
				
				String selectModified = "SELECT * FROM Contacts WHERE FirstName = \"" + contact1FirstName + "\" AND " 
					    + "LastName = \"" + contact1LastName + "\" AND UserID = " + contact1UserID 
					    + " AND Email = \"" + contact1Email + "\" AND PhoneNumber = \"" + modifiedPhoneNum + "\" AND ContactCarrier = \""
						+ modifiedCarrier + "\" ORDER BY ContactID ;";
				
				rs01 = stmt.executeQuery(selectModified);
				
				if (!rs01.next())
		        {
		        	//there is no existing entry
		        	//ERROR
					errorString = "the entry was not modified in the database\n";
		        	errorExists = true;
		        }
				
				// else, there is an entry
		        else
		        {
		            //set values to resultingContact
		        	resultingContact.setContactID(rs01.getInt("ContactID"));
		        	resultingContact.setFirstName(rs01.getString("FirstName"));
		        	resultingContact.setLastName(rs01.getString("LastName"));
		        	resultingContact.setContactEmail(rs01.getString("Email"));
		        	resultingContact.setContactPhone(rs01.getString("PhoneNumber")); 
		        	resultingContact.setContactCarrier(rs01.getString("ContactCarrier")); 
		        	resultingContact.setUserID(rs01.getInt("UserID"));
		        }
				
				//if there is another entry in the database, then error (the sendContact() added a 2nd entry)
				if (rs01.next()) {
					dupEntryErrorString = "The sendContact() added a 2nd entry when it should have modified\n";
					dupEntryError = true;
				}
				
			} catch (Exception e) {
				exceptionThrown = true;
				e.printStackTrace();
			}
			
			//close connection
			conn.closeConnection();
			
		} catch (URISyntaxException e) {
		    // TODO Auto-generated catch block
			exceptionThrown = true;
			e.printStackTrace();
		}
		
		//Test with asserts
		//make sure no exception was thrown
		assertFalse("test03_09 error: exception thrown\n", exceptionThrown);
		//make sure entry was save to the database
		assertFalse("test03_09 error: " + errorString, errorExists);
		//make sure no 2nd entry was added
		assertFalse("test03_09 error: " + dupEntryErrorString, dupEntryError);
		//make sure entry's info matches input
		assertNotNull("test03_09 error: Contact is null\n", resultingContact.getContactID());
		assertEquals("test03_09 error: UserID incorrect, have: " + resultingContact.getUserID() 
				+ "\nwanted: "+ contact1UserID +"\n", contact1UserID, resultingContact.getUserID());
		assertTrue("test03_09 error: FirstName incorrect have: " + resultingContact.getFirstName()
				+ "\nwanted: " + contact1FirstName + "\n", resultingContact.getFirstName().equals(contact1FirstName));
		assertTrue("test03_09 error: LastName incorrect have: " + resultingContact.getLastName()
				+ "\nwanted: "+ contact1LastName +"\n", resultingContact.getLastName().equals(contact1LastName));
		assertTrue("test03_09 error: Email incorrect have: " + resultingContact.getContactEmail()
				+ "\nwanted: "+ contact1Email +"\n", resultingContact.getContactEmail().equals(contact1Email));
		//assert that the phone number is "null"
		assertTrue("test03_02 error: Email incorrect have: " + resultingContact.getContactPhone()
                + "\nwanted: "+ databaseNullValue +"\n", resultingContact.getContactPhone().equals(databaseNullValue));
		assertTrue("test03_09 error: ContactCarrier incorrect have: " + resultingContact.getContactCarrier()
				+ "\nwanted: "+ databaseNullValue +"\n", resultingContact.getContactCarrier().equals(databaseNullValue));
		assertTrue("test03_09 error: Response incorrect have: " + resultingResponse.getEntity().toString()
                + "\nwanted: "+ expectedResponse.getEntity().toString() +"\n" , resultingResponse.getEntity().equals(expectedResponse.getEntity()));
	}
	
	/* Test sendContact() modify
	 * Setting: contact1 is added to the empty Contacts table; then the contact1 is modified to have a null contactCarrier
	 * Result: contact1 has been added and the following response has been raised 
	     (Response.status(Response.Status.BAD_REQUEST).entity(HttpResponseConstants.INVALID_PHONE_CARRIER_SELECTED).build();)
	 * 
	*/
	@Test
	public void test03_10_sendContactModifyContact1CarrierNull() {
		//initialized error variables
		String errorString = "";
		boolean errorExists = false;
		String dupEntryErrorString = "";
		boolean dupEntryError = false;
		
		boolean exceptionThrown = false;
		Contact resultingContact = new Contact();
		Response resultingResponse = null;
		Response expectedResponse = Response.status(Response.Status.BAD_REQUEST).entity(HttpResponseConstants.INVALID_PHONE_CARRIER_SELECTED).build();
		String modifiedCarrier = null;
		
		//initialize connection variables
		DatabaseConnectionSingleton conn;
		Statement stmt = null;
		
		//create Contact object with all attributes set
		Contact contact1 = createContact1();
		
		//since buildInsert() is run in ContactDB constructor and is based on constructor input,
		// going to call constructor to test buildInsert()
		ContactDB contactDB = new ContactDB(contact1);
		
		try {
			//open connection
			conn = DatabaseConnectionSingleton.getInstance();
			conn.openConnection();
			
			//call function that will add the contact to database
			resultingResponse = contactDB.sendContact();
			
			//make sure entry was created and added correctly
			String selectQry = selectContact1Qry;
			
			try {
				stmt = conn.getConnection().createStatement();
				ResultSet rs01 = stmt.executeQuery(selectQry);
				
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
		            //all we want is the contactID, so we can add this value to our Contact object with a modified phoneNum
		        	contact1.setContactID(rs01.getInt("ContactID"));
		        }
				
				//modify the phoneNumber or contact1 and then re-send the contact into the database
				contact1.setContactCarrier(modifiedCarrier);
				contactDB = new ContactDB(contact1);
				resultingResponse = contactDB.sendContact();
				
				//check that the contact is unmodified in the database
				
				String unchangedSelect = "SELECT * FROM Contacts WHERE FirstName = \"" + contact1FirstName + "\" AND " 
					    + "LastName = \"" + contact1LastName + "\" AND UserID = " + contact1UserID 
					    + " AND Email = \"" + contact1Email + "\" AND PhoneNumber = \"" + contact1Phone + "\" AND ContactCarrier = \""
						+ contact1Carrier +  "\"" + " AND ContactID = " + contact1.getContactID() + " ORDER BY ContactID ;";
				
				rs01 = stmt.executeQuery(unchangedSelect);
				
				if (!rs01.next())
		        {
		        	//there is no existing entry
		        	//ERROR
					errorString = "the entry was modified in the database when it should not have changed\n";
		        	errorExists = true;
		        }
				
				// else, there is an entry
		        else
		        {
		            //set values to resultingContact
		        	resultingContact.setContactID(rs01.getInt("ContactID"));
		        	resultingContact.setFirstName(rs01.getString("FirstName"));
		        	resultingContact.setLastName(rs01.getString("LastName"));
		        	resultingContact.setContactEmail(rs01.getString("Email"));
		        	resultingContact.setContactPhone(rs01.getString("PhoneNumber")); 
		        	resultingContact.setContactCarrier(rs01.getString("ContactCarrier")); 
		        	resultingContact.setUserID(rs01.getInt("UserID"));
		        }
				
				//if there is another entry in the database, then error (the sendContact() added a 2nd entry)
				if (rs01.next()) {
					dupEntryErrorString = "The sendContact() added a 2nd entry when it should have modified\n";
					dupEntryError = true;
				}
				
			} catch (Exception e) {
				exceptionThrown = true;
				e.printStackTrace();
			}
			
			//close connection
			conn.closeConnection();
			
		} catch (URISyntaxException e) {
		    // TODO Auto-generated catch block
			exceptionThrown = true;
			e.printStackTrace();
		}
		
		//Test with asserts
		//make sure no exception was thrown
		assertFalse("test03_10 error: exception thrown\n", exceptionThrown);
		//make sure entry was save to the database
		assertFalse("test03_10 error: " + errorString, errorExists);
		//make sure no 2nd entry was added
		assertFalse("test03_10 error: " + dupEntryErrorString, dupEntryError);
		//make sure entry's info matches input
		assertNotNull("test03_10 error: Contact is null\n", resultingContact.getContactID());
		assertEquals("test03_10 error: UserID incorrect, have: " + resultingContact.getUserID() 
				+ "\nwanted: "+ contact1UserID +"\n", contact1UserID, resultingContact.getUserID());
		assertTrue("test03_10 error: FirstName incorrect have: " + resultingContact.getFirstName()
				+ "\nwanted: " + contact1FirstName + "\n", resultingContact.getFirstName().equals(contact1FirstName));
		assertTrue("test03_10 error: LastName incorrect have: " + resultingContact.getLastName()
				+ "\nwanted: "+ contact1LastName +"\n", resultingContact.getLastName().equals(contact1LastName));
		assertTrue("test03_10 error: Email incorrect have: " + resultingContact.getContactEmail()
				+ "\nwanted: "+ contact1Email +"\n", resultingContact.getContactEmail().equals(contact1Email));
		assertTrue("test03_10 error: PhoneNumber incorrect have: " + resultingContact.getContactPhone()
				+ "\nwanted: "+ contact1Phone +"\n", resultingContact.getContactPhone().equals(contact1Phone));
		//assert that the carrier is "null"
		assertTrue("test03_02 error: Email incorrect have: " + resultingContact.getContactCarrier()
                + "\nwanted: "+ contact1Carrier +"\n", resultingContact.getContactCarrier().equals(contact1Carrier));
		assertTrue("test03_10 error: Response incorrect have: " + resultingResponse.getEntity().toString()
                + "\nwanted: "+ expectedResponse.getEntity().toString() +"\n" , resultingResponse.getEntity().equals(expectedResponse.getEntity()));
	}
	
	/* Test sendContact() modify
	 * Setting: contact1 is added to the empty Contacts table; then the contact1 is modified to have a null email
	 * Result: contact1 has been added and has the new email after the modification
	 * 
	*/
	@Test
	public void test03_11_sendContactModifyContact1Email() {
		//initialized error variables
		String errorString = "";
		boolean errorExists = false;
		String dupEntryErrorString = "";
		boolean dupEntryError = false;
		
		boolean exceptionThrown = false;
		Contact resultingContact = new Contact();
		Response resultingResponse = null;
		Response expectedResponse = Response.status(Response.Status.OK).entity(ContactConstants.CONTACT_WAS_SUCCESSFULLY_UPDATED).build();
		String modifiedEmail = null;
		
		//initialize connection variables
		DatabaseConnectionSingleton conn;
		Statement stmt = null;
		
		//create Contact object with all attributes set
		Contact contact1 = createContact1();
		
		//since buildInsert() is run in ContactDB constructor and is based on constructor input,
		// going to call constructor to test buildInsert()
		ContactDB contactDB = new ContactDB(contact1);
		
		try {
			//open connection
			conn = DatabaseConnectionSingleton.getInstance();
			conn.openConnection();
			
			//call function that will add the contact to database
			resultingResponse = contactDB.sendContact();
			
			//make sure entry was created and added correctly
			String selectQry = selectContact1Qry;
			
			try {
				stmt = conn.getConnection().createStatement();
				ResultSet rs01 = stmt.executeQuery(selectQry);
				
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
		            //all we want is the contactID, so we can add this value to our Contact object with a modified phoneNum
		        	contact1.setContactID(rs01.getInt("ContactID"));
		        }
				
				//modify the phoneNumber or contact1 and then re-send the contact into the database
				contact1.setContactEmail(modifiedEmail);
				contactDB = new ContactDB(contact1);
				resultingResponse = contactDB.sendContact();
				
				String selectModified = "SELECT * FROM Contacts WHERE FirstName = \"" + contact1FirstName + "\" AND " 
					    + "LastName = \"" + contact1LastName + "\" AND UserID = " + contact1UserID 
					    + " AND Email = \"" + modifiedEmail + "\" AND PhoneNumber = \"" + contact1Phone + "\" AND ContactCarrier = \""
						+ contact1Carrier + "\" ORDER BY ContactID ;";
				
				rs01 = stmt.executeQuery(selectModified);
				
				if (!rs01.next())
		        {
		        	//there is no existing entry
		        	//ERROR
					errorString = "the entry was not modified in the database\n";
		        	errorExists = true;
		        }
				
				// else, there is an entry
		        else
		        {
		            //set values to resultingContact
		        	resultingContact.setContactID(rs01.getInt("ContactID"));
		        	resultingContact.setFirstName(rs01.getString("FirstName"));
		        	resultingContact.setLastName(rs01.getString("LastName"));
		        	resultingContact.setContactEmail(rs01.getString("Email"));
		        	resultingContact.setContactPhone(rs01.getString("PhoneNumber")); 
		        	resultingContact.setContactCarrier(rs01.getString("ContactCarrier")); 
		        	resultingContact.setUserID(rs01.getInt("UserID"));
		        }
				
				//if there is another entry in the database, then error (the sendContact() added a 2nd entry)
				if (rs01.next()) {
					dupEntryErrorString = "The sendContact() added a 2nd entry when it should have modified\n";
					dupEntryError = true;
				}
				
			} catch (Exception e) {
				exceptionThrown = true;
				e.printStackTrace();
			}
			
			//close connection
			conn.closeConnection();
			
		} catch (URISyntaxException e) {
		    // TODO Auto-generated catch block
			exceptionThrown = true;
			e.printStackTrace();
		}
		
		//Test with asserts
		//make sure no exception was thrown
		assertFalse("test03_11 error: exception thrown\n", exceptionThrown);
		//make sure entry was save to the database
		assertFalse("test03_11 error: " + errorString, errorExists);
		//make sure no 2nd entry was added
		assertFalse("test03_11 error: " + dupEntryErrorString, dupEntryError);
		//make sure entry's info matches input
		assertNotNull("test03_11 error: Contact is null\n", resultingContact.getContactID());
		assertEquals("test03_11 error: UserID incorrect, have: " + resultingContact.getUserID() 
				+ "\nwanted: "+ contact1UserID +"\n", contact1UserID, resultingContact.getUserID());
		assertTrue("test03_11 error: FirstName incorrect have: " + resultingContact.getFirstName()
				+ "\nwanted: " + contact1FirstName + "\n", resultingContact.getFirstName().equals(contact1FirstName));
		assertTrue("test03_11 error: LastName incorrect have: " + resultingContact.getLastName()
				+ "\nwanted: "+ contact1LastName +"\n", resultingContact.getLastName().equals(contact1LastName));
		//assert that the email is "null"
		assertTrue("test03_02 error: Email incorrect have: " + resultingContact.getContactEmail()
                + "\nwanted: "+ databaseNullValue +"\n", resultingContact.getContactEmail().equals(databaseNullValue));
		assertTrue("test03_11 error: PhoneNumber incorrect have: " + resultingContact.getContactPhone()
				+ "\nwanted: "+ contact1Phone +"\n", resultingContact.getContactPhone().equals(contact1Phone));
		assertTrue("test03_11 error: ContactCarrier incorrect have: " + resultingContact.getContactCarrier()
				+ "\nwanted: "+ contact1Carrier +"\n", resultingContact.getContactCarrier().equals(contact1Carrier));
		assertTrue("test03_11 error: Response incorrect have: " + resultingResponse.getEntity().toString()
                + "\nwanted: "+ expectedResponse.getEntity().toString() +"\n" , resultingResponse.getEntity().equals(expectedResponse.getEntity()));
	}
	
	/* Test sendContact() modify
	 * Setting: contact1 is added to the empty Contacts table; then the contact1 is modified to have a -1 userID
	 * Result: contact1 has been added and the following response has been raised 
	     (Response.status(Response.Status.BAD_REQUEST).entity(HttpResponseConstants.INVALID_CONTACT).build())
	 * 
	*/
	
	/*
	 * HOWEVER, the database and app are set up in a way that ASSUMES the user will not be editing the UserID, so we do not need to test the above
	 */
	
	/* Test sendContact() modify
	 * Setting: contact1 is added to the empty Contacts table; then the contact1 is modified to have a 10 userID
	 * Result: contact1 has been modified to have a userID of 10
	 * 
	*/
	
	/*
	 * HOWEVER, the database and app are set up in a way that ASSUMES the user will not be editing the UserID, so we do not need to test the above
	 */
	
	/* Test sendContact() modify
	 * Setting: contact2 is added to the empty Contacts table; then the contact2 is modified to now have an email
	 * Result: contact2 has been added and now has an email after the modification
	 * 
	*/
	@Test
	public void test03_14_sendContactModifyContact2Email() {
		//initialized error variables
		String errorString = "";
		boolean errorExists = false;
		String dupEntryErrorString = "";
		boolean dupEntryError = false;
		
		boolean exceptionThrown = false;
		Contact resultingContact = new Contact();
		Response resultingResponse = null;
		Response expectedResponse = Response.status(Response.Status.OK).entity(ContactConstants.CONTACT_WAS_SUCCESSFULLY_UPDATED).build();
		String modifiedEmail = "modtest02@email.com";
		
		//initialize connection variables
		DatabaseConnectionSingleton conn;
		Statement stmt = null;
		
		//create Contact object with all attributes set
		Contact contact2 = createContact2();
		
		//since buildInsert() is run in ContactDB constructor and is based on constructor input,
		// going to call constructor to test buildInsert()
		ContactDB contactDB = new ContactDB(contact2);
		
		try {
			//open connection
			conn = DatabaseConnectionSingleton.getInstance();
			conn.openConnection();
			
			//call function that will add the contact to database
			resultingResponse = contactDB.sendContact();
			
			//make sure entry was created and added correctly
			String selectQry = selectContact2Qry;
			
			try {
				stmt = conn.getConnection().createStatement();
				ResultSet rs01 = stmt.executeQuery(selectQry);
				
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
		            //all we want is the contactID, so we can add this value to our Contact object with a modified phoneNum
		        	contact2.setContactID(rs01.getInt("ContactID"));
		        }
				
				//modify the phoneNumber or contact1 and then re-send the contact into the database
				contact2.setContactEmail(modifiedEmail);
				contactDB = new ContactDB(contact2);
				resultingResponse = contactDB.sendContact();
				
				String selectModified = "SELECT * FROM Contacts WHERE FirstName = \"" + contact2FirstName + "\" AND " 
					    + "LastName = \"" + contact2LastName + "\" AND UserID = " + contact2UserID 
					    + " AND Email = \"" + modifiedEmail + "\" AND PhoneNumber = \"" + contact2Phone + "\" AND ContactCarrier = \""
						+ contact2Carrier + "\" ORDER BY ContactID ;";
				
				rs01 = stmt.executeQuery(selectModified);
				
				if (!rs01.next())
		        {
		        	//there is no existing entry
		        	//ERROR
					errorString = "the entry was not modified in the database\n";
		        	errorExists = true;
		        }
				
				// else, there is an entry
		        else
		        {
		            //set values to resultingContact
		        	resultingContact.setContactID(rs01.getInt("ContactID"));
		        	resultingContact.setFirstName(rs01.getString("FirstName"));
		        	resultingContact.setLastName(rs01.getString("LastName"));
		        	resultingContact.setContactEmail(rs01.getString("Email"));
		        	resultingContact.setContactPhone(rs01.getString("PhoneNumber")); 
		        	resultingContact.setContactCarrier(rs01.getString("ContactCarrier")); 
		        	resultingContact.setUserID(rs01.getInt("UserID"));
		        }
				
				//if there is another entry in the database, then error (the sendContact() added a 2nd entry)
				if (rs01.next()) {
					dupEntryErrorString = "The sendContact() added a 2nd entry when it should have modified\n";
					dupEntryError = true;
				}
				
			} catch (Exception e) {
				exceptionThrown = true;
				e.printStackTrace();
			}
			
			//close connection
			conn.closeConnection();
			
		} catch (URISyntaxException e) {
		    // TODO Auto-generated catch block
			exceptionThrown = true;
			e.printStackTrace();
		}
		
		//Test with asserts
		//make sure no exception was thrown
		assertFalse("test03_14 error: exception thrown\n", exceptionThrown);
		//make sure entry was save to the database
		assertFalse("test03_14 error: " + errorString, errorExists);
		//make sure no 2nd entry was added
		assertFalse("test03_14 error: " + dupEntryErrorString, dupEntryError);
		//make sure entry's info matches input
		assertNotNull("test03_14 error: Contact is null\n", resultingContact.getContactID());
		assertEquals("test03_14 error: UserID incorrect, have: " + resultingContact.getUserID() 
				+ "\nwanted: "+ contact1UserID +"\n", contact1UserID, resultingContact.getUserID());
		assertTrue("test03_14 error: FirstName incorrect have: " + resultingContact.getFirstName()
				+ "\nwanted: " + contact1FirstName + "\n", resultingContact.getFirstName().equals(contact1FirstName));
		assertTrue("test03_14 error: LastName incorrect have: " + resultingContact.getLastName()
				+ "\nwanted: "+ contact1LastName +"\n", resultingContact.getLastName().equals(contact1LastName));
		assertTrue("test03_14 error: Email incorrect have: " + resultingContact.getContactEmail()
				+ "\nwanted: "+ modifiedEmail +"\n", resultingContact.getContactEmail().equals(modifiedEmail));
		assertTrue("test03_14 error: PhoneNumber incorrect have: " + resultingContact.getContactPhone()
				+ "\nwanted: "+ contact1Phone +"\n", resultingContact.getContactPhone().equals(contact1Phone));
		assertTrue("test03_1 error: ContactCarrier incorrect have: " + resultingContact.getContactCarrier()
				+ "\nwanted: "+ contact1Carrier +"\n", resultingContact.getContactCarrier().equals(contact1Carrier));
		assertTrue("test03_14 error: Response incorrect have: " + resultingResponse.getEntity().toString()
                + "\nwanted: "+ expectedResponse.getEntity().toString() +"\n" , resultingResponse.getEntity().equals(expectedResponse.getEntity()));
	}
	
	/* Test sendContact() modify
	 * No need to test modifying contact2 so that
	 *    - a null phoneNumber -> response raised
	 *    - a null contactCarrier -> response raised
	 *    - a null userID -> response raised
	 * because these were tested with contact1 and contact2 will act the same (filled values becoming null to give an invalid contact)
	 * 
	*/
	
	/* Test sendContact() modify
	 * No need to test modifying contact2 so that
	 *    - a phoneNumber gets a new value -> new valid contact exists
	 *    - a contactCarrier gets a new value -> new valid contact exists
	 *    - a userID gets a new value -> new valid contact exists
	 * because these were tested with contact1 and contact2 will act the same (filled values being changed to give a valid contact)
	 * 
	*/
	
	
	/* Test sendContact() modify
	 * Setting: contact3 is added to the empty Contacts table; then the contact3 is modified to now have a phoneNumber and contactCarrier
	 * Result: contact3 has been added and now has a phoneNumber and contactCarrier after the modification
	 * 
	*/
	@Test
	public void test03_15_sendContactModifyContact3PhoneNumAndCarrier() {
		//initialized error variables
		String errorString = "";
		boolean errorExists = false;
		String dupEntryErrorString = "";
		boolean dupEntryError = false;
		
		boolean exceptionThrown = false;
		Contact resultingContact = new Contact();
		Response resultingResponse = null;
		Response expectedResponse = Response.status(Response.Status.OK).entity(ContactConstants.CONTACT_WAS_SUCCESSFULLY_UPDATED).build();
		String modifiedPhoneNum = "3333333333";
		String modifiedCarrier = "@txt.att.net";
		
		//initialize connection variables
		DatabaseConnectionSingleton conn;
		Statement stmt = null;
		
		//create Contact object with all attributes set
		Contact contact3 = createContact3();
		
		//since buildInsert() is run in ContactDB constructor and is based on constructor input,
		// going to call constructor to test buildInsert()
		ContactDB contactDB = new ContactDB(contact3);
		
		try {
			//open connection
			conn = DatabaseConnectionSingleton.getInstance();
			conn.openConnection();
			
			//call function that will add the contact to database
			resultingResponse = contactDB.sendContact();
			
			//make sure entry was created and added correctly
			String selectQry = selectContact1Qry;
			
			try {
				stmt = conn.getConnection().createStatement();
				ResultSet rs01 = stmt.executeQuery(selectQry);
				
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
		            //all we want is the contactID, so we can add this value to our Contact object with a modified phoneNum
		        	contact3.setContactID(rs01.getInt("ContactID"));
		        }
				
				//modify the phoneNumber and contactCarrier of contact3 and then re-send the contact into the database
				contact3.setContactPhone(modifiedPhoneNum);
				contact3.setContactCarrier(modifiedCarrier);
				contactDB = new ContactDB(contact3);
				resultingResponse = contactDB.sendContact();
				
				String selectModified = "SELECT * FROM Contacts WHERE FirstName = \"" + contact3FirstName + "\" AND " 
					    + "LastName = \"" + contact3LastName + "\" AND UserID = " + contact3UserID 
					    + " AND Email = \"" + contact3Email + "\" AND PhoneNumber = \"" + modifiedPhoneNum + "\" AND ContactCarrier = \""
						+ modifiedCarrier + "\" ORDER BY ContactID ;";
				
				rs01 = stmt.executeQuery(selectModified);
				
				if (!rs01.next())
		        {
		        	//there is no existing entry
		        	//ERROR
					errorString = "the entry was not modified in the database\n";
		        	errorExists = true;
		        }
				
				// else, there is an entry
		        else
		        {
		            //set values to resultingContact
		        	resultingContact.setContactID(rs01.getInt("ContactID"));
		        	resultingContact.setFirstName(rs01.getString("FirstName"));
		        	resultingContact.setLastName(rs01.getString("LastName"));
		        	resultingContact.setContactEmail(rs01.getString("Email"));
		        	resultingContact.setContactPhone(rs01.getString("PhoneNumber")); 
		            resultingContact.setContactCarrier(rs01.getString("ContactCarrier"));
		        	resultingContact.setUserID(rs01.getInt("UserID"));
		        }
				
				//if there is another entry in the database, then error (the sendContact() added a 2nd entry)
				if (rs01.next()) {
					dupEntryErrorString = "The sendContact() added a 2nd entry when it should have modified\n";
					dupEntryError = true;
				}
				
			} catch (Exception e) {
				exceptionThrown = true;
				e.printStackTrace();
			}
			
			//close connection
			conn.closeConnection();
			
		} catch (URISyntaxException e) {
		    // TODO Auto-generated catch block
			exceptionThrown = true;
			e.printStackTrace();
		}
		
		//Test with asserts
		//make sure no exception was thrown
		assertFalse("test03_15 error: exception thrown\n", exceptionThrown);
		//make sure entry was save to the database
		assertFalse("test03_15 error: " + errorString, errorExists);
		//make sure no 2nd entry was added
		assertFalse("test03_15 error: " + dupEntryErrorString, dupEntryError);
		//make sure entry's info matches input
		assertNotNull("test03_15 error: Contact is null\n", resultingContact.getContactID());
		assertEquals("test03_15 error: UserID incorrect, have: " + resultingContact.getUserID() 
				+ "\nwanted: "+ contact3UserID +"\n", contact3UserID, resultingContact.getUserID());
		assertTrue("test03_15 error: FirstName incorrect have: " + resultingContact.getFirstName()
				+ "\nwanted: " + contact3FirstName + "\n", resultingContact.getFirstName().equals(contact3FirstName));
		assertTrue("test03_15 error: LastName incorrect have: " + resultingContact.getLastName()
				+ "\nwanted: "+ contact3LastName +"\n", resultingContact.getLastName().equals(contact3LastName));
		assertTrue("test03_15 error: Email incorrect have: " + resultingContact.getContactEmail()
				+ "\nwanted: "+ contact3Email +"\n", resultingContact.getContactEmail().equals(contact3Email));
		assertTrue("test03_15 error: PhoneNumber incorrect have: " + resultingContact.getContactPhone()
				+ "\nwanted: "+ modifiedPhoneNum +"\n", resultingContact.getContactPhone().equals(modifiedPhoneNum));
		assertTrue("test03_15 error: ContactCarrier incorrect have: " + resultingContact.getContactCarrier()
				+ "\nwanted: "+ modifiedCarrier +"\n", resultingContact.getContactCarrier().equals(modifiedCarrier));
		assertTrue("test03_15 error: Response incorrect have: " + resultingResponse.getEntity().toString()
                + "\nwanted: "+ expectedResponse.getEntity().toString() +"\n" , resultingResponse.getEntity().equals(expectedResponse.getEntity()));
	}
	
	/* Test sendContact() modify
	 * No need to test modifying contact3 so that
	 *    - a null email -> response raised
	 *    - a null userID -> response raised
	 * because these were tested with contact1 and contact2 will act the same (filled values becoming null to give an invalid contact)
	 * 
	*/
	
	/* Test sendContact() modify
	 * No need to test modifying contact3 so that
	 *    - an email gets a new value -> new valid contact exists
	 *    - a userID gets a new value -> new valid contact exists
	 * because these were tested with contact1 and contact2 will act the same (filled values being changed to give a valid contact)
	 * 
	*/
	
	/* Test sendContact() modify
	 * Setting: contact1 is added, contact4 is added, and contact 4 is modified to have a null email
	 * Result: contact1 is unchanged and contact4 now has a null email
	 */
	
	@Test
	public void test03_16_sendContactModifySecondsContact1Email() {
		//initialized error variables
		String errorString = "";
		boolean errorExists = false;
		String dupEntryErrorString = "";
		boolean dupEntryError = false;
		
		Response aResponse = null; // this is the response for sending contact1 to the database
		Response expectedAResponse = Response.status(Response.Status.OK).entity(ContactConstants.CONTACT_WAS_SUCCESSFULLY_ADDED).build();
		Contact aResultingContact = new Contact();
		boolean exceptionThrown = false;
		Contact resultingContact = new Contact();
		Response resultingResponse = null;
		Response expectedResponse = Response.status(Response.Status.OK).entity(ContactConstants.CONTACT_WAS_SUCCESSFULLY_UPDATED).build();
		String modifiedEmail = null;
		
		//initialize connection variables
		DatabaseConnectionSingleton conn;
		Statement stmt = null;
		
		//create Contact object 1 and 4 with all attributes set
		Contact contact1 = createContact1();
		Contact contact4 = createContact4();
		
		//build ContactDB objects for contacts 1 and 4
		ContactDB contactDB = new ContactDB(contact1);
		ContactDB contactDB4 = new ContactDB(contact4);
		
		try {
			//open connection
			conn = DatabaseConnectionSingleton.getInstance();
			conn.openConnection();
			
			//call function that will add contact 1 and then contact 4 to database
			aResponse = contactDB.sendContact();
			resultingResponse = contactDB4.sendContact();
			
			try {
				/* identify contact1 ContactID and add that value to the contact1.ContactID attribute */
				stmt = conn.getConnection().createStatement();
				ResultSet rs01 = stmt.executeQuery(selectContact1Qry);
				
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
		            //all we want is the contactID, so we can add this value to our Contact object with a modified phoneNum
		        	contact1.setContactID(rs01.getInt("ContactID"));
		        }
				
				/* identify contact4 ContactID and add that value to the contact4.ContactID attribute */
				stmt = conn.getConnection().createStatement();
				ResultSet rs02 = stmt.executeQuery(selectContact4Qry);
				
				if (!rs02.next())
		        {
		        	//there is no existing entry
		        	//ERROR
					errorString = "the entry was not added to the database\n";
		        	errorExists = true;
		        }
				
				// else, there is an entry
		        else
		        {
		            //all we want is the contactID, so we can add this value to our Contact object with a modified phoneNum
		        	contact4.setContactID(rs02.getInt("ContactID"));
		        }
				
				/* modify contact4's email to be null */
				contact4.setContactEmail(modifiedEmail);
				contactDB4 = new ContactDB(contact4);
				resultingResponse = contactDB4.sendContact();
				
				/* select contact 4's values in the database */
				String selectModified = "SELECT * FROM Contacts WHERE FirstName = \"" + contact4FirstName + "\" AND " 
					    + "LastName = \"" + contact4LastName + "\" AND UserID = " + contact4UserID 
					    + " AND Email = \"" + modifiedEmail + "\" AND PhoneNumber = \"" + contact4Phone + "\" AND ContactCarrier = \""
						+ contact4Carrier + "\" ORDER BY ContactID ;";
				
				rs02 = stmt.executeQuery(selectModified);
				
				if (!rs02.next())
		        {
		        	//there is no existing entry
		        	//ERROR
					errorString = "contact 4 is not in the database correctly\n";
		        	errorExists = true;
		        }
				
				// else, there is an entry
		        else
		        {
		            //set values to resultingContact
		        	resultingContact.setContactID(rs02.getInt("ContactID"));
		        	resultingContact.setFirstName(rs02.getString("FirstName"));
		        	resultingContact.setLastName(rs02.getString("LastName"));
		        	resultingContact.setContactEmail(rs02.getString("Email"));
		        	resultingContact.setContactPhone(rs02.getString("PhoneNumber")); 
		        	resultingContact.setContactCarrier(rs02.getString("ContactCarrier")); 
		        	resultingContact.setUserID(rs02.getInt("UserID"));
		        }
				
				//if there is another entry in the database, then error (the sendContact() added a 2nd entry)
				if (rs02.next()) {
					dupEntryErrorString = "There is a duplicate entry in the database; ERROR\n";
					dupEntryError = true;
				}
				
				/* select contact 1's values in the database */
				String selectThisContact1Qry = "SELECT * FROM Contacts WHERE FirstName = \"" + contact1FirstName + "\" AND " 
					    + "LastName = \"" + contact1LastName + "\" AND UserID = " + contact1UserID 
					    + " AND Email = \"" + contact1Email + "\" AND PhoneNumber = \"" + contact1Phone + "\" AND ContactCarrier = \""
						+ contact1Carrier + "\" AND ContactID = " + contact1.getContactID() + " ORDER BY ContactID ;";
				rs01 = stmt.executeQuery(selectThisContact1Qry);
				
				if (!rs01.next())
		        {
		        	//there is no existing entry
		        	//ERROR
					errorString = "contact 1 is not in the databse correctly\n";
		        	errorExists = true;
		        }
				
				// else, there is an entry
		        else
		        {
		        	aResultingContact.setContactID(rs01.getInt("ContactID"));
		        	aResultingContact.setFirstName(rs01.getString("FirstName"));
		        	aResultingContact.setLastName(rs01.getString("LastName"));
		        	aResultingContact.setContactEmail(rs01.getString("Email"));
		        	aResultingContact.setContactPhone(rs01.getString("PhoneNumber")); 
		        	aResultingContact.setContactCarrier(rs01.getString("ContactCarrier")); 
		        	aResultingContact.setUserID(rs01.getInt("UserID"));
		        }
				
				
				
			} catch (Exception e) {
				exceptionThrown = true;
				e.printStackTrace();
			}
			
			//close connection
			conn.closeConnection();
			
		} catch (URISyntaxException e) {
		    // TODO Auto-generated catch block
			exceptionThrown = true;
			e.printStackTrace();
		}
		
		//make sure no exception was thrown
		assertFalse("test03_07 error: exception thrown\n", exceptionThrown);
		//make sure entry was save to the database
		assertFalse("test03_07 error: " + errorString, errorExists);
		//make sure no 2nd entry was added
		assertFalse("test03_07 error: " + dupEntryErrorString, dupEntryError);
		
		/* test that contact1 is correct with its unchanged values */
		assertNotNull("test03_07 error: Contact is null\n", aResultingContact.getContactID());
		assertEquals("test03_07 error: UserID incorrect, have: " + aResultingContact.getUserID() 
				+ "\nwanted: "+ contact1UserID +"\n", contact1UserID, aResultingContact.getUserID());
		assertTrue("test03_07 error: FirstName incorrect have: " + aResultingContact.getFirstName()
				+ "\nwanted: " + contact1FirstName + "\n", aResultingContact.getFirstName().equals(contact1FirstName));
		assertTrue("test03_07 error: LastName incorrect have: " + aResultingContact.getLastName()
				+ "\nwanted: "+ contact1LastName +"\n", aResultingContact.getLastName().equals(contact1LastName));
		assertTrue("test03_07 error: Email incorrect have: " + aResultingContact.getContactEmail()
				+ "\nwanted: "+ contact1Email +"\n", aResultingContact.getContactEmail().equals(contact1Email));
		assertTrue("test03_07 error: PhoneNumber incorrect have: " + aResultingContact.getContactPhone()
				+ "\nwanted: "+ contact1Phone +"\n", aResultingContact.getContactPhone().equals(contact1Phone));
		assertTrue("test03_07 error: ContactCarrier incorrect have: " + aResultingContact.getContactCarrier()
				+ "\nwanted: "+ contact1Carrier +"\n", aResultingContact.getContactCarrier().equals(contact1Carrier));
		assertTrue("test03_07 error: Response incorrect have: " + aResponse.getEntity().toString()
                + "\nwanted: "+ expectedAResponse.getEntity().toString() +"\n" , aResponse.getEntity().equals(expectedAResponse.getEntity()));
		
		/* test that contact 4 is correct with its modified value */
		//make sure entry's info matches input
		assertNotNull("test03_07 error: Contact is null\n", resultingContact.getContactID());
		assertEquals("test03_07 error: UserID incorrect, have: " + resultingContact.getUserID() 
				+ "\nwanted: "+ contact4UserID +"\n", contact1UserID, resultingContact.getUserID());
		assertTrue("test03_07 error: FirstName incorrect have: " + resultingContact.getFirstName()
				+ "\nwanted: " + contact4FirstName + "\n", resultingContact.getFirstName().equals(contact4FirstName));
		assertTrue("test03_07 error: LastName incorrect have: " + resultingContact.getLastName()
				+ "\nwanted: "+ contact4LastName +"\n", resultingContact.getLastName().equals(contact4LastName));
		assertTrue("test03_07 error: Email incorrect have: " + resultingContact.getContactEmail()
				+ "\nwanted: "+ databaseNullValue +"\n", resultingContact.getContactEmail().equals(databaseNullValue));
		assertTrue("test03_07 error: PhoneNumber incorrect have: " + resultingContact.getContactPhone()
				+ "\nwanted: "+ contact4Phone +"\n", resultingContact.getContactPhone().equals(contact4Phone));
		assertTrue("test03_07 error: ContactCarrier incorrect have: " + resultingContact.getContactCarrier()
				+ "\nwanted: "+ contact4Carrier +"\n", resultingContact.getContactCarrier().equals(contact4Carrier));
		assertTrue("test03_07 error: Response incorrect have: " + resultingResponse.getEntity().toString()
                + "\nwanted: "+ expectedResponse.getEntity().toString() +"\n" , resultingResponse.getEntity().equals(expectedResponse.getEntity()));
	}
	
	/* Test deleteContact()
	 * Setting: contact1 is added to the empty Contacts list, the ContactID is identified and added to contact1.ContactID, and then
	 *     the contact is deleted with the deleteContact() function
	 * Result: contact1 is no longer in the table, the table is empty
	*/
	
	/* Test deleteContact()
	 * Settings: add contact1 and contact1 again for two different contactID's and then delete the first contact1
	 * Result: only the second contact1 exists
	*/
	
	/* Test deleteContact()
	 * Settings: add contact1 and contact1 again for two different contactID's and then delete the second contact1
	 * Result: only the first contact1 exists
	*/
	
	
	
	
	
	
	
	
	
	
	

}
