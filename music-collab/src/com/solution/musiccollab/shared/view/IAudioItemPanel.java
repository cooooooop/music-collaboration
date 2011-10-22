package com.solution.musiccollab.shared.view;

import com.solution.musiccollab.shared.value.AudioFileDAO;

public interface IAudioItemPanel {
	void setupPanel(IAudioList audioList, AudioFileDAO audioFileDAO);
	void setPlayStopStatus(boolean isPlaying);
}
