package com.solution.musiccollab.client;

import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.solution.musiccollab.client.interfaces.UsersService;
import com.solution.musiccollab.client.interfaces.UsersServiceAsync;
import com.solution.musiccollab.shared.value.UserDAO;
import com.solution.musiccollab.shared.view.UsersList;

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
		
		usersService.getCurrentUser(new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
				label.setText(SERVER_ERROR);				
			}

			@Override
			public void onSuccess(String result) {
				if(result != null) {
					Label userLabel = new Label("Logged in as " + result);
					Button logoutButton = new Button("Logout");
					HorizontalPanel hPanel = new HorizontalPanel();
					hPanel.add(userLabel);
					hPanel.add(logoutButton);
					RootPanel.get("loginButtonContainer").add(hPanel);
					
					logoutButton.addClickHandler(new ClickHandler() {
						
						@Override
						public void onClick(ClickEvent event) {
							Window.open("logout", "_self", "");
						}
					});
					
				}
				else {
					Button loginButton = new Button("Login");
					RootPanel.get("loginButtonContainer").add(loginButton);
					loginButton.addClickHandler(new ClickHandler() {
						
						@Override
						public void onClick(ClickEvent event) {
							Window.open("login", "_self", "");
						}
					});
				}
				
			}
		});
		
		usersService.getAll(new AsyncCallback<List<UserDAO>>() {
			
			@Override
			public void onSuccess(List<UserDAO> result) {
				list.addItems(result);
				label.setText("Success");
			}
			
			@Override
			public void onFailure(Throwable caught) {
				label.setText(SERVER_ERROR);
			}
		});

	}
}