package com.solution.musiccollab.client.interfaces;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.solution.musiccollab.shared.value.AudioFileDAO;
import com.solution.musiccollab.shared.value.MixDAO;
import com.solution.musiccollab.shared.value.MixDetails;
import com.solution.musiccollab.shared.value.UserDAO;

public interface AudioServiceAsync {
	void getAll(AsyncCallback<List<AudioFileDAO>> callback); 
	void getAudioByUser(UserDAO user, AsyncCallback<List<AudioFileDAO>> callback);
	void getAudioFilesLimit(int limit, AsyncCallback<List<AudioFileDAO>> callback);
	void getAudioData(AudioFileDAO audioFileDAO, AsyncCallback<byte[]> callback);
	void getMixDetailsList(MixDAO mixDAO, AsyncCallback<List<MixDetails>> callback);
	void getAllMixes(AsyncCallback<List<MixDAO>> callback);
	
	void saveAudioFile(AudioFileDAO audioFileDAO, AsyncCallback<AudioFileDAO> callback);
	void saveMix(MixDAO mixDAO, AsyncCallback<MixDAO> callback);
	void deleteAudioFile(AudioFileDAO audioFileDAO, AsyncCallback<Boolean> callback);
	void deleteMix(MixDAO mixDAO, AsyncCallback<Boolean> callback);
}
