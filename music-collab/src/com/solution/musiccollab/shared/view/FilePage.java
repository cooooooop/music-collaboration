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
import com.solution.musiccollab.shared.value.AudioFileDAO;
import com.solution.musiccollab.shared.value.UserDAO;

public class FilePage extends Composite implements IUpdatable{

	@UiField
	AudioFilesList relatedAudioFilesList;
	
	@UiField
	Label userNameLabel;
	
	@UiField
	Label titleLabel;
	
	@UiField
	Label backLabel;
	
	private ArrayList<NavigationEventHandler> handlers = new ArrayList<NavigationEventHandler>();
	private UserDAO owner;
	
	@UiTemplate("uibinder/FilePage.ui.xml")
	interface MyUiBinder extends UiBinder<Widget, FilePage> { }
	private static final MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	public FilePage() {
		initWidget(uiBinder.createAndBindUi(this));
		
		backLabel.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				for(NavigationEventHandler handler : handlers) {
					handler.onHomeNavigation(new NavigationEvent(Model.currentUser));
		        }
			}
		});
		
		userNameLabel.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				for(NavigationEventHandler handler : handlers) {
					handler.onMemberPageNavigation(new NavigationEvent(FilePage.this.owner));
		        }
			}
		});
		
	}
	
	@Override
	public void update() {
		relatedAudioFilesList.setCaptionText("Related Files");
		relatedAudioFilesList.setVisible(relatedAudioFilesList.getSize() > 0);
		
	}
	
	public void setAudioDAO(AudioFileDAO audioFileDAO) {
		this.owner = audioFileDAO.getOwnerUserDAO();
		
		this.userNameLabel.setText(audioFileDAO.getOwnerUserDAO().getNickname());
		this.titleLabel.setText(audioFileDAO.getFileName());
	}
	
	public AudioFilesList getAudioFilesList() {
		return relatedAudioFilesList;
	}
	
	public void addNavigationEventHandler(NavigationEventHandler handler) {
		handlers.add(handler);
    }

    public void removeNavigationEventHandler(NavigationEventHandler handler) {
    	handlers.remove(handler);
    }
	
}
