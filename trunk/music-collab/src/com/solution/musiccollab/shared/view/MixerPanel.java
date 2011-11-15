package com.solution.musiccollab.shared.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.solution.musiccollab.shared.event.DAOEventHandler;
import com.solution.musiccollab.shared.event.DAOEvent;
import com.solution.musiccollab.shared.event.FileSelectEvent;
import com.solution.musiccollab.shared.event.FileSelectEventHandler;
import com.solution.musiccollab.shared.event.NavigationEvent;
import com.solution.musiccollab.shared.event.NavigationEventHandler;
import com.solution.musiccollab.shared.model.Model;
import com.solution.musiccollab.shared.value.AudioFileDAO;
import com.solution.musiccollab.shared.value.MixDAO;
import com.solution.musiccollab.shared.view.editor.MixEditor;

public class MixerPanel extends Composite implements IUpdatable, IDAOEditor {

	@UiField
	Label fileNameLabel;
	
	@UiField
	MixerList mixerList;
	
	@UiField
	AudioFilesList audioFilesList;
	
	@UiField
	MixEditor mixEditor;
	
	@UiField
	Label uploadDateLabel;
	
/*	@UiField
	CheckBox loopingCheckBox;*/
	
	@UiField
	Label fileOwnerLabel;
	
	@UiField
	Label downloadsLabel;
	
	@UiField
	Button downloadButton;

	@UiField
	Button playStopButton;

	@UiField
	Button saveButton;
	
	@UiField
	Button zoomInButton;

	@UiField
	Button zoomOutButton;
	
	private boolean playing = false;
	
	private MixDAO mixDAO;
	
	@UiTemplate("uibinder/MixerPanel.ui.xml")
	interface MyUiBinder extends UiBinder<Widget, MixerPanel> { }
	private static final MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	public MixerPanel() {
		initWidget(uiBinder.createAndBindUi(this));
		
		fileOwnerLabel.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				for(NavigationEventHandler handler : mixerList.getNavigationHandlers()) {
					handler.onMemberPageNavigation(new NavigationEvent(mixDAO.getOwnerUserDAO()));
		        }
			}
		});
	
		downloadButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				for(FileSelectEventHandler handler : mixerList.getHandlers()) {
					handler.onMixSelected(new FileSelectEvent(mixDAO, false, MixerPanel.this));
		        }
			}
		});
		
		saveButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				for(DAOEventHandler handler : mixerList.getDAOEventHandlers()) {
					handler.onSaveMix(new DAOEvent(mixDAO, MixerPanel.this));
		        }
			}
		});
		
		playStopButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if(playing) {
					for(FileSelectEventHandler handler : mixerList.getHandlers()) {
						handler.onMixStop(new FileSelectEvent(mixDAO, false, MixerPanel.this));
			        }
					
					setPlayStopStatus(playing = false);
				}
				else {
					for(FileSelectEventHandler handler : mixerList.getHandlers()) {
						handler.onMixPlay(new FileSelectEvent(mixDAO, false, MixerPanel.this));
			        }
					
					setPlayStopStatus(playing = true);
				}
			}
		});
		
		zoomInButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				mixEditor.increaseZoom();
			}
		});
		
		zoomOutButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				mixEditor.decreaseZoom();
			}
		});
	}
	
	public MixerList getMixerList() {
		return mixerList;
	}
	
	public IAudioList getAudioList() {
		return audioFilesList;
	}
	
	@Override
	public void update() {
//		mixerList.setVisible(mixerList.getSize() > 0);
		
		if(audioFilesList.getSize() > 0) {
			audioFilesList.setVisible(true);
			
			for(IAudioItemPanel panel : audioFilesList.getData()) {
				if(panel instanceof AudioFileLiteItemPanel)
					((AudioFileLiteItemPanel) panel).setMixerPanel(this);
			}
		}
		
		if(mixDAO == null)
			return;
		
		mixerList.setMixDAO(mixDAO);
		mixEditor.setMixDAO(mixDAO);
		
		fileNameLabel.setText(mixDAO.getMixName());

		if(mixDAO.getDownloads() == 1)
			downloadsLabel.setText(mixDAO.getDownloads() + " listen");
		else
			downloadsLabel.setText(mixDAO.getDownloads() + " listens");
		
		fileOwnerLabel.setText(mixDAO.getOwnerUserDAO().getNickname());
		
		DateTimeFormat fmt = DateTimeFormat.getFormat("MMM dd, yyyy 'at' HH:mm:ss a");
		uploadDateLabel.setText("created " + fmt.format(mixDAO.getCreateDate()));
		
		//use most restrictive license of all in mix
//		if(mixDAO.getAllowCommercialUse() != null && audioFileDAO.getAllowCommercialUse())
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

	}

	public MixDAO getMixDAO() {
		return mixDAO;
	}

	public void setMixDAO(MixDAO mixDAO) {
		this.mixDAO = mixDAO;
	}
	
	public void addToMix(AudioFileDAO audioFileDAO) {
		mixerList.addItem(mixDAO.addMixDetail(audioFileDAO));
		mixEditor.setMixDAO(mixDAO);
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
		if(dao instanceof AudioFileDAO) {
			for(NavigationEventHandler handler : mixerList.getNavigationHandlers()) {
				handler.onHomeNavigation(new NavigationEvent(Model.currentUser));
	        }
		}
	}

	@Override
	public void updateDAO(Object dao) {
		// TODO Auto-generated method stub
		
	}
	
}
