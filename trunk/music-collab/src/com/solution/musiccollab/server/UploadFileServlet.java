package com.solution.musiccollab.server;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

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
import com.solution.musiccollab.server.audio.AudioUtil;
import com.solution.musiccollab.shared.value.AudioFileDAO;

public class UploadFileServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private BlobstoreService blobService = BlobstoreServiceFactory.getBlobstoreService();
	private BlobInfoFactory blobInfoFactory = new BlobInfoFactory(DatastoreServiceFactory.getDatastoreService());
    private DAO dao = new DAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	resp.getWriter().write(blobService.createUploadUrl("/uploadFile?userid=" + req.getParameter("userid")));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //TODO: check contentType of upload, make sure it is valid audio. store contentType for later download
    	
    	//TODO: preview action currently stores unused blob, either delete after use or reconfigure the 
    	//upload JSP to upload data straight to this servlet without the blobstore intermediary
	    
    	try {
	    	
    		Map<String, BlobKey> blobs = blobService.getUploadedBlobs(req);
	        BlobKey blobKey = blobs.get(blobs.keySet().iterator().next());
	
	        String filePath = blobKey.getKeyString();
		    String title = req.getParameter("title");
		    
		    BlobInfo blobInfo =  blobInfoFactory.loadBlobInfo(blobKey);

		    Boolean commercialUse = true;
			
		    //Store the file in the Datastore under the current user
	        
			AudioFileDAO file = dao.getOrCreateAudioFile(filePath);
			file.setFileName(title);
			file.setOwner(req.getParameter("userid"));
			file.setAllowCommercialUse(commercialUse);
			file.setUploadDate(new Date());
			file.setContentType(blobInfo.getContentType());
			dao.ofy().put(file);
			
			writeResponse(resp, filePath);

	    }
	    catch (Exception e) {
			writeResponse(resp, "");
	    }
        
    }
    
    private void writeResponse(HttpServletResponse response, String responseString) throws IOException {
		response.setContentLength(responseString.length());
		response.setContentType("text/html");
	    response.setCharacterEncoding("utf-8");
	    response.setStatus(HttpServletResponse.SC_OK);
	    response.getWriter().print(responseString);
	    response.getWriter().flush();
    }
    
}
