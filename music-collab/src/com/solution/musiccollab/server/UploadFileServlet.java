package com.solution.musiccollab.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

public class UploadFileServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("Hello, world");
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		try {
			ServletFileUpload upload = new ServletFileUpload();
			upload.setSizeMax(50000);
			res.setContentType("text/plain");
			PrintWriter out = res.getWriter();
		
			try {
				FileItemIterator iterator = upload.getItemIterator(req);
				while (iterator.hasNext()) {
					FileItemStream item = iterator.next();
					InputStream in = item.openStream();
				
					if (item.isFormField()) {
						out.println("Got a form field: " + item.getFieldName());
					} 
					else {
						String fieldName = item.getFieldName();
						String fileName = item.getName();
						String contentType = item.getContentType();
					
						out.println("--------------");
						out.println("fileName = " + fileName);
						out.println("field name = " + fieldName);
						out.println("contentType = " + contentType);
					
						String fileContents = null;
						try {
							 fileContents = IOUtils.toString(in);
							 out.println("lenght: " + fileContents.length());
							 out.println(fileContents);
						} 
						finally {		
							IOUtils.closeQuietly(in);
						}
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
