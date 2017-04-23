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
		public Response createContact(Contact contact) {
			
			ContactResourceValidator validator = new ContactResourceValidator(contact);
			
			if(validator.validate() == true) {
				ContactDB contactdb = new ContactDB(contact);
			    return contactdb.sendContact();
			}
			else {
				return validator.getResponse();
			}
			
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
		
		@POST
		@Path("/getcontacts")
		@Consumes(MediaType.APPLICATION_JSON)
		public List<Contact> getContactsFromDB(User user) {
			//UserDB userDB = new UserDB(user);
			//return userDB.getContacts(user.getUserID());
			List<Contact> temp = null;
			return temp;
		}
		

}
