package com.solution.musiccollab.shared.view;

import java.util.List;

import com.google.gwt.user.client.ui.ListBox;
import com.solution.musiccollab.shared.value.UserDAO;

public class UsersList extends ListBox {
	
	public UsersList() {
	    setVisibleItemCount(4);
	}
	
	public void addItems(List<UserDAO> users) {
		for (UserDAO user : users) {
			addItem(user.getNickname());
		}
	}
	
}
