package com.solution.musiccollab.server;

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
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.solution.musiccollab.shared.model.Model;
import com.solution.musiccollab.shared.value.AudioFileDAO;
import com.solution.musiccollab.shared.value.MixDAO;

public class MixServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private BlobstoreService blobService = BlobstoreServiceFactory.getBlobstoreService();
	private BlobInfoFactory blobInfoFactory = new BlobInfoFactory(DatastoreServiceFactory.getDatastoreService());
    private DAO dao = new DAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	DAO dao = new DAO();
        String action = req.getParameter("action");
    	String uniqueID = req.getParameter("uniqueID");
    	String userid = req.getParameter("userid");
        
    	if(action != null && action.equals("play")) {
    		MixDAO mixDAO = dao.ofy().query(MixDAO.class).filter("uniqueID", uniqueID).get();
    		for (String filePath : mixDAO.getSamplePathList()) {
	    		AudioFileDAO audioFileDAO = dao.ofy().query(AudioFileDAO.class).filter("filePath", filePath).get();
				if(userid != null)
					audioFileDAO.addDownload(userid);
				
				dao.ofy().put(audioFileDAO);
				
				BlobKey blobKey = new BlobKey(audioFileDAO.getFilePath());
				BlobInfo blobInfo =  blobInfoFactory.loadBlobInfo(blobKey);
				
				blobService.serve(blobKey, resp);
				break;
    		}
    	}
    	else {
	    	
	        String uploadUrl = blobService.createUploadUrl("/mix?userid=" + req.getParameter("userid") + "&audioFilePath=" + req.getParameter("audioFilePath") + "&message=");
	        req.setAttribute("uploadUrl", uploadUrl);
	        req.setAttribute("audioFiles", dao.ofy().query(AudioFileDAO.class).list());
	        req.getRequestDispatcher("mix.jsp").forward(req, resp);
    	}
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    try {
		    //Store the mix in the Datastore under the current user
	        
	    	String mixName = req.getParameter("mixName");
			MixDAO mix = dao.getOrCreateMix(mixName);
			mix.setOwner(req.getParameter("userid"));
			mix.addSamplePath(req.getParameter("audioFilePath"));
			mix.addSamplePath(req.getParameter("fileList"));
			dao.ofy().put(mix);
			
			//connect to all related AudioFiles
			
			for (String filePath : mix.getSamplePathList()) {
				AudioFileDAO audioFileDAO = dao.ofy().query(AudioFileDAO.class).filter("filePath", filePath).get();
				audioFileDAO.addMix(mix.getMixName());
				dao.ofy().put(audioFileDAO);
			}

			resp.sendRedirect("/mix?message=Mix Completed&userid=" + req.getParameter("userid") + "&audioFilePath=" + req.getParameter("audioFilePath"));
	    }
	    catch (Exception e) {
	    	e.printStackTrace();
	    	resp.sendRedirect("/mix?message=Mix Failed&userid=" + req.getParameter("userid") + "&audioFilePath=" + req.getParameter("audioFilePath"));
	    }
        
    }
}
