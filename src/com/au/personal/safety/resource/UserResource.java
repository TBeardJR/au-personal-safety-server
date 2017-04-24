package com.au.personal.safety.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
//local
import com.au.personal.safety.users.User;
import com.au.personal.safety.users.UserDB;


@Path("/user")
public class UserResource {
	@POST
	@Path("/createuser")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createUser(String userName) {
		
		User user = new User();
		user.setUserName(userName);
		UserDB userDB = new UserDB(user);
		
		Response respond = userDB.createNewUser();
		return respond;
	}
	
	@POST
	@Path("/setPin")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response setPin(@PathParam("userName") String userName,  @PathParam("pin") String pin) {
		User user = new User();
		user.setUserName(userName);
		user.setUserPin(pin);
		UserDB userDB = new UserDB(user);
		
		Response respond = userDB.setUserPin();
		return respond;
	}
	
	@POST
	@Path("/getPin")
	@Consumes(MediaType.APPLICATION_JSON)
	public String getPin(@PathParam("userName") String userName) {
		User user = new User();
		user.setUserName(userName);
		UserDB userDB = new UserDB(user);
		
		String pin = userDB.getUserPin();
		return pin;
	}
}
