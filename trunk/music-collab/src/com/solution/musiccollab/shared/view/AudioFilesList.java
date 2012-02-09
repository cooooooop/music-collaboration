package com.solution.musiccollab.shared.view;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.solution.musiccollab.shared.ClassGenerator;
import com.solution.musiccollab.shared.event.DAOEventHandler;
import com.solution.musiccollab.shared.event.FileSelectEventHandler;
import com.solution.musiccollab.shared.event.NavigationEventHandler;
import com.solution.musiccollab.shared.value.AudioFileDAO;

public class AudioFilesList extends ListPanel implements IAudioList {
	
	private ArrayList<FileSelectEventHandler> handlers = new ArrayList<FileSelectEventHandler>();
	private ArrayList<NavigationEventHandler> navigationHandlers = new ArrayList<NavigationEventHandler>();
	private ArrayList<DAOEventHandler> daoHandlers = new ArrayList<DAOEventHandler>();
	private List<IAudioItemPanel> data;
	private String caption = "Audio Files";
	private VerticalPanel list;
	
	public AudioFilesList() {
		
		title.setText(caption);
		list = new VerticalPanel();
		list.setHeight("100%");
		list.setWidth("100%");
		
		list.getElement().setAttribute("style", "background-color:#C5A159; border-style:solid; border-color:#CCC;");
		
		rootPanel.add(list);
	}
	
	public void setItems(List<AudioFileDAO> audioFileDAOs, String itemPanelType) {
		list.clear();
		data = new ArrayList<IAudioItemPanel>();
		for (AudioFileDAO audioFileDAO : audioFileDAOs) {
			IAudioItemPanel panel = (IAudioItemPanel) ClassGenerator.newInstance(itemPanelType);
			panel.setupPanel(this, audioFileDAO);
			list.add((Widget)panel);
			data.add(panel);
		}
	}

	@Override
	public void removeItem(AudioFileDAO audioFileDAO, IAudioItemPanel audioItemPanel) {
		list.remove((Widget) audioItemPanel);
		data.remove(audioItemPanel);
	}
	
	public List<AudioFileDAO> getItems() {
		List<AudioFileDAO> items = new ArrayList<AudioFileDAO>();
		for(IAudioItemPanel panel : data) {
			items.add(panel.getData());
		}
		
		return items;
	}
	
	public int getSize() {
		if(data != null)
			return data.size();
		return 0;
	}
	

	public List<IAudioItemPanel> getData() {
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
    
}
