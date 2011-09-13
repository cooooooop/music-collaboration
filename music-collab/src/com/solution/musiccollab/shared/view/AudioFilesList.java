package com.solution.musiccollab.shared.view;

import java.util.List;

import com.google.gwt.user.client.ui.ListBox;
import com.solution.musiccollab.shared.value.AudioFileDAO;

public class AudioFilesList extends ListBox {
	
	public AudioFilesList() {
	    setVisibleItemCount(4);
	}
	
	public void addItems(List<AudioFileDAO> audioFiles) {
		for (AudioFileDAO audioFile : audioFiles) {
			addItem(audioFile.getFileName());
		}
	}
	
}
