package com.solution.musiccollab.shared.value;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private Boolean allowCommercialUse = false;
    private List<String> userDownloads = new ArrayList<String>();
    private List<String> mixes = new ArrayList<String>();
    private Date uploadDate;
    private String contentType;
    private long fileSize;
    private int bitrate;
    
    @Transient
    private UserDAO ownerUserDAO;

    public AudioFileDAO() { /*empty constructor required for objectify*/ }
    
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

	public void addDownload(String userid) {
		if(!userDownloads.contains(userid))
			userDownloads.add(userid);
	}

	public Integer getDownloads() {
		return userDownloads.size();
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public Date getUploadDate() {
		return uploadDate;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		if(contentType.equals("audio/mp3"))
			this.contentType = "audio/mpeg";
		else
			this.contentType = contentType;
	}

	public List<String> getMixes() {
		return mixes;
	}

	public void setMixes(List<String> mixes) {
		this.mixes = mixes;
	}
	
	public void addMix(String mix) {
		this.mixes.add(mix);
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public int getBitrate() {
		return bitrate;
	}

	public void setBitrate(int bitrate) {
		this.bitrate = bitrate;
	}

}