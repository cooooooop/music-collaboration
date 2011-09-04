package com.solution.musiccollab.shared.view;

import com.google.gwt.user.client.ui.ListBox;

public class UsersList extends ListBox {
	
	public UsersList() {
		addItem("foo");
	    addItem("bar");
	    addItem("baz");
	    addItem("toto");
	    addItem("tintin");

	    setVisibleItemCount(5);
	}
	
}
