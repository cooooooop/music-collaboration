package com.solution.musiccollab.server;

import java.util.ArrayList;
import java.util.List;

import com.solution.musiccollab.client.interfaces.AudioService;
import com.solution.musiccollab.server.audio.value.WAVSound;
import com.solution.musiccollab.shared.UUID;
import com.solution.musiccollab.shared.value.AudioFileDAO;
import com.solution.musiccollab.shared.value.MixDAO;
import com.solution.musiccollab.shared.value.MixDetails;
import com.solution.musiccollab.shared.value.UserDAO;
import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class AudioServiceImpl extends RemoteServiceServlet implements AudioService {

	private BlobstoreService blobService = BlobstoreServiceFactory.getBlobstoreService();
	private BlobInfoFactory blobInfoFactory = new BlobInfoFactory(DatastoreServiceFactory.getDatastoreService());
	
	public List<AudioFileDAO> getAudioByUser(UserDAO user) {
		DAO dao = new DAO();
		List<AudioFileDAO> rawList = dao.ofy().query(AudioFileDAO.class).filter("owner", user.getUserid()).list();
		attachTransientData(rawList);
		return rawList;
	}
	
	public List<AudioFileDAO> getAudioFilesLimit(int limit) {
		DAO dao = new DAO();
		List<AudioFileDAO> rawList = dao.ofy().query(AudioFileDAO.class).limit(limit).list();
		attachTransientData(rawList);
		return rawList;
	}

	@Override
	public List<AudioFileDAO> getAll(String contentType) {
		DAO dao = new DAO();
		List<AudioFileDAO> rawList;
		if(contentType != null)
			rawList = dao.ofy().query(AudioFileDAO.class).filter("contentType", contentType).list();
		else
			rawList = dao.ofy().query(AudioFileDAO.class).list();
		attachTransientData(rawList);
		return rawList;
	}
	
	private void attachTransientData(List<AudioFileDAO> rawList) {
		DAO dao = new DAO();
		for(AudioFileDAO audioFile : rawList) {
			audioFile.setOwnerUserDAO(dao.ofy().query(UserDAO.class).filter("userid", audioFile.getOwner()).get());
			WAVSound audioAsWAV = new WAVSound(getAudioData(audioFile));
			audioFile.setAudioLength(audioAsWAV.getLengthMillis());
		}
	}
	
	private void attachTransientDataMixDetails(List<MixDetails> rawList) {
		DAO dao = new DAO();
		for(MixDetails mixDetails : rawList) {
			AudioFileDAO audioFile = dao.ofy().query(AudioFileDAO.class).filter("filePath", mixDetails.getFilePath()).get();
			mixDetails.setAudioFile(audioFile);
			byte[] data = getAudioData(audioFile);
			
			int interval = data.length / 300;
			
			List<Byte> bytes = new ArrayList<Byte>();
			for(int i = 0; i < data.length; i += interval) {
				bytes.add(data[i]);
			}
			
			byte[] array = new byte[bytes.size()];
			for(int i = 0; i < bytes.size(); i++) {
				array[i] = bytes.get(i);
			}
			
			mixDetails.setData(array);
		}
	}
	
	private void attachTransientData(MixDAO mixDAO) {
		DAO dao = new DAO();
		mixDAO.setOwnerUserDAO(dao.ofy().query(UserDAO.class).filter("userid", mixDAO.getOwner()).get());
		mixDAO.setMixDetailsList(dao.ofy().query(MixDetails.class).filter("uniqueID in", mixDAO.getMixDetailsIDList()).list());
	}

	@Override
	public byte[] getAudioData(AudioFileDAO audioFileDAO) {
			
		BlobKey blobKey = new BlobKey(audioFileDAO.getFilePath());
		BlobInfo blobInfo =  blobInfoFactory.loadBlobInfo(blobKey);
		
		return fetchData(blobKey, blobInfo);
	}
	
	@Override
	public AudioFileDAO getAudioFile(String filePath) {
		DAO dao = new DAO();
		List<AudioFileDAO> audioFiles = dao.ofy().query(AudioFileDAO.class).filter("filePath", filePath).list();
		attachTransientData(audioFiles);
		return audioFiles.get(0);
	}
	
	@Override
	public List<MixDetails> getMixDetailsList(MixDAO mixDAO) {
		DAO dao = new DAO();
		
		List<MixDetails> rawList = dao.ofy().query(MixDetails.class).filter("uniqueID in", mixDAO.getMixDetailsIDList()).list();
		attachTransientDataMixDetails(rawList);
		return rawList;
	}

	@Override
	public AudioFileDAO saveAudioFile(AudioFileDAO audioFileDAO) {
		DAO dao = new DAO();
		dao.ofy().put(audioFileDAO);
		
		return audioFileDAO;
	}
	
	@Override
	public MixDAO saveMix(MixDAO mixDAO) {
		DAO dao = new DAO();
		
		if(mixDAO.getUniqueID() == null)
			mixDAO.setUniqueID(UUID.uuid());
			
		dao.ofy().put(mixDAO);
		
		for(MixDetails mixDetails : mixDAO.getMixDetailsList()) {
			if(mixDetails.delete())
				deleteMixDetails(mixDetails);
			else
				saveMixDetails(mixDetails);
		}
		
		attachTransientData(mixDAO);
		
		return mixDAO;
	}
	
	private MixDetails saveMixDetails(MixDetails mixDetails) {
		DAO dao = new DAO();
		
		dao.ofy().put(mixDetails);
		
		return mixDetails;
	}

	@Override
	public List<MixDAO> getAllMixes() {
		DAO dao = new DAO();
		List<MixDAO> rawList = dao.ofy().query(MixDAO.class).list();
		for(MixDAO mixDAO : rawList) {
			attachTransientData(mixDAO);
		}
		return rawList;
	}

	@Override
	public Boolean deleteAudioFile(AudioFileDAO audioFileDAO) {
		DAO dao = new DAO();
		BlobKey blobKey = new BlobKey(audioFileDAO.getFilePath());
		blobService.delete(blobKey);
		dao.ofy().delete(audioFileDAO);
		return true;
	}

	@Override
	public Boolean deleteMix(MixDAO mixDAO) {
		DAO dao = new DAO();
		dao.ofy().delete(mixDAO);
		return true;
	}
	
	private Boolean deleteMixDetails(MixDetails mixDetails) {
		DAO dao = new DAO();
		dao.ofy().delete(mixDetails);
		return true;
	}
	
	public byte[] fetchData(BlobKey blobKey, BlobInfo blobInfo) {
    	int halfMeg = 524288;
    	byte[] bytes = new byte[(int)blobInfo.getSize()];
    	
    	int i = 1;
    	for(i = 1; i * halfMeg < blobInfo.getSize(); i++) {
    		byte[] fetched = blobService.fetchData(blobKey, (i - 1) * halfMeg, i * halfMeg - 1);
    		
    		for(int j = 0; j < fetched.length; j++) {
    			bytes[j + (i - 1) * halfMeg] = fetched[j];
    		}
    	}
    	
    	byte[] fetched = blobService.fetchData(blobKey, (i - 1) * halfMeg, i * halfMeg - 1);
		
		for(int j = 0; j < fetched.length; j++) {
			bytes[j + (i - 1) * halfMeg] = fetched[j];
		}
		
		return bytes;

    }
}
