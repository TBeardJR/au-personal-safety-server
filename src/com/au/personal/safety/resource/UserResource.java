package com.au.personal.safety.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import com.au.personal.safety.contacts.Contact;
import com.au.personal.safety.users.User;
import com.au.personal.safety.users.UserDB;
import javax.ws.rs.core.Response;

@Path("/user")
public class UserResource {
	@POST
	@Path("/createuser")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createUser(User user) {
		UserDB userDB = new UserDB(user);
		//return userDB.getContacts(user.getUserID());
		Response respond = userDB.createNewUser();
		return respond;
	}
	
	@POST
	@Path("/setPin")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response setPin(String userName) {
		User user = new User();
		user.setUserName(userName);
		UserDB userDB = new UserDB(user);
		
		
		//return userDB.getContacts(user.getUserID());
		Response respond = userDB.createNewUser();
		return respond;
	}
	
	@POST
	@Path("/getPin")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getPin(String userName) {
		User user = new User();
		user.setUserName(userName);
		UserDB userDB = new UserDB(user);
		userDB.getUserPin(userName);
		
		//return userDB.getContacts(user.getUserID());
		Response respond = userDB.createNewUser();
		return respond;
	}
}
