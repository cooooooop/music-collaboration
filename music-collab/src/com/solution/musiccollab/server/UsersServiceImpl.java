package com.solution.musiccollab.server;

import java.util.List;

import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.solution.musiccollab.client.interfaces.UsersService;
import com.solution.musiccollab.shared.value.AudioFileDAO;
import com.solution.musiccollab.shared.value.UserDAO;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class UsersServiceImpl extends RemoteServiceServlet implements
		UsersService {

	public List<UserDAO> getAll() {
		DAO dao = new DAO();
		return dao.ofy().query(UserDAO.class).order("lastLogin").list();
	}
	
	public List<UserDAO> getUsersLimit(int limit) {
		DAO dao = new DAO();
		return dao.ofy().query(UserDAO.class).limit(limit).order("lastLogin").list();
	}
	
	public UserDAO getCurrentUser() {
		UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        
        if(user!=null) {
        	//create user and store if does not exist
        	DAO dao = new DAO();
        	UserDAO userDAO = dao.getOrCreateUser(user);
        	dao.ofy().put(userDAO);
        	return userDAO;
        }
        
        return null;
	}
	
	public List<AudioFileDAO> getAudioByUser(UserDAO user) {
		DAO dao = new DAO();
		return dao.ofy().query(AudioFileDAO.class).filter("owner", user.getUserid()).list();
	}

	@Override
	public UserDAO saveUser(UserDAO userDAO) {
		DAO dao = new DAO();
		dao.ofy().put(userDAO);
		return userDAO;
	}

	@Override
	public Boolean deleteUser(UserDAO userDAO) {
		DAO dao = new DAO();
		dao.ofy().delete(userDAO);
		return true;
	}
	
	@Override
	public String getUploadURL(String url) {
		return BlobstoreServiceFactory.getBlobstoreService().createUploadUrl(url);
	}
	
}
