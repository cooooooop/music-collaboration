package com.solution.musiccollab.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.solution.musiccollab.shared.value.UserDAO;

public interface UsersServiceAsync {
	void getAll(AsyncCallback<List<UserDAO>> callback);
}
