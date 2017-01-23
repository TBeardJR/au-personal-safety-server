package com.simple.web.app;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Path("/sms")
public class SMSResource {
	
	
	
	@POST
	@Path("/send")
	public Response sendSMS(@QueryParam("message") String message) {
		
		Map<String, String> env = System.getenv();		
		String ACCOUNT_SID = env.get("ACCOUNT_SID");
		String AUTH_TOKEN = env.get("AUTH_TOKEN");

		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

	    Message smsMessage = Message.creator(new PhoneNumber("+16788991701"),
	        new PhoneNumber("+14703090324"), message).create();


		return Response.status(200).entity(null).build();

	}

}
