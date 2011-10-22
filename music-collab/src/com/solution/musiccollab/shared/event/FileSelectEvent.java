package com.solution.musiccollab.shared.event;

import com.solution.musiccollab.shared.value.AudioFileDAO;
import com.solution.musiccollab.shared.value.MixDAO;
import com.solution.musiccollab.shared.view.AudioFileItemPanel;
import com.solution.musiccollab.shared.view.IAudioItemPanel;
import com.solution.musiccollab.shared.view.MixItemPanel;
import com.solution.musiccollab.shared.view.MixerPanel;

public class FileSelectEvent {

    private final AudioFileDAO selectedFile;
    private final MixDAO selectedMix;
    private final boolean looping;
    private final IAudioItemPanel audioItemPanel;
    private final MixerPanel mixerPanel;
    private final MixItemPanel mixItemPanel;

    public FileSelectEvent(AudioFileDAO selectedFile, boolean looping, IAudioItemPanel audioItemPanel) {
		this.selectedFile = selectedFile;
		this.selectedMix = null;
		this.looping = looping;
		this.audioItemPanel = audioItemPanel;
		this.mixerPanel = null;
		this.mixItemPanel = null;
	}
    
    public FileSelectEvent(MixDAO selectedMixDAO, boolean looping, MixItemPanel mixItemPanel) {
		this.selectedFile = null;
		this.selectedMix = selectedMixDAO;
		this.looping = looping;
		this.audioItemPanel = null;
		this.mixerPanel = null;
		this.mixItemPanel = mixItemPanel;
	}
    
    public FileSelectEvent(MixDAO selectedMixDAO, boolean looping, MixerPanel mixerPanel) {
		this.selectedFile = null;
		this.selectedMix = selectedMixDAO;
		this.looping = looping;
		this.audioItemPanel = null;
		this.mixerPanel = mixerPanel;
		this.mixItemPanel = null;
	}
    
    public AudioFileDAO getSelectedFile() {
        return selectedFile;
    }

	public boolean isLooping() {
		return looping;
	}

	public MixDAO getSelectedMix() {
		return selectedMix;
	}

	public IAudioItemPanel getAudioItemPanel() {
		return audioItemPanel;
	}

	public MixerPanel getMixerPanel() {
		return mixerPanel;
	}

	public MixItemPanel getMixItemPanel() {
		return mixItemPanel;
	}

}
