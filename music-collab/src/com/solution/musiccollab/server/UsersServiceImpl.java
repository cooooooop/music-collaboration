package com.solution.musiccollab.server;

import java.util.ArrayList;
import java.util.Arrays;
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
		DAO dao = new DAO();
		return dao.ofy().query(UserDAO.class).list();
	}
	
	public String getCurrentUser() {
		UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        
        if(user!=null) {
        	//create user and store if does not exist
        	DAO dao = new DAO();
        	dao.ofy().put(dao.getOrCreateUser(user));
        	return user.getNickname();
        }
        
        return null;
	}
	
}