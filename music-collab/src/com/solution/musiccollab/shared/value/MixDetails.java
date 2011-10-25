package com.solution.musiccollab.shared.value;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Transient;

@SuppressWarnings("serial")
public class MixDetails implements Serializable
{
	public static final String CHANNEL_RIGHT = "CHANNEL_RIGHT";
	public static final String CHANNEL_LEFT = "CHANNEL_LEFT";
	public static final String CHANNEL_MONO = "CHANNEL_MONO";
	
	@Id
	private String uniqueID;
	private String filePath;
	private Boolean delete = false; //set delete to true before saving to delete
	//all time measured in milliseconds
	private long startTime; //time sample begins in the final file
	private long trimStartTime; //time to start sample relative to sample beginning
	private long trimEndTime; //time to stop sample relative to sample beginning
	private double[] volumeArray; //volume of each byte (0.0 - 1.0), should be same size as sample data array
	private String channel = CHANNEL_MONO; 
	
	@Transient
	private AudioFileDAO audioFile;
	
	public MixDetails() { /*empty constructor required for objectify*/ }
	
	public MixDetails(String uniqueID, AudioFileDAO audioFileDAO) {
		this.uniqueID = uniqueID;
		this.filePath = audioFileDAO.getFilePath();
		this.audioFile = audioFileDAO;
	}
	
	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getTrimStartTime() {
		return trimStartTime;
	}

	public void setTrimStartTime(long trimStartTime) {
		this.trimStartTime = trimStartTime;
	}

	public long getTrimEndTime() {
		return trimEndTime;
	}

	public void setTrimEndTime(long trimEndTime) {
		this.trimEndTime = trimEndTime;
	}

	public double[] getVolumeArray() {
		return volumeArray;
	}

	public void setVolumeArray(double[] volumeArray) {
		this.volumeArray = volumeArray;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getUniqueID() {
		return uniqueID;
	}

	public void setUniqueID(String uniqueID) {
		this.uniqueID = uniqueID;
	}

	public Boolean delete() {
		return delete;
	}

	public void delete(Boolean delete) {
		this.delete = delete;
	}
	
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public AudioFileDAO getAudioFile() {
		return audioFile;
	}

	public void setAudioFile(AudioFileDAO audioFile) {
		this.audioFile = audioFile;
	}

}