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
	
	//private boolean isUserIDEmpty;
	
	public ContactResourceValidator(Contact contact_in) {
		super();
		thisContact = contact_in;
		
		/* FINISH THIS */
		
	}
	
	
	@Override
	public boolean validate() {
        //if contact has a valid UserID
		// AND
		// contact has a (1) valid email OR (2) valid phone number with a valid carrier
		// return true
		
		//else return false
		
		boolean result = false;
		
		if(validateThisAttribute(thisContact.getUserID(), "UserID")) {
			
			if(validateThisAttribute(thisContact.getContactEmail(), "ContactEmail")) {
				//check if has a valid phone too
				//if it has a valid phone number but an invalid carrier, change result to false
				// we always want a valid carrier for a valid phone number
				if (validateThisAttribute(thisContact.getContactPhone(), "ContactPhone") == true &&
				validateThisAttribute(thisContact.getContactCarrier(), "ContactCarrier") == false) {
					result = false;
				}
				//if the entry has a valid carrier, but an invalid phone number,
				// we are taking this to mean that the user wants to enter a phone number, but it was entered
				// incorrectly
				else if (validateThisAttribute(thisContact.getContactPhone(), "ContactPhone") == false &&
						validateThisAttribute(thisContact.getContactCarrier(), "ContactCarrier") == true) {
					result = false;
				}
				
				else {
				    result = true;
				}
			}
			else if (validateThisAttribute(thisContact.getContactPhone(), "ContactPhone") &&
					validateThisAttribute(thisContact.getContactCarrier(), "ContactCarrier")) {
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
		if(validateThisAttribute(thisContact.getUserID(), "UserID")) {
		
		    //if valid email
		    if(validateThisAttribute(thisContact.getContactEmail(), "ContactEmail")) {
			    if (validateThisAttribute(thisContact.getContactPhone(), "ContactPhone") == true &&
				    validateThisAttribute(thisContact.getContactCarrier(), "ContactCarrier") == false) {
				    response = Response.status(Response.Status.BAD_REQUEST).entity(HttpResponseConstants.INVALID_PHONE_CARRIER_SELECTED).build();;
			    }
				else if (validateThisAttribute(thisContact.getContactPhone(), "ContactPhone") == false &&
					validateThisAttribute(thisContact.getContactCarrier(), "ContactCarrier") == true) {
					response = Response.status(Response.Status.BAD_REQUEST).entity(HttpResponseConstants.INVALID_CONTACT).build();;
				}	
				else {
					response = null;
				}
			    
		    }
    		//else if valid phone and carrier
	    	else if (validateThisAttribute(thisContact.getContactPhone(), "ContactPhone") &&
		    		validateThisAttribute(thisContact.getContactCarrier(), "ContactCarrier")) {
			    response = null;
		    }
		    //else if valid phone but invalid phone carrier and invalid email, build INVALID_PHONE_CARRIER_SELECTED
			else if (validateThisAttribute(thisContact.getContactPhone(), "ContactPhone") == true && 
					validateThisAttribute(thisContact.getContactCarrier(), "ContactCarrier") == false &&
					validateThisAttribute(thisContact.getContactEmail(), "ContactEmail") == false) {
				response = Response.status(Response.Status.BAD_REQUEST).entity(HttpResponseConstants.INVALID_PHONE_CARRIER_SELECTED).build();
			}
			else {
				response = Response.status(Response.Status.BAD_REQUEST).entity(HttpResponseConstants.INVALID_CONTACT).build();
			}
		}
		//if invalid userID, build INVALID_CONTACT
		else if (validateThisAttribute(thisContact.getUserID(), "UserID") == false) {
			response = Response.status(Response.Status.BAD_REQUEST).entity(HttpResponseConstants.INVALID_CONTACT).build();
		}
		
		//else if invalid email and invalid phone number, build INVALID_CONTACT
		else {
			response = Response.status(Response.Status.BAD_REQUEST).entity(HttpResponseConstants.INVALID_CONTACT).build();
		}
		//else if
		
		//else, no need to build response
		
		/* FINISH THIS */
		
		
		
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
			    if(attr_value_in.length() == 9) {
			    	//try converting the string to an integer
			    	try {
			    		Integer intValue = Integer.parseInt(attr_value_in);
			    		//if get to here, the attr_value_in is the desired nine digit string
			    		result = true;
			    	}
			        //catch the exception thrown when cannot convert the string to an integer because on non-integer characters in the string
			    	catch (Exception e) {
			    		//break out of the try-catch, do not need to so anything, the result is false
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
		return thisContact;
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
