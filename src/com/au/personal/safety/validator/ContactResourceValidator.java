package com.au.personal.safety.validator;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.core.Response;

import com.au.personal.safety.constants.HttpResponseConstants;
import com.au.personal.safety.contacts.Contact;

public class ContactResourceValidator extends HttpRequestValidator {
	
	
	/*
	 * The definition of a VALID contact ("empty" = empty/blank string):
	 * non-null and non-empty firstName or lastName
	 * AND
	 * non-null and non-empty email or phone
	 * AND
	 * non-null integer value > 0 for userContactID
	*/
	
	public ContactResourceValidator(Contact contact_in) {
		super();
		
		/* FINISH THIS */
		
	}
	
	@Override
	public boolean validate() {

		/* FINISH THIS */
		
		return isRequestValid;
	}
	
	@Override
	public Response getResponse() {
		
		/* FINISH THIS */
		
		return response;
	}
	
	@Override
	protected void buildResponse() {

		/* FINISH THIS */
		
	}
	
	public boolean validateThisAttribute(String attr_value_in, String attr_name_in) {
		boolean result = false;
		
		/* FINISH THIS */
		
		return result;
	}
	
	public String stripCharacters(String value_in) {
		String result = "";
		
		/* FINISH THIS */
		
		return result;
	}
	
	public String formatPhone(String value_in) {
		String result = "";
		
		/* FINISH THIS */
		
		return result;
	}
}
