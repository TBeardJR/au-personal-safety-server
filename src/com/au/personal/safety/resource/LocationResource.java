package com.au.personal.safety.resource;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.au.personal.safety.database.Location;

@Path("/location")
public class LocationResource {
	
	@POST
	@Path("/store/{userID}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response storeLastLocation(Location location, @PathParam("userID") Integer userID) {
		Response response = null;
		location.saveNewLocation(location.getLong(), location.getLat(), location.getUserID());
		return response;
	}
}
