package com.solution.musiccollab.server;

import java.io.BufferedOutputStream;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.allen_sauer.gwt.voices.client.Sound;
import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.solution.musiccollab.server.audio.AudioHelper;
import com.solution.musiccollab.shared.model.Model;
import com.solution.musiccollab.shared.value.AudioFileDAO;

public class DownloadFileServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private BlobstoreService blobService = BlobstoreServiceFactory.getBlobstoreService();
	private BlobInfoFactory blobInfoFactory = new BlobInfoFactory(DatastoreServiceFactory.getDatastoreService());

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
    	
        String filePath = request.getParameter("filePath");
        String action = request.getParameter("action");
        String userid = request.getParameter("userid");
        
        DAO dao = new DAO();
        
        try {
			AudioFileDAO audioFileDAO = dao.ofy().query(AudioFileDAO.class).filter("filePath", filePath).get();
			if(userid != null)
				audioFileDAO.addDownload(userid);
			
			dao.ofy().put(audioFileDAO);
			
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
        catch (Exception e) {
        	throw new ServletException(e);
        }
	        
    }
}
