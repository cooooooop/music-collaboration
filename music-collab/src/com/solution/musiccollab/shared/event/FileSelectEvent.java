package com.solution.musiccollab.shared.event;

import com.solution.musiccollab.shared.value.AudioFileDAO;
import com.solution.musiccollab.shared.view.AudioFileItemPanel;

public class FileSelectEvent {

    private final AudioFileDAO selectedFile;
    private final boolean looping;
    private final AudioFileItemPanel originator;

    public FileSelectEvent(AudioFileDAO selectedFile, boolean looping, AudioFileItemPanel originator) {
		this.selectedFile = selectedFile;
		this.looping = looping;
		this.originator = originator;
	}

    public AudioFileDAO getSelectedFile() {
        return selectedFile;
    }

	public boolean isLooping() {
		return looping;
	}

	public AudioFileItemPanel getOriginator() {
		return originator;
	}
}
