package com.au.personal.safety.validator;

import javax.ws.rs.core.Response;

import com.au.personal.safety.constants.HttpResponseConstants;
import com.au.personal.safety.helper.HttpResponse;

public abstract class HttpRequestValidator implements Validator, HttpResponse {
	
	protected boolean isRequestValid;
	protected boolean isParametersNull;
	protected Response response;
	
	public HttpRequestValidator() {
		isRequestValid = true;	
		isParametersNull = false;
	}
	
	public abstract boolean validate();
	protected abstract void buildResponse();
	
	protected <T> boolean performBasicValidation(T[] parameters) {
		for(int x = 0; x < parameters.length; x++) {
			if(parameters[x] == null) {
				isRequestValid = false;
				isParametersNull = true;				
				break;
			}
		}
		return isRequestValid;
	}
}
