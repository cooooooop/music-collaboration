package com.solution.musiccollab.client.interfaces;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.solution.musiccollab.shared.value.AudioFileDAO;
import com.solution.musiccollab.shared.value.UserDAO;

public interface UsersServiceAsync {
	void getAll(AsyncCallback<List<UserDAO>> callback);
	void getCurrentUser(AsyncCallback<UserDAO> callback);
	void getUsersLimit(int limit, AsyncCallback<List<UserDAO>> callback); 
}
