package com.solution.musiccollab.shared.event;

import com.solution.musiccollab.shared.value.AudioFileDAO;
import com.solution.musiccollab.shared.value.MixDAO;
import com.solution.musiccollab.shared.value.UserDAO;

public class NavigationEvent {

    private final UserDAO userDAO;
    private final AudioFileDAO audioFileDAO;
    private final MixDAO mixDAO;

    public NavigationEvent(UserDAO userDAO) {
		this.userDAO = userDAO;
		this.audioFileDAO = null;
		this.mixDAO = null;
	}
    
    public NavigationEvent(AudioFileDAO audioFileDAO) {
		this.userDAO = audioFileDAO.getOwnerUserDAO();
		this.audioFileDAO = audioFileDAO;
		this.mixDAO = null;
	}
    
    public NavigationEvent(MixDAO mixDAO) {
		this.userDAO = null;
		this.audioFileDAO = null;
		this.mixDAO = mixDAO;
	}

    public UserDAO getUserDAO() {
        return userDAO;
    }

	public AudioFileDAO getAudioFileDAO() {
		return audioFileDAO;
	}

	public MixDAO getMixDAO() {
		return mixDAO;
	}

}
