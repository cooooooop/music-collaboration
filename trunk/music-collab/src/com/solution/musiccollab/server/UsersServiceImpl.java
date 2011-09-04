package com.solution.musiccollab.server;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.solution.musiccollab.client.interfaces.UsersService;
import com.solution.musiccollab.shared.value.UserDAO;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class UsersServiceImpl extends RemoteServiceServlet implements
		UsersService {

	public List<UserDAO> getAll() {
		List<UserDAO> list = new ArrayList<UserDAO>();
		UserDAO user = new UserDAO("coooooop@gmail.com");
		list.add(user);
		return list;
	}
	
	public String getCurrentUser() {
		UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        
        if(user!=null)
        	return user.getNickname();
        
        return null;
	}
	
}
