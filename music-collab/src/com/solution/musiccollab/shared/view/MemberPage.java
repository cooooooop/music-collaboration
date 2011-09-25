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

public class MemberPage extends Composite implements IUpdatable{

	@UiField
	AudioFilesList audioFilesList;
	
	@UiField
	Label userNameLabel;
	
	@UiField
	Label fileCountLabel;
	
	@UiField
	Button backButton;
	
	private ArrayList<NavigationEventHandler> handlers = new ArrayList<NavigationEventHandler>();
	
	@UiTemplate("uibinder/MemberPage.ui.xml")
	interface MyUiBinder extends UiBinder<Widget, MemberPage> { }
	private static final MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	public MemberPage() {
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
		fileCountLabel.setText("File Uploads: " + audioFilesList.getSize());
			
		audioFilesList.setVisible(audioFilesList.getSize() > 0);
		
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userNameLabel.setText(userDAO.getNickname());
	}
	
	public AudioFilesList getAudioFilesList() {
		return audioFilesList;
	}
	
	public void addNavigationEventHandler(NavigationEventHandler handler) {
		handlers.add(handler);
    }

    public void removeNavigationEventHandler(NavigationEventHandler handler) {
    	handlers.remove(handler);
    }
	
}
