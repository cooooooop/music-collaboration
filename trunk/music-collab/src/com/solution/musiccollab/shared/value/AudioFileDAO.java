package com.solution.musiccollab.shared.value;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@SuppressWarnings("serial")
@Entity
public class AudioFileDAO implements Serializable
{
    @Id
	private String fileName;
    private String owner;
    private byte[] data;

    public AudioFileDAO() {
    	super();
    }
    
    public AudioFileDAO(String fileName) {
    	this.setFileName(fileName);
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

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}
}