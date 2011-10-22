package com.solution.musiccollab.shared.event;

import com.google.gwt.event.shared.EventHandler;

public interface DAOEventHandler extends EventHandler {
    void onSaveMix(DAOEvent event);
    void onDeleteMix(DAOEvent event);
    void onSaveAudio(DAOEvent event);
    void onDeleteAudio(DAOEvent event);
    void onSaveUser(DAOEvent event);
    void onDeleteUser(DAOEvent event);
}