package com.solution.musiccollab.shared.view;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.solution.musiccollab.shared.event.DAOEventHandler;
import com.solution.musiccollab.shared.event.FileSelectEventHandler;
import com.solution.musiccollab.shared.event.NavigationEventHandler;
import com.solution.musiccollab.shared.value.AudioFileDAO;
import com.solution.musiccollab.shared.value.MixDAO;

public class MixesList extends CaptionPanel {
	
	private ArrayList<FileSelectEventHandler> handlers = new ArrayList<FileSelectEventHandler>();
	private ArrayList<NavigationEventHandler> navigationHandlers = new ArrayList<NavigationEventHandler>();
	private ArrayList<DAOEventHandler> daoHandlers = new ArrayList<DAOEventHandler>();
	private List<MixDAO> data;
	private String caption = "Mix Projects";
	private VerticalPanel list;
	
	public MixesList() {
		
		setCaptionText(caption);
		list = new VerticalPanel();
		list.setHeight("100%");
		list.setWidth("100%");
		
		list.getElement().setAttribute("style", "background-color:#C5A159; border-style:solid; border-color:#FFF;");
		
		add(list);
	}
	
	public void setItems(List<MixDAO> mixDAOs) {
		list.clear();
		for (MixDAO mixDAO : mixDAOs) {
			MixItemPanel mixItemPanel = new MixItemPanel();
			mixItemPanel.setupPanel(this, mixDAO);
			list.add(mixItemPanel);
		}
		data = mixDAOs;
	}
	
	public void removeItem(MixDAO mixDAO, MixItemPanel mixItemPanel) {
		list.remove(mixItemPanel);
		data.remove(mixDAO);
	}

	public int getSize() {
		if(data != null)
			return data.size();
		return 0;
	}
	
	public void addFileSelectEventHandler(FileSelectEventHandler handler) {
		handlers.add(handler);
    }

    public void removeFileSelectEventHandler(FileSelectEventHandler handler) {
    	handlers.remove(handler);
    }
    
    public ArrayList<FileSelectEventHandler> getHandlers() {
    	return handlers;
    }
    
    public void addNavigationEventHandler(NavigationEventHandler handler) {
    	navigationHandlers.add(handler);
    }

    public void removeNavigationEventHandler(NavigationEventHandler handler) {
    	navigationHandlers.remove(handler);
    }
    
    public ArrayList<NavigationEventHandler> getNavigationHandlers() {
    	return navigationHandlers;
    }

    public void addDAOEventHandler(DAOEventHandler handler) {
    	daoHandlers.add(handler);
    }

    public void removeDAOEventHandler(DAOEventHandler handler) {
    	daoHandlers.remove(handler);
    }
	public ArrayList<DAOEventHandler> getDAOEventHandlers() {
		return daoHandlers;
	}
}
