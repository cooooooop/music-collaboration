package com.solution.musiccollab.client;

import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.VerticalAlign;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.HasVerticalAlignment.VerticalAlignmentConstant;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.solution.musiccollab.client.interfaces.UsersService;
import com.solution.musiccollab.client.interfaces.UsersServiceAsync;
import com.solution.musiccollab.shared.value.UserDAO;
import com.solution.musiccollab.shared.value.AudioFileDAO;
import com.solution.musiccollab.shared.view.AudioFilesList;
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
		final Label label = new Label();
		final AudioFilesList audioFilesList = new AudioFilesList();
		final UsersList list = new UsersList();
		list.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				//populate the audioFilesList
				usersService.getAudioByUser(list.getSelected(), new AsyncCallback<List<AudioFileDAO>>() {

					@Override
					public void onFailure(Throwable caught) {
						label.setText(SERVER_ERROR);
					}

					@Override
					public void onSuccess(List<AudioFileDAO> result) {
						audioFilesList.addItems(result);
						label.setText("Successfully retrieved AudioFile list.");
					}

				});
			}
		});
		
		
		
		RootPanel.get("errorLabelContainer").add(label);
		
		usersService.getCurrentUser(new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
				label.setText(SERVER_ERROR);				
			}

			@Override
			public void onSuccess(String result) {
				if(result != null) {
					HorizontalPanel listPanel = new HorizontalPanel();
					listPanel.setSpacing(5);
					listPanel.add(list);
					listPanel.add(audioFilesList);
					
					RootPanel.get("usersList").add(listPanel);
					Label userLabel = new Label("Logged in as " + result);
					Button logoutButton = new Button("Logout");
					HorizontalPanel hPanel = new HorizontalPanel();
					hPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
					hPanel.setSpacing(5);
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
				label.setText("Successfully retrieved Users list.");
			}
			
			@Override
			public void onFailure(Throwable caught) {
				label.setText(SERVER_ERROR);
			}
		});

	}
}