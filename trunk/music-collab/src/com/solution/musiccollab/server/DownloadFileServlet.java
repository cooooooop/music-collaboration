package com.solution.musiccollab.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.solution.musiccollab.shared.value.AudioFileDAO;

public class DownloadFileServlet extends HttpServlet {
	
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        String owner = request.getParameter("owner");
        String fileName = request.getParameter("fileName");
        BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
        
        DAO dao = new DAO();
        
        try {
			AudioFileDAO audioFileDAO = dao.ofy().query(AudioFileDAO.class).filter("owner", owner).filter("fileName", fileName).get();
	
			String length = String.valueOf(audioFileDAO.getData().length);
			
	        response.setHeader("Content-Type", "audio/mp3");
	        response.setHeader("Content-Length", length);
	        response.setHeader("Content-disposition", "attachment;filename=\"" + fileName + "\"");
	
	            
	        bos.write(audioFileDAO.getData());
	        
	        bos.flush();
	        bos.close();
        }
        catch (Exception e) {
        	throw new ServletException(e);
        }
	        
    }
}
