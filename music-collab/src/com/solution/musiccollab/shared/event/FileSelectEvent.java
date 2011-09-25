package com.solution.musiccollab.shared.event;

import com.solution.musiccollab.shared.value.AudioFileDAO;

public class FileSelectEvent {

    private final AudioFileDAO selectedFile;

    public FileSelectEvent(AudioFileDAO selectedFile) {
		this.selectedFile = selectedFile;
	}

    public AudioFileDAO getSelectedFile() {
        return selectedFile;
    }
}
