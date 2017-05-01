package com.au.personal.safety.alerts;

import com.au.personal.safety.database.DatabaseConnectionSingleton;
import com.au.personal.safety.users.*;
import com.au.personal.safety.constants.AlertConstants;
import com.au.personal.safety.email.EmailMessage;
import com.au.personal.safety.email.Email;
import com.au.personal.safety.validator.EmailResourceValidator;

import java.sql.ResultSet;
import java.sql.Statement;

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
		correspondingUser = userIN;
		
	}
	

    
    /* Functions */
    
    private User getCorrespondingUser() {
    	return correspondingUser;
    }
    
    private void setCorrespondingUser(User userIN) {
    	correspondingUser = userIN;
    }
    
    public Response alertContacts() {
    	//get all contacts for the corresponding User
    	String getContacts = "SELECT * FROM Contacts WHERE UserID = " + getCorrespondingUser().getUserID() + " ;";
    	
    	//initialize connection variables
    	DatabaseConnectionSingleton conn;
    	Statement stmt = null;
    	Response result = null;
    	
    	try {
			//open connection
			conn = DatabaseConnectionSingleton.getInstance();
			conn.openConnection();
			stmt = conn.getConnection().createStatement();
			
			//Execute Select Query.
			//rs01 will have values if this user has Contacts
			//rs01 will be null if there is this user does not have contacts
			ResultSet rs01 = stmt.executeQuery(getContacts);
			
			//If this User does not have any contacts then return an error saying there are none
			if (!rs01.next())
	        {
				result = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(AlertConstants.NO_CONTACTS_FOUND).build();
				return result;
	        }
			// If this user does have contacts begin contacting each
	        else
	        {
	        	//create a String object for recipients (each recipient is separated by a comma) 
	        	//  for all phone numbers, the phone recipient entry is the ContactPhone + ContactCarrier value
	        	//  for all emails, the email recipient entry is the ContactEmail value
	        	String recieptString = "";
	        	
	        	//get the email and/or phone number with contact carrier for the current Contact entry
	        	do {
	        		String email = rs01.getString("Email");
	        		//if there is an email, add it to the recieptString
	        		if(email.length() != 0) {
	        			if(recieptString.length() !=0){
	        				recieptString = recieptString + ", ";
	        			}
	        			recieptString = recieptString + rs01.getString("Email");
	        		} 
	        		String phone = rs01.getString("PhoneNumber");
	        		String carrier = rs01.getString("ContactCarrier");
	        		//if there is a phone number and contact carrier, add it to the recieptString
	        		if(phone.length() != 0 && carrier.length() != 0) {
	        			if(recieptString.length() !=0){
	        				recieptString = recieptString + ", ";
	        			}
	        			recieptString = recieptString + rs01.getString("PhoneNumber") + rs01.getString("ContactCarrier");
	        		}
	        		
	        		//go to next contact entry with desired UserID, if there is a next entry
	        		
	        	} while (rs01.next());
	        	
	        	//get the User's current location information from the Location table
	        	String getLocation = "SELECT * FROM Location WHERE UserID = " + correspondingUser.getUserID() + " ;";
	        	String currentLat = "";
	        	String currentLong = "";
	        	
	        	//Execute Query
	        	//rs02 will have values if the user has a location value
	        	ResultSet rs02 = stmt.executeQuery(getLocation);
	        	
	        	//If the user does hava a lat/long then set it
	        	if (rs02.next()) {
	        		//get the user's latitude and longitude
	        		currentLat = rs02.getString("Latitude");
	        		currentLong = rs02.getString("Longitude");	
		        }//else it defaults to empty strings
	   
	        	
	        	//create an emailMessage object
	        	EmailMessage messageToSend = new EmailMessage();
	        	//set the values for the emailMessage
	        	messageToSend.setRecipients(recieptString);
	        	messageToSend.setSubject("Alert! SafeTrip Alert Sent From " + correspondingUser.getUserID());
	        	
	        	messageToSend.setMessageText("Alert! " + correspondingUser.getUserID()
        	        + " has sent an alert from the following location: latitude = "
        	        + currentLat + " longitude = " + currentLong + "\n");
	        	
	        	//check that the emailMessage is valid with a EmailResourceValidator object
	        	EmailResourceValidator checkEmail = new EmailResourceValidator(messageToSend);
	        	
	        	
	        	//if it is valid, send the emails/texts via a created Email object
	        	if (checkEmail.validate() == true) {
	        		Email thisEmail = new Email(messageToSend);
	        		Response responseAfterSendingEmail = thisEmail.sendMessage();
	        		result = responseAfterSendingEmail;
	        	}
	        	else{
	        		result = Response.serverError().entity("Email invalid").build(); 
	        	}
	        	
	        }
    	
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    		return Response.serverError().entity("Error").build(); 
    	}
    	
    	return result;
    }
    
    
    
    
}
