package com.solution.musiccollab.value;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AudioFile
{
    @Id
	private String fileName;
    private String owner;
    private byte[] data;

    public AudioFile() {
    	super();
    }
    
    public AudioFile(String fileName) {
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