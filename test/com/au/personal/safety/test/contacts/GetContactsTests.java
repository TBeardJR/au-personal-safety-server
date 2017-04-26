package com.au.personal.safety.test.contacts;
import org.junit.*; //imports the @Before, etc.
import static org.junit.Assert.*; //import the asserts
import java.util.List;
import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.ws.rs.core.Response;

import org.junit.runners.MethodSorters;

import com.au.personal.safety.alerts.SendAlerts;
import com.au.personal.safety.constants.ContactConstants;
import com.au.personal.safety.constants.HttpResponseConstants;
import com.au.personal.safety.contacts.Contact;
import com.au.personal.safety.contacts.ContactDB;
import com.au.personal.safety.database.DatabaseConnectionSingleton;
import com.au.personal.safety.users.User;
import com.au.personal.safety.alerts.SendAlerts;
public class GetContactsTests {
	@Before
	public void beforeEachTest() {
		DatabaseConnectionSingleton conn;
		Statement stmt;
		//initialize connection variables
		//DatabaseConnectionSingleton conn;
		conn = null;
		stmt = null;
		
		try {
			//open connection
			conn = DatabaseConnectionSingleton.getInstance();
			conn.openConnection();
			stmt = conn.getConnection().createStatement();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	@Ignore
	public void test_Sydney() {
		Contact contact = new Contact();
		contact.setContactID(7);
		ContactDB contactDB = new ContactDB(contact);
		List<Contact> contactList = contactDB.getContacts(1);
		Assert.assertEquals(3, contactList.size());
		
		
		
	}
	
	@Test
	public void test_Alert() {
		User user = new User();
		user.setUserID(15);
		SendAlerts aSendAlertsObj = new SendAlerts(user);
		Response theResponse = aSendAlertsObj.alertContacts();
		
		
		
		
	}
}
