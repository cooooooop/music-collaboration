package com.solution.musiccollab.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.MultipartStream.ItemInputStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import com.solution.musiccollab.shared.value.AudioFileDAO;
import com.solution.musiccollab.shared.value.DAO;

public class UploadFileServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private DAO dao = new DAO();

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("Hello, world");
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		int fileSize = 10000000;
		
		try {
			ServletFileUpload upload = new ServletFileUpload();
			upload.setSizeMax(fileSize);
			res.setContentType("text/plain");
			PrintWriter out = res.getWriter();
		
			try {
				FileItemIterator iterator = upload.getItemIterator(req);
				while (iterator.hasNext()) {
					FileItemStream item = iterator.next();
					if(item != null && item.getContentType()!= null && item.getContentType().equals("audio/mp3")) {
						//an mp3 file has been uploaded
						ItemInputStream in = (ItemInputStream) item.openStream();
						ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
						while(in.available() > 0) {
							byteArrayOutputStream.write(in.read());
						}
						out.println("item.getName() = " + item.getName());
						
						byte[] bytes = byteArrayOutputStream.toByteArray();
						for(int i = 0; i < 1000; i += 10) {
							System.out.println(i);
							out.println("bytes[" + i + "]:" + bytes[i]);
						}
						
						//Store the file in the Datastore
						AudioFileDAO file = dao.getOrCreateAudioFile(item.getName());
						file.setData(bytes);
						file.setOwner("cooooooop@gmail.com");
						dao.ofy().put(file);
					}
				
				}
			} 
			catch (SizeLimitExceededException e) {
				out.println("You exceeded the maximum size ("
					+ e.getPermittedSize() + ") of the file ("
					+ e.getActualSize() + ")");
			}
		} 
		catch (Exception ex) {
	
			throw new ServletException(ex);
		}
	}
}
