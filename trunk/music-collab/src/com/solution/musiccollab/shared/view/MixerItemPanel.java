package com.solution.musiccollab.shared.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
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
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.solution.musiccollab.shared.event.FileSelectEvent;
import com.solution.musiccollab.shared.event.FileSelectEventHandler;
import com.solution.musiccollab.shared.event.NavigationEvent;
import com.solution.musiccollab.shared.event.NavigationEventHandler;
import com.solution.musiccollab.shared.value.AudioFileDAO;
import com.solution.musiccollab.shared.value.MixDAO;
import com.solution.musiccollab.shared.value.MixDetails;

public class MixerItemPanel extends Composite {
	
	private MixerList parentList;
	private AudioFileDAO data;

	@UiField
	Label fileNameLabel;
	
	@UiField
	TextBox startTimeTextBox;

	@UiField
	TextBox trimStartTimeTextBox;

	@UiField
	TextBox trimEndTimeTextBox;
	
	@UiTemplate("uibinder/MixerItemPanel.ui.xml")
	interface MyUiBinder extends UiBinder<Widget, MixerItemPanel> { }
	private static final MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	public MixerItemPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void setupPanel(MixerList parent, final MixDetails mixDetails) {
		this.parentList = parent;
		this.data = mixDetails.getAudioFile();
		
		fileNameLabel.setText(data.getFileName());
		
		startTimeTextBox.setText(String.valueOf(mixDetails.getStartTime()));
		trimStartTimeTextBox.setText(String.valueOf(mixDetails.getTrimStartTime()));
		trimEndTimeTextBox.setText(String.valueOf(mixDetails.getTrimEndTime()));
		
		startTimeTextBox.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				mixDetails.setStartTime(Long.valueOf(startTimeTextBox.getText()));
			}
		});
		
		trimStartTimeTextBox.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				mixDetails.setTrimStartTime(Long.valueOf(trimStartTimeTextBox.getText()));
			}
		});

		trimEndTimeTextBox.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				mixDetails.setTrimEndTime(Long.valueOf(trimEndTimeTextBox.getText()));
			}
		});
	}
	
}
