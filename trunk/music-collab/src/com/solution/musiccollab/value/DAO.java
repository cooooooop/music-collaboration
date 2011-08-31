package com.solution.musiccollab.value;

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.DAOBase;

public class DAO extends DAOBase {
	
	static {
		try {
			ObjectifyService.register(AudioFile.class);
		}
		catch (Exception e) {
			e.printStackTrace(); 
		}
	}

    /** Your DAO can have your own useful methods */
    public AudioFile getOrCreateAudioFile(String fileName)
    {
    	AudioFile found = ofy().find(AudioFile.class, fileName);
        if (found == null)
            return new AudioFile(fileName);
        else
            return found;
    }
}
