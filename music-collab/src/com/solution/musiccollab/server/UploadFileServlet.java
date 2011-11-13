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
        String uploadUrl = blobService.createUploadUrl("/uploadFile?userid=" + req.getParameter("userid") + "&message=");
        req.setAttribute("uploadUrl", uploadUrl);
        req.getRequestDispatcher("upload_file.jsp").forward(req, resp);
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
		    String preview = req.getParameter("btnPreview");
		    String upload = req.getParameter("btnUpload");
		    
		    BlobInfo blobInfo =  blobInfoFactory.loadBlobInfo(blobKey);

		    if(upload != null) {
			    Boolean commercialUse = req.getParameter("ccRadio").equals("yes");
				
			    //Store the file in the Datastore under the current user
		        
				AudioFileDAO file = dao.getOrCreateAudioFile(filePath);
				file.setFileName(title);
				file.setOwner(req.getParameter("userid"));
				file.setAllowCommercialUse(commercialUse);
				file.setUploadDate(new Date());
				file.setContentType(blobInfo.getContentType());
				dao.ofy().put(file);
	
				resp.sendRedirect("/uploadFile?message=Upload Successful&userid=" + req.getParameter("userid"));
		    }
		    else if(preview != null) {
		        BufferedOutputStream bos = new BufferedOutputStream(resp.getOutputStream());

				resp.setHeader("Content-Type", blobInfo.getContentType());
				resp.setHeader("Content-disposition", "attachment;filename=\"" + title + "\"");
			
				byte[] data = fetchData(blobKey, blobInfo);
				
				bos.write(AudioUtil.concat(data, data, blobInfo.getContentType()));
				bos.flush();
		        bos.close();
		        
		        blobService.delete(blobKey);
		        
		    }
	    }
	    catch (Exception e) {
	    	resp.sendRedirect("/uploadFile?message=Upload Failed&userid=" + req.getParameter("userid"));
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
}
