package com.solution.musiccollab.client.interfaces;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.solution.musiccollab.shared.value.UserDAO;

@RemoteServiceRelativePath("users")
public interface UsersService extends RemoteService {
	List<UserDAO> getAll();
}
