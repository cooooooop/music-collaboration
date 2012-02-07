package com.solution.musiccollab.shared.event;

import com.google.gwt.event.shared.EventHandler;

public interface NavigationEventHandler extends EventHandler {
    void onHomeNavigation(NavigationEvent event);
    void onMemberPageNavigation(NavigationEvent event);
    void onDirectoryPageNavigation(NavigationEvent event);
    void onUploadNavigation(NavigationEvent event);
    void onMixerNavigation(NavigationEvent event);
    void onLoginRequest(NavigationEvent event);
    void onLogoutRequest(NavigationEvent event);
    void onFilePageNavigation(NavigationEvent event);
    void onMixPageNavigation(NavigationEvent event);
}