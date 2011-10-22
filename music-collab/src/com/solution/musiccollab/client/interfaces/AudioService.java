package com.solution.musiccollab.client.interfaces;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.solution.musiccollab.shared.value.AudioFileDAO;
import com.solution.musiccollab.shared.value.MixDAO;
import com.solution.musiccollab.shared.value.UserDAO;

@RemoteServiceRelativePath("audio")
public interface AudioService extends RemoteService {
	List<AudioFileDAO> getAll();
	List<AudioFileDAO> getAudioByUser(UserDAO user);
	List<AudioFileDAO> getAudioFilesLimit(int limit);
	byte[] getAudioData(AudioFileDAO audioFileDAO);
	List<AudioFileDAO> getAudioFileList(MixDAO mixDAO);
	List<MixDAO> getAllMixes();

	AudioFileDAO saveAudioFile(AudioFileDAO audioFileDAO);
	MixDAO saveMix(MixDAO mixDAO);
	Boolean deleteAudioFile(AudioFileDAO audioFileDAO);
	Boolean deleteMix(MixDAO mixDAO);
}
