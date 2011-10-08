package com.solution.musiccollab.shared.event;

import com.solution.musiccollab.shared.value.AudioFileDAO;
import com.solution.musiccollab.shared.value.MixDAO;
import com.solution.musiccollab.shared.view.AudioFileItemPanel;

public class FileSelectEvent {

    private final AudioFileDAO selectedFile;
    private final String selectedMix;
    private final boolean looping;
    private final AudioFileItemPanel originator;

    public FileSelectEvent(AudioFileDAO selectedFile, boolean looping, AudioFileItemPanel originator) {
		this.selectedFile = selectedFile;
		this.selectedMix = null;
		this.looping = looping;
		this.originator = originator;
	}

    public FileSelectEvent(String selectedMix, boolean looping, AudioFileItemPanel originator) {
		this.selectedMix = selectedMix;
		this.selectedFile = null;
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

	public String getSelectedMix() {
		return selectedMix;
	}
}
