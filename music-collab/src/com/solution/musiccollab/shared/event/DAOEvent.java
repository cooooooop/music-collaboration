package com.solution.musiccollab.shared.event;

import com.solution.musiccollab.shared.view.IDAOEditor;

public class DAOEvent {

	private final Object dao;
	private final IDAOEditor daoEditor;
	
    public DAOEvent(Object dao, IDAOEditor daoEditor) {
		this.dao = dao;
		this.daoEditor = daoEditor;
	}

	public Object getDao() {
		return dao;
	}

	public IDAOEditor getDaoEditor() {
		return daoEditor;
	}
    
}
