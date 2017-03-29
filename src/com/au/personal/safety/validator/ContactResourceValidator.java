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
import com.au.personal.safety.contacts.PhoneCarriers;

public class ContactResourceValidator extends HttpRequestValidator {
	
	
	/*
	 * The definition of a VALID contact ("empty" = empty/blank string):
	 * non-null and non-empty email or phone (if have phone, phone must be nine digits, nothing more nor less)
	 * AND
	 * non-null integer value > 0 for UserID
	 * AND if have a phone, must have a
	 * non-null and non-empty and valid ContactCarrier (class PhoneCarriers defines valid carriers)
	*/
	
	/*
	 * Note: Not all contacts will have both a first name and last name, and not all contacts will 
	 *  have both an email and phone number
	*/
	
	private Contact thisContact;
	private boolean isContactValid;
	//private boolean isUserIDEmpty;
	
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
		
		if(attr_name_in.equals("ContactEmail")) {
			
		}
        else if(attr_name_in.equals("ContactPhone")) {
			
		}
        else if(attr_name_in.equals("ContactCarrier")) {
	        
        }
        else if(attr_name_in.equals("UserID")) {
	        
        }
		
		
		
		
		
		/* FINISH THIS */
		
		return result;
	}
	
	public boolean isEmptyString(String name_in) {
		boolean result = false;
		
		/* FINISH THIS */
		
		return result;
	}
	
	
	
	/*
	 * Purpose: tells if an entered phone carrier is valid
	 * Input: string representing the phone carrier address
	 * Output: true if the entered value is valid; else, returns false
	 * Note: look at the class PhoneCarriers for more details
	 */
	public boolean isAPhoneCarrier(String value_in) {
		//instantiate the return value to false
		boolean result = false;
		//search the PhoneCarriers.carrierDictionary for value_in
		PhoneCarriers phoneCarriersInstance = new PhoneCarriers();
		result = phoneCarriersInstance.getCarrierDictionary().containsValue(value_in);
		return result;
	}
	
	/* I believe the below functions should go into Contact class if think they are needed */
	// not needed for this class
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
