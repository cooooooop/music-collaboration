package com.solution.musiccollab.server;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.solution.musiccollab.shared.value.AudioFileDAO;

public class UploadFileServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private BlobstoreService blobService = BlobstoreServiceFactory.getBlobstoreService();
    private DAO dao = new DAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uploadUrl = blobService.createUploadUrl("/uploadFile");
        req.setAttribute("uploadUrl", uploadUrl);
        req.getRequestDispatcher("upload_file.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, BlobKey> blobs = blobService.getUploadedBlobs(req);
        BlobKey blobKey = blobs.get(blobs.keySet().iterator().next());

        String filePath = blobKey.getKeyString();
	    String title = req.getParameter("title");
	    
	    //Store the file in the Datastore under the current user
		UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
		
        if(user != null) {
			AudioFileDAO file = dao.getOrCreateAudioFile(filePath);
			file.setFileName(title);
			file.setOwner(user.getUserId());
			dao.ofy().put(file);
        }
        
        resp.sendRedirect("/uploadFile");
    }
}