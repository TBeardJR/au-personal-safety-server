package com.au.personal.safety.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.au.personal.safety.contacts.*;


	@Path("/contact")
	public class ContactResource {
		@POST
		@Path("/sendtodb")
		@Consumes(MediaType.APPLICATION_JSON)
		public Response sendContactToDB(Contact contact) {
			ContactDB contactdb = new ContactDB(contact);
			Response response = contactdb.sendContact();
			return response;
	}
}
