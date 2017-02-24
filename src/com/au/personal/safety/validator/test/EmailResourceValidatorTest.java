package com.au.personal.safety.validator.test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.au.personal.safety.constants.HttpResponseConstants;
import com.au.personal.safety.email.EmailMessage;
import com.au.personal.safety.validator.EmailResourceValidator;
import org.junit.runners.Parameterized;
import org.junit.experimental.runners.Enclosed;


@RunWith(Enclosed.class)
public class EmailResourceValidatorTest {
	
	@RunWith(Parameterized.class)
	public static class EmailResourceValidatorTestParameterized {
		private String recipientEmailAddresses;
		
		public EmailResourceValidatorTestParameterized(String recipientEmailAddresses) {
		     this.recipientEmailAddresses = recipientEmailAddresses;
		   }

		@Parameterized.Parameters
	    public static Collection<Object[]> recipientEmailAddresses() {
	       return Arrays.asList(new Object[][] {
	          { "invalidEmailAddress" },
	          { "invalidEmailAddress@domaincom" },
	          { "@domaincom" },          
	          { "@domain.com" },
	          { "invalidEmailAddress@domain.com,invalidEmailAddress" }
	      });
	    }

		
		@Test
		public void testEmailResourceValidator_InvalidEmailAddressFormat_ShouldReturnFalse() {
			String expectedResponse = HttpResponseConstants.INVALID_EMAIL_ADDRESS_FORMAT;
			EmailMessage emailMessage = new EmailMessage();
			emailMessage.setSubject("MySubject");
			emailMessage.setRecipients(recipientEmailAddresses);
			emailMessage.setMessageText("message");
			EmailResourceValidator validator = new EmailResourceValidator(emailMessage);
			
			boolean isRequestValid = validator.validate();
			
			Assert.assertFalse("Should return false.", isRequestValid);
			Assert.assertEquals("Response should be " + expectedResponse, expectedResponse, validator.getResponse().getEntity());
		}
		
		
	}
	
	public static class EmailResourceValidatorTestNonParameterized {		
		@Test
		public void testEmailResourceValidator_NullEmailMessage_ShouldReturnFalse() {
			String expectedResponse = HttpResponseConstants.NULL_PARAMETERS;
			EmailMessage emailMessage = null;
			EmailResourceValidator validator = new EmailResourceValidator(emailMessage);
			
			boolean isRequestValid = validator.validate();
			
			Assert.assertFalse("Should return false.", isRequestValid);
			Assert.assertEquals("Response should be " + expectedResponse, expectedResponse, validator.getResponse().getEntity());
		}
		
		@Test
		public void testEmailResourceValidator_NullMessageText_ShouldReturnFalse() {
			String expectedResponse = HttpResponseConstants.MISSING_PARTS_OF_EMAIL;
			EmailMessage emailMessage = new EmailMessage();
			emailMessage.setSubject("MySubject");
			emailMessage.setRecipients("recipients");
			emailMessage.setMessageText(null);
			EmailResourceValidator validator = new EmailResourceValidator(emailMessage);
			
			boolean isRequestValid = validator.validate();
			
			Assert.assertFalse("Should return false.", isRequestValid);
			Assert.assertEquals("Response should be " + expectedResponse, expectedResponse, validator.getResponse().getEntity());
		}
		
		@Test
		public void testEmailResourceValidator_EmptyMessageText_ShouldReturnFalse() {
			String expectedResponse = HttpResponseConstants.MISSING_PARTS_OF_EMAIL;
			EmailMessage emailMessage = new EmailMessage();
			emailMessage.setSubject("MySubject");
			emailMessage.setRecipients("recipients");
			emailMessage.setMessageText("");
			EmailResourceValidator validator = new EmailResourceValidator(emailMessage);
			
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
			EmailResourceValidator validator = new EmailResourceValidator(emailMessage);
			
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
			EmailResourceValidator validator = new EmailResourceValidator(emailMessage);
			
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
			EmailResourceValidator validator = new EmailResourceValidator(emailMessage);
			
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
			EmailResourceValidator validator = new EmailResourceValidator(emailMessage);
			
			boolean isRequestValid = validator.validate();
			
			Assert.assertTrue("Should return true.", isRequestValid);
		}
		
		
		
	}
}
