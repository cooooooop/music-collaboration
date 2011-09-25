package com.solution.musiccollab.client.interfaces;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.solution.musiccollab.shared.value.AudioFileDAO;
import com.solution.musiccollab.shared.value.UserDAO;

@RemoteServiceRelativePath("audio")
public interface AudioService extends RemoteService {
	List<AudioFileDAO> getAll();
	List<AudioFileDAO> getAudioByUser(UserDAO user);
	List<AudioFileDAO> getAudioFilesLimit(int limit);
}
