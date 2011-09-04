package com.solution.musiccollab.server;

import java.util.ArrayList;
import java.util.List;

import com.solution.musiccollab.client.UsersService;
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
		return list;
	}

}
