package com.solution.musiccollab.shared.view;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.solution.musiccollab.shared.event.NavigationEventHandler;
import com.solution.musiccollab.shared.value.UserDAO;

public class UsersList extends ListPanel {
	
	private ArrayList<NavigationEventHandler> handlers = new ArrayList<NavigationEventHandler>();
	
	private List<UserDAO> data;
	private String caption = "Users";
	private VerticalPanel list;
	
	public UsersList() {
		
		title.setText(caption);
		list = new VerticalPanel();

		list.setHeight("100%");
		list.setWidth("100%");

		list.getElement().setAttribute("style", "background-color:#C5A159; border-style:solid; border-color:#CCC;");
		
		rootPanel.add(list);
	}
	
	public void setItems(List<UserDAO> userDAOs) {
		list.clear();
		for (UserDAO userDAO : userDAOs) {
			UserItemPanel userItemPanel = new UserItemPanel();
			userItemPanel.setupPanel(this, userDAO);
			list.add(userItemPanel);
		}
		data = userDAOs;
	}
	
	public int getSize() {
		if(data != null)
			return data.size();
		return 0;
	}
	
	public void addNavigationEventHandler(NavigationEventHandler handler) {
		handlers.add(handler);
    }

    public void removeNavigationEventHandler(NavigationEventHandler handler) {
    	handlers.remove(handler);
    }
    
    public ArrayList<NavigationEventHandler> getHandlers() {
    	return handlers;
    }
}