package com.solution.musiccollab.shared.event;

import com.google.gwt.event.shared.EventHandler;

public interface FileSelectEventHandler extends EventHandler {
    void onFileSelected(FileSelectEvent event);
    void onFilePlay(FileSelectEvent event);
    void onFileStop(FileSelectEvent event);
    void onMixSelected(FileSelectEvent event);
    void onMixPlay(FileSelectEvent event);
    void onMixStop(FileSelectEvent event);
}