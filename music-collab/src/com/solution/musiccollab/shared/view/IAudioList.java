package com.solution.musiccollab.shared.view;

import java.util.ArrayList;
import java.util.List;

import com.solution.musiccollab.shared.event.DAOEventHandler;
import com.solution.musiccollab.shared.event.FileSelectEventHandler;
import com.solution.musiccollab.shared.event.NavigationEventHandler;
import com.solution.musiccollab.shared.value.AudioFileDAO;

public interface IAudioList {
	public void setItems(List<AudioFileDAO> audioFileDAOs, String itemPanelType);
	
	public void removeItem(AudioFileDAO audioFileDAO, IAudioItemPanel audioItemPanel);

	public int getSize();
	
	public void addFileSelectEventHandler(FileSelectEventHandler handler);

    public void removeFileSelectEventHandler(FileSelectEventHandler handler);
    
    public ArrayList<FileSelectEventHandler> getHandlers();
    
    public void addNavigationEventHandler(NavigationEventHandler handler);

    public void removeNavigationEventHandler(NavigationEventHandler handler);
    
    public ArrayList<NavigationEventHandler> getNavigationHandlers();
    
    public void addDAOEventHandler(DAOEventHandler handler);

    public void removeDAOEventHandler(DAOEventHandler handler);
    
    public ArrayList<DAOEventHandler> getDAOEventHandlers();
    
}
