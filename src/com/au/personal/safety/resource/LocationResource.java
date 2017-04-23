package com.au.personal.safety.resource;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.au.personal.safety.location.Location;
import com.au.personal.safety.users.User;
import com.au.personal.safety.users.UserDB;
import com.au.personal.safety.validator.HttpRequestValidator;
import com.au.personal.safety.validator.LocationResourceValidator;

@Path("/location")
public class LocationResource {
	
	@POST
	@Path("/store/{userName}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response storeLastLocation(Location location, @PathParam("userName") @DefaultValue(" ") String userName) {
		Response response = null;
		User thisUser = new User();
		thisUser.setUserName(userName);
		UserDB thisUserDB = new UserDB(thisUser);
		int userID = thisUserDB.getUserID();
		if(userID < 0){
			response = Response.serverError().entity("Not a User").build(); 
		} else{
			location.setUserID(userID);
			HttpRequestValidator validator = new LocationResourceValidator(location);
			if(validator.validate()) {
				location.saveNewLocation(location.getLong(), location.getLat(), location.getUserID()); // TODO needs to return a response
				response = Response.ok().entity("Location saved!").build();
			} else {
				response = validator.getResponse();
			}
		}
		return response;
	}
}
