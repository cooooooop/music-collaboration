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
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.solution.musiccollab.shared.event.DAOEvent;
import com.solution.musiccollab.shared.event.DAOEventHandler;
import com.solution.musiccollab.shared.event.NavigationEvent;
import com.solution.musiccollab.shared.event.NavigationEventHandler;
import com.solution.musiccollab.shared.model.Model;
import com.solution.musiccollab.shared.value.AudioFileDAO;
import com.solution.musiccollab.shared.value.UserDAO;

public class FilePage extends Composite implements IUpdatable, IDAOEditor {

	@UiField
	AudioFilesList relatedAudioFilesList;
	
	@UiField
	Label userNameLabel;
	
	@UiField
	Label titleLabel;
	
	@UiField
	Label backLabel;
	
	@UiField
	VerticalPanel richTextPanel;
	
	@UiField
	Button saveButton;
	
	private ArrayList<NavigationEventHandler> handlers = new ArrayList<NavigationEventHandler>();
	private ArrayList<DAOEventHandler> daoHandlers = new ArrayList<DAOEventHandler>();
	private AudioFileDAO audioFileDAO;
	private RichTextArea richTextArea = new RichTextArea();
	private RichTextToolbar richTextToolbar = new RichTextToolbar(richTextArea);
	
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
					handler.onMemberPageNavigation(new NavigationEvent(FilePage.this.audioFileDAO.getOwnerUserDAO()));
		        }
			}
		});
		
		richTextArea.setWidth("100%");
		richTextPanel.add(richTextToolbar);
		richTextPanel.add(richTextArea);		
		richTextPanel.setHeight("100%");
	}
	
	@Override
	public void update() {
		relatedAudioFilesList.title.setText("Related Files");
		relatedAudioFilesList.setVisible(relatedAudioFilesList.getSize() > 0);
		
	}
	
	public void setAudioDAO(AudioFileDAO audioFileDAO) {
		this.audioFileDAO = audioFileDAO;
		
		this.userNameLabel.setText(audioFileDAO.getOwnerUserDAO().getNickname());
		this.titleLabel.setText(audioFileDAO.getFileName());
		
		this.richTextArea.setHTML(audioFileDAO.getUserContent());
		
		saveButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				FilePage.this.audioFileDAO.setUserContent(richTextArea.getHTML());
				for(DAOEventHandler handler : daoHandlers) {
					handler.onSaveAudio(new DAOEvent(FilePage.this.audioFileDAO, FilePage.this));
		        }
			}
		});
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
    
    public void addDAOEventHandler(DAOEventHandler handler) {
		daoHandlers.add(handler);
    }

    public void removeDAOEventHandler(DAOEventHandler handler) {
    	daoHandlers.remove(handler);
    }

	@Override
	public void removeDAO(Object dao) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateDAO(Object dao) {
		// TODO Auto-generated method stub
		
	}
	
}
