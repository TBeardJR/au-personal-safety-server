package com.au.personal.safety.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.au.personal.safety.contacts.*;
import com.au.personal.safety.validator.ContactResourceValidator;
import com.au.personal.safety.users.*;

	@Path("/contact")
	public class ContactResource {
		@POST
		@Path("/sendtodb")
		@Consumes(MediaType.APPLICATION_JSON)
		public Response sendContactToDB(Contact contact) {
			Response response = null;
			//add ContactResourceValidator code here
			ContactResourceValidator validator = new ContactResourceValidator(contact);
			//if contact is valid, add entry to or update the existing entry in database
			if(validator.validate() == true) {
				ContactDB contactdb = new ContactDB(contact);
			    response = contactdb.sendContact();
			}
			else {
				response = validator.getResponse();
			}
			
			return response;
		}
		
		@POST
		@Path("/deletecontacts")
		@Consumes(MediaType.APPLICATION_JSON)
		public Response deleteContactFromDB(int contactID) {
			Contact contact = new Contact();
			contact.setContactID(contactID);
			ContactDB contactdb = new ContactDB(contact);
			return contactdb.deleteContact();
		}
		

}
