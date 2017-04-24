package com.au.personal.safety.alerts;

import com.au.personal.safety.database.DatabaseConnectionSingleton;
import com.au.personal.safety.users.*;
import com.au.personal.safety.constants.AlertConstants;
import com.au.personal.safety.email.*;
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
		setCorrespondingUser(userIN);
		
	}
	
	/* initialize object with an int parameter that represents the userID of a User */
	// note: this assumes that the integer is valid
    public SendAlerts(int userID_IN) {
    	
    	//need to create a function in UserDB class that will return a User object for inputted UserID value
    	
    	//get all contacts for the corresponding User
    	String getContacts = "GET * FROM User WHERE UserID = " + userID_IN + " ;";
    	User desiredUser = new User();
    	
    	//initialize connection variables
    	DatabaseConnectionSingleton conn;
    	Statement stmt = null;
    	
    	try {
			//open connection
			conn = DatabaseConnectionSingleton.getInstance();
			conn.openConnection();
			//get the result for the query
			stmt = conn.getConnection().createStatement();
			ResultSet rs01 = stmt.executeQuery(getContacts);
			
			if (!rs01.next())
	        {
				//error there is no user with the matching UserID
				
	        }
			else {
				desiredUser.setUserID(rs01.getInt("UserID"));
				desiredUser.setUserName(rs01.getString("UserName"));
				desiredUser.setUserPin(rs01.getString("UserPin"));
			}
    	
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	setCorrespondingUser(desiredUser);
    	
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
    	String getContacts = "GET * FROM Contacts WHERE UserID = " + getCorrespondingUser().getUserID() + " ;";
    	
    	//initialize connection variables
    	DatabaseConnectionSingleton conn;
    	Statement stmt = null;
    	
    	try {
			//open connection
			conn = DatabaseConnectionSingleton.getInstance();
			conn.openConnection();
			//get the result for the query
			stmt = conn.getConnection().createStatement();
			ResultSet rs01 = stmt.executeQuery(getContacts);
			
			if (!rs01.next())
	        {
	        	//there is no existing entry
	        	//ERROR
				//the User has no contacts or the database connection is bad
				result = Response.status(Response.Status.OK).entity(AlertConstants.NO_CONTACTS_FOUND).build();
				return result; //get out of this function
	        }
			
			// else, there is an entry
	        else
	        {
	        	//create a String object for recipients (each recipient is separated by a comma) 
	        	//  for all phone numbers, the phone recipient entry is the ContactPhone + ContactCarrier value
	        	//  for all emails, the email recipient entry is the ContactEmail value
	        	String recieptString = "";
	        	
	        	//get the email and/or phone number with contact carrier for the current Contact entry
	        	do {
	        		//if there is an email, add it to the recieptString
	        		if(!rs01.getString("Email").equals("null")) {
	        			recieptString = recieptString + ", " + rs01.getString("Email");
	        		}
	        		
	        		//if there is a phone number and contact carrier, add it to the recieptString
	        		if(!rs01.getString("PhoneNumber").equals("null") && !rs01.getString("ContactCarrier").equals("null")) {
	        			recieptString = recieptString + ", " + rs01.getString("PhoneNumber") + rs01.getString("ContactCarrier");
	        		}
	        		
	        		//go to next contact entry with desired UserID, if there is a next entry
	        		
	        	} while (rs01.next());
	        	
	        	//get the User's current location information from the Location table
	        	String getLocation = "SELECT * FROM Location WHERE UserID = " + correspondingUser.getUserID() + " ;";
	        	String currentLat = "";
	        	String currentLong = "";
	        	
	        	
	        	ResultSet rs02 = stmt.executeQuery(getLocation);
	        	if (!rs02.next()) {
	        		//then the user does not have a current location, why not? this is probably a problem on our end
	        		
	        		
		        }
	        	else {
	        		//get the user's latitude and longitude
	        		currentLat = rs02.getString("Latitude");
	        		currentLong = rs02.getString("Longitude");
	        	}
	        	
	        	//create an emailMessage object
	        	EmailMessage messageToSend = new EmailMessage();
	        	//set the values for the emailMessage
	        	messageToSend.setRecipients(recieptString);
	        	messageToSend.setSubject("Alert! SafeTrip Alert Sent From " + correspondingUser.getFirstName()
	        	    + " " + correspondingUser.getLastName());
	        	
	        	messageToSend.setMessageText("Alert! " + correspondingUser.getFirstName()
        	        + " " + correspondingUser.getLastName() + " has sent an alert from the following location: latitude = "
        	        + currentLat + " longitude = " + currentLong + "\n");
	        	
	        	//check that the emailMessage is valid with a EmailResourceValidator object
	        	EmailResourceValidator checkEmail = new EmailResourceValidator(messageToSend);
	        	
	        	
	        	//if it is valid, send the emails/texts via a created Email object
	        	if (checkEmail.validate() == true) {
	        		Email thisEmail = new Email(messageToSend);
	        		Response responseAfterSendingEmail = thisEmail.sendMessage();
	        		result = responseAfterSendingEmail;
	        	}
	        	
	        }
    	
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    	
    	return result;
    }
    
    
    
    
}
