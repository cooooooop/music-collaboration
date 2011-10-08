package com.solution.musiccollab.shared.event;

import com.solution.musiccollab.shared.value.AudioFileDAO;
import com.solution.musiccollab.shared.value.UserDAO;

public class NavigationEvent {

    private final UserDAO userDAO;
    private final AudioFileDAO audioFileDAO;

    public NavigationEvent(UserDAO userDAO) {
		this.userDAO = userDAO;
		this.audioFileDAO = null;
	}
    
    public NavigationEvent(AudioFileDAO audioFileDAO) {
		this.userDAO = audioFileDAO.getOwnerUserDAO();
		this.audioFileDAO = audioFileDAO;
	}

    public UserDAO getUserDAO() {
        return userDAO;
    }

	public AudioFileDAO getAudioFileDAO() {
		return audioFileDAO;
	}
}
