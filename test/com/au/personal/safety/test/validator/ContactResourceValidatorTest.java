package com.au.personal.safety.test.validator;

import org.junit.*; //imports the @Before, etc.
import static org.junit.Assert.*; //import the asserts

import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.junit.Test;

import javax.ws.rs.core.Response;

import com.au.personal.safety.constants.HttpResponseConstants;
import com.au.personal.safety.contacts.Contact;
import com.au.personal.safety.contacts.ContactDB;
import com.au.personal.safety.database.DatabaseConnectionSingleton;
import com.au.personal.safety.validator.ContactResourceValidator;
import com.au.personal.safety.validator.HttpRequestValidator;

//Sorts by methods by ascending name so that tests run in this desired order
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class ContactResourceValidatorTest {
    
	/* variables */
	String notSetValue = null;
	
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
	String phoneNum4 = "444444444";
	String email4 = ""; //invalid value, but have a valid phone num, so contact is valid
	String phoneCarrier4 = "@vmobl.com ";
	int userID4 = 1;
	
	Contact validContact5;
	String phoneNum5 = ""; //invalid value, but have a valid email, so contact is valid
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
	String phoneCarrierIv5 = null; //invalid b/c we need a valid carrier for our valid phone
	int userIDIv5 = 1;
	
	Contact invalidContact6;
	String phoneNumIv6 = "666666666";
	String emailIv6 = "invalidContact6@email";
	String phoneCarrier6 = ""; //invalid b/c we need a valid carrier for our valid phone
	int userIDIv6 = 1;
	
	Contact invalidContact7;
	String phoneNumIv7 = "777777777";
	String emailIv7 = "invalidContact7@email";
	String phoneCarrier7 = null; //invalid b/c we need a valid carrier for our valid phone
	int userIDIv7 = 1;
	
	
	
	/* methods to use in tests */
	private Contact createValidContact1() {
		Contact result = new Contact(notSetValue, notSetValue, email1, phoneNum1, phoneCarrier1, userID1);
        return result;
	}
	
    private Contact createValidContact2() {
    	Contact result = new Contact(notSetValue, notSetValue, notSetValue, phoneNum2, phoneCarrier2, userID2);
        return result;
	}
    private Contact createValidContact3() {
    	Contact result = new Contact(notSetValue, notSetValue, email3, notSetValue,	notSetValue, userID3);
        return result;
	}
    private Contact createValidContact4() {
    	Contact result = new Contact(notSetValue, notSetValue, email4, phoneNum4, phoneCarrier4, userID4);
        return result;
	}
    private Contact createValidContact5() {
    	Contact result = new Contact(notSetValue, notSetValue, email5, phoneNum5, notSetValue, userID5);
        return result;
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
    private void createInvalidContact6() {
		
	}
    private void createInvalidContact7() {
		
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
		boolean validateExpected = true;
		//test function
		validContact1 = createValidContact1();
		ContactResourceValidator crv = new ContactResourceValidator(validContact1);
		boolean validateResult = crv.validate();
		Response responseResult = crv.getResponse();
		//assert results
		Assert.assertTrue("test03_01 error: expected: " + validateExpected + "\n   result: "
				+ validateResult + "\n", validateExpected == validateResult);
		Assert.assertNull("test03_01 error: expected: null\n   result: "
				+ responseResult + "\n", responseResult);
	}
	
    @Test public void test03_02_validateContact2() {
    	//expected return = true
    	//expected response = null?? I think
    	boolean validateExpected = true;
		//test function
		validContact2 = createValidContact2();
		ContactResourceValidator crv = new ContactResourceValidator(validContact2);
		boolean validateResult = crv.validate();
		Response responseResult = crv.getResponse();
		//assert results
		Assert.assertTrue("test03_02 error: expected: " + validateExpected + "\n   result: "
				+ validateResult + "\n", validateExpected == validateResult);
		Assert.assertNull("test03_02 error: expected: null\n   result: "
				+ responseResult + "\n", responseResult);
	}
	
    @Test public void test03_03_validateContact3() {
    	//expected return = true
    	//expected response = null?? I think
    	boolean validateExpected = true;
		//test function
		validContact3 = createValidContact3();
		ContactResourceValidator crv = new ContactResourceValidator(validContact3);
		boolean validateResult = crv.validate();
		Response responseResult = crv.getResponse();
		//assert results
		Assert.assertTrue("test03_03 error: expected: " + validateExpected + "\n   result: "
				+ validateResult + "\n", validateExpected == validateResult);
		Assert.assertNull("test03_03 error: expected: null\n   result: "
				+ responseResult + "\n", responseResult);
	}
    
    @Test public void test03_04_validateContact4() {
    	//expected return = true
    	//expected response = null?? I think
    	boolean validateExpected = true;
		//test function
		validContact4 = createValidContact4();
		ContactResourceValidator crv = new ContactResourceValidator(validContact4);
		boolean validateResult = crv.validate();
		Response responseResult = crv.getResponse();
		//assert results
		Assert.assertTrue("test03_04 error: expected: " + validateExpected + "\n   result: "
				+ validateResult + "\n", validateExpected == validateResult);
		Assert.assertNull("test03_04 error: expected: null\n   result: "
				+ responseResult + "\n", responseResult);
	}
    
    @Test public void test03_05_validateContact5() {
    	//expected return = true
    	//expected response = null?? I think
    	boolean validateExpected = true;
		//test function
		validContact5 = createValidContact5();
		ContactResourceValidator crv = new ContactResourceValidator(validContact5);
		boolean validateResult = crv.validate();
		Response responseResult = crv.getResponse();
		//assert results
		Assert.assertTrue("test03_05 error: expected: " + validateExpected + "\n   result: "
				+ validateResult + "\n", validateExpected == validateResult);
		Assert.assertNull("test03_05 error: expected: null\n   result: "
				+ responseResult + "\n", responseResult);
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
    @Test public void test03_11_validateIvContact6() {
		
    	//expected return = false
    	//expected response = INVALID_PHONE_CARRIER_SELECTED
    	
		fail("Not yet implemented");
	}
    @Test public void test03_12_validateIvContact7() {
		
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
