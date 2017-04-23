package com.au.personal.safety.constants;

public class UserConstants {
	//Create User Responses
	public static final String USER_WAS_SUCCESSFULLY_CREATED = "User was successfully created.";
	public static final String USER_ALREADY_EXISTS = "ERROR: User already Exists.";
	public static final String USER_COULD_NOT_BE_CREATED_URI = "ERROR: User could not be created. URI Syntax.";
	public static final String USER_COULD_NOT_BE_CREATED_SQL = "ERROR: User could not be created. SQL Exception.";
	
	//Set pin responses
	public static final String PIN_CREATED = "Pin was successfully set.";
	public static final String USER_DOES_NOT_EXIST = "ERROR: User does not exist. Pin not set";
	public static final String PIN_COULD_NOT_BE_CREATED_URI = "ERROR: Pin could not be set. URI Syntax.";
	public static final String PIN_COULD_NOT_BE_CREATED_SQL = "ERROR: Pin could not be set. SQL Exception.";
}
