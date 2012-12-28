package com.solution.musiccollab.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.objectify.Query;
import com.solution.musiccollab.shared.value.UserDAO;

public class EmailServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	DAO dao = new DAO();
		
    	String password = req.getParameter("key");
    	//DO NOT COMMIT PASSWORD!!
    	//DO NOT DEPLOY WITH NO PASSWORD!!
		if(password == null || !password.equals(""))
			return;
    	
    	String host = "mail.google.com";
    	String to = "cooooooop@hotmail.com";
    	String from = "Curtis Cooper <cooooooop@gmail.com>";
    	String subject = req.getParameter("subject");
    	String messageText = req.getParameter("message");

    	// Create some properties and get the default Session.
    	Properties props = System.getProperties();
    	props.put("mail.host", host);
    	props.put("mail.transport.protocol", "smtp");
    	Session mailSession = Session.getDefaultInstance(props, null);
    	 
    	messageText += "<p>You are receiving this message because you are a member of the Jamster online service.  "
    				+ "If you would like to stop receiving these messages, hit reply and let me know, I'll remove your account.";
    	
    	try {
    		String debugMessage = "Message sent to:\n";
	    	Message msg = new MimeMessage(mailSession);
	    	msg.setFrom(new InternetAddress(from));
	    	InternetAddress[] replyAddress = {new InternetAddress("cooooooop@gmail.com")};
	    	msg.setReplyTo(replyAddress);
	    	
	    	List<UserDAO> users = dao.ofy().query(UserDAO.class).list();
	    	InternetAddress[] address = {new InternetAddress(to)};
	    	InternetAddress[] addresses = new InternetAddress[users.size()];
	    	for(int i = 0; i < users.size(); i++) {
	    		UserDAO user = users.get(i);
	    		addresses[i] = new InternetAddress(user.getEmail());
	    		debugMessage += "\n" + user.getEmail();
	    	}
	    	msg.setRecipients(Message.RecipientType.BCC, addresses);
	    	msg.setSubject(subject);
	    	msg.setSentDate(new Date());
	    	msg.setContent(messageText, "text/html; charset=utf-8");
	    	 
	    	// Hand the message to the default transport service
	    	// for delivery.
	    	 
	    	Transport.send(msg);
	    	
	    	resp.getWriter().write(debugMessage);
    	}
    	catch(Exception e) {
    		//ignore
    	}
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		don't support
//    	dao = new DAO();
//		List<UserDAO> users = dao.ofy().query(UserDAO.class).order("lastLogin").list();
//    	JSONSerializer.userListSerialize(users, resp);
        
    }
}
