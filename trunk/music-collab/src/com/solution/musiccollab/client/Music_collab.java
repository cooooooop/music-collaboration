package com.solution.musiccollab.client;

import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.solution.musiccollab.client.interfaces.UsersService;
import com.solution.musiccollab.client.interfaces.UsersServiceAsync;
import com.solution.musiccollab.shared.value.UserDAO;

public class Music_collab implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final UsersServiceAsync usersService = GWT.create(UsersService.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		final UsersList list = new UsersList();
		final Label label = new Label();
		
		RootPanel.get("usersList").add(list);
		RootPanel.get("errorLabelContainer").add(label);
		
		
		usersService.getAll(new AsyncCallback<List<UserDAO>>() {
			
			@Override
			public void onSuccess(List<UserDAO> result) {
				label.setText("Success");
			}
			
			@Override
			public void onFailure(Throwable caught) {
				label.setText(SERVER_ERROR);
			}
		});

	}
}

class UsersList extends ListBox {
	
	public UsersList() {

	    setVisibleItemCount(5);
	}
	
}