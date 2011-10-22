package com.solution.musiccollab.shared.value;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@SuppressWarnings("serial")
@Entity
public class MixDAO implements Serializable
{
    @Id
    private String uniqueID;
	private String mixName;
    private String owner;
    private List<String> userDownloads = new ArrayList<String>();
    private List<String> samplePathList = new ArrayList<String>();
    private Date createDate;
    private String contentType = "audio/wav";
    
    @Transient
    private UserDAO ownerUserDAO;
    
    public MixDAO() { /*empty constructor required for objectify*/ }
    
    public MixDAO(String uniqueID) {
    	this.uniqueID = uniqueID;
    	this.createDate = new Date();
    }

	public String getUniqueID() {
		return uniqueID;
	}

	public void setUniqueID(String uniqueID) {
		this.uniqueID = uniqueID;
	}

	public String getMixName() {
		return mixName;
	}

	public void setMixName(String mixName) {
		this.mixName = mixName;
	}

	public List<String> getSamplePathList() {
		return samplePathList;
	}

	public void setSamplePathList(List<String> samplePathList) {
		this.samplePathList = samplePathList;
	}
	
	public void addSamplePath(String samplePath) {
		this.samplePathList.add(samplePath);
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public List<String> getUserDownloads() {
		return userDownloads;
	}
	
	public Integer getDownloads() {
		return userDownloads.size();
	}

	public void setUserDownloads(List<String> userDownloads) {
		this.userDownloads = userDownloads;
	}
	
	public void addDownload(String userid) {
		if(!userDownloads.contains(userid))
			userDownloads.add(userid);
	}

	public UserDAO getOwnerUserDAO() {
		return ownerUserDAO;
	}

	public void setOwnerUserDAO(UserDAO ownerUserDAO) {
		this.ownerUserDAO = ownerUserDAO;
		this.owner = ownerUserDAO.getUserid();
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

}