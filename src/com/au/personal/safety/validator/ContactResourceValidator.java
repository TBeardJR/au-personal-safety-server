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
	 * non-null and non-empty email or phone 
	 *   (for a email to be valid, it must have an "@" and have a string length > 0)
	 *   (for a phone to be valid, it must be 10 digits, nothing more nor less)
	 * AND
	 * non-null integer value > 0 for UserID
	 * AND if have a phone, must have a
	 * non-null and non-empty and valid ContactCarrier (class PhoneCarriers defines valid carriers)
	*/
	
	/*
	 * Note: Not all contacts will have both a first name and last name, and not all contacts will 
	 *  have both an email and phone number
	*/
	
	private Contact contact;
	
	//private boolean isUserIDEmpty;
	
	public ContactResourceValidator(Contact contact) {
		super();
		this.contact = contact;
	}
	
	
	@Override
	public boolean validate() {
        //if contact has a valid UserID
		// AND
		// contact has a (1) valid email OR (2) valid phone number with a valid carrier
		// return true
		
		//else return false
		
		boolean result = false;
		
		if(validateThisAttribute(contact.getUserID(), "UserID")) {
			
			if(validateThisAttribute(contact.getContactEmail(), "ContactEmail")) {
				//check if has a valid phone too
				//if it has a valid phone number but an invalid carrier, change result to false
				// we always want a valid carrier for a valid phone number
				if (validateThisAttribute(contact.getContactPhone(), "ContactPhone") == true &&
				validateThisAttribute(contact.getContactCarrier(), "ContactCarrier") == false) {
					result = false;
				}
				//if the entry has a valid carrier, but an invalid phone number,
				// we are taking this to mean that the user wants to enter a phone number, but it was entered
				// incorrectly
				else if (validateThisAttribute(contact.getContactPhone(), "ContactPhone") == false &&
						validateThisAttribute(contact.getContactCarrier(), "ContactCarrier") == true) {
					result = false;
				}
				
				else {
				    result = true;
				}
			}
			else if (validateThisAttribute(contact.getContactPhone(), "ContactPhone") &&
					validateThisAttribute(contact.getContactCarrier(), "ContactCarrier")) {
				result = true;
			}
			
		}
		//build response for validation
		buildResponse();
		return result;
	}
	
	@Override
	public Response getResponse() {
		return response;
	}
	
	@Override
	protected void buildResponse() {
		if(validateThisAttribute(contact.getUserID(), "UserID")) {
		
		    //if valid email
		    if(validateThisAttribute(contact.getContactEmail(), "ContactEmail")) {
			    if (validateThisAttribute(contact.getContactPhone(), "ContactPhone") == true &&
				    validateThisAttribute(contact.getContactCarrier(), "ContactCarrier") == false) {
				    response = Response.status(Response.Status.BAD_REQUEST).entity(HttpResponseConstants.INVALID_PHONE_CARRIER_SELECTED).build();
			    }
				else if (validateThisAttribute(contact.getContactPhone(), "ContactPhone") == false &&
					validateThisAttribute(contact.getContactCarrier(), "ContactCarrier") == true) {
					response = Response.status(Response.Status.BAD_REQUEST).entity(HttpResponseConstants.INVALID_CONTACT).build();
				}	
				else {
					response = null;
				}
			    
		    }
    		//else if valid phone and carrier
	    	else if (validateThisAttribute(contact.getContactPhone(), "ContactPhone") &&
		    		validateThisAttribute(contact.getContactCarrier(), "ContactCarrier")) {
			    response = null;
		    }
		    //else if valid phone but invalid phone carrier and invalid email, build INVALID_PHONE_CARRIER_SELECTED
			else if (validateThisAttribute(contact.getContactPhone(), "ContactPhone") == true && 
					validateThisAttribute(contact.getContactCarrier(), "ContactCarrier") == false &&
					validateThisAttribute(contact.getContactEmail(), "ContactEmail") == false) {
				response = Response.status(Response.Status.BAD_REQUEST).entity(HttpResponseConstants.INVALID_PHONE_CARRIER_SELECTED).build();
			}
			else {
				response = Response.status(Response.Status.BAD_REQUEST).entity(HttpResponseConstants.INVALID_CONTACT).build();
			}
		}
		//if invalid userID, build INVALID_CONTACT
		else if (validateThisAttribute(contact.getUserID(), "UserID") == false) {
			response = Response.status(Response.Status.BAD_REQUEST).entity(HttpResponseConstants.INVALID_CONTACT).build();
		}
		
		//else if invalid email and invalid phone number, build INVALID_CONTACT
		else {
			response = Response.status(Response.Status.BAD_REQUEST).entity(HttpResponseConstants.INVALID_CONTACT).build();
		}

	}
	
	public boolean validateThisAttribute(String attr_value_in, String attr_name_in) {
		boolean result = false;
		if (attr_value_in != null && attr_name_in != null) {
		
		    if(attr_name_in.equals("ContactEmail")) {
			    if (attr_value_in.length() > 0 && attr_value_in.contains("@")) {
				    //if get to here, the attr_value_in is the desired string of greater the zero length and
			    	// contains an "@"
			    	result = true;
			    }
    		}
            else if(attr_name_in.equals("ContactPhone")) {
			    if(attr_value_in.length() == 10) {
			    	//try converting the string to an integer
			    	try {
			    		//Long intValue = 0.0;
			    		Long intValue = Long.parseLong(attr_value_in);
			    		//if get to here, the attr_value_in is the desired nine digit string
			    		result = true;
			    	}
			        //catch the exception thrown when cannot convert the string to an integer because on non-integer characters in the string
			    	catch (Exception e) {
			    		//break out of the try-catch, do not need to so anything, the result is false
			    		System.out.print(e.getStackTrace());
			    	}
			    	
			    }
		    }
            else if(attr_name_in.equals("ContactCarrier")) {
	            PhoneCarriers pc = new PhoneCarriers();
	            result = pc.getCarrierDictionary().containsValue(attr_value_in);
            	//if the attr_value_in is a value in the PhoneCarries dictionary (LinkedHashMap), the
	            // result value will change to true
            }
            //UserID is an integer value so it cannot be checked with this function, it is checked with next function
		}
		return result;
	}
	
	public boolean validateThisAttribute(int attr_value_in, String attr_name_in) {
		boolean result = false;
		//Note: an int cannot be null, it can be initialized to zero if not set, so
		//  we do not check for attr_value_in != null
		// Plus it is best to have only the first function check for null attr_values, so java can know
		//  which function is best to use
		if (attr_name_in != null) {
		    if(attr_name_in.equals("UserID")) {
                if (attr_value_in > 0) {
                	result = true;
                }
            }
		}
		return result;
	}
	
	public Contact getContact() {
		return contact;
	}
	
	
}
