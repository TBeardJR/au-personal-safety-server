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
	String phoneCarrier4 = "@vmobl.com";
	int userID4 = 1;
	
	Contact validContact5;
	String phoneNum5 = ""; //invalid value, but have a valid email, so contact is valid
	String email5 = "validContact5@email";
	int userID5 = 1;
	
	Contact invalidContact1; //all values invalid -> INVALID_CONTACT
	String phoneNumIv1 = "";
	String emailIv1 = "";
	String phoneCarrierIv1 = "";
	int userIDIv1 = 0;
	
	Contact invalidContact2; //all values invalid -> INVALID_CONTACT
	String phoneNumIv2 = null;
	String emailIv2 = null;
	String phoneCarrierIv2 = null;
	int userIDIv = 0;
	
	Contact invalidContact3;
	String phoneNumIv3 = "333-333-333"; //invalid -> INVALID_CONTACT
	String emailIv3 = "invalidContact3@email";
	String phoneCarrierIv3 = "@messaging.sprintpcs.com";
	int userIDIv3 = 1;
	
	Contact invalidContact4;
	String phoneNumIv4 = "444444444";
	String phoneCarrierIv4 = ""; //invalid b/c we need a valid carrier for our valid phone -> INVALID_CARRIER_SELECTED
	int userIDIv4 = 1;
	
	Contact invalidContact5;
	String phoneNumIv5 = "555555555";
	String phoneCarrierIv5 = null; //invalid b/c we need a valid carrier for our valid phone -> INVALID_CARRIER_SELECTED
	int userIDIv5 = 1;
	
	Contact invalidContact6;
	String phoneNumIv6 = "666666666";
	String emailIv6 = "invalidContact6@email";
	String phoneCarrier6 = ""; //invalid b/c we need a valid carrier for our valid phone -> INVALID_CARRIER_SELECTED
	int userIDIv6 = 1;
	
	Contact invalidContact7;
	String phoneNumIv7 = "777777777";
	String emailIv7 = "invalidContact7@email";
	String phoneCarrier7 = null; //invalid b/c we need a valid carrier for our valid phone -> INVALID_CARRIER_SELECTED
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
    private Contact createInvalidContact1() {
		Contact result = new Contact(notSetValue, notSetValue, emailIv1, phoneNumIv1, phoneCarrierIv1, userIDIv1);
    	return result;
	}
    private Contact createInvalidContact2() {
		Contact result = new Contact(notSetValue, notSetValue, emailIv2, phoneNumIv2, phoneCarrierIv2, userIDIv);
    	return result;
	}
    private Contact createInvalidContact3() {
		Contact result = new Contact(notSetValue, notSetValue, emailIv3, phoneNumIv3, phoneCarrierIv3, userIDIv3);
    	return result;
	}
    private Contact createInvalidContact4() {
		Contact result = new Contact(notSetValue, notSetValue, notSetValue, phoneNumIv4, phoneCarrierIv4, userIDIv4);
		return result;
	}
    private Contact createInvalidContact5() {
		Contact result = new Contact(notSetValue, notSetValue, notSetValue, phoneNumIv5, phoneCarrierIv5, userIDIv5);
    	return result;
	}
    private Contact createInvalidContact6() {
		Contact result = new Contact(notSetValue, notSetValue, emailIv6, phoneNumIv6, phoneCarrier6, userIDIv6);
        return result;
	}
    private Contact createInvalidContact7() {
		Contact result = new Contact(notSetValue, notSetValue, emailIv7, phoneNumIv7, phoneCarrier7, userIDIv7);
    	return result;
	}
	
		
	/*
	 * Test that the constructor creates an object
	 */
	@Test
	public void test01_01_instanceCreated() {
		
		//set JAWS_DB_URL = "..."
		
		//check that the object was created
		//
		Contact contactHere = createValidContact1();
		ContactResourceValidator crv = new ContactResourceValidator(contactHere);
		
		
		Assert.assertNotNull("test01_01 error1: contactDB1 is null\n", crv);
		Assert.assertEquals("test01_01 error2: contact was not set\n", contactHere, crv.getContact());
		
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
		String responseResultString = "";
		//if the responseResult is null, we cannot get an entity from it to print, so set the responseResultString = null
		if (responseResult == null) {
			responseResultString = null;
		}
		//else, get the entity from responseResult to print as a string
		else {
		    responseResultString = responseResult.getEntity().toString();
		}
		//assert results
		Assert.assertTrue("test03_01 error1: expected: " + validateExpected + "\n   result: "
				+ validateResult + "\n", validateExpected == validateResult);
		Assert.assertNull("test03_01 error2: expected: null\n   result: "
				+ responseResultString + "\n", responseResult);
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
		String responseResultString = "";
		//if the responseResult is null, we cannot get an entity from it to print, so set the responseResultString = null
		if (responseResult == null) {
			responseResultString = null;
		}
		//else, get the entity from responseResult to print as a string
		else {
		    responseResultString = responseResult.getEntity().toString();
		}
		//assert results
		Assert.assertTrue("test03_02 error1: expected: " + validateExpected + "\n   result: "
				+ validateResult + "\n", validateExpected == validateResult);
		Assert.assertNull("test03_02 error2: expected: null\n   result: "
				+ responseResultString + "\n", responseResult);
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
		String responseResultString = "";
		//if the responseResult is null, we cannot get an entity from it to print, so set the responseResultString = null
		if (responseResult == null) {
			responseResultString = null;
		}
		//else, get the entity from responseResult to print as a string
		else {
		    responseResultString = responseResult.getEntity().toString();
		}
		//assert results
		Assert.assertTrue("test03_03 error1: expected: " + validateExpected + "\n   result: "
				+ validateResult + "\n", validateExpected == validateResult);
		Assert.assertNull("test03_03 error2: expected: null\n   result: "
				+ responseResultString + "\n", responseResult);
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
		String responseResultString = "";
		//if the responseResult is null, we cannot get an entity from it to print, so set the responseResultString = null
		if (responseResult == null) {
			responseResultString = null;
		}
		//else, get the entity from responseResult to print as a string
		else {
		    responseResultString = responseResult.getEntity().toString();
		}
		//assert results
		Assert.assertTrue("test03_04 error1: expected: " + validateExpected + "\n   result: "
				+ validateResult + "\n", validateExpected == validateResult);
		Assert.assertNull("test03_04 error2: expected: null\n   result: "
				+ responseResultString + "\n", responseResult);
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
		String responseResultString = "";
		//if the responseResult is null, we cannot get an entity from it to print, so set the responseResultString = null
		if (responseResult == null) {
			responseResultString = null;
		}
		//else, get the entity from responseResult to print as a string
		else {
		    responseResultString = responseResult.getEntity().toString();
		}
		//assert results
		Assert.assertTrue("test03_05 error1: expected: " + validateExpected + "\n   result: "
				+ validateResult + "\n", validateExpected == validateResult);
		Assert.assertNull("test03_05 error2: expected: null\n   result: "
				+ responseResultString + "\n", responseResult);
	}
    
    @Test public void test03_06_validateIvContact1() {
    	//expected return = false
    	//expected response = INVALID_CONTACT
    	boolean validateExpected = false;
    	Response responseExpected = Response.status(Response.Status.BAD_REQUEST).entity(HttpResponseConstants.INVALID_CONTACT).build();
    	String responseExpectedString = responseExpected.getEntity().toString();
		//test function
		Contact invalidContactHere = createInvalidContact1();
		ContactResourceValidator crv = new ContactResourceValidator(invalidContactHere);
		boolean validateResult = crv.validate();
		Response responseResult = crv.getResponse();
		String responseResultString = "";
		Object entityResult = null;
		//if the responseResult is null, we cannot get an entity from it to print, so set the responseResultString = null
		if (responseResult == null) {
			responseResultString = null;
		}
		//else, get the entity from responseResult to print as a string
		else {
		    responseResultString = responseResult.getEntity().toString();
		    entityResult = responseResult.getEntity();
		}
		//assert results
		Assert.assertTrue("test03_06 error1: expected: " + validateExpected + "\n   result: "
				+ validateResult + "\n", validateExpected == validateResult);
		Assert.assertTrue("test03_06 error2: expected: " + responseExpectedString + "\n   result: "
				+ responseResultString + "\n", responseExpected.getEntity().equals(entityResult));
	}
	
    @Test public void test03_07_validateIvContact2() {
    	//expected return = false
    	//expected response = INVALID_CONTACT
    	boolean validateExpected = false;
    	Response responseExpected = Response.status(Response.Status.BAD_REQUEST).entity(HttpResponseConstants.INVALID_CONTACT).build();
    	String responseExpectedString = responseExpected.getEntity().toString();
		//test function
		Contact invalidContactHere = createInvalidContact2();
		ContactResourceValidator crv = new ContactResourceValidator(invalidContactHere);
		boolean validateResult = crv.validate();
		Response responseResult = crv.getResponse();
		String responseResultString = "";
		Object entityResult = null;
		//if the responseResult is null, we cannot get an entity from it to print, so set the responseResultString = null
		if (responseResult == null) {
			responseResultString = null;
		}
		//else, get the entity from responseResult to print as a string
		else {
		    responseResultString = responseResult.getEntity().toString();
		    entityResult = responseResult.getEntity();
		}
		//assert results
		Assert.assertTrue("test03_07 error1: expected: " + validateExpected + "\n   result: "
				+ validateResult + "\n", validateExpected == validateResult);
		Assert.assertTrue("test03_07 error2: expected: " + responseExpectedString + "\n   result: "
				+ responseResultString + "\n", responseExpected.getEntity().equals(entityResult));
	}
    
    @Test public void test03_08_validateIvContact3() {
    	//expected return = false
    	//expected response = INVALID_CONTACT
    	boolean validateExpected = false;
    	Response responseExpected = Response.status(Response.Status.BAD_REQUEST).entity(HttpResponseConstants.INVALID_CONTACT).build();
    	String responseExpectedString = responseExpected.getEntity().toString();
		//test function
		Contact invalidContactHere = createInvalidContact3();
		ContactResourceValidator crv = new ContactResourceValidator(invalidContactHere);
		boolean validateResult = crv.validate();
		Response responseResult = crv.getResponse();
		String responseResultString = "";
		Object entityResult = null;
		//if the responseResult is null, we cannot get an entity from it to print, so set the responseResultString = null
		if (responseResult == null) {
			responseResultString = null;
		}
		//else, get the entity from responseResult to print as a string
		else {
		    responseResultString = responseResult.getEntity().toString();
		    entityResult = responseResult.getEntity();
		}
		//assert results
		Assert.assertTrue("test03_08 error1: expected: " + validateExpected + "\n   result: "
				+ validateResult + "\n", validateExpected == validateResult);
		Assert.assertTrue("test03_08 error2: expected: " + responseExpectedString + "\n   result: "
				+ responseResultString + "\n", responseExpected.getEntity().equals(entityResult));
	}
    
    @Test public void test03_09_validateIvContact4() {
    	//expected return = false
    	//expected response = INVALID_PHONE_CARRIER_SELECTED
    	boolean validateExpected = false;
    	Response responseExpected = Response.status(Response.Status.BAD_REQUEST).entity(HttpResponseConstants.INVALID_PHONE_CARRIER_SELECTED).build();
    	String responseExpectedString = responseExpected.getEntity().toString();
		//test function
		Contact invalidContactHere = createInvalidContact4();
		ContactResourceValidator crv = new ContactResourceValidator(invalidContactHere);
		boolean validateResult = crv.validate();
		Response responseResult = crv.getResponse();
		String responseResultString = "";
		Object entityResult = null;
		//if the responseResult is null, we cannot get an entity from it to print, so set the responseResultString = null
		if (responseResult == null) {
			responseResultString = null;
		}
		//else, get the entity from responseResult to print as a string
		else {
		    responseResultString = responseResult.getEntity().toString();
		    entityResult = responseResult.getEntity();
		}
		//assert results
		Assert.assertTrue("test03_09 error1: expected: " + validateExpected + "\n   result: "
				+ validateResult + "\n", validateExpected == validateResult);
		Assert.assertTrue("test03_09 error2: expected: " + responseExpectedString + "\n   result: "
				+ responseResultString + "\n", responseExpected.getEntity().equals(entityResult));
	}
    
    @Test public void test03_10_validateIvContact5() {
    	//expected return = false
    	//expected response = INVALID_PHONE_CARRIER_SELECTED
    	boolean validateExpected = false;
    	Response responseExpected = Response.status(Response.Status.BAD_REQUEST).entity(HttpResponseConstants.INVALID_PHONE_CARRIER_SELECTED).build();
    	String responseExpectedString = responseExpected.getEntity().toString();
		//test function
		Contact invalidContactHere = createInvalidContact5();
		ContactResourceValidator crv = new ContactResourceValidator(invalidContactHere);
		boolean validateResult = crv.validate();
		Response responseResult = crv.getResponse();
		String responseResultString = "";
		Object entityResult = null;
		//if the responseResult is null, we cannot get an entity from it to print, so set the responseResultString = null
		if (responseResult == null) {
			responseResultString = null;
		}
		//else, get the entity from responseResult to print as a string
		else {
		    responseResultString = responseResult.getEntity().toString();
		    entityResult = responseResult.getEntity();
		}
		//assert results
		Assert.assertTrue("test03_10 error1: expected: " + validateExpected + "\n   result: "
				+ validateResult + "\n", validateExpected == validateResult);
		Assert.assertTrue("test03_10 error2: expected: " + responseExpectedString + "\n   result: "
				+ responseResultString + "\n", responseExpected.getEntity().equals(entityResult));
	}
    @Test public void test03_11_validateIvContact6() {
    	//expected return = false
    	//expected response = INVALID_PHONE_CARRIER_SELECTED
    	boolean validateExpected = false;
    	Response responseExpected = Response.status(Response.Status.BAD_REQUEST).entity(HttpResponseConstants.INVALID_PHONE_CARRIER_SELECTED).build();
    	String responseExpectedString = responseExpected.getEntity().toString();
		//test function
		Contact invalidContactHere = createInvalidContact6();
		ContactResourceValidator crv = new ContactResourceValidator(invalidContactHere);
		boolean validateResult = crv.validate();
		Response responseResult = crv.getResponse();
		String responseResultString = "";
		Object entityResult = null;
		//if the responseResult is null, we cannot get an entity from it to print, so set the responseResultString = null
		if (responseResult == null) {
			responseResultString = null;
		}
		//else, get the entity from responseResult to print as a string
		else {
		    responseResultString = responseResult.getEntity().toString();
		    entityResult = responseResult.getEntity();
		}
		//assert results
		Assert.assertTrue("test03_11 error1: expected: " + validateExpected + "\n   result: "
				+ validateResult + "\n", validateExpected == validateResult);
		Assert.assertTrue("test03_11 error2: expected: " + responseExpectedString + "\n   result: "
				+ responseResultString + "\n", responseExpected.getEntity().equals(entityResult));
	}
    @Test public void test03_12_validateIvContact7() {
    	//expected return = false
    	//expected response = INVALID_PHONE_CARRIER_SELECTED
    	boolean validateExpected = false;
    	Response responseExpected = Response.status(Response.Status.BAD_REQUEST).entity(HttpResponseConstants.INVALID_PHONE_CARRIER_SELECTED).build();
    	String responseExpectedString = responseExpected.getEntity().toString();
		//test function
		Contact invalidContactHere = createInvalidContact7();
		ContactResourceValidator crv = new ContactResourceValidator(invalidContactHere);
		boolean validateResult = crv.validate();
		Response responseResult = crv.getResponse();
		String responseResultString = "";
		Object entityResult = null;
		//if the responseResult is null, we cannot get an entity from it to print, so set the responseResultString = null
		if (responseResult == null) {
			responseResultString = null;
		}
		//else, get the entity from responseResult to print as a string
		else {
		    responseResultString = responseResult.getEntity().toString();
		    entityResult = responseResult.getEntity();
		}
		//assert results
		Assert.assertTrue("test03_12 error1: expected: " + validateExpected + "\n   result: "
				+ validateResult + "\n", validateExpected == validateResult);
		Assert.assertTrue("test03_12 error2: expected: " + responseExpectedString + "\n   result: "
				+ responseResultString + "\n", responseExpected.getEntity().equals(entityResult));
	}
	
	/*
	 * Test getResponse
	 */
	
    //not going to test getResponse because the implementation will be return a value for a variable
	
	/*
	 * Test buildResponse
	 */
    
    //cannot test this independently because the method is classified as protected
    // can rely on the validate tests b/c the buildResponse function is used within the validate function
    // AND the defined tests check that the correct response was built
	
    
    
    
	/*
	 * Test validateThisAttribute
	 * 
	 * To run this test must make this function "public" instead of "private" AND uncomment the below code
	 */
    
    /*
    
    //cannot test this independently because the method is classified as private
    // can rely on the validate tests b/c the buildResponse function is used within the validate function
    // AND the defined tests check that the correct response was built
    
    //However, since I am using TDD, I am going to change the function to public to write the function
    // and then return it private once the function is written 
    
    @Test public void test05_01_validateThisAttr_email_null() {
    	//validateThisAttribute(String attr_value_in, String attr_name_in)
    	//ContactEmail
    	boolean expected = false;
    	//create ContactResourceValidator object
    	Contact contactHere = createValidContact1();
    	ContactResourceValidator crv = new ContactResourceValidator(contactHere);
    	//run the function want to test (validateThisAttribute)
    	boolean result = crv.validateThisAttribute(null, "ContactEmail");
    	Assert.assertTrue("test05_01 error: expected: " + expected + "\n   result: " + result + "\n", result == expected);
    }
    
    @Test public void test05_02_validateThisAttr_email_blank() {
    	//validateThisAttribute(String attr_value_in, String attr_name_in)
    	//ContactEmail
    	boolean expected = false;
    	//create ContactResourceValidator object
    	Contact contactHere = createValidContact1();
    	ContactResourceValidator crv = new ContactResourceValidator(contactHere);
    	//run the function want to test (validateThisAttribute)
    	boolean result = crv.validateThisAttribute("", "ContactEmail");
    	Assert.assertTrue("test05_02 error: expected: " + expected + "\n   result: " + result + "\n", result == expected);
    }
    
    @Test public void test05_03_validateThisAttr_email_valid() {
    	//validateThisAttribute(String attr_value_in, String attr_name_in)
    	//ContactEmail
    	boolean expected = true;
    	//create ContactResourceValidator object
    	Contact contactHere = createValidContact1();
    	ContactResourceValidator crv = new ContactResourceValidator(contactHere);
    	//run the function want to test (validateThisAttribute)
    	boolean result = crv.validateThisAttribute("@", "ContactEmail");
    	Assert.assertTrue("test05_03 error: expected: " + expected + "\n   result: " + result + "\n", result == expected);
    }
    
    @Test public void test05_04_validateThisAttr_phone_null() {
    	//validateThisAttribute(String attr_value_in, String attr_name_in)
    	//ContactPhone
    	boolean expected = false;
    	//create ContactResourceValidator object
    	Contact contactHere = createValidContact1();
    	ContactResourceValidator crv = new ContactResourceValidator(contactHere);
    	//run the function want to test (validateThisAttribute)
    	boolean result = crv.validateThisAttribute(null, "ContactPhone");
    	Assert.assertTrue("test05_04 error: expected: " + expected + "\n   result: " + result + "\n", result == expected);
    }
    
    @Test public void test05_05_validateThisAttr_phone_blank() {
    	//validateThisAttribute(String attr_value_in, String attr_name_in)
    	//ContactPhone
    	boolean expected = false;
    	//create ContactResourceValidator object
    	Contact contactHere = createValidContact1();
    	ContactResourceValidator crv = new ContactResourceValidator(contactHere);
    	//run the function want to test (validateThisAttribute)
    	boolean result = crv.validateThisAttribute("11111111", "ContactPhone");
    	Assert.assertTrue("test05_05 error: expected: " + expected + "\n   result: " + result + "\n", result == expected);
    }
    
    @Test public void test05_06_validateThisAttr_phone_lessThanNineDigits() {
    	//validateThisAttribute(String attr_value_in, String attr_name_in)
    	//ContactPhone
    	boolean expected = false;
    	//create ContactResourceValidator object
    	Contact contactHere = createValidContact1();
    	ContactResourceValidator crv = new ContactResourceValidator(contactHere);
    	//run the function want to test (validateThisAttribute)
    	boolean result = crv.validateThisAttribute("11111111", "ContactPhone");
    	Assert.assertTrue("test05_06 error: expected: " + expected + "\n   result: " + result + "\n", result == expected);
    }
    @Test public void test05_07_validateThisAttr_phone_moreThanNineDigits() {
    	//validateThisAttribute(String attr_value_in, String attr_name_in)
    	//ContactPhone
    	boolean expected = false;
    	//create ContactResourceValidator object
    	Contact contactHere = createValidContact1();
    	ContactResourceValidator crv = new ContactResourceValidator(contactHere);
    	//run the function want to test (validateThisAttribute)
    	boolean result = crv.validateThisAttribute("1111111111", "ContactPhone");
    	Assert.assertTrue("test05_07 error: expected: " + expected + "\n   result: " + result + "\n", result == expected);
    }
    @Test public void test05_08_validateThisAttr_phone_charsAndDigits() {
    	//validateThisAttribute(String attr_value_in, String attr_name_in)
    	//ContactPhone
    	boolean expected = false;
    	//create ContactResourceValidator object
    	Contact contactHere = createValidContact1();
    	ContactResourceValidator crv = new ContactResourceValidator(contactHere);
    	//run the function want to test (validateThisAttribute)
    	boolean result = crv.validateThisAttribute("111a11111", "ContactPhone");
    	Assert.assertTrue("test05_08 error: expected: " + expected + "\n   result: " + result + "\n", result == expected);
    }
    
    @Test public void test05_09_validateThisAttr_phone_valid() {
    	//validateThisAttribute(String attr_value_in, String attr_name_in)
    	//ContactPhone
    	boolean expected = true;
    	//create ContactResourceValidator object
    	Contact contactHere = createValidContact1();
    	ContactResourceValidator crv = new ContactResourceValidator(contactHere);
    	//run the function want to test (validateThisAttribute)
    	boolean result = crv.validateThisAttribute("111111111", "ContactPhone");
    	Assert.assertTrue("test05_09 error: expected: " + expected + "\n   result: " + result + "\n", result == expected);
    }
    @Test public void test05_10_validateThisAttr_carrier_null() {
    	//validateThisAttribute(String attr_value_in, String attr_name_in)
    	//ContactCarrier
    	boolean expected = false;
    	//create ContactResourceValidator object
    	Contact contactHere = createValidContact1();
    	ContactResourceValidator crv = new ContactResourceValidator(contactHere);
    	//run the function want to test (validateThisAttribute)
    	boolean result = crv.validateThisAttribute(null, "ContactPhone");
    	Assert.assertTrue("test05_10 error: expected: " + expected + "\n   result: " + result + "\n", result == expected);
    }
    @Test public void test05_11_validateThisAttr_carrier_blank() {
    	//validateThisAttribute(String attr_value_in, String attr_name_in)
    	//ContactCarrier
    	boolean expected = false;
    	//create ContactResourceValidator object
    	Contact contactHere = createValidContact1();
    	ContactResourceValidator crv = new ContactResourceValidator(contactHere);
    	//run the function want to test (validateThisAttribute)
    	boolean result = crv.validateThisAttribute("", "ContactPhone");
    	Assert.assertTrue("test05_11 error: expected: " + expected + "\n   result: " + result + "\n", result == expected);
    }
    @Test public void test05_12_validateThisAttr_carrier_notInDictionary() {
    	//validateThisAttribute(String attr_value_in, String attr_name_in)
    	//ContactCarrier
    	boolean expected = false;
    	//create ContactResourceValidator object
    	Contact contactHere = createValidContact1();
    	ContactResourceValidator crv = new ContactResourceValidator(contactHere);
    	//run the function want to test (validateThisAttribute)
    	boolean result = crv.validateThisAttribute("@notReal", "ContactPhone");
    	Assert.assertTrue("test05_12 error: expected: " + expected + "\n   result: " + result + "\n", result == expected);
    }
    @Test public void test05_13_validateThisAttr_carrier_valid() {
    	//validateThisAttribute(String attr_value_in, String attr_name_in)
    	//ContactCarrier
    	boolean expected = true;
    	//create ContactResourceValidator object
    	Contact contactHere = createValidContact1();
    	ContactResourceValidator crv = new ContactResourceValidator(contactHere);
    	//run the function want to test (validateThisAttribute)
    	boolean result = crv.validateThisAttribute("@txt.att.net", "ContactCarrier");
    	Assert.assertTrue("test05_13 error1: expected: " + expected + "\n   result: " + result + "\n", result == expected);
    	boolean result2 = crv.validateThisAttribute("@tmomail.net", "ContactCarrier");
    	Assert.assertTrue("test05_13 error2: expected: " + expected + "\n   result: " + result2 + "\n", result2 == expected);
    	boolean result3 = crv.validateThisAttribute("@vmobl.com", "ContactCarrier");
    	Assert.assertTrue("test05_13 error2: expected: " + expected + "\n   result: " + result3 + "\n", result3 == expected);
    	boolean result4 = crv.validateThisAttribute("@cingularme.com", "ContactCarrier");
    	Assert.assertTrue("test05_13 error2: expected: " + expected + "\n   result: " + result4 + "\n", result4 == expected);
    	boolean result5 = crv.validateThisAttribute("@messaging.sprintpcs.com", "ContactCarrier");
    	Assert.assertTrue("test05_13 error2: expected: " + expected + "\n   result: " + result5 + "\n", result5 == expected);
    	boolean result6 = crv.validateThisAttribute("@vtext.com", "ContactCarrier");
    	Assert.assertTrue("test05_13 error2: expected: " + expected + "\n   result: " + result6 + "\n", result6 == expected);
    	boolean result7 = crv.validateThisAttribute("@messaging.nextel.com", "ContactCarrier");
    	Assert.assertTrue("test05_13 error2: expected: " + expected + "\n   result: " + result7 + "\n", result7 == expected);
    }
	
	//UserID
    @Test public void test06_01_validateThisAttr_userID_null() {
    	//validateThisAttribute(String attr_value_in, String attr_name_in)
    	//ContactCarrier
    	boolean expected = false;
    	//create ContactResourceValidator object
    	Contact contactHere = createValidContact1();
    	ContactResourceValidator crv = new ContactResourceValidator(contactHere);
    	//run the function want to test (validateThisAttribute)
    	boolean result = crv.validateThisAttribute(null, "UserID");
    	Assert.assertTrue("test06_01 error: expected: " + expected + "\n   result: " + result + "\n", result == expected);
    }
    @Test public void test06_02_validateThisAttr_userID_zero() {
    	//validateThisAttribute(String attr_value_in, String attr_name_in)
    	//ContactCarrier
    	boolean expected = false;
    	//create ContactResourceValidator object
    	Contact contactHere = createValidContact1();
    	ContactResourceValidator crv = new ContactResourceValidator(contactHere);
    	//run the function want to test (validateThisAttribute)
    	boolean result = crv.validateThisAttribute(0, "UserID");
    	Assert.assertTrue("test06_01 error: expected: " + expected + "\n   result: " + result + "\n", result == expected);
    }
    @Test public void test06_03_validateThisAttr_userID_valid() {
    	//validateThisAttribute(String attr_value_in, String attr_name_in)
    	//ContactCarrier
    	boolean expected = true;
    	//create ContactResourceValidator object
    	Contact contactHere = createValidContact1();
    	ContactResourceValidator crv = new ContactResourceValidator(contactHere);
    	//run the function want to test (validateThisAttribute)
    	boolean result = crv.validateThisAttribute(1, "UserID");
    	Assert.assertTrue("test06_03 error: expected: " + expected + "\n   result: " + result + "\n", result == expected);
    }
    */
	
}
