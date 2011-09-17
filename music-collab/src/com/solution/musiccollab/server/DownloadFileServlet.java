package com.solution.musiccollab.server;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.solution.musiccollab.shared.value.AudioFileDAO;

public class DownloadFileServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private BlobstoreService blobService = BlobstoreServiceFactory.getBlobstoreService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        String owner = request.getParameter("owner");
        String filePath = request.getParameter("filePath");
        
        DAO dao = new DAO();
        
        try {
			AudioFileDAO audioFileDAO = dao.ofy().query(AudioFileDAO.class).filter("owner", owner).filter("filePath", filePath).get();
			blobService.serve(new BlobKey(audioFileDAO.getFilePath()), response);
        }
        catch (Exception e) {
        	throw new ServletException(e);
        }
	        
    }
}
