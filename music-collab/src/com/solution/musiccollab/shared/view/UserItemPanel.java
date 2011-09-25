package com.solution.musiccollab.shared.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.solution.musiccollab.shared.event.NavigationEvent;
import com.solution.musiccollab.shared.event.NavigationEventHandler;
import com.solution.musiccollab.shared.model.Model;
import com.solution.musiccollab.shared.value.UserDAO;

public class UserItemPanel extends Composite {
	
	@UiField
	Label nameLabel;
	
	private UserDAO data;
	
	@UiTemplate("uibinder/UserItemPanel.ui.xml")
	interface MyUiBinder extends UiBinder<Widget, UserItemPanel> { }
	private static final MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	public UserItemPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void setupPanel(final UsersList parent, final UserDAO userDAO) {
		data = userDAO;
		nameLabel.setText(userDAO.getNickname());
		
		nameLabel.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				for(NavigationEventHandler handler : parent.getHandlers()) {
					handler.onMemberPageNavigation(new NavigationEvent(userDAO));
		        }
			}
		});
	}
	
}
