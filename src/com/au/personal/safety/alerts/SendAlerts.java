package com.au.personal.safety.alerts;

import com.au.personal.safety.users.*;

import javax.ws.rs.core.Response;

/*
 * Purpose: This class send alerts to all emergency contacts for a specific User
 */

public class SendAlerts {

	/* Variables */
	
	private User correspondingUser;
	
	/* Constructors */
	
	/* initialize object with a User parameter */
	//note: this assumes that the User is valid, need to create UserValidator class
	public SendAlerts(User userIN) {
		setCorrespondingUser(userIN);
		
	}
	
	/* initialize object with an int parameter that represents the userID of a User */
	// note: this assumes that the integer is valid
    public SendAlerts(int userID_IN) {
    	
    	//need to create a function in UserDB class that will return a User object for inputted UserID value
    	
	}
    
    /* Functions */
    
    private User getCorrespondingUser() {
    	return correspondingUser;
    }
    
    private void setCorrespondingUser(User userIN) {
    	correspondingUser = userIN;
    }
    
    public Response alertContacts() {
    	Response result = null;
    	
    	//get all contacts for the corresponding User
    	
    	//create a String object for recipients (each recipient is separated by a comma) 
    	//  for all phone numbers, the phone recipient entry is the ContactPhone + ContactCarrier value
    	//  for all emails, the email recipient entry is the ContactEmail value
    	
    	//create an emailMessage object
    	
    	//set the values for the emailMessage
    	
    	//check that the emailMessage is valid with a EmailResourceValidator object
    	
    	//if it is valid, send the emails/texts
    	
    	
    	return result;
    }
    
    
    
    
}
