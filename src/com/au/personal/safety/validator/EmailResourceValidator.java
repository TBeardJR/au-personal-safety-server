package com.au.personal.safety.validator;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.core.Response;

import com.au.personal.safety.constants.HttpResponseConstants;
import com.au.personal.safety.email.EmailMessage;

public class EmailResourceValidator extends HttpRequestValidator {

	private EmailMessage emailMessage;
	private boolean isEmailMissingRequiredParts;
	private boolean isRecipientsEmailAddressesValid;
	private boolean isDuplicateRecipientEmailAddresses;

	public EmailResourceValidator(EmailMessage emailMessage) {
		super();
		this.emailMessage = emailMessage;
		isEmailMissingRequiredParts = false;
		isRecipientsEmailAddressesValid = true;
		isDuplicateRecipientEmailAddresses = false;
	}

	@Override
	public boolean validate() {
		EmailMessage[] parameters = { emailMessage };
		performBasicValidation(parameters);
		if (isRequestValid) {
			if (isEmailMissingRequiredParts() || !isRecipientsEmailAddressesValid() || isDuplicateRecipientEmailAddresses()) {
				buildResponse();
				isRequestValid = false;
			}
		} else {
			buildResponse();
		}
		return isRequestValid;
	}
	
	@Override
	public Response getResponse() {
		return response;
	}
	
	@Override
	protected void buildResponse() {
		if (isParametersNull) {
			response = Response.status(Response.Status.BAD_REQUEST).entity(HttpResponseConstants.NULL_PARAMETERS).build();
		} else if (isEmailMissingRequiredParts) {
			response = Response.status(Response.Status.BAD_REQUEST).entity(HttpResponseConstants.MISSING_PARTS_OF_EMAIL).build();
		} else if (!isRecipientsEmailAddressesValid) {
			response = Response.status(Response.Status.BAD_REQUEST).entity(HttpResponseConstants.INVALID_EMAIL_ADDRESS_FORMAT).build();
		} else {
			response = Response.status(Response.Status.BAD_REQUEST).entity(HttpResponseConstants.DUPLICATE_RECIPIENT_EMAIL_ADDRESSES).build();
		}
	}
	
	private boolean isDuplicateRecipientEmailAddresses() {
		List<String> recipientEmailAddressesList = Arrays.asList(emailMessage.getRecipients().split(","));
		Set<String> recipientEmailAddressesSet = new HashSet<String>(recipientEmailAddressesList); // converting to set removes duplicates automatically
		if(recipientEmailAddressesSet.size() < recipientEmailAddressesList.size()) {
			isDuplicateRecipientEmailAddresses = true;
		}
		return isDuplicateRecipientEmailAddresses;
	}

	private boolean isRecipientsEmailAddressesValid() {
		String[] recipientEmailAddresses = emailMessage.getRecipients().split(",");
		String regex = "\\w+@\\w+\\.com";
		Pattern pattern = Pattern.compile(regex);
		for (int x = 0; x < recipientEmailAddresses.length; x++) {
			Matcher matcher = pattern.matcher(recipientEmailAddresses[x]);
			if(!matcher.find()) {
				isRecipientsEmailAddressesValid = false;
			}
		}
		return isRecipientsEmailAddressesValid;
	}

	private boolean isEmailMissingRequiredParts() {
		if (emailMessage.getMessageText() == null || emailMessage.getMessageText() == ""
				|| emailMessage.getRecipients() == null || emailMessage.getRecipients() == "") {
			isEmailMissingRequiredParts = true;
		}
		return isEmailMissingRequiredParts;
	}

}
