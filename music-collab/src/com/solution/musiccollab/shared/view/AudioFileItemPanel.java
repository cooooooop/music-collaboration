package com.solution.musiccollab.shared.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.solution.musiccollab.shared.event.FileSelectEvent;
import com.solution.musiccollab.shared.event.FileSelectEventHandler;
import com.solution.musiccollab.shared.value.AudioFileDAO;

public class AudioFileItemPanel extends Composite {
	
	private AudioFilesList parentList;
	private AudioFileDAO data;
	
	@UiField
	Label fileNameLabel;
	
	@UiField
	HTML commercialUseHTML;
	
	@UiField
	Label fileOwnerLabel;
	
	@UiField
	Button downloadButton;
	
	@UiField
	Button playButton;
	
	@UiTemplate("uibinder/AudioFileItemPanel.ui.xml")
	interface MyUiBinder extends UiBinder<Widget, AudioFileItemPanel> { }
	private static final MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	public AudioFileItemPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void setupPanel(AudioFilesList parent, AudioFileDAO audioFileDAO) {
		this.parentList = parent;
		this.data = audioFileDAO;
		
		fileNameLabel.setText(audioFileDAO.getFileName());
		
		if(audioFileDAO.getOwnerUserDAO() == null)
			fileOwnerLabel.setText(audioFileDAO.getOwner());
		else
			fileOwnerLabel.setText(audioFileDAO.getOwnerUserDAO().getNickname());
		
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
					handler.onFileSelected(new FileSelectEvent(data));
		        }
			}
		});
		
		playButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				for(FileSelectEventHandler handler : parentList.getHandlers()) {
					handler.onFilePlay(new FileSelectEvent(data));
		        }
			}
		});
	}
	
}
