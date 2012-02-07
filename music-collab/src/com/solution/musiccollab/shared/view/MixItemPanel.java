package com.solution.musiccollab.shared.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Widget;
import com.solution.musiccollab.shared.event.DAOEvent;
import com.solution.musiccollab.shared.event.DAOEventHandler;
import com.solution.musiccollab.shared.event.FileSelectEvent;
import com.solution.musiccollab.shared.event.FileSelectEventHandler;
import com.solution.musiccollab.shared.event.NavigationEvent;
import com.solution.musiccollab.shared.event.NavigationEventHandler;
import com.solution.musiccollab.shared.model.Model;
import com.solution.musiccollab.shared.value.AudioFileDAO;
import com.solution.musiccollab.shared.value.MixDAO;

public class MixItemPanel extends Composite implements IDAOEditor {
	
	private MixesList parentList;
	private MixDAO data;

	@UiField
	Label fileNameLabel;
	
	@UiField
	Label uploadDateLabel;
	
/*	@UiField
	CheckBox loopingCheckBox;*/
	
	@UiField
	HTML commercialUseHTML;
	
	@UiField
	Label fileOwnerLabel;
	
	@UiField
	Label downloadsLabel;
	
	@UiField
	Button downloadButton;

	@UiField
	Button playStopButton;

	@UiField
	Button jamButton;

	@UiField
	Button deleteButton;
	
	private boolean playing = false;
	
	@UiTemplate("uibinder/MixItemPanel.ui.xml")
	interface MyUiBinder extends UiBinder<Widget, MixItemPanel> { }
	private static final MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	public MixItemPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void setupPanel(MixesList parent, MixDAO mixDAO) {
		this.parentList = parent;
		this.data = mixDAO;
		
		fileNameLabel.setText(mixDAO.getMixName());
		if(mixDAO.getDownloads() == 1)
			downloadsLabel.setText(mixDAO.getDownloads() + " listen");
		else
			downloadsLabel.setText(mixDAO.getDownloads() + " listens");
		
		fileOwnerLabel.setText(mixDAO.getOwnerUserDAO().getNickname());
		
		DateTimeFormat fmt = DateTimeFormat.getFormat("MMM dd, yyyy 'at' HH:mm:ss a");
		uploadDateLabel.setText("created " + fmt.format(mixDAO.getCreateDate()));
		
		fileOwnerLabel.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				for(NavigationEventHandler handler : parentList.getNavigationHandlers()) {
					handler.onMemberPageNavigation(new NavigationEvent(data.getOwnerUserDAO()));
		        }
			}
		});
		
		fileNameLabel.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				for(NavigationEventHandler handler : parentList.getNavigationHandlers()) {
					handler.onMixPageNavigation(new NavigationEvent(data));
		        }
			}
		});

		//TODO: use most restrictive license of all related audiofiles
//		if(audioFileDAO.getAllowCommercialUse() != null && audioFileDAO.getAllowCommercialUse())
//			commercialUseHTML.setHTML(new SafeHtml() {
//				
//				@Override
//				public String asString() {
//					return "<a rel=\"license\" href=\"http://creativecommons.org/licenses/by/3.0/\" target=\"_blank\"><img alt=\"Creative Commons License\" style=\"border-width:0\" src=\"http://i.creativecommons.org/l/by/3.0/80x15.png\" /></a";
//				}
//			});
//		else
//			commercialUseHTML.setHTML(new SafeHtml() {
//				
//				@Override
//				public String asString() {
//					return "<a rel=\"license\" href=\"http://creativecommons.org/licenses/by-nc/3.0/\" target=\"_blank\"><img alt=\"Creative Commons License\" style=\"border-width:0\" src=\"http://i.creativecommons.org/l/by-nc/3.0/80x15.png\" /></a";
//				}
//			});

		downloadButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				for(FileSelectEventHandler handler : parentList.getHandlers()) {
					handler.onMixSelected(new FileSelectEvent(data, false, MixItemPanel.this));
		        }
			}
		});
		
		playStopButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if(playing) {
					for(FileSelectEventHandler handler : parentList.getHandlers()) {
						handler.onMixStop(new FileSelectEvent(data, false, MixItemPanel.this));
			        }
					
					setPlayStopStatus(playing = false);
				}
				else {
					for(FileSelectEventHandler handler : parentList.getHandlers()) {
						handler.onMixPlay(new FileSelectEvent(data, false, MixItemPanel.this));
			        }
					
					setPlayStopStatus(playing = true);
				}
			}
		});
		
		jamButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				for(NavigationEventHandler handler : parentList.getNavigationHandlers()) {
					handler.onMixerNavigation(new NavigationEvent(data));
		        }
			}
		});
		
		if(Model.currentUser == null || !Model.currentUser.getUserid().equals(data.getOwner())) {
			deleteButton.setVisible(false);
		}
		else {
			deleteButton.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					for(DAOEventHandler handler : parentList.getDAOEventHandlers()) {
						handler.onDeleteMix(new DAOEvent(data, MixItemPanel.this));
					}
				}
			});
		}
	}
	
	public void setPlayStopStatus(boolean playing) {
		this.playing = playing;
		if(this.playing)
			playStopButton.setText("Stop");
		else
			playStopButton.setText("Play");
	}

	@Override
	public void removeDAO(Object dao) {
		if(dao instanceof MixDAO) {
			parentList.removeItem((MixDAO) dao, MixItemPanel.this); 
		}
	}

	@Override
	public void updateDAO(Object dao) {
		// TODO Auto-generated method stub
		
	}
	
}
