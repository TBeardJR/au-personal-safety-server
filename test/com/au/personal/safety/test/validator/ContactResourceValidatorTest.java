package com.au.personal.safety.test.validator;

import org.junit.*; //imports the @Before, etc.
import static org.junit.Assert.*; //import the asserts

import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.mockito.Mock;
import org.junit.Test;

import static org.mockito.Mockito.*;

import com.au.personal.safety.constants.HttpResponseConstants;
import com.au.personal.safety.contacts.Contact;
import com.au.personal.safety.contacts.ContactDB;
import com.au.personal.safety.database.DatabaseConnectionSingleton;
import com.au.personal.safety.validator.ContactResourceValidator;


//Sorts by methods by ascending name so that tests run in this desired order
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class ContactResourceValidatorTest {
    
	/* variables */
	Contact validContact1;
	String phoneNum1 = "111111111";
	String email1 = "validContact1@email";
	String phoneCarrier1 = "@txt.att.net";
	int userID1 = 1;
	
	Contact validContact2;
	String phoneNum2 = "222222222";
	String phoneCarrier2 = "@vtext.com";
	int userID2 = 1;
	
	Contact validContact3;
	String email3 = "validContact3@email";
	int userID3 = 1;
	
	Contact validContact4;
	String phone4 = "444444444";
	String email4 = ""; //invalid value, but have a valid phone num, so contact is valid
	String phoneCarrier4 = "@vmobl.com ";
	int userID4 = 1;
	
	Contact validContact5;
	String phone5 = ""; //invalid value, but have a valid email, so contact is valid
	String email5 = "validContact5@email";
	int userID5 = 1;
	
	Contact invalidContact1; //all values invalid
	String phoneNumIv1 = "";
	String emailIv1 = "";
	String phoneCarrierIv1 = "";
	int userIDIv1 = 0;
	
	Contact invalidContact2; //all values invalid
	String phoneNumIv2 = null;
	String emailIv2 = null;
	String phoneCarrierIv2 = null;
	int userIDIv = 0;
	
	Contact invalidContact3;
	String phoneNumIv3 = "333-333-333"; //invalid
	String emailIv3 = "invalidContact3@email";
	String phoneCarrierIv3 = "@messaging.sprintpcs.com";
	int userIDIv3 = 1;
	
	Contact invalidContact4;
	String phoneNumIv4 = "444444444";
	String phoneCarrierIv4 = ""; //invalid b/c we need a valid carrier for our valid phone
	int userIDIv4 = 1;
	
	Contact invalidContact5;
	String phoneNumIv5 = "555555555";
	String emailIv5 = "invalidContact5@email";
	String phoneCarrier5 = ""; //invalid b/c we need a valid carrier for our valid phone
	int userIDIv5 = 1;
	
	
	
	/* methods to use in tests */
	private void createValidContact1() {
		
	}
	
    private void createValidContact2() {
		
	}
    private void createValidContact3() {
		
	}
    private void createValidContact4() {
		
	}
    private void createValidContact5() {
		
	}
    private void createInvalidContact1() {
		
	}
    private void createInvalidContact2() {
		
	}
    private void createInvalidContact3() {
		
	}
    private void createInvalidContact4() {
		
	}
    private void createInvalidContact5() {
		
	}
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	/*
	 * Test that the constructor creates an object
	 */
	@Test
	public void test01_01_instanceCreated() {
		
		//set JAWS_DB_URL = "..."
		
		//check that the object was created
		//assertNotNull("test01_01: contactDB1 is null\n", contactDB1);
		
		fail("Not yet implemented");
	}
	
	/*
	 * Test ContactResourceValidator
	 */
	
	
	
	/*
	 * Test validate
	 */
	//check that the return value for validate is as expected AND check that the class attribute "response" is
	//  as expected
	//run these checks for all cases of contacts defined in the attribute section of this class
	
	@Test public void test03_01_validateContact1() {
		//expected return = true
		//expected response = null?? I think
		
		ContactResourceValidator crv = new ContactResourceValidator(validContact1);
		
		fail("Not yet implemented");
	}
	
    @Test public void test03_02_validateContact2() {
		
    	//expected return = true
    	//expected response = null?? I think
    	
		fail("Not yet implemented");
	}
	
    @Test public void test03_03_validateContact3() {
		
    	//expected return = true
    	//expected response = null?? I think
    	
		fail("Not yet implemented");
	}
    
    @Test public void test03_04_validateContact4() {
		
    	//expected return = true
    	//expected response = null?? I think
    	
		fail("Not yet implemented");
	}
    
    @Test public void test03_05_validateContact5() {
		
    	//expected return = true
    	//expected response = null?? I think
    	
		fail("Not yet implemented");
	}
    
    @Test public void test03_06_validateIvContact1() {
		
    	//expected return = false
    	//expected response = INVALID_CONTACT
    	
		fail("Not yet implemented");
	}
	
    @Test public void test03_07_validateIvContact2() {
		
    	//expected return = false
    	//expected response = INVALID_CONTACT
    	
		fail("Not yet implemented");
	}
    
    @Test public void test03_08_validateIvContact3() {
		
    	//expected return = false
    	//expected response = INVALID_CONTACT
    	
		fail("Not yet implemented");
	}
    
    @Test public void test03_09_validateIvContact4() {
		
    	//expected return = false
    	//expected response = INVALID_PHONE_CARRIER_SELECTED
    	
		fail("Not yet implemented");
	}
    
    @Test public void test03_10_validateIvContact5() {
		
    	//expected return = false
    	//expected response = INVALID_PHONE_CARRIER_SELECTED
    	
		fail("Not yet implemented");
	}
    
	
	/*
	 * Test getResponse
	 */
	
	
	/*
	 * Test buildResponse
	 */
	
	/*
	 * Test validateThisAttribute
	 */
	
	/*
	 * isEmptyString
	 */
	
	
	
	/*
	 * Test isAPhoneCarrier
	 */
    
    
    
	
    /*
	 * Test stripCharacters
	 */
	
	/*
	 * Test formatPhone
	 */
	

}
