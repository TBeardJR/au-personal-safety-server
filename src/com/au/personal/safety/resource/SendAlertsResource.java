package com.au.personal.safety.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.au.personal.safety.email.*;
import com.au.personal.safety.users.User;
import com.au.personal.safety.users.UserDB;
import com.au.personal.safety.validator.EmailResourceValidator;
import com.au.personal.safety.alerts.SendAlerts;


/*
 * Purpose: to use the SendAlerts class to send alerts to all contacts for the corresponding user
 */
@Path("/alert")
public class SendAlertsResource {
	
	
	@POST
	@Path("/alertcontacts/{userName}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response sendAlerts(@PathParam("userName") String userName) {
		//Response result = null;
		
		User thisUser = new User();
		thisUser.setUserName(userName);
		UserDB thisUserDB = new UserDB(thisUser);
		int userID_IN = thisUserDB.getUserID();
		if(userID_IN < 0){
			return Response.serverError().entity("Not a User").build(); 
		}else {
			//get user for inputted UserID value
			thisUser.setUserID(userID_IN);
			SendAlerts aSendAlertsObj = new SendAlerts(thisUser);
			return aSendAlertsObj.alertContacts();
		}

	}
	
}
