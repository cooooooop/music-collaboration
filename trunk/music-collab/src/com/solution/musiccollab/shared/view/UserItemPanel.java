package com.solution.musiccollab.shared.view;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.solution.musiccollab.shared.event.NavigationEvent;
import com.solution.musiccollab.shared.event.NavigationEventHandler;
import com.solution.musiccollab.shared.value.UserDAO;

public class UserItemPanel extends Composite {
	
	@UiField
	Label nameLabel;
	
	@UiField
	Label loginLabel;
	
	private UserDAO data;
	
	@UiTemplate("uibinder/UserItemPanel.ui.xml")
	interface MyUiBinder extends UiBinder<Widget, UserItemPanel> { }
	private static final MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	public UserItemPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void setupPanel(final UsersList parent, final UserDAO userDAO) {
		data = userDAO;
		nameLabel.setText(data.getNickname());
		
		loginLabel.setText(getLoginText());
		
		nameLabel.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				for(NavigationEventHandler handler : parent.getHandlers()) {
					handler.onMemberPageNavigation(new NavigationEvent(userDAO));
		        }
			}
		});
	}
	
	private String getLoginText() {
		Date today = new Date();
		long days = getDaysDiff(today, data.getLastLogin());
		long hours = getHoursDiff(today, data.getLastLogin());
		long mins = getMinsDiff(today, data.getLastLogin());
		
		if(days > 30)
			return "Last seen " + new Integer((int) Math.floor(days / 30)) + " months ago";
		else if(days > 1)
			return "Last seen " + days + " days ago";
		else if(days == 1)
			return "Last seen " + days + " day ago";
		else if(hours > 1)
			return "Last seen " + hours + " hours ago";
		else if(hours == 1)
			return "Last seen " + hours + " hour ago";
		else if(mins > 1)
			return "Last seen " + mins + " minutes ago";
		else if(mins == 1)
			return "Last seen " + mins + " minute ago";
		
		return "Online now";
	}
	
	private long getDaysDiff(Date date1, Date date2) {
		long diff = date1.getTime() - date2.getTime();
		return diff / (1000 * 60 * 60 * 24);
	}
	
	private long getHoursDiff(Date date1, Date date2) {
		long diff = date1.getTime() - date2.getTime();
		return diff / (1000 * 60 * 60);
	}
	
	private long getMinsDiff(Date date1, Date date2) {
		long diff = date1.getTime() - date2.getTime();
		return diff / (1000 * 60);
	}
	
}
