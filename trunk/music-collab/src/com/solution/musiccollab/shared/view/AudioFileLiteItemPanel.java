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
import com.solution.musiccollab.shared.event.FileSelectEvent;
import com.solution.musiccollab.shared.event.FileSelectEventHandler;
import com.solution.musiccollab.shared.event.NavigationEvent;
import com.solution.musiccollab.shared.event.NavigationEventHandler;
import com.solution.musiccollab.shared.value.AudioFileDAO;

public class AudioFileLiteItemPanel extends Composite implements IAudioItemPanel {
	
	private IAudioList parentList;
	private AudioFileDAO data;
	private MixerPanel mixerPanel;

	@UiField
	Label titleLabel;
	
	@UiField
	HTML commercialUseHTML;
	
	@UiField
	Label fileOwnerLabel;
	
	@UiField
	Button downloadButton;

	@UiField
	Button playStopButton;

	@UiField
	Button addToMixButton;

	private boolean playing = false;
	
	@UiTemplate("uibinder/AudioFileLiteItemPanel.ui.xml")
	interface MyUiBinder extends UiBinder<Widget, AudioFileLiteItemPanel> { }
	private static final MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	public AudioFileLiteItemPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void setupPanel(IAudioList parent, AudioFileDAO audioFileDAO) {
		this.parentList = parent;
		this.data = audioFileDAO;
		
		titleLabel.setText(audioFileDAO.getFileName());
		titleLabel.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				for(NavigationEventHandler handler : parentList.getNavigationHandlers()) {
					handler.onFilePageNavigation(new NavigationEvent(data));
		        }
			}
		});
		
		fileOwnerLabel.setText(audioFileDAO.getOwnerUserDAO().getNickname());
		
		fileOwnerLabel.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				for(NavigationEventHandler handler : parentList.getNavigationHandlers()) {
					handler.onMemberPageNavigation(new NavigationEvent(data));
		        }
			}
		});
		
		if(audioFileDAO.getAllowCommercialUse() != null && audioFileDAO.getAllowCommercialUse())
			commercialUseHTML.setHTML(new SafeHtml() {
				
				@Override
				public String asString() {
					return "<a rel=\"license\" href=\"http://creativecommons.org/licenses/by/3.0/\" target=\"_blank\"><img alt=\"Creative Commons License\" style=\"border-width:0\" src=\"http://i.creativecommons.org/l/by/3.0/80x15.png\" /></a";
				}
			});
		else
			commercialUseHTML.setHTML(new SafeHtml() {
				
				@Override
				public String asString() {
					return "<a rel=\"license\" href=\"http://creativecommons.org/licenses/by-nc/3.0/\" target=\"_blank\"><img alt=\"Creative Commons License\" style=\"border-width:0\" src=\"http://i.creativecommons.org/l/by-nc/3.0/80x15.png\" /></a";
				}
			});

		downloadButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				for(FileSelectEventHandler handler : parentList.getHandlers()) {
					handler.onFileSelected(new FileSelectEvent(data, false, AudioFileLiteItemPanel.this));
		        }
			}
		});
		
		playStopButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if(playing) {
					for(FileSelectEventHandler handler : parentList.getHandlers()) {
						handler.onFileStop(new FileSelectEvent(data, false, AudioFileLiteItemPanel.this));
			        }
					
					setPlayStopStatus(playing = false);
				}
				else {
					for(FileSelectEventHandler handler : parentList.getHandlers()) {
						handler.onFilePlay(new FileSelectEvent(data, false, AudioFileLiteItemPanel.this));
			        }
					
					setPlayStopStatus(playing = true);
				}
			}
		});
		
		addToMixButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if(mixerPanel != null)
					mixerPanel.addToMix(data);
			}
		});
		
	}
	
	public void setPlayStopStatus(boolean playing) {
		this.playing = playing;
		if(this.playing)
			playStopButton.setText("Stop");
		else
			playStopButton.setText("Play");
	}

	public MixerPanel getMixerPanel() {
		return mixerPanel;
	}

	public void setMixerPanel(MixerPanel mixerPanel) {
		this.mixerPanel = mixerPanel;
	}
	
	@Override
	public AudioFileDAO getData() {
		return data;
	}
}
