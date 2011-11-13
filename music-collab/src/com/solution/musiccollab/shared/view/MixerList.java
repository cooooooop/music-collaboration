package com.solution.musiccollab.shared.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.solution.musiccollab.shared.ClassGenerator;
import com.solution.musiccollab.shared.event.DAOEventHandler;
import com.solution.musiccollab.shared.event.FileSelectEventHandler;
import com.solution.musiccollab.shared.event.NavigationEventHandler;
import com.solution.musiccollab.shared.value.AudioFileDAO;
import com.solution.musiccollab.shared.value.MixDAO;
import com.solution.musiccollab.shared.value.MixDetails;

public class MixerList extends CaptionPanel {
	
	private ArrayList<FileSelectEventHandler> handlers = new ArrayList<FileSelectEventHandler>();
	private ArrayList<NavigationEventHandler> navigationHandlers = new ArrayList<NavigationEventHandler>();
	private ArrayList<DAOEventHandler> daoHandlers = new ArrayList<DAOEventHandler>();
	private List<MixerItemPanel> data;
	private String caption = "Mix Editor";
	private VerticalPanel list;
	private MixDAO mixDAO;
	
	public MixerList() {
		
		setCaptionText(caption);
		list = new VerticalPanel();
		list.setHeight("100%");
		list.setWidth("100%");
		
//		list.getElement().setAttribute("style", "background-color:#C5A159; border-style:solid; border-color:#FFF;");
		
		add(list);
	}
	
	public void setItems(List<MixDetails> mixDetailsList) {
		list.clear();
		data = new ArrayList<MixerItemPanel>();
		for (MixDetails mixDetails : mixDetailsList) {
			MixerItemPanel panel = new MixerItemPanel();
			panel.setupPanel(this, mixDetails);
			list.add((Widget)panel);
			data.add(panel);
		}
	}
	
	public void addItem(MixDetails mixDetails) {
		MixerItemPanel panel = new MixerItemPanel();
		panel.setupPanel(this, mixDetails);
		list.add((Widget)panel);
		data.add(panel);
	}
	

	public void removeItem(MixDetails mixDetails, MixerItemPanel mixerItemPanel) {
		list.remove((Widget) mixerItemPanel);
		data.remove(mixerItemPanel);
		
		mixDAO.removeMixDetail(mixDetails);
	}

	public int getSize() {
		if(data != null)
			return data.size();
		return 0;
	}
	
	public List<MixerItemPanel> getData() {
		return data;
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

	public MixDAO getMixDAO() {
		return mixDAO;
	}

	public void setMixDAO(MixDAO mixDAO) {
		this.mixDAO = mixDAO;
	}
    
}
