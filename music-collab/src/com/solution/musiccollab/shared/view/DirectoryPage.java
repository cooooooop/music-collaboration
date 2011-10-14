package com.solution.musiccollab.shared.view;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.solution.musiccollab.shared.event.NavigationEvent;
import com.solution.musiccollab.shared.event.NavigationEventHandler;
import com.solution.musiccollab.shared.model.Model;
import com.solution.musiccollab.shared.value.UserDAO;

public class DirectoryPage extends Composite implements IUpdatable {

	@UiField
	UsersList usersList;

	@UiField
	Label userCountLabel;
	
	@UiField
	Button backButton;
	
	private ArrayList<NavigationEventHandler> handlers = new ArrayList<NavigationEventHandler>();
	
	@UiTemplate("uibinder/DirectoryPage.ui.xml")
	interface MyUiBinder extends UiBinder<Widget, DirectoryPage> { }
	private static final MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	public DirectoryPage() {
		initWidget(uiBinder.createAndBindUi(this));
		
		backButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				for(NavigationEventHandler handler : handlers) {
					handler.onHomeNavigation(new NavigationEvent(Model.currentUser));
		        }
			}
		});
	}
	
	@Override
	public void update() {
		userCountLabel.setText("Member Count: " + usersList.getSize());
		
		usersList.setVisible(usersList.getSize() > 0);
		
	}

	public UsersList getUsersList() {
		return usersList;
	}
	
	public void addNavigationEventHandler(NavigationEventHandler handler) {
		handlers.add(handler);
    }

    public void removeNavigationEventHandler(NavigationEventHandler handler) {
    	handlers.remove(handler);
    }
	
}
