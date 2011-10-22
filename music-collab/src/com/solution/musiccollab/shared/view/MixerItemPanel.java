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

public class MixerItemPanel extends Composite implements IAudioItemPanel {
	
	private IAudioList parentList;
	private AudioFileDAO data;

	@UiField
	Label fileNameLabel;
	
	@UiTemplate("uibinder/MixerItemPanel.ui.xml")
	interface MyUiBinder extends UiBinder<Widget, MixerItemPanel> { }
	private static final MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	public MixerItemPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void setupPanel(IAudioList parent, AudioFileDAO audioFileDAO) {
		this.parentList = parent;
		this.data = audioFileDAO;
		
		fileNameLabel.setText(audioFileDAO.getFileName());
		
	}

	@Override
	public void setPlayStopStatus(boolean isPlaying) {
		// TODO Auto-generated method stub
		
	}
	
}
