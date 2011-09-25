package com.solution.musiccollab.client.interfaces;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.solution.musiccollab.shared.value.AudioFileDAO;
import com.solution.musiccollab.shared.value.UserDAO;

public interface AudioServiceAsync {
	void getAll(AsyncCallback<List<AudioFileDAO>> callback); 
	void getAudioByUser(UserDAO user, AsyncCallback<List<AudioFileDAO>> callback);
	void getAudioFilesLimit(int limit, AsyncCallback<List<AudioFileDAO>> callback); 
}
