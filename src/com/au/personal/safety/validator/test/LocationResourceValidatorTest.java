package com.au.personal.safety.validator.test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.au.personal.safety.constants.HttpResponseConstants;
import com.au.personal.safety.database.Location;
import com.au.personal.safety.email.EmailMessage;
import com.au.personal.safety.validator.EmailResourceValidator;
import com.au.personal.safety.validator.HttpRequestValidator;
import com.au.personal.safety.validator.LocationResourceValidator;

@RunWith(Enclosed.class)
public class LocationResourceValidatorTest {
	
	@RunWith(Parameterized.class)
	public static class LocationResourceValidatorTestParameterized {
		
		private double latitude;
		private double longitude;
		
		public LocationResourceValidatorTestParameterized(double latitude, double longitude) {
		     this.longitude = longitude;
		     this.latitude = latitude;
		   }

		@Parameterized.Parameters
	    public static Collection<Object[]> locationCoordinates() {
	       return Arrays.asList(new Object[][] {
	          { -0.1, 50.0 },
	          { 20.0, -180.1 },
	          { 90.1, 50.0 },          
	          { 20.0, 180.1 },
	          { -0.1, -180.1 },
	          { 90.1, 180.1 }
	      });
	    }	    
		
	    @Test
		public void testLocationResourceValidator_InvalidLocation_ShouldReturnFalse() {
			String expectedResponse = HttpResponseConstants.INVALID_LOCATION;
			Integer userID = 1;
			Location location = new Location();
			location.setLong(longitude);
			location.setLat(latitude);
			location.setUserID(userID);
			HttpRequestValidator validator = new LocationResourceValidator(location);
			
			boolean isRequestValid = validator.validate();
			
			Assert.assertFalse("Should return false.", isRequestValid);
			Assert.assertEquals("Response should be " + expectedResponse, expectedResponse, validator.getResponse().getEntity());
		}
		
		
	}
	
	public static class LocationResourceValidatorTestNonParameterized {
		
		@Test
		public void testLocationValidator_NullLocation_ShouldReturnFalse() {
			String expectedResponse = HttpResponseConstants.NULL_PARAMETERS;
			Location location = null;
			HttpRequestValidator validator = new LocationResourceValidator(location);

			boolean isRequestValid = validator.validate();

			Assert.assertFalse("Should return false.", isRequestValid);
			Assert.assertEquals("Response should be " + expectedResponse, expectedResponse,
					validator.getResponse().getEntity());
		}
		
		@Test
		public void testLocationValidator_EmptyUserID_ShouldReturnFalse() {
			int emptyUserID = -1;
			String expectedResponse = HttpResponseConstants.NULL_PARAMETERS;
			Location location = new Location();
			location.setUserID(emptyUserID);
			HttpRequestValidator validator = new LocationResourceValidator(location);

			boolean isRequestValid = validator.validate();

			Assert.assertFalse("Should return false.", isRequestValid);
			Assert.assertEquals("Response should be " + expectedResponse, expectedResponse, validator.getResponse().getEntity());
		}
		
		@Test
		public void testLocationResourceValidator_LongitudeEmpty_ShouldReturnFalse() {
			String expectedResponse = HttpResponseConstants.INVALID_LOCATION;
			Integer userID = 1;
			Location location = new Location();
			location.setLat(20.0);
			location.setUserID(userID);
			HttpRequestValidator validator = new LocationResourceValidator(location);
			
			boolean isRequestValid = validator.validate();
			
			Assert.assertFalse("Should return false.", isRequestValid);
			Assert.assertEquals("Response should be " + expectedResponse, expectedResponse, validator.getResponse().getEntity());
		}
		
		@Test
		public void testLocationResourceValidator_LatitudeEmpty_ShouldReturnFalse() {
			String expectedResponse = HttpResponseConstants.INVALID_LOCATION;
			Integer userID = 1;
			Location location = new Location();
			location.setLong(20.0);
			location.setUserID(userID);
			HttpRequestValidator validator = new LocationResourceValidator(location);
			
			boolean isRequestValid = validator.validate();
			
			Assert.assertFalse("Should return false.", isRequestValid);
			Assert.assertEquals("Response should be " + expectedResponse, expectedResponse, validator.getResponse().getEntity());
		}
		
		@Test
		public void testLocationResourceValidator_AllDataIsValid_ShouldReturnTrue() {
			Integer userID = 1;
			Location location = new Location();
			location.setLong(20.0);
			location.setLat(20.0);
			location.setUserID(userID);
			HttpRequestValidator validator = new LocationResourceValidator(location);
			
			boolean isRequestValid = validator.validate();
			
			Assert.assertTrue("Should return true.", isRequestValid);
		}
    }	
}
