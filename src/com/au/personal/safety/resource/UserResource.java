package com.au.personal.safety.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import com.au.personal.safety.users.User;
import com.au.personal.safety.users.UserDB;

@Path("/user")
public class UserResource {
	@POST
	@Path("/getcontacts")
	@Consumes(MediaType.APPLICATION_JSON)
	public List getContactsFromDB(User user) {
		UserDB userDB = new UserDB(user);
		return userDB.getContacts(user.getUserID());
	}
}
