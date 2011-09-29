package com.solution.musiccollab.shared.view;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.solution.musiccollab.shared.event.FileSelectEventHandler;
import com.solution.musiccollab.shared.event.NavigationEventHandler;
import com.solution.musiccollab.shared.value.AudioFileDAO;

public class AudioFilesList extends CaptionPanel {
	
	private ArrayList<FileSelectEventHandler> handlers = new ArrayList<FileSelectEventHandler>();
	private ArrayList<NavigationEventHandler> navigationHandlers = new ArrayList<NavigationEventHandler>();
	private List<AudioFileDAO> data;
	private String caption = "Audio Files";
	private VerticalPanel list;
	
	public AudioFilesList() {
		
		setCaptionText(caption);
		list = new VerticalPanel();
		list.setHeight("100%");
		list.setWidth("100%");
		
		list.getElement().setAttribute("style", "background-color:#C5A159; border-style:solid; border-color:#FFF;");
		
		add(list);
	}
	
	public void setItems(List<AudioFileDAO> audioFileDAOs) {
		list.clear();
		for (AudioFileDAO audioFileDAO : audioFileDAOs) {
			AudioFileItemPanel audioFileItemPanel = new AudioFileItemPanel();
			audioFileItemPanel.setupPanel(this, audioFileDAO);
			list.add(audioFileItemPanel);
		}
		data = audioFileDAOs;
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
    
}
