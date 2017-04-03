package com.au.personal.safety.test.contacts;

import org.junit.*; //imports the @Before, etc.
import static org.junit.Assert.*; //import the asserts

import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.junit.Test;

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
	/* variable to be called within test */
	/* desired values that have been saved in the database for contacts 1, 2, and 3 */
	// IMPORTANT: we want empty strings saved in database, not Null values, b/c this is easier to implement
	int contact1UserID = 1;
	String contact1FirstName = "test";
	String contact1LastName = "01";
	String contact1Email = "test01@email";
	String contact1Phone = "1111111111";
	
	String selectContact1Qry = "SELECT * FROM Contacts WHERE FirstName = \"" + contact1FirstName + "\" AND " 
	    + "LastName = \"" + contact1LastName + "\" AND UserID = " + contact1UserID 
	    + " AND Email = \"" + contact1Email + "\" AND PhoneNumber = \"" + contact1Phone + "\" ;";
	
	int contact2UserID = 2;
	String contact2FirstName = "test";
	String contact2LastName = "02";
	String contact2Email = null;
	String contact2Phone = "2222222222";
	
	String selectContact2Qry = "SELECT * FROM Contacts WHERE FirstName = \"" + contact2FirstName + "\" AND " 
	    + "LastName = \"" + contact2LastName + "\" " + "AND UserID = " + contact2UserID 
	    + " AND Email = \"" + contact2Email + "\" AND PhoneNumber = \"" + contact2Phone + "\" ;";
	
	int contact3UserID = 3;
	String contact3FirstName = "test";
	String contact3LastName = "03";
	String contact3Email = "test03@email";
	String contact3Phone = null;
	
	String selectContact3Qry = "SELECT * FROM Contacts WHERE FirstName = \"" + contact3FirstName + "\" AND " 
		    + "LastName = \"" + contact3LastName + "\" " + "AND UserID = " + contact3UserID 
		    + " AND Email = \"" + contact3Email + "\" AND PhoneNumber = \"" + contact3Phone + "\" ;";
	
	/* Set connection variables to test database */
	
	
	
	/* methods to be called within tests */
	//contact with all attributes set to non null values
	public Contact createContact1() {
		Contact result = new Contact();
		result.setFirstName(contact1FirstName);
		result.setLastName(contact1LastName);
		result.setUserID(contact1UserID);
		result.setContactEmail(contact1Email);
		result.setContactPhone(contact1Phone);
		return result;
	}
	//contact has a null email
	public Contact createContact2() {
		Contact result = new Contact();
		result.setFirstName("test");
		result.setLastName("02");
		result.setUserID(2);
		result.setContactPhone("2222222222");
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
	//contact has a null Email and a null Phone Number
	public Contact createInvalidContact() {
		Contact result = new Contact();
		result.setFirstName("invalid");
		result.setLastName("test");
		result.setUserID(1);
		return result;
	}
	
	/* Before each test:
	 *  - connect to the database
	 *  - clear the Contacts table
	 */
	@Before
	public void beforeEachTest() {
		
		//initialize connection variables
		DatabaseConnectionSingleton conn;
		Statement stmt = null;
		
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
		//check that the contactQuery value is correct
		String expectedCQ = "INSERT INTO Contacts (FirstName, LastName, PhoneNumber, Email, UserID) "
				+ "VALUES (\"" + contact1FirstName
				+ "\", \"" + contact1LastName + "\", \"" + contact1Phone + "\", \"" 
				+ contact1Email + "\", " + contact1UserID + ");";
		assertTrue("test01_01: contactDB1.contactQuery is: " + contactDB1.getContactQuery() + "\n should be: "
				+ expectedCQ + "\n", contactDB1.getContactQuery().equals(expectedCQ));
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
	 * Setting: ContactDB.contact has non-null values for FirstName, LastName, ContactEmail, ContactPhone, and UserID
	 * Result: contactDB.contactQuery has correct string value
	 * */
	/*
	@Test
	public void test02_01_buildInsertWithAllNonNullValues() {
		//create Contact object with all attributes set
		Contact contact1 = createContact1();
		
		//since buildInsert() is run in ContactDB constructor and is based on constructor input,
		// going to call constructor to test buildInsert()
		ContactDB contactDB2 = new ContactDB(contact1);
		
		//check that contactQuery value is correct
		String expectedCQ = "INSERT INTO Contacts (FirstName, LastName, PhoneNumber, Email, UserID) "
				+ "VALUES (\"" + contact1FirstName
				+ "\", \"" + contact1LastName + "\", \"" + contact1Phone + "\", \"" 
				+ contact1Email + "\", " + contact1UserID + ");";
		assertTrue("test02_01: contactDB1.contactQuery is: " + contactDB2.getContactQuery() + "\n should be: "
				+ expectedCQ + "\n", contactDB2.getContactQuery().equals(expectedCQ));
	}
	*/
	
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
		contactDB2.buildInsert();
		
		//check that contactQuery value is correct
		String expectedCQ = "INSERT INTO Contacts (FirstName, LastName, PhoneNumber, Email, UserID) "
				+ "VALUES (\"" + contact2FirstName
				+ "\", \"" + contact2LastName + "\", \"" + contact2Phone + "\", \"" 
				+ contact2Email + "\", " + contact2UserID + ");";
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
				+ "VALUES (\"" + contact3FirstName
				+ "\", \"" + contact3LastName + "\", \"" + contact3Phone + "\", \"" 
				+ contact3Email + "\", " + contact3UserID + ");";
		assertTrue("test02_01: contactDB1.contactQuery is: " + contactDB3.getContactQuery() + "\n should be: "
				+ expectedCQ + "\n", contactDB3.getContactQuery().equals(expectedCQ));
	}
	
	/* Test sendContact() Add Contact
	 * Setting: A new contact is created and wants to be put into the database
	 *  The contact has a zero null values
	 * Result: The new contact is in the Contact table with the correct values
	 * */
	
	@Ignore
	public void test03_01_sendContactAddWithAllNonNullValues() {
		//initialized error variables
		String errorString = "";
    	boolean errorExists = false;
    	boolean exceptionThrown = false;
		//initialize connection variables
		DatabaseConnectionSingleton conn;
		Statement stmt = null;
		//create Contact and ContactDB variables
		Contact contact1 = createContact1();
		Contact resultingContact = new Contact();
		ContactDB contactDB = new ContactDB(contact1);
		
		try {
			//open connection
			conn = DatabaseConnectionSingleton.getInstance();
			conn.openConnection();
			
			//call function that will add the contact to database
			contactDB.sendContact();
			
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
		
	}
	
	
	/* Test sendContact() Add Contact
	 * Setting: A new contact is created and wants to be put into the database
	 *  The contact has a null value for ContactEmail
	 * Result: The new contact is in the Contact table with the correct values, specifically
	 *  look for ContactEmail = ""
	 * */
	@Ignore
	public void test03_02_sendContactAddWithNullEmail() {
		//initialized error variables
		String errorString = "";
		boolean errorExists = false;
		boolean exceptionThrown = false;
		//initialize connection variables
		DatabaseConnectionSingleton conn;
		Statement stmt = null;
		//create Contact and ContactDB variables
		Contact contact2 = createContact2();
		Contact resultingContact = new Contact();
		ContactDB contactDB = new ContactDB(contact2);
				
		try {
			//open connection
			conn = DatabaseConnectionSingleton.getInstance();
			conn.openConnection();
			
			//call function that will add the contact to database
			contactDB.sendContact();
					
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
				
		//make sure no exception was thrown
		assertFalse("test03_02 error: exception thrown\n", exceptionThrown);
		//make sure entry was save to the database
		assertFalse("test03_02 error: " + errorString, errorExists);
		//make sure entry's info matches input
		assertNotNull("test03_01 error: Contact is null\n", resultingContact.getContactID());
		assertEquals("test03_02 error: UserID incorrect, have: " + resultingContact.getUserID() 
				+ "\nwanted: "+ contact2FirstName +"\n", contact2FirstName, resultingContact.getUserID());
		assertTrue("test03_02 error: FirstName incorrect have: " + resultingContact.getFirstName()
				+ "\nwanted: " + contact2FirstName + "\n", resultingContact.getFirstName().equals(contact2FirstName));
		assertTrue("test03_02 error: LastName incorrect have: " + resultingContact.getLastName()
				+ "\nwanted: "+ contact2LastName +"\n", resultingContact.getLastName().equals(contact2LastName));
		assertTrue("test03_02 error: Email incorrect have: " + resultingContact.getContactEmail()
				+ "\nwanted: "+ contact2Email +"\n", resultingContact.getContactEmail().equals(contact2Email));
		assertTrue("test03_02 error: PhoneNumber incorrect have: " + resultingContact.getContactPhone()
				+ "\nwanted: "+ contact2Phone +"\n", resultingContact.getContactPhone().equals(contact2Phone));
	}
	
	/* Test sendContact() Add Contact
	 * Setting: A new contact is created and wants to be put into the database
	 *  The contact has a null value for ContactPhone
	 * Result: The new contact is in the Contact table with the correct values
	 * */
	@Ignore
	public void test03_03_sendContactAddWithNullPhone() {
		//initialized error variables
		String errorString = "";
		boolean errorExists = false;
		boolean exceptionThrown = false;
		//initialize connection variables
		DatabaseConnectionSingleton conn;
		Statement stmt = null;
		//create Contact and ContactDB variables
		Contact contact3 = createContact3();
		Contact resultingContact = new Contact();
		ContactDB contactDB = new ContactDB(contact3);
						
		try {
			//open connection
			conn = DatabaseConnectionSingleton.getInstance();
			conn.openConnection();
					
			//call function that will add the contact to database
			contactDB.sendContact();
							
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
		            //set the entry's info to rs01 attributes
		        	resultingContact.setContactID(rs01.getInt("ContactID"));
		        	resultingContact.setFirstName(rs01.getString("FirstName"));
		        	resultingContact.setLastName(rs01.getString("LastName"));
		        	resultingContact.setContactEmail(rs01.getString("Email"));
		        	resultingContact.setContactPhone(rs01.getString("PhoneNumber")); 
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
						
		//make sure no exception was thrown
		assertFalse("test03_03 error: exception thrown\n", exceptionThrown);
		//make sure entry was save to the database
		assertFalse("test03_03 error: " + errorString, errorExists);
		//make sure entry's info matches input
		assertNotNull("test03_03 error: Contact is null\n", resultingContact.getContactID());
		assertEquals("test03_03 error: UserID incorrect, have: " + resultingContact.getUserID() 
				+ "\nwanted: "+ contact3UserID +"\n", contact3UserID, resultingContact.getUserID());
		assertTrue("test03_03 error: FirstName incorrect have: " + resultingContact.getFirstName()
				+ "\nwanted: " + contact3FirstName + "\n", resultingContact.getFirstName().equals(contact3FirstName));
		assertTrue("test03_03 error: LastName incorrect have: " + resultingContact.getLastName()
				+ "\nwanted: "+ contact3LastName +"\n", resultingContact.getLastName().equals(contact3LastName));
		assertTrue("test03_03 error: Email incorrect have: " + resultingContact.getContactEmail()
				+ "\nwanted: "+ contact3Email +"\n", resultingContact.getContactEmail().equals(contact3Email));
		assertTrue("test03_03 error: PhoneNumber incorrect have: " + resultingContact.getContactPhone()
				+ "\nwanted: "+ contact3Phone +"\n", resultingContact.getContactPhone().equals(contact3Phone));
	}
	
	/* Test sendContact() Add Contact INVALID RESPONSE RAISED
	 * Setting: A new contact is created and wants to be put into the database
	 *  The contact has null values for ContactPhone and ContactEmail
	 * Result: The new contact is not added and an Invalid Add Response Raised
	 * */
/*
	@Test
	public void test03_04_sendContactAddInvalidContactWithNoEmailNoPhone() {
		//initialized error variables
		String errorString = "";
		boolean errorExists = false;
		boolean exceptionThrown = false;
		//initialize connection variables
		DatabaseConnectionSingleton conn;
		Statement stmt = null;
		//create Contact and ContactDB variables
		Contact contact2 = createContact2();
		Contact resultingContact = new Contact();
		ContactDB contactDB = new ContactDB(contact2);
								
		try {
			//open connection
			conn = DatabaseConnectionSingleton.getInstance();
			conn.openConnection();
			
			try {
				//call function that will raise an error? Raise a response?
				contactDB.sendContact();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
								
			//close connection
			conn.closeConnection();
									
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			exceptionThrown = true;
			e.printStackTrace();
		}
								
				
		
		//createInvalidContact();
		
		//check that the contact was NOT added
		
		//check that a Response was raised?
		
		fail("not completed yet");
	}
*/
	
	/* Test sendContact() Modify Contact
	 * Setting: A contact is updated with new values
	 *  The contact previous had zero null values
	 *  The contact will now have a new FirstName, LastName, ContactEmail, and ContactPhone
	 *  The contact will have the same contactUserID
	 * Result: The new contact is in the Contact table with the correct values
	 * */
	@Ignore
	public void test04_01_sendContactModifyAllValues() {
		//initialized error variables
		String errorString = "";
		boolean errorExists = false;
		boolean exceptionThrown = false;
		//initialize connection variables
		DatabaseConnectionSingleton conn;
		Statement stmt = null;
		//initialize resulting contact
		Contact resultingContact = new Contact();
		//create altered values for updatedContact
		String firstName = "altered_" + contact1FirstName;
		String lastName = "altered_" + contact1LastName;
		String contactEmail = "altered_" + contact1Email;
		String contactPhone = "altered_" + contact1Phone;
		int contactUserID = 1; //same userID as original
				
		try {
			//open connection
			conn = DatabaseConnectionSingleton.getInstance();
			conn.openConnection();
			
			//create Contact and ContactDB variables
			Contact contact1 = createContact1();
			ContactDB contactDB = new ContactDB(contact1);
			//add the contact to database
			contactDB.sendContact();
			
			//get the ContactID from the original entry
			String selectOriginal = selectContact1Qry;
			int originalID = -1;
			try {
				stmt = conn.getConnection().createStatement();
				ResultSet rs01 = stmt.executeQuery(selectOriginal);
								
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
		        	originalID = rs01.getInt("ContactID");
		        }
						
			} catch (Exception e) {
				exceptionThrown = true;
				e.printStackTrace();
			}
			
			/*
			 * THIS MAY NEED TO BE CHANGED ONCE SYDNEY UPLOADS HER NEW ContactDB CODE THAT WILL ALLOW
			 * FOR UPDATING, CURRENTLY THERE IS NOT WAY TO UPDATE A CONTACT
			*/
			
			//create Contact and ContactDB variables
			Contact updatedContact = new Contact();
			updatedContact.setFirstName(firstName);
			updatedContact.setLastName(lastName);
			updatedContact.setUserID(contactUserID); 
			updatedContact.setContactEmail(contactEmail);
			updatedContact.setContactPhone(contactPhone);
			updatedContact.setContactID(originalID);
			
			//alter the contact in that database
			
			/*
			 * THIS WILL NEED TO BE CHANGED ONCE SYDNEY UPLOADS HER NEW ContactDB CODE THAT WILL ALLOW
			 * FOR UPDATING, CURRENTLY THERE IS NOT WAY TO UPDATE A CONTACT
			*/
			ContactDB updatedContactDB = new ContactDB(updatedContact);
			updatedContactDB.sendContact();
			
			/*
			 * BELOW SHOULD BE GOOD
			*/
			
			//make sure entry was created and added correctly
			String selectUpdated = "SELECT * FROM Contacts WHERE ContactID = " + originalID + " ;";
							
			try {
				stmt = conn.getConnection().createStatement();
				ResultSet rs01 = stmt.executeQuery(selectUpdated);
								
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
		            //set the entry's info to rs01 attributes
		        	resultingContact.setContactID(rs01.getInt("ContactID"));
		        	resultingContact.setFirstName(rs01.getString("FirstName"));
		        	resultingContact.setLastName(rs01.getString("LastName"));
		        	resultingContact.setContactEmail(rs01.getString("Email"));
		        	resultingContact.setContactPhone(rs01.getString("PhoneNumber")); 
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
				
		//make sure no exception was thrown
		assertFalse("test03_01 error: exception thrown\n", exceptionThrown);
		//make sure entry was save to the database
		assertFalse("test03_01 error: " + errorString, errorExists);
		//make sure entry's info matches input
		assertNotNull("test03_01 error: Contact is null\n", resultingContact.getContactID());
		assertEquals("test03_01 error: UserID incorrect, have: " + resultingContact.getUserID() 
				+ "\nwanted: "+ contactUserID +"\n", contactUserID, resultingContact.getUserID());
		assertTrue("test03_01 error: FirstName incorrect have: " + resultingContact.getFirstName()
				+ "\nwanted: " + firstName + "\n", resultingContact.getFirstName().equals(firstName));
		assertTrue("test03_01 error: LastName incorrect have: " + resultingContact.getLastName()
				+ "\nwanted: "+ lastName +"\n", resultingContact.getLastName().equals(lastName));
		assertTrue("test03_01 error: Email incorrect have: " + resultingContact.getContactEmail()
				+ "\nwanted: "+ contactEmail +"\n", resultingContact.getContactEmail().equals(contactEmail));
		assertTrue("test03_01 error: PhoneNumber incorrect have: " + resultingContact.getContactPhone()
				+ "\nwanted: "+ contactPhone +"\n", resultingContact.getContactPhone().equals(contactPhone));
				
	}
	
	/* Test sendContact() Modify Contact
	 * Setting: A contact is updated with new values
	 *  The contact previous had zero null values
	 *  The contact will now have a new FirstName
	 * Result: The new contact is in the Contact table with the correct values
	 * */
	@Ignore
	public void test04_02_sendContactModifyFirstName() {
		//initialized error variables
				String errorString = "";
				boolean errorExists = false;
				boolean exceptionThrown = false;
				//initialize connection variables
				DatabaseConnectionSingleton conn;
				Statement stmt = null;
				//initialize resulting contact
				Contact resultingContact = new Contact();
				//create altered values for updatedContact
				String firstName = "altered_" + contact1FirstName;
				String lastName = contact1LastName;
				String contactEmail = contact1Email;
				String contactPhone = contact1Phone;
				int contactUserID = 1; //same userID as original
						
				try {
					//open connection
					conn = DatabaseConnectionSingleton.getInstance();
					conn.openConnection();
					
					//create Contact and ContactDB variables
					Contact contact1 = createContact1();
					ContactDB contactDB = new ContactDB(contact1);
					//add the contact to database
					contactDB.sendContact();
					
					//get the ContactID from the original entry
					String selectOriginal = selectContact1Qry;
					int originalID = -1;
					try {
						stmt = conn.getConnection().createStatement();
						ResultSet rs01 = stmt.executeQuery(selectOriginal);
										
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
				        	originalID = rs01.getInt("ContactID");
				        }
								
					} catch (Exception e) {
						exceptionThrown = true;
						e.printStackTrace();
					}
					
					/*
					 * THIS MAY NEED TO BE CHANGED ONCE SYDNEY UPLOADS HER NEW ContactDB CODE THAT WILL ALLOW
					 * FOR UPDATING, CURRENTLY THERE IS NOT WAY TO UPDATE A CONTACT
					*/
					
					//create Contact and ContactDB variables
					Contact updatedContact = new Contact();
					updatedContact.setFirstName(firstName);
					updatedContact.setLastName(lastName);
					updatedContact.setUserID(contactUserID); 
					updatedContact.setContactEmail(contactEmail);
					updatedContact.setContactPhone(contactPhone);
					updatedContact.setContactID(originalID);
					
					//alter the contact in that database
					
					/*
					 * THIS WILL NEED TO BE CHANGED ONCE SYDNEY UPLOADS HER NEW ContactDB CODE THAT WILL ALLOW
					 * FOR UPDATING, CURRENTLY THERE IS NOT WAY TO UPDATE A CONTACT
					*/
					ContactDB updatedContactDB = new ContactDB(updatedContact);
					updatedContactDB.sendContact();
					
					/*
					 * BELOW SHOULD BE GOOD
					*/
					
					//make sure entry was created and added correctly
					String selectUpdated = "SELECT * FROM Contacts WHERE ContactID = " + originalID + " ;";
									
					try {
						stmt = conn.getConnection().createStatement();
						ResultSet rs01 = stmt.executeQuery(selectUpdated);
										
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
						
				//make sure no exception was thrown
				assertFalse("test03_01 error: exception thrown\n", exceptionThrown);
				//make sure entry was save to the database
				assertFalse("test03_01 error: " + errorString, errorExists);
				//make sure entry's info matches input
				assertNotNull("test03_01 error: Contact is null\n", resultingContact.getContactID());
				assertEquals("test03_01 error: UserID incorrect, have: " + resultingContact.getUserID() 
						+ "\nwanted: "+ contactUserID +"\n", contactUserID, resultingContact.getUserID());
				assertTrue("test03_01 error: FirstName incorrect have: " + resultingContact.getFirstName()
						+ "\nwanted: " + firstName + "\n", resultingContact.getFirstName().equals(firstName));
				assertTrue("test03_01 error: LastName incorrect have: " + resultingContact.getLastName()
						+ "\nwanted: "+ lastName +"\n", resultingContact.getLastName().equals(lastName));
				assertTrue("test03_01 error: Email incorrect have: " + resultingContact.getContactEmail()
						+ "\nwanted: "+ contactEmail +"\n", resultingContact.getContactEmail().equals(contactEmail));
				assertTrue("test03_01 error: PhoneNumber incorrect have: " + resultingContact.getContactPhone()
						+ "\nwanted: "+ contactPhone +"\n", resultingContact.getContactPhone().equals(contactPhone));
		
	}
	
	/* Test sendContact() Modify Contact
	 * Setting: A contact is updated with new values
	 *  The contact previous had zero null values
	 *  The contact will now have a new LastName
	 * Result: The new contact is in the Contact table with the correct values
	 * */
	@Ignore
	public void test04_03_sendContactModifyLastName() {
		//initialized error variables
				String errorString = "";
				boolean errorExists = false;
				boolean exceptionThrown = false;
				//initialize connection variables
				DatabaseConnectionSingleton conn;
				Statement stmt = null;
				//initialize resulting contact
				Contact resultingContact = new Contact();
				//create altered values for updatedContact
				String firstName = contact1FirstName;
				String lastName = "altered_" + contact1LastName;
				String contactEmail = contact1Email;
				String contactPhone = contact1Phone;
				int contactUserID = 1; //same userID as original
						
				try {
					//open connection
					conn = DatabaseConnectionSingleton.getInstance();
					conn.openConnection();
					
					//create Contact and ContactDB variables
					Contact contact1 = createContact1();
					ContactDB contactDB = new ContactDB(contact1);
					//add the contact to database
					contactDB.sendContact();
					
					//get the ContactID from the original entry
					String selectOriginal = selectContact1Qry;
					int originalID = -1;
					try {
						stmt = conn.getConnection().createStatement();
						ResultSet rs01 = stmt.executeQuery(selectOriginal);
										
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
				        	originalID = rs01.getInt("ContactID");
				        }
								
					} catch (Exception e) {
						exceptionThrown = true;
						e.printStackTrace();
					}
					
					/*
					 * THIS MAY NEED TO BE CHANGED ONCE SYDNEY UPLOADS HER NEW ContactDB CODE THAT WILL ALLOW
					 * FOR UPDATING, CURRENTLY THERE IS NOT WAY TO UPDATE A CONTACT
					*/
					
					//create Contact and ContactDB variables
					Contact updatedContact = new Contact();
					updatedContact.setFirstName(firstName);
					updatedContact.setLastName(lastName);
					updatedContact.setUserID(contactUserID); 
					updatedContact.setContactEmail(contactEmail);
					updatedContact.setContactPhone(contactPhone);
					updatedContact.setContactID(originalID);
					
					//alter the contact in that database
					
					/*
					 * THIS WILL NEED TO BE CHANGED ONCE SYDNEY UPLOADS HER NEW ContactDB CODE THAT WILL ALLOW
					 * FOR UPDATING, CURRENTLY THERE IS NOT WAY TO UPDATE A CONTACT
					*/
					ContactDB updatedContactDB = new ContactDB(updatedContact);
					updatedContactDB.sendContact();
					
					/*
					 * BELOW SHOULD BE GOOD
					*/
					
					//make sure entry was created and added correctly
					String selectUpdated = "SELECT * FROM Contacts WHERE ContactID = " + originalID + " ;";
									
					try {
						stmt = conn.getConnection().createStatement();
						ResultSet rs01 = stmt.executeQuery(selectUpdated);
										
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
				            //set the entry's info to rs01 attributes
				        	resultingContact.setContactID(rs01.getInt("ContactID"));
				        	resultingContact.setFirstName(rs01.getString("FirstName"));
				        	resultingContact.setLastName(rs01.getString("LastName"));
				        	resultingContact.setContactEmail(rs01.getString("Email"));
				        	resultingContact.setContactPhone(rs01.getString("PhoneNumber")); 
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
						
				//make sure no exception was thrown
				assertFalse("test03_01 error: exception thrown\n", exceptionThrown);
				//make sure entry was save to the database
				assertFalse("test03_01 error: " + errorString, errorExists);
				//make sure entry's info matches input
				assertNotNull("test03_01 error: Contact is null\n", resultingContact.getContactID());
				assertEquals("test03_01 error: UserID incorrect, have: " + resultingContact.getUserID() 
						+ "\nwanted: "+ contactUserID +"\n", contactUserID, resultingContact.getUserID());
				assertTrue("test03_01 error: FirstName incorrect have: " + resultingContact.getFirstName()
						+ "\nwanted: " + firstName + "\n", resultingContact.getFirstName().equals(firstName));
				assertTrue("test03_01 error: LastName incorrect have: " + resultingContact.getLastName()
						+ "\nwanted: "+ lastName +"\n", resultingContact.getLastName().equals(lastName));
				assertTrue("test03_01 error: Email incorrect have: " + resultingContact.getContactEmail()
						+ "\nwanted: "+ contactEmail +"\n", resultingContact.getContactEmail().equals(contactEmail));
				assertTrue("test03_01 error: PhoneNumber incorrect have: " + resultingContact.getContactPhone()
						+ "\nwanted: "+ contactPhone +"\n", resultingContact.getContactPhone().equals(contactPhone));
		
		fail("not implemented yet");
	}
	
	/* Test sendContact() Modify Contact
	 * Setting: A contact is updated with new values
	 *  The contact previous had zero null values
	 *  The contact will now have a new ContactEmail
	 * Result: The new contact is in the Contact table with the correct values
	 * */
	@Ignore
	public void test04_04_sendContactModifyEmail() {
		//initialized error variables
				String errorString = "";
				boolean errorExists = false;
				boolean exceptionThrown = false;
				//initialize connection variables
				DatabaseConnectionSingleton conn;
				Statement stmt = null;
				//initialize resulting contact
				Contact resultingContact = new Contact();
				//create altered values for updatedContact
				String firstName = contact1FirstName;
				String lastName = contact1LastName;
				String contactEmail = "altered_" + contact1Email;
				String contactPhone = contact1Phone;
				int contactUserID = 1; //same userID as original
						
				try {
					//open connection
					conn = DatabaseConnectionSingleton.getInstance();
					conn.openConnection();
					
					//create Contact and ContactDB variables
					Contact contact1 = createContact1();
					ContactDB contactDB = new ContactDB(contact1);
					//add the contact to database
					contactDB.sendContact();
					
					//get the ContactID from the original entry
					String selectOriginal = selectContact1Qry;
					int originalID = -1;
					try {
						stmt = conn.getConnection().createStatement();
						ResultSet rs01 = stmt.executeQuery(selectOriginal);
										
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
				        	originalID = rs01.getInt("ContactID");
				        }
								
					} catch (Exception e) {
						exceptionThrown = true;
						e.printStackTrace();
					}
					
					/*
					 * THIS MAY NEED TO BE CHANGED ONCE SYDNEY UPLOADS HER NEW ContactDB CODE THAT WILL ALLOW
					 * FOR UPDATING, CURRENTLY THERE IS NOT WAY TO UPDATE A CONTACT
					*/
					
					//create Contact and ContactDB variables
					Contact updatedContact = new Contact();
					updatedContact.setFirstName(firstName);
					updatedContact.setLastName(lastName);
					updatedContact.setUserID(contactUserID); 
					updatedContact.setContactEmail(contactEmail);
					updatedContact.setContactPhone(contactPhone);
					updatedContact.setContactID(originalID);
					
					//alter the contact in that database
					
					/*
					 * THIS WILL NEED TO BE CHANGED ONCE SYDNEY UPLOADS HER NEW ContactDB CODE THAT WILL ALLOW
					 * FOR UPDATING, CURRENTLY THERE IS NOT WAY TO UPDATE A CONTACT
					*/
					ContactDB updatedContactDB = new ContactDB(updatedContact);
					updatedContactDB.sendContact();
					
					/*
					 * BELOW SHOULD BE GOOD
					*/
					
					//make sure entry was created and added correctly
					String selectUpdated = "SELECT * FROM Contacts WHERE ContactID = " + originalID + " ;";
									
					try {
						stmt = conn.getConnection().createStatement();
						ResultSet rs01 = stmt.executeQuery(selectUpdated);
										
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
				            //set the entry's info to rs01 attributes
				        	resultingContact.setContactID(rs01.getInt("ContactID"));
				        	resultingContact.setFirstName(rs01.getString("FirstName"));
				        	resultingContact.setLastName(rs01.getString("LastName"));
				        	resultingContact.setContactEmail(rs01.getString("Email"));
				        	resultingContact.setContactPhone(rs01.getString("PhoneNumber")); 
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
						
				//make sure no exception was thrown
				assertFalse("test03_01 error: exception thrown\n", exceptionThrown);
				//make sure entry was save to the database
				assertFalse("test03_01 error: " + errorString, errorExists);
				//make sure entry's info matches input
				assertNotNull("test03_01 error: Contact is null\n", resultingContact.getContactID());
				assertEquals("test03_01 error: UserID incorrect, have: " + resultingContact.getUserID() 
						+ "\nwanted: "+ contactUserID +"\n", contactUserID, resultingContact.getUserID());
				assertTrue("test03_01 error: FirstName incorrect have: " + resultingContact.getFirstName()
						+ "\nwanted: " + firstName + "\n", resultingContact.getFirstName().equals(firstName));
				assertTrue("test03_01 error: LastName incorrect have: " + resultingContact.getLastName()
						+ "\nwanted: "+ lastName +"\n", resultingContact.getLastName().equals(lastName));
				assertTrue("test03_01 error: Email incorrect have: " + resultingContact.getContactEmail()
						+ "\nwanted: "+ contactEmail +"\n", resultingContact.getContactEmail().equals(contactEmail));
				assertTrue("test03_01 error: PhoneNumber incorrect have: " + resultingContact.getContactPhone()
						+ "\nwanted: "+ contactPhone +"\n", resultingContact.getContactPhone().equals(contactPhone));
		
	}
	
	/* Test sendContact() Modify Contact
	 * Setting: A contact is updated with new values
	 *  The contact previous had zero null values
	 *  The contact will now have a new ContactPhone
	 * Result: The new contact is in the Contact table with the correct values
	 * */
	@Ignore
	public void test04_05_sendContactModifyPhone() {
		//initialized error variables
				String errorString = "";
				boolean errorExists = false;
				boolean exceptionThrown = false;
				//initialize connection variables
				DatabaseConnectionSingleton conn;
				Statement stmt = null;
				//initialize resulting contact
				Contact resultingContact = new Contact();
				//create altered values for updatedContact
				String firstName = contact1FirstName;
				String lastName = contact1LastName;
				String contactEmail = contact1Email;
				String contactPhone = "altered_" + contact1Phone;
				int contactUserID = 1; //same userID as original
						
				try {
					//open connection
					conn = DatabaseConnectionSingleton.getInstance();
					conn.openConnection();
					
					//create Contact and ContactDB variables
					Contact contact1 = createContact1();
					ContactDB contactDB = new ContactDB(contact1);
					//add the contact to database
					contactDB.sendContact();
					
					//get the ContactID from the original entry
					String selectOriginal = selectContact1Qry;
					int originalID = -1;
					try {
						stmt = conn.getConnection().createStatement();
						ResultSet rs01 = stmt.executeQuery(selectOriginal);
										
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
				        	originalID = rs01.getInt("ContactID");
				        }
								
					} catch (Exception e) {
						exceptionThrown = true;
						e.printStackTrace();
					}
					
					/*
					 * THIS MAY NEED TO BE CHANGED ONCE SYDNEY UPLOADS HER NEW ContactDB CODE THAT WILL ALLOW
					 * FOR UPDATING, CURRENTLY THERE IS NOT WAY TO UPDATE A CONTACT
					*/
					
					//create Contact and ContactDB variables
					Contact updatedContact = new Contact();
					updatedContact.setFirstName(firstName);
					updatedContact.setLastName(lastName);
					updatedContact.setUserID(contactUserID); 
					updatedContact.setContactEmail(contactEmail);
					updatedContact.setContactPhone(contactPhone);
					updatedContact.setContactID(originalID);
					
					//alter the contact in that database
					
					/*
					 * THIS WILL NEED TO BE CHANGED ONCE SYDNEY UPLOADS HER NEW ContactDB CODE THAT WILL ALLOW
					 * FOR UPDATING, CURRENTLY THERE IS NOT WAY TO UPDATE A CONTACT
					*/
					ContactDB updatedContactDB = new ContactDB(updatedContact);
					updatedContactDB.sendContact();
					
					/*
					 * BELOW SHOULD BE GOOD
					*/
					
					//make sure entry was created and added correctly
					String selectUpdated = "SELECT * FROM Contacts WHERE ContactID = " + originalID + " ;";
									
					try {
						stmt = conn.getConnection().createStatement();
						ResultSet rs01 = stmt.executeQuery(selectUpdated);
										
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
				            //set the entry's info to rs01 attributes
				        	resultingContact.setContactID(rs01.getInt("ContactID"));
				        	resultingContact.setFirstName(rs01.getString("FirstName"));
				        	resultingContact.setLastName(rs01.getString("LastName"));
				        	resultingContact.setContactEmail(rs01.getString("Email"));
				        	resultingContact.setContactPhone(rs01.getString("PhoneNumber")); 
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
						
				//make sure no exception was thrown
				assertFalse("test03_01 error: exception thrown\n", exceptionThrown);
				//make sure entry was save to the database
				assertFalse("test03_01 error: " + errorString, errorExists);
				//make sure entry's info matches input
				assertNotNull("test03_01 error: Contact is null\n", resultingContact.getContactID());
				assertEquals("test03_01 error: UserID incorrect, have: " + resultingContact.getUserID() 
						+ "\nwanted: "+ contactUserID +"\n", contactUserID, resultingContact.getUserID());
				assertTrue("test03_01 error: FirstName incorrect have: " + resultingContact.getFirstName()
						+ "\nwanted: " + firstName + "\n", resultingContact.getFirstName().equals(firstName));
				assertTrue("test03_01 error: LastName incorrect have: " + resultingContact.getLastName()
						+ "\nwanted: "+ lastName +"\n", resultingContact.getLastName().equals(lastName));
				assertTrue("test03_01 error: Email incorrect have: " + resultingContact.getContactEmail()
						+ "\nwanted: "+ contactEmail +"\n", resultingContact.getContactEmail().equals(contactEmail));
				assertTrue("test03_01 error: PhoneNumber incorrect have: " + resultingContact.getContactPhone()
						+ "\nwanted: "+ contactPhone +"\n", resultingContact.getContactPhone().equals(contactPhone));
		
	}
	
	/* Test sendContact() Modify Contact
	 * Setting: A contact is updated with new values
	 *  The contact previous had zero null values
	 *  The contact will now have a null ContactEmail
	 * Result: The new contact is in the Contact table with the correct values
	 * */
	@Ignore
	public void test04_06_sendContactModifyEmailToNull() {
		//initialized error variables
				String errorString = "";
				boolean errorExists = false;
				boolean exceptionThrown = false;
				//initialize connection variables
				DatabaseConnectionSingleton conn;
				Statement stmt = null;
				//initialize resulting contact
				Contact resultingContact = new Contact();
				//create altered values for updatedContact
				String firstName = "altered_" + contact1FirstName;
				String lastName = "altered_" + contact1LastName;
				String contactEmail = null;
				String contactPhone = "altered_" + contact1Phone;
				int contactUserID = 1; //same userID as original
						
				try {
					//open connection
					conn = DatabaseConnectionSingleton.getInstance();
					conn.openConnection();
					
					//create Contact and ContactDB variables
					Contact contact1 = createContact1();
					ContactDB contactDB = new ContactDB(contact1);
					//add the contact to database
					contactDB.sendContact();
					
					//get the ContactID from the original entry
					String selectOriginal = selectContact1Qry;
					int originalID = -1;
					try {
						stmt = conn.getConnection().createStatement();
						ResultSet rs01 = stmt.executeQuery(selectOriginal);
										
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
				        	originalID = rs01.getInt("ContactID");
				        }
								
					} catch (Exception e) {
						exceptionThrown = true;
						e.printStackTrace();
					}
					
					/*
					 * THIS MAY NEED TO BE CHANGED ONCE SYDNEY UPLOADS HER NEW ContactDB CODE THAT WILL ALLOW
					 * FOR UPDATING, CURRENTLY THERE IS NOT WAY TO UPDATE A CONTACT
					*/
					
					//create Contact and ContactDB variables
					Contact updatedContact = new Contact();
					updatedContact.setFirstName(firstName);
					updatedContact.setLastName(lastName);
					updatedContact.setUserID(contactUserID); 
					updatedContact.setContactEmail(contactEmail);
					updatedContact.setContactPhone(contactPhone);
					updatedContact.setContactID(originalID);
					
					//alter the contact in that database
					
					/*
					 * THIS WILL NEED TO BE CHANGED ONCE SYDNEY UPLOADS HER NEW ContactDB CODE THAT WILL ALLOW
					 * FOR UPDATING, CURRENTLY THERE IS NOT WAY TO UPDATE A CONTACT
					*/
					ContactDB updatedContactDB = new ContactDB(updatedContact);
					updatedContactDB.sendContact();
					
					/*
					 * BELOW SHOULD BE GOOD
					*/
					
					//make sure entry was created and added correctly
					String selectUpdated = "SELECT * FROM Contacts WHERE ContactID = " + originalID + " ;";
									
					try {
						stmt = conn.getConnection().createStatement();
						ResultSet rs01 = stmt.executeQuery(selectUpdated);
										
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
				            //set the entry's info to rs01 attributes
				        	resultingContact.setContactID(rs01.getInt("ContactID"));
				        	resultingContact.setFirstName(rs01.getString("FirstName"));
				        	resultingContact.setLastName(rs01.getString("LastName"));
				        	resultingContact.setContactEmail(rs01.getString("Email"));
				        	resultingContact.setContactPhone(rs01.getString("PhoneNumber")); 
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
						
				//make sure no exception was thrown
				assertFalse("test03_01 error: exception thrown\n", exceptionThrown);
				//make sure entry was save to the database
				assertFalse("test03_01 error: " + errorString, errorExists);
				//make sure entry's info matches input
				assertNotNull("test03_01 error: Contact is null\n", resultingContact.getContactID());
				assertEquals("test03_01 error: UserID incorrect, have: " + resultingContact.getUserID() 
						+ "\nwanted: "+ contactUserID +"\n", contactUserID, resultingContact.getUserID());
				assertTrue("test03_01 error: FirstName incorrect have: " + resultingContact.getFirstName()
						+ "\nwanted: " + firstName + "\n", resultingContact.getFirstName().equals(firstName));
				assertTrue("test03_01 error: LastName incorrect have: " + resultingContact.getLastName()
						+ "\nwanted: "+ lastName +"\n", resultingContact.getLastName().equals(lastName));
				assertTrue("test03_01 error: Email incorrect have: " + resultingContact.getContactEmail()
						+ "\nwanted: "+ contactEmail +"\n", resultingContact.getContactEmail().equals(contactEmail));
				assertTrue("test03_01 error: PhoneNumber incorrect have: " + resultingContact.getContactPhone()
						+ "\nwanted: "+ contactPhone +"\n", resultingContact.getContactPhone().equals(contactPhone));
		
	}
	
	/* Test sendContact() Modify Contact
	 * Setting: A contact is updated with new values
	 *  The contact previous had zero null values
	 *  The contact will now have a null ContactPhone
	 * Result: The new contact is in the Contact table with the correct values
	 * */
	@Ignore
	public void test04_07_sendContactModifyPhoneToNull() {
		//initialized error variables
				String errorString = "";
				boolean errorExists = false;
				boolean exceptionThrown = false;
				//initialize connection variables
				DatabaseConnectionSingleton conn;
				Statement stmt = null;
				//initialize resulting contact
				Contact resultingContact = new Contact();
				//create altered values for updatedContact
				String firstName = "altered_" + contact1FirstName;
				String lastName = "altered_" + contact1LastName;
				String contactEmail = "altered_" + contact1Email;
				String contactPhone = null;
				int contactUserID = 1; //same userID as original
						
				try {
					//open connection
					conn = DatabaseConnectionSingleton.getInstance();
					conn.openConnection();
					
					//create Contact and ContactDB variables
					Contact contact1 = createContact1();
					ContactDB contactDB = new ContactDB(contact1);
					//add the contact to database
					contactDB.sendContact();
					
					//get the ContactID from the original entry
					String selectOriginal = selectContact1Qry;
					int originalID = -1;
					try {
						stmt = conn.getConnection().createStatement();
						ResultSet rs01 = stmt.executeQuery(selectOriginal);
										
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
				        	originalID = rs01.getInt("ContactID");
				        }
								
					} catch (Exception e) {
						exceptionThrown = true;
						e.printStackTrace();
					}
					
					/*
					 * THIS MAY NEED TO BE CHANGED ONCE SYDNEY UPLOADS HER NEW ContactDB CODE THAT WILL ALLOW
					 * FOR UPDATING, CURRENTLY THERE IS NOT WAY TO UPDATE A CONTACT
					*/
					
					//create Contact and ContactDB variables
					Contact updatedContact = new Contact();
					updatedContact.setFirstName(firstName);
					updatedContact.setLastName(lastName);
					updatedContact.setUserID(contactUserID); 
					updatedContact.setContactEmail(contactEmail);
					updatedContact.setContactPhone(contactPhone);
					updatedContact.setContactID(originalID);
					
					//alter the contact in that database
					
					/*
					 * THIS WILL NEED TO BE CHANGED ONCE SYDNEY UPLOADS HER NEW ContactDB CODE THAT WILL ALLOW
					 * FOR UPDATING, CURRENTLY THERE IS NOT WAY TO UPDATE A CONTACT
					*/
					ContactDB updatedContactDB = new ContactDB(updatedContact);
					updatedContactDB.sendContact();
					
					/*
					 * BELOW SHOULD BE GOOD
					*/
					
					//make sure entry was created and added correctly
					String selectUpdated = "SELECT * FROM Contacts WHERE ContactID = " + originalID + " ;";
									
					try {
						stmt = conn.getConnection().createStatement();
						ResultSet rs01 = stmt.executeQuery(selectUpdated);
										
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
				            //set the entry's info to rs01 attributes
				        	resultingContact.setContactID(rs01.getInt("ContactID"));
				        	resultingContact.setFirstName(rs01.getString("FirstName"));
				        	resultingContact.setLastName(rs01.getString("LastName"));
				        	resultingContact.setContactEmail(rs01.getString("Email"));
				        	resultingContact.setContactPhone(rs01.getString("PhoneNumber")); 
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
						
				//make sure no exception was thrown
				assertFalse("test03_01 error: exception thrown\n", exceptionThrown);
				//make sure entry was save to the database
				assertFalse("test03_01 error: " + errorString, errorExists);
				//make sure entry's info matches input
				assertNotNull("test03_01 error: Contact is null\n", resultingContact.getContactID());
				assertEquals("test03_01 error: UserID incorrect, have: " + resultingContact.getUserID() 
						+ "\nwanted: "+ contactUserID +"\n", contactUserID, resultingContact.getUserID());
				assertTrue("test03_01 error: FirstName incorrect have: " + resultingContact.getFirstName()
						+ "\nwanted: " + firstName + "\n", resultingContact.getFirstName().equals(firstName));
				assertTrue("test03_01 error: LastName incorrect have: " + resultingContact.getLastName()
						+ "\nwanted: "+ lastName +"\n", resultingContact.getLastName().equals(lastName));
				assertTrue("test03_01 error: Email incorrect have: " + resultingContact.getContactEmail()
						+ "\nwanted: "+ contactEmail +"\n", resultingContact.getContactEmail().equals(contactEmail));
				assertTrue("test03_01 error: PhoneNumber incorrect have: " + resultingContact.getContactPhone()
						+ "\nwanted: "+ contactPhone +"\n", resultingContact.getContactPhone().equals(contactPhone));
		
	}
	
	/* Test sendContact() Modify Contact
	 * Setting: A contact is updated with new values
	 *  The contact previous had a null ContactEmail
	 *  The contact will now have a ContactEmail, thus no null values
	 * Result: The new contact is in the Contact table with the correct values
	 * */
	@Ignore
	public void test04_08_sendContactModifyNullEmail() {
		//initialized error variables
		String errorString = "";
		boolean errorExists = false;
		boolean exceptionThrown = false;
		//initialize connection variables
		DatabaseConnectionSingleton conn;
		Statement stmt = null;
		//initialize resulting contact
		Contact resultingContact = new Contact();
		//create altered values for updatedContact
		String firstName = contact2FirstName;
		String lastName = contact2LastName;
		String contactEmail = "altered_" + contact2Email;
		String contactPhone = contact2Phone;
		int contactUserID = 1; //same userID as original
				
		try {
			//open connection
			conn = DatabaseConnectionSingleton.getInstance();
			conn.openConnection();
			
			//create Contact and ContactDB variables
			Contact contact2 = createContact2();
			ContactDB contactDB = new ContactDB(contact2);
			//add the contact to database
			contactDB.sendContact();
					
			//get the ContactID from the original entry
			String selectOriginal = selectContact1Qry;
			int originalID = -1;
			try {
				stmt = conn.getConnection().createStatement();
				ResultSet rs01 = stmt.executeQuery(selectOriginal);
								
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
		        	originalID = rs01.getInt("ContactID");
		        }
								
			} catch (Exception e) {
				exceptionThrown = true;
				e.printStackTrace();
			}
					
			/*
			 * THIS MAY NEED TO BE CHANGED ONCE SYDNEY UPLOADS HER NEW ContactDB CODE THAT WILL ALLOW
			 * FOR UPDATING, CURRENTLY THERE IS NOT WAY TO UPDATE A CONTACT
			*/
					
			//create Contact and ContactDB variables
			Contact updatedContact = new Contact();
			updatedContact.setFirstName(firstName);
			updatedContact.setLastName(lastName);
			updatedContact.setUserID(contactUserID); 
			updatedContact.setContactEmail(contactEmail);
			updatedContact.setContactPhone(contactPhone);
			updatedContact.setContactID(originalID);
			
			//alter the contact in that database
					
			/*
			 * THIS WILL NEED TO BE CHANGED ONCE SYDNEY UPLOADS HER NEW ContactDB CODE THAT WILL ALLOW
			 * FOR UPDATING, CURRENTLY THERE IS NOT WAY TO UPDATE A CONTACT
			*/
			ContactDB updatedContactDB = new ContactDB(updatedContact);
			updatedContactDB.sendContact();
					
			/*
			 * BELOW SHOULD BE GOOD
			*/
					
			//make sure entry was created and added correctly
			String selectUpdated = "SELECT * FROM Contacts WHERE ContactID = " + originalID + " ;";
							
			try {
				stmt = conn.getConnection().createStatement();
				ResultSet rs01 = stmt.executeQuery(selectUpdated);
								
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
		            //set the entry's info to rs01 attributes
		        	resultingContact.setContactID(rs01.getInt("ContactID"));
		        	resultingContact.setFirstName(rs01.getString("FirstName"));
		        	resultingContact.setLastName(rs01.getString("LastName"));
		        	resultingContact.setContactEmail(rs01.getString("Email"));
		        	resultingContact.setContactPhone(rs01.getString("PhoneNumber")); 
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
						
		//make sure no exception was thrown
		assertFalse("test03_01 error: exception thrown\n", exceptionThrown);
		//make sure entry was save to the database
		assertFalse("test03_01 error: " + errorString, errorExists);
		//make sure entry's info matches input
		assertNotNull("test03_01 error: Contact is null\n", resultingContact.getContactID());
		assertEquals("test03_01 error: UserID incorrect, have: " + resultingContact.getUserID() 
				+ "\nwanted: "+ contactUserID +"\n", contactUserID, resultingContact.getUserID());
		assertTrue("test03_01 error: FirstName incorrect have: " + resultingContact.getFirstName()
				+ "\nwanted: " + firstName + "\n", resultingContact.getFirstName().equals(firstName));
		assertTrue("test03_01 error: LastName incorrect have: " + resultingContact.getLastName()
				+ "\nwanted: "+ lastName +"\n", resultingContact.getLastName().equals(lastName));
		assertTrue("test03_01 error: Email incorrect have: " + resultingContact.getContactEmail()
				+ "\nwanted: "+ contactEmail +"\n", resultingContact.getContactEmail().equals(contactEmail));
		assertTrue("test03_01 error: PhoneNumber incorrect have: " + resultingContact.getContactPhone()
				+ "\nwanted: "+ contactPhone +"\n", resultingContact.getContactPhone().equals(contactPhone));
		
	}
	
	/* Test sendContact() Modify Contact
	 * Setting: A contact is updated with new values
	 *  The contact previous had a null ContactPhone
	 *  The contact will now have a ContactPhone, thus no null values
	 * Result: The new contact is in the Contact table with the correct values
	 * */
	@Ignore
	public void test04_09_sendContactModifyNullPhone() {
		//initialized error variables
				String errorString = "";
				boolean errorExists = false;
				boolean exceptionThrown = false;
				//initialize connection variables
				DatabaseConnectionSingleton conn;
				Statement stmt = null;
				//initialize resulting contact
				Contact resultingContact = new Contact();
				//create altered values for updatedContact
				String firstName = contact3FirstName;
				String lastName = contact3LastName;
				String contactEmail = contact3Email;
				String contactPhone = "altered" + contact3Phone;
				int contactUserID = 1; //same userID as original
						
				try {
					//open connection
					conn = DatabaseConnectionSingleton.getInstance();
					conn.openConnection();
					
					//create Contact and ContactDB variables
					Contact contact3 = createContact3();
					ContactDB contactDB = new ContactDB(contact3);
					//add the contact to database
					contactDB.sendContact();
							
					//get the ContactID from the original entry
					String selectOriginal = selectContact1Qry;
					int originalID = -1;
					try {
						stmt = conn.getConnection().createStatement();
						ResultSet rs01 = stmt.executeQuery(selectOriginal);
										
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
				        	originalID = rs01.getInt("ContactID");
				        }
										
					} catch (Exception e) {
						exceptionThrown = true;
						e.printStackTrace();
					}
							
					/*
					 * THIS MAY NEED TO BE CHANGED ONCE SYDNEY UPLOADS HER NEW ContactDB CODE THAT WILL ALLOW
					 * FOR UPDATING, CURRENTLY THERE IS NOT WAY TO UPDATE A CONTACT
					*/
							
					//create Contact and ContactDB variables
					Contact updatedContact = new Contact();
					updatedContact.setFirstName(firstName);
					updatedContact.setLastName(lastName);
					updatedContact.setUserID(contactUserID); 
					updatedContact.setContactEmail(contactEmail);
					updatedContact.setContactPhone(contactPhone);
					updatedContact.setContactID(originalID);
					
					//alter the contact in that database
							
					/*
					 * THIS WILL NEED TO BE CHANGED ONCE SYDNEY UPLOADS HER NEW ContactDB CODE THAT WILL ALLOW
					 * FOR UPDATING, CURRENTLY THERE IS NOT WAY TO UPDATE A CONTACT
					*/
					ContactDB updatedContactDB = new ContactDB(updatedContact);
					updatedContactDB.sendContact();
							
					/*
					 * BELOW SHOULD BE GOOD
					*/
							
					//make sure entry was created and added correctly
					String selectUpdated = "SELECT * FROM Contacts WHERE ContactID = " + originalID + " ;";
									
					try {
						stmt = conn.getConnection().createStatement();
						ResultSet rs01 = stmt.executeQuery(selectUpdated);
										
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
				            //set the entry's info to rs01 attributes
				        	resultingContact.setContactID(rs01.getInt("ContactID"));
				        	resultingContact.setFirstName(rs01.getString("FirstName"));
				        	resultingContact.setLastName(rs01.getString("LastName"));
				        	resultingContact.setContactEmail(rs01.getString("Email"));
				        	resultingContact.setContactPhone(rs01.getString("PhoneNumber")); 
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
								
				//make sure no exception was thrown
				assertFalse("test03_01 error: exception thrown\n", exceptionThrown);
				//make sure entry was save to the database
				assertFalse("test03_01 error: " + errorString, errorExists);
				//make sure entry's info matches input
				assertNotNull("test03_01 error: Contact is null\n", resultingContact.getContactID());
				assertEquals("test03_01 error: UserID incorrect, have: " + resultingContact.getUserID() 
						+ "\nwanted: "+ contactUserID +"\n", contactUserID, resultingContact.getUserID());
				assertTrue("test03_01 error: FirstName incorrect have: " + resultingContact.getFirstName()
						+ "\nwanted: " + firstName + "\n", resultingContact.getFirstName().equals(firstName));
				assertTrue("test03_01 error: LastName incorrect have: " + resultingContact.getLastName()
						+ "\nwanted: "+ lastName +"\n", resultingContact.getLastName().equals(lastName));
				assertTrue("test03_01 error: Email incorrect have: " + resultingContact.getContactEmail()
						+ "\nwanted: "+ contactEmail +"\n", resultingContact.getContactEmail().equals(contactEmail));
				assertTrue("test03_01 error: PhoneNumber incorrect have: " + resultingContact.getContactPhone()
						+ "\nwanted: "+ contactPhone +"\n", resultingContact.getContactPhone().equals(contactPhone));
		
	}
	
	/* Test sendContact() Modify Contact
	 * Setting: A contact is updated with new values
	 *  The contact previous had a null ContactPhone
	 *  The contact will now have a ContactPhone and null ContactEmail
	 * Result: The new contact is in the Contact table with the correct values
	 * */
	@Ignore
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
	
	/*
	 * Test sendContact() Modify user's second contact
	 * Setting: contactA for UserA is added to the database and then
	 *   contactB for userA is added to the database
	 *   ContactB had zero null values
	 *   The desired update will change the correct entry's first name, last name,
	 *   email, and phone number. The userID will of course stay the same
	 */
	public void test05_01_updateSecondContactForUser() {
		
	}
	
	/*
	 * Note: I have not made any tests for updating values to a blank string, b/c changing a
	 *     filled string to another filled string applies the same tests
	 * Note2: The ContactResourceValidator will have tests for invalid empty strings values (meaning,
	 *     empty email + empty phone = INVALID)
	 */

}
