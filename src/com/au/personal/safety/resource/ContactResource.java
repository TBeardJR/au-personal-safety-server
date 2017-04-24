package com.au.personal.safety.resource;

import java.util.List;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.au.personal.safety.contacts.*;
import com.au.personal.safety.validator.ContactResourceValidator;
import com.au.personal.safety.users.*;
import javax.ws.rs.PathParam;
	@Path("/contact")
	public class ContactResource {
		@POST
		@Path("/sendtodb/{userName}")
		@Consumes(MediaType.APPLICATION_JSON)
		public Response createContact(ContactIn contact, @PathParam("userName") String userName) {
			User thisUser = new User();
			thisUser.setUserName(userName);
			UserDB thisUserDB = new UserDB(thisUser);
			int userID = thisUserDB.getUserID();
			
			if(userID < 0){
				return Response.serverError().entity("Not a User").build(); 
				
			}else{
				Contact newContact = new Contact(); 
				newContact.setUserID(userID);
				newContact.setFirstName(contact.FirstName);
				newContact.setLastName(contact.LastName);
				newContact.setContactPhone(contact.ContactPhone);
				newContact.setContactCarrier(contact.ContactCarrier);
				newContact.setContactEmail(contact.ContactEmail);
				newContact.setContactID(contact.ContactID);
				
				ContactResourceValidator validator = new ContactResourceValidator(newContact);
				
				if(validator.validate() == true) {
					ContactDB contactdb = new ContactDB(newContact);
				    return contactdb.sendContact();
				}
				else {
					return validator.getResponse();
				}
			}
			
		}
		
		@POST
		@Path("/deletecontacts/{contactID}")
		@Consumes(MediaType.APPLICATION_JSON)
		public Response deleteContactFromDB(@PathParam("contactID") int contactID) {
			Contact contact = new Contact();
			contact.setContactID(contactID);
			ContactDB contactdb = new ContactDB(contact);
			return contactdb.deleteContact();
		}
		
		@POST
		@Path("/getcontacts/{userName}")
		@Consumes(MediaType.APPLICATION_JSON)
		public List<Contact> getContactsFromDB(@PathParam("userName") String userName) {
			User user = new User();
			user.setUserName(userName);
			UserDB userDB = new UserDB(user);
			int userID = userDB.getUserID();
			
			if(userID < 0){
				List<Contact> empty = new ArrayList<Contact>();
				return empty; 
			} else{
				Contact contact = new Contact();
				ContactDB contactDB = new ContactDB(contact);
				return contactDB.getContacts(userID);
			}
			
		}
		

}
