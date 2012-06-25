package com.solution.musiccollab.server;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.google.appengine.repackaged.org.json.JSONObject;
import com.solution.musiccollab.shared.value.AudioFileDAO;
import com.solution.musiccollab.shared.value.UserDAO;

public class JSONSerializer {
	
	public static void userListSerialize(List<UserDAO> users, HttpServletResponse response) {
		try {
			JSONObject respJSON = new JSONObject();
			List<JSONObject> jsons = new LinkedList<JSONObject>();
	
			for(UserDAO user : users) {
				JSONObject json = new JSONObject();
				json.put("userid", user.getUserid());
				json.put("email", user.getEmail());
				json.put("lastLogin", user.getLastLogin());
				json.put("nickname", user.getNickname());
				jsons.add(json);
			}
			
			respJSON.put("users", jsons);
			writeResponse(response, respJSON.toString());
		}
		catch (Exception e){
			response.setContentLength(0);
			response.setContentType("text/html");
		    response.setCharacterEncoding("utf-8");
		    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
		
	}
	
	public static void audioListSerialize(List<AudioFileDAO> audioFiles, HttpServletResponse response) {
		try {
			JSONObject respJSON = new JSONObject();
			List<JSONObject> jsons = new LinkedList<JSONObject>();
	
			for(AudioFileDAO audio : audioFiles) {
				JSONObject json = new JSONObject();
				json.put("filePath", audio.getFilePath());
				json.put("fileName", audio.getFileName());
				json.put("owner", audio.getOwner());
				json.put("uploadDate", audio.getUploadDate());
				jsons.add(json);
			}
			
			respJSON.put("audioFiles", jsons);
			writeResponse(response, respJSON.toString());
		}
		catch (Exception e){
			response.setContentLength(0);
			response.setContentType("text/html");
		    response.setCharacterEncoding("utf-8");
		    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
		
	}
	
	private static void writeResponse(HttpServletResponse response, String responseString) {
		response.setContentLength(responseString.length());
		response.setContentType("text/html");
	    response.setCharacterEncoding("utf-8");
	    response.setStatus(HttpServletResponse.SC_OK);
	    try {
			response.getWriter().print(responseString);
			response.getWriter().flush();
		} catch (IOException e) {
		    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
    }
	
}
