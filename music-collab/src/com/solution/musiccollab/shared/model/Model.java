package com.solution.musiccollab.shared.model;

import com.allen_sauer.gwt.voices.client.Sound;
import com.solution.musiccollab.shared.value.UserDAO;
import com.solution.musiccollab.shared.view.AudioFileItemPanel;
import com.solution.musiccollab.shared.view.IAudioItemPanel;
import com.solution.musiccollab.shared.view.MixItemPanel;
import com.solution.musiccollab.shared.view.MixerPanel;

public class Model {
	public static UserDAO currentUser;
	public static Sound currentSound;
	public static IAudioItemPanel currentPlayingAudioFileItemPanel;
	public static MixerPanel currentPlayingMixerPanel;
	public static MixItemPanel currentPlayingMixItemPanel;
}

