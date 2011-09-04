package com.solution.musiccollab.server;

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.DAOBase;
import com.solution.musiccollab.shared.value.AudioFileDAO;

public class DAO extends DAOBase {
	
	static {
		try {
			ObjectifyService.register(AudioFileDAO.class);
		}
		catch (Exception e) {
			e.printStackTrace(); 
		}
	}

    /** Your DAO can have your own useful methods */
    public AudioFileDAO getOrCreateAudioFile(String fileName)
    {
    	AudioFileDAO found = ofy().find(AudioFileDAO.class, fileName);
        if (found == null)
            return new AudioFileDAO(fileName);
        else
            return found;
    }
}
