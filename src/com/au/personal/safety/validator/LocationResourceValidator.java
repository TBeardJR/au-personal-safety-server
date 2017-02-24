package com.au.personal.safety.validator;

import javax.ws.rs.core.Response;

import com.au.personal.safety.constants.HttpResponseConstants;
import com.au.personal.safety.database.Location;

public class LocationResourceValidator extends HttpRequestValidator {
	
	private Location location;
	private Integer userID;

	public LocationResourceValidator(Location location, Integer userID) {
		super();
		this.location = location;
		this.userID = userID;
	}

	@Override
	public Response getResponse() {
		// TODO Auto-generated method stub
		return Response.status(Response.Status.BAD_REQUEST).entity(HttpResponseConstants.NULL_PARAMETERS).build();
	}

	@Override
	public boolean validate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void buildResponse() {
		// TODO Auto-generated method stub

	}

}
