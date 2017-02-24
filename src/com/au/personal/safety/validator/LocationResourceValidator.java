package com.au.personal.safety.validator;

import javax.ws.rs.core.Response;

import com.au.personal.safety.constants.HttpResponseConstants;
import com.au.personal.safety.database.Location;
import com.au.personal.safety.email.EmailMessage;

public class LocationResourceValidator extends HttpRequestValidator {
	
	private Location location;
	private boolean isLocationValid;
	private boolean isUserIDEmpty;

	public LocationResourceValidator(Location location) {
		super();
		this.location = location;
		this.isLocationValid = true;
		this.isUserIDEmpty = false;
	}	

	@Override
	public boolean validate() {
		Location[] parameters = { location };
		performBasicValidation(parameters);
		if(isRequestValid) {
			if(isUserIDEmpty() || !isLocationValid()) {
				isRequestValid = false;
				buildResponse();
			}
		} else {
			buildResponse();
		}
		return isRequestValid;
	}

	@Override
	protected void buildResponse() {
		if (isParametersNull) {
			response = Response.status(Response.Status.BAD_REQUEST).entity(HttpResponseConstants.NULL_PARAMETERS).build();
		} else if(!isLocationValid) {
			response = Response.status(Response.Status.BAD_REQUEST).entity(HttpResponseConstants.INVALID_LOCATION).build();
		}
	}
	
	@Override
	public Response getResponse() {
		return response;
	}
	
	public boolean isUserIDEmpty() {
		if(location.getUserID() == -1) {
			isUserIDEmpty = true;
			isParametersNull = true;
		}
		
		return isUserIDEmpty;
	}
	
	private boolean isLocationValid() {
		if(location.getLong() > 180.0 || location.getLong() < -180.0 ||
				location.getLat() > 90.0 || location.getLat() < 0.0) {
			isLocationValid = false;
		}
		
		return isLocationValid;
	}

}
