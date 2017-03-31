package com.au.personal.safety.contacts;

import java.util.Dictionary;
import java.util.LinkedHashMap;

/*
 * This class creates/contains the dictionary of phone carriers and their corresponding
 * email addresses; the phone app and server can search through this dictionary to find 
 * and check values
 */

/*
 * Websites containing valid phone carrier email addresses:
 * http://www.sensiblesoftware.com/weblog/2011/02/28/cell-phone-email-addresses/
 * The 6 most popular:
 *    T-Mobile: phonenumber@tmomail.net 
      Virgin Mobile: phonenumber@vmobl.com 
      Cingular: phonenumber@cingularme.com 
      Sprint: phonenumber@messaging.sprintpcs.com
      Verizon: phonenumber@vtext.com
      Nextel: phonenumber@messaging.nextel.com
 * Others that we will check for:
     AT&T: 	phonenumber@txt.att.net
 */

public class PhoneCarriers {
    /* Class Attribute */
	private LinkedHashMap carrierDictionary;  
	    /*
	     * keys = phone carrier names as strings
	     * values = corresponding phone addresses as strings
	     */
	/* Constructor */
	public PhoneCarriers() {
		//create the carrierDictionary
		generateDictionary();
	}
	
	/* Methods */
	
	/*
	 * Purpose: generate the values for the class attribute "carrierDictionary"
	 * Input: none
	 * Output: none
	 * Resulting state: the attribute "carrierDictionary" has been instantiated with values 
	 */
	private void generateDictionary() {
		Dictionary<String, String> temp;
		carrierDictionary = new LinkedHashMap();
		//add the carriers and their addresses 
		carrierDictionary.put("AT&T", "@txt.att.net");
		carrierDictionary.put("T-Mobile", "@tmomail.net");
		carrierDictionary.put("Virgin Mobile", "@vmobl.com");
		carrierDictionary.put("Cingular", "@cingularme.com");
		carrierDictionary.put("Sprint", "@messaging.sprintpcs.com");
		carrierDictionary.put("Verizon", "@vtext.com");
		carrierDictionary.put("Nextel", "@messaging.nextel.com");
	}
	
	/*
	 * Purpose: to get the value of "carrierDictionary"
	 * Input: none
	 * Return: value for the class attribute called "carrierDictionary"
	 */
	public LinkedHashMap getCarrierDictionary() {
		return carrierDictionary;
	}
	
}
