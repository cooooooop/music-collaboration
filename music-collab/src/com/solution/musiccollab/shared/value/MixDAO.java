package com.solution.musiccollab.shared.value;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.solution.musiccollab.shared.UUID;

@SuppressWarnings("serial")
@Entity
public class MixDAO implements Serializable
{
    @Id
    private String uniqueID;
	private String mixName;
    private String owner;
    private String userContent;
    private List<String> userDownloads = new ArrayList<String>();
    private Date createDate;
    private String contentType = "audio/wav";
    private List<String> mixDetailsIDList = new ArrayList<String>();
    
    @Transient
    private UserDAO ownerUserDAO;
    @Transient
    private List<MixDetails> mixDetailsList = new ArrayList<MixDetails>();
    
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
	
	public String getUserContent() {
		return userContent;
	}

	public void setUserContent(String userContent) {
		this.userContent = userContent;
	}

	public String getMixName() {
		return mixName;
	}

	public void setMixName(String mixName) {
		this.mixName = mixName;
	}

	public MixDetails addMixDetail(AudioFileDAO audioFileDAO) {
		String detailsID = UUID.uuid();
		mixDetailsIDList.add(detailsID);
		
		MixDetails mixDetails = new MixDetails(detailsID, audioFileDAO);
		mixDetailsList.add(mixDetails);
		
		return mixDetails;
	}
	
	public void removeMixDetail(MixDetails mixDetails) {
		this.mixDetailsList.remove(mixDetails);
		this.mixDetailsIDList.remove(mixDetails.getUniqueID());
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
	
	public List<String> getMixDetailsIDList() {
		return mixDetailsIDList;
	}

	public void setMixDetailsIDList(List<String> mixDetailsIDList) {
		this.mixDetailsIDList = mixDetailsIDList;
	}

	public List<MixDetails> getMixDetailsList() {
		return mixDetailsList;
	}

	public void setMixDetailsList(List<MixDetails> mixDetailsList) {
		this.mixDetailsList = mixDetailsList;
	}

}