package com.solution.musiccollab.server;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.objectify.Query;
import com.solution.musiccollab.shared.value.AudioFileDAO;
import com.solution.musiccollab.shared.value.UserDAO;

public class AudioServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    private DAO dao = new DAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		dao = new DAO();
		
		int limit;
		String order = req.getParameter("order");
		String owner = req.getParameter("owner");
		Query<AudioFileDAO> query = dao.ofy().query(AudioFileDAO.class);
		try {
			limit = Integer.parseInt(req.getParameter("limit"));
			query = query.limit(limit);
		}
		catch (NumberFormatException e) {
			//ignore
		}
		
		if(order != null)
			query = query.order(order);
		if(owner != null)
			query = query.filter("owner", owner);
		
		List<AudioFileDAO> rawList = query.list();
		for(AudioFileDAO audioFile : rawList)
			audioFile.setOwnerUserDAO(dao.ofy().query(UserDAO.class).filter("userid", audioFile.getOwner()).get());
			
    	JSONSerializer.audioListSerialize(rawList, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//    	don't support
//    	dao = new DAO();
//		List<UserDAO> users = dao.ofy().query(UserDAO.class).order("lastLogin").list();
//    	JSONSerializer.userListSerialize(users, resp);
        
    }
}
