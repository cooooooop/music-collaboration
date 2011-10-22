package com.solution.musiccollab.client.interfaces;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.solution.musiccollab.shared.value.AudioFileDAO;
import com.solution.musiccollab.shared.value.MixDAO;
import com.solution.musiccollab.shared.value.UserDAO;

@RemoteServiceRelativePath("users")
public interface UsersService extends RemoteService {
	List<UserDAO> getAll();
	UserDAO getCurrentUser();
	List<UserDAO> getUsersLimit(int limit);
	
	UserDAO saveUser(UserDAO userDAO);
	Boolean deleteUser(UserDAO userDAO);
}
