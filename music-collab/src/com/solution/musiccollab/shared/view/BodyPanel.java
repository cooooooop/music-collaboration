package com.solution.musiccollab.shared.view;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.solution.musiccollab.shared.value.AudioFileDAO;
import com.solution.musiccollab.shared.value.UserDAO;

public class BodyPanel extends Composite implements IUpdatable{

	@UiField
	UsersList usersList;
	
	@UiField
	AudioFilesList audioFilesList;
	
	@UiTemplate("uibinder/BodyPanel.ui.xml")
	interface MyUiBinder extends UiBinder<Widget, BodyPanel> { }
	private static final MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	public BodyPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@Override
	public void update() {
		usersList.setVisible(usersList.getSize() > 0);
		audioFilesList.setVisible(audioFilesList.getSize() > 0);
	}

	public UsersList getUsersList() {
		return usersList;
	}
	
	public AudioFilesList getAudioFilesList() {
		return audioFilesList;
	}
	
}
