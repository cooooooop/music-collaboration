package com.solution.musiccollab.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.files.AppEngineFile;
import com.google.appengine.api.files.FileService;
import com.google.appengine.api.files.FileServiceFactory;
import com.google.appengine.api.files.FileWriteChannel;
import com.solution.musiccollab.server.audio.AudioUtil;
import com.solution.musiccollab.shared.value.AudioFileDAO;
import com.solution.musiccollab.shared.value.MixDAO;
import com.solution.musiccollab.shared.value.MixDetails;

public class DownloadFileServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private BlobstoreService blobService = BlobstoreServiceFactory.getBlobstoreService();
	private BlobInfoFactory blobInfoFactory = new BlobInfoFactory(DatastoreServiceFactory.getDatastoreService());
	private AudioServiceImpl audioService = new AudioServiceImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
    	
        String audioid = request.getParameter("audioid"); //filePath for AudioFile, uniqueID for Mix
        String action = request.getParameter("action");
        String userid = request.getParameter("userid");
        
        DAO dao = new DAO();
        
        try {
			AudioFileDAO audioFileDAO = dao.ofy().query(AudioFileDAO.class).filter("filePath", audioid).get();
			MixDAO mixDAO = dao.ofy().query(MixDAO.class).filter("uniqueID", audioid).get();

			if(audioFileDAO != null) {
				if(userid != null) {
					audioFileDAO.addDownload(userid);
					dao.ofy().put(audioFileDAO);
				}

				BlobKey blobKey = new BlobKey(audioFileDAO.getFilePath());
				BlobInfo blobInfo =  blobInfoFactory.loadBlobInfo(blobKey);
				
				if(action != null && action.equals("download")) {
					response.setHeader("Content-Type", audioFileDAO.getContentType());
					response.setHeader("Content-Length", String.valueOf(blobInfo.getSize()));
					response.setHeader("Content-disposition", "attachment;filename=\"" + blobInfo.getFilename() + "\"");
				}
				
	//			byte[] data = blobService.fetchData(blobKey, 0, blobInfo.getSize());
	//			
	//			data = AudioHelper.loopSample(data, 3);
	//			
	//			bos.write(data);
	//	        
	//	        bos.flush();
	//	        bos.close();
				blobService.serve(blobKey, response);
			}
			else if(mixDAO != null) {
				//try to use laoe library - currently generates null pointer
//				AClip clip = new AClip();
//				Audio audio = clip.getAudio();
				
				mixDAO.setMixDetailsList(audioService.getMixDetailsList(mixDAO));
				//assuming merge for now
				if(userid != null) {
					mixDAO.addDownload(userid);
					dao.ofy().put(mixDAO);
				}
				
				List<byte[]> dataList = new ArrayList<byte[]>();
				for(MixDetails mixDetails : mixDAO.getMixDetailsList()) {
					BlobKey blobKey = new BlobKey(mixDetails.getFilePath());
					BlobInfo blobInfo =  blobInfoFactory.loadBlobInfo(blobKey);
					byte[] data = fetchData(blobKey, blobInfo);
					dataList.add(data);
				}
				
				response.setHeader("Content-Type", mixDAO.getContentType());
				//TODO: find out content extension
				response.setHeader("Content-disposition", "attachment;filename=\"" + mixDAO.getMixName() + ".wav\"");
				
				//temporary logic
				if(dataList.size() == 1) {
					bos.write(dataList.get(0));
				}
				else if(dataList.size() == 2) {
					bos.write(AudioUtil.merge(dataList.get(0), dataList.get(1), mixDAO.getContentType()));
				}
				
				bos.flush();
		        bos.close();
				
			}
        }
        catch (Exception e) {
        	throw new ServletException(e);
        }
    }
    
    private byte[] fetchData(BlobKey blobKey, BlobInfo blobInfo) {
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
    
    public BlobKey putInBlobStoreString(String fileName, String contentType, byte[] filebytes) throws IOException {
        // Get a file service
        FileService fileService = FileServiceFactory.getFileService();
        AppEngineFile file = fileService.createNewBlobFile(contentType, fileName);
        // Open a channel to write to it
        boolean lock = true;
        FileWriteChannel writeChannel = null;
        writeChannel = fileService.openWriteChannel(file, lock);
        // lets buffer the bitch
        BufferedInputStream in = new BufferedInputStream(new ByteArrayInputStream(filebytes));
        byte[] buffer = new byte[524288]; // 0.5 MB buffers
        int read;
        while( (read = in.read(buffer)) > 0 ){ //-1 means EndOfStream
            ByteBuffer bb = ByteBuffer.wrap(buffer);
            writeChannel.write(bb);
        }
        writeChannel.closeFinally();
        return fileService.getBlobKey(file);
    }
}

