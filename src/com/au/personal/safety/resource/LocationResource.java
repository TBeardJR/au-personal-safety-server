package com.au.personal.safety.resource;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.au.personal.safety.database.Location;
import com.au.personal.safety.validator.HttpRequestValidator;
import com.au.personal.safety.validator.LocationResourceValidator;

@Path("/location")
public class LocationResource {
	
	@POST
	@Path("/store/{userID}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response storeLastLocation(Location location, @PathParam("userID") @DefaultValue("-1") Integer userID) {
		Response response = null;
		location.setUserID(userID);
		HttpRequestValidator validator = new LocationResourceValidator(location);
		if(validator.validate()) {
			location.saveNewLocation(location.getLong(), location.getLat(), location.getUserID()); // TODO needs to return a response
		} else {
			response = validator.getResponse();
		}
		
		return response;
	}
}
