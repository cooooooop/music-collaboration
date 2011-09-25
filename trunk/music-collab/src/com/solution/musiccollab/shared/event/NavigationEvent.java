package com.solution.musiccollab.shared.event;

import com.solution.musiccollab.shared.value.UserDAO;

public class NavigationEvent {

    private final UserDAO userDAO;

    public NavigationEvent(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

    public UserDAO getUserDAO() {
        return userDAO;
    }
}
