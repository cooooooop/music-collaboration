package com.solution.musiccollab.server;

import java.util.Date;

import com.google.appengine.api.users.User;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.DAOBase;
import com.solution.musiccollab.shared.value.AudioFileDAO;
import com.solution.musiccollab.shared.value.MixDAO;
import com.solution.musiccollab.shared.value.UserDAO;

public class DAO extends DAOBase {
	
	static {
		try {
			ObjectifyService.register(AudioFileDAO.class);
			ObjectifyService.register(UserDAO.class);
			ObjectifyService.register(MixDAO.class);
		}
		catch (Exception e) {
			e.printStackTrace(); 
		}
	}

    /** Your DAO can have your own useful methods */
    public AudioFileDAO getOrCreateAudioFile(String filePath)
    {
    	AudioFileDAO found = ofy().find(AudioFileDAO.class, filePath);
        if (found == null)
            return new AudioFileDAO(filePath);
        else
            return found;
    }
    
    public UserDAO getOrCreateUser(User user)
    {
    	UserDAO found = ofy().find(UserDAO.class, user.getUserId());
        if (found == null)
        	found = new UserDAO(user.getUserId());
        
    	found.setEmail(user.getEmail());
    	found.setNickname(user.getNickname());
    	found.setLastLogin(new Date());
    	return found;
    }
    
    public MixDAO getOrCreateMix(String mixName)
    {
    	MixDAO found = ofy().find(MixDAO.class, mixName);
        if (found == null)
            return new MixDAO(mixName);
        else
            return found;
    }
}
