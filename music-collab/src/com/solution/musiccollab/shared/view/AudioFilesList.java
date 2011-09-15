package com.solution.musiccollab.shared.view;

import java.util.List;

import com.google.gwt.user.client.ui.ListBox;
import com.solution.musiccollab.shared.value.AudioFileDAO;
import com.solution.musiccollab.shared.value.UserDAO;

public class AudioFilesList extends ListBox {
	
	protected List<AudioFileDAO> data;
	
	public AudioFilesList() {
	    setVisibleItemCount(4);
	}
	
	public void addItems(List<AudioFileDAO> audioFiles) {
		for (AudioFileDAO audioFile : audioFiles) {
			addItem(audioFile.getFileName());
		}
		
		this.data = audioFiles;
	}
	
	public AudioFileDAO getSelected() {
		return data.get(getSelectedIndex());
	}
	
}
