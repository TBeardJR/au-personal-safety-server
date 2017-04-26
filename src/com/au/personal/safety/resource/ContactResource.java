package com.au.personal.safety.resource;

import java.util.List;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.au.personal.safety.contacts.*;
import com.au.personal.safety.validator.ContactResourceValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.au.personal.safety.users.*;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

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
		
		@GET
		@Path("/getcontacts/{userName}")
		@Produces(MediaType.APPLICATION_JSON)
		public Response getContactsFromDB(@PathParam("userName") String userName) throws JsonProcessingException {
			
			User user = new User();
			user.setUserName(userName);
			UserDB userDB = new UserDB(user);
			int userID = userDB.getUserID();
			
			if(userID < 0){
				Contact error = new Contact();
				error.setFirstName("ERROR");
				List<Contact> empty = new ArrayList<Contact>();
				empty.add(error);
				String serializedForumPostResponses = serializeToJson(empty);
				Response response = Response.ok().entity(serializedForumPostResponses).build();
				return response; 
			} else{
				Contact contact = new Contact();
				ContactDB contactDB = new ContactDB(contact);
				String serializedForumPostResponses = serializeToJson(contactDB.getContacts(userID));
				Response response = Response.ok().entity(serializedForumPostResponses).build();
				return response;
			}
			
		}
		
		private String serializeToJson(List<Contact> contacts) throws JsonProcessingException {		
			ObjectMapper objectMapper = new ObjectMapper();
	    	objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // pretty print
			return objectMapper.writeValueAsString(contacts);
		}
		

}
