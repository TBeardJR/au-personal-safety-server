package com.au.personal.safety.validator.test;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.au.personal.safety.constants.HttpResponseConstants;
import com.au.personal.safety.database.Location;
import com.au.personal.safety.email.EmailMessage;
import com.au.personal.safety.validator.EmailResourceValidator;
import com.au.personal.safety.validator.HttpRequestValidator;
import com.au.personal.safety.validator.LocationResourceValidator;

public class LocationResourceValidatorTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testLocationValidator_NullLocation_ShouldReturnFalse() {
		String expectedResponse = HttpResponseConstants.NULL_PARAMETERS;
		Integer userID = 1;
		Location location = null;
		HttpRequestValidator validator = new LocationResourceValidator(location, userID);

		boolean isRequestValid = validator.validate();

		Assert.assertFalse("Should return false.", isRequestValid);
		Assert.assertEquals("Response should be " + expectedResponse, expectedResponse,
				validator.getResponse().getEntity());
	}
	
	@Test
	public void testLocationValidator_NullUserID_ShouldReturnFalse() {
		String expectedResponse = HttpResponseConstants.NULL_PARAMETERS;
		Integer userID = null;
		Location location = new Location();
		HttpRequestValidator validator = new LocationResourceValidator(location, userID);

		boolean isRequestValid = validator.validate();

		Assert.assertFalse("Should return false.", isRequestValid);
		Assert.assertEquals("Response should be " + expectedResponse, expectedResponse,
				validator.getResponse().getEntity());
	}
	
	@Test
	public void testEmailResourceValidator_EmptyMessageText_ShouldReturnFalse() {
		String expectedResponse = HttpResponseConstants.MISSING_PARTS_OF_EMAIL;
		EmailMessage emailMessage = new EmailMessage();
		emailMessage.setSubject("MySubject");
		emailMessage.setRecipients("recipients");
		emailMessage.setMessageText("");
		HttpRequestValidator validator = new EmailResourceValidator(emailMessage);
		
		boolean isRequestValid = validator.validate();
		
		Assert.assertFalse("Should return false.", isRequestValid);
		Assert.assertEquals("Response should be " + expectedResponse, expectedResponse, validator.getResponse().getEntity());
	}
	
	@Test
	public void testEmailResourceValidator_NullRecipients_ShouldReturnFalse() {
		String expectedResponse = HttpResponseConstants.MISSING_PARTS_OF_EMAIL;
		EmailMessage emailMessage = new EmailMessage();
		emailMessage.setSubject("MySubject");
		emailMessage.setRecipients(null);
		emailMessage.setMessageText("message");
		HttpRequestValidator validator = new EmailResourceValidator(emailMessage);
		
		boolean isRequestValid = validator.validate();
		
		Assert.assertFalse("Should return false.", isRequestValid);
		Assert.assertEquals("Response should be " + expectedResponse, expectedResponse, validator.getResponse().getEntity());
	}
	
	@Test
	public void testEmailResourceValidator_EmptyRecipients_ShouldReturnFalse() {
		String expectedResponse = HttpResponseConstants.MISSING_PARTS_OF_EMAIL;
		EmailMessage emailMessage = new EmailMessage();
		emailMessage.setSubject("MySubject");
		emailMessage.setRecipients("");
		emailMessage.setMessageText("message");
		HttpRequestValidator validator = new EmailResourceValidator(emailMessage);
		
		boolean isRequestValid = validator.validate();
		
		Assert.assertFalse("Should return false.", isRequestValid);
		Assert.assertEquals("Response should be " + expectedResponse, expectedResponse, validator.getResponse().getEntity());
	}
	
	@Test
	public void testEmailResourceValidator_DuplicateEmailAddress_ShouldReturnFalse() {
		String expectedResponse = HttpResponseConstants.DUPLICATE_RECIPIENT_EMAIL_ADDRESSES;
		EmailMessage emailMessage = new EmailMessage();					
		emailMessage.setSubject("MySubject");
		emailMessage.setRecipients("emailaddress@domain.com,emailaddress@domain.com");
		emailMessage.setMessageText("message");
		HttpRequestValidator validator = new EmailResourceValidator(emailMessage);
		
		boolean isRequestValid = validator.validate();
		
		Assert.assertFalse("Should return false.", isRequestValid);
		Assert.assertEquals("Response should be " + expectedResponse, expectedResponse, validator.getResponse().getEntity());
	}
	
	@Test
	public void testEmailResourceValidator_AllDataIsValid_ShouldReturnTrue() {
		EmailMessage emailMessage = new EmailMessage();
		emailMessage.setSubject("MySubject");
		emailMessage.setRecipients("emailaddress@domain.com,emailaddress2@domain.com");
		emailMessage.setMessageText("message");
		HttpRequestValidator validator = new EmailResourceValidator(emailMessage);
		
		boolean isRequestValid = validator.validate();
		
		Assert.assertTrue("Should return true.", isRequestValid);
	}
}
