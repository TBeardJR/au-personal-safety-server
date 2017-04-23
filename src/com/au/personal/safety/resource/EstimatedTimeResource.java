package com.au.personal.safety.resource;

import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.au.personal.safety.constants.EmailConstants;
import com.au.personal.safety.email.Email;
import com.au.personal.safety.email.EmailMessage;
import com.au.personal.safety.validator.EmailResourceValidator;

@Path("/estimatedtime")
public class EstimatedTimeResource {
	
	private @Context ServletContext servletContext;	
	
	
	@POST
	@Path("/starttimer/{estimatedTime}/{name}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response startTimer(EmailMessage emailMessage, @PathParam("estimatedTime") long estimatedTime, @PathParam("name") String name) {
		Response response = null;
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {	
			  long timeLeft = estimatedTime;			  
			  @Override
			  public void run() {	
				  timeLeft--;
				  servletContext.setAttribute(name + "time left", timeLeft);
				  if(timeLeft == 0) {
					  System.out.println("Recipients: " + emailMessage.getRecipients());
					  timer.cancel();
					  servletContext.removeAttribute(name);
					  emailMessage.setMessageText(name + " is in danger!");
					  emailMessage.setSubject("Safe Trip: " + name + "is in Danger!");
						  Email email = new Email(emailMessage);
						  email.sendMessage();			  
				  }
			  }				  
		}, estimatedTime, 1000);
		
		servletContext.setAttribute(name, timer);
		
		response = Response.status(Response.Status.OK).entity("Timer has started for " + name).build();
		
		return response;

	}
	
	@POST
	@Path("/stoptimer/{name}")
	public Response stopTimer(@PathParam("name") String name) {
		Response response = null;		
		Timer timer = (Timer) servletContext.getAttribute(name);
		timer.cancel();			
		servletContext.removeAttribute(name);	
		servletContext.removeAttribute(name + "time left");	
		response = Response.status(Response.Status.OK).entity("Timer has been cancelled for " + name).build();
		
		return response;
	}
	
	@POST
	@Path("/extendtimer/{estimatedTime}/{name}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response extendTimer(EmailMessage emailMessage, @PathParam("estimatedTime") int estimatedTime, @PathParam("name") String name) {
		Response response = null;		
		Timer timer = (Timer) servletContext.getAttribute(name);
		timer.cancel();			
		servletContext.removeAttribute(name);	
		Timer extendedTimer = new Timer();
		extendedTimer.schedule(new TimerTask() {
			long timeLeft = (long) servletContext.getAttribute(name + "time left") + estimatedTime;	
			  @Override
			  public void run() {	
				  timeLeft--;
				  servletContext.setAttribute(name + "time left", timeLeft);
				  if(timeLeft == 0) {
					  extendedTimer.cancel();
					  servletContext.removeAttribute(name);
					  emailMessage.setMessageText(name + " is in danger! extended");
					  emailMessage.setSubject("Safe Trip: " + name + "is in Danger!");
					  Email email = new Email(emailMessage);
					  email.sendMessage();	
				  }				  				  
			  }
			}, estimatedTime, 1000);
		
		servletContext.setAttribute(name, timer);
		
		response = Response.status(Response.Status.OK).entity("Timer has been extended for " + name).build();
		
		return response;
	}
}
