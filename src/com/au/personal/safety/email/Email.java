package com.au.personal.safety.email;

import java.util.Map;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;

public class Email {

	private Properties properties;
	private String sendingEmailAddress;
	private String sendingEmailPassword;
	private Session session;
	private Message message;
	private EmailMessage emailMessage;

	public Email(EmailMessage emailMessage) {
		properties = new Properties();
		this.emailMessage = emailMessage;
		setSendingEmailAddressAndPassword();
		setProperties();
		createNewSession();	
		buildMessage();
	}
	
	public void sendMessage() {
		try {
			Transport.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	private void buildMessage() {		
		try {
			message = new MimeMessage(session);
			message.setFrom(new InternetAddress(sendingEmailAddress));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(emailMessage.getRecipients()));
			message.setSubject(emailMessage.getSubject());
			message.setText(emailMessage.getMessageText());
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	private void setSendingEmailAddressAndPassword() {
		Map<String, String> env = System.getenv();		
		sendingEmailAddress = env.get("EMAIL_ADDRESS");
		sendingEmailPassword = env.get("EMAIL_PASSWORD");
	}

	private void createNewSession() {
		session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(sendingEmailAddress, sendingEmailPassword);
			}
		});
	}

	private void setProperties() {
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.socketFactory.port", "465");
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.port", "465");
	}
}
