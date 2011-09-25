package com.solution.musiccollab.shared.value;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@SuppressWarnings("serial")
@Entity
public class AudioFileDAO implements Serializable
{
    @Id
	private String fileName;
    private String owner;
    private String filePath;
    private Boolean allowCommercialUse;
    
    @Transient
    private UserDAO ownerUserDAO;

    public AudioFileDAO() {
    	super();
    }
    
    public AudioFileDAO(String filePath) {
    	this.setFilePath(filePath);
    }

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public void setOwnerUserDAO(UserDAO ownerUserDAO) {
		this.ownerUserDAO = ownerUserDAO;
	}

	public UserDAO getOwnerUserDAO() {
		return ownerUserDAO;
	}

	public void setAllowCommercialUse(Boolean allowCommercialUse) {
		this.allowCommercialUse = allowCommercialUse;
	}

	public Boolean getAllowCommercialUse() {
		return allowCommercialUse;
	}

}