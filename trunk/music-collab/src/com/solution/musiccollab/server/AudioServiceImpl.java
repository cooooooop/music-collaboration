package com.solution.musiccollab.server;

import java.util.List;

import com.solution.musiccollab.client.interfaces.AudioService;
import com.solution.musiccollab.shared.value.AudioFileDAO;
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
	public List<AudioFileDAO> getAll() {
		DAO dao = new DAO();
		List<AudioFileDAO> rawList = dao.ofy().query(AudioFileDAO.class).list();
		attachTransientData(rawList);
		return rawList;
	}
	
	private void attachTransientData(List<AudioFileDAO> rawList) {
		DAO dao = new DAO();
		for(AudioFileDAO audioFile : rawList) {
			audioFile.setOwnerUserDAO(dao.ofy().query(UserDAO.class).filter("userid", audioFile.getOwner()).get());
		}
	}

	@Override
	public byte[] getAudioData(AudioFileDAO audioFileDAO) {
			
		BlobKey blobKey = new BlobKey(audioFileDAO.getFilePath());
		BlobInfo blobInfo =  blobInfoFactory.loadBlobInfo(blobKey);
		
		return blobService.fetchData(blobKey, 0, blobInfo.getSize());
	}
	
}
