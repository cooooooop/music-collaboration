package com.solution.musiccollab.server.audio.value;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.solution.musiccollab.server.audio.AudioHelper;

public class WAVSound {

	private byte[] data;
	
	public WAVSound(byte[] data) {
		this.data = data;
	}
	
	public String getChunkID() {
		return new String(Arrays.copyOfRange(data, 0, 4));
	}
	
	public void setChunkID(byte[] bytes) {
		for(int i = 0; i < 4; i++) {
			data[i] = bytes[i];
		}
	}

	public int getChunkSize() {
		return AudioHelper.toInt(Arrays.copyOfRange(data, 4, 8));
	}
	
	public void setChunkSize(byte[] bytes) {
		for(int i = 4; i < 8; i++) {
			data[i] = bytes[i - 4];
		}
	}
	
	public String getFormat() {
		return new String(Arrays.copyOfRange(data, 8, 12));
	}
	
	public void setFormat(byte[] bytes) {
		for(int i = 8; i < 12; i++) {
			data[i] = bytes[i - 8];
		}
	}
	
	public String getSubchunk1ID() {
		return new String(Arrays.copyOfRange(data, 12, 16));
	}
	
	public void setSubchunk1ID(byte[] bytes) {
		for(int i = 12; i < 16; i++) {
			data[i] = bytes[i - 12];
		}
	}
	
	public int getSubchunk1Size() {
		return AudioHelper.toInt(Arrays.copyOfRange(data, 16, 20));
	}
	
	public void setSubchunk1Size(byte[] bytes) {
		for(int i = 16; i < 20; i++) {
			data[i] = bytes[i - 16];
		}
	}

	public short getAudioFormat() {
		return AudioHelper.toShort(Arrays.copyOfRange(data, 20, 22));
	}
	
	public void setAudioFormat(byte[] bytes) {
		for(int i = 20; i < 22; i++) {
			data[i] = bytes[i - 20];
		}
	}

	public short getNumChannels() {
		return AudioHelper.toShort(Arrays.copyOfRange(data, 22, 24));
	}
	
	public void setNumChannels(byte[] bytes) {
		for(int i = 22; i < 24; i++) {
			data[i] = bytes[i - 22];
		}
	}

	public int getSampleRate() {
		return AudioHelper.toInt(Arrays.copyOfRange(data, 24, 28));
	}
	
	public void setSampleRate(byte[] bytes) {
		for(int i = 24; i < 28; i++) {
			data[i] = bytes[i - 24];
		}
	}

	public int getByteRate() {
		return AudioHelper.toInt(Arrays.copyOfRange(data, 28, 32));
	}
	
	public void setByteRate(byte[] bytes) {
		for(int i = 28; i < 32; i++) {
			data[i] = bytes[i - 28];
		}
	}
	
	public short getBlockAlign() {
		return AudioHelper.toShort(Arrays.copyOfRange(data, 32, 34));
	}
	
	public void setBlockAlign(byte[] bytes) {
		for(int i = 32; i < 34; i++) {
			data[i] = bytes[i - 32];
		}
	}
	
	public short getBitsPerSample() {
		return AudioHelper.toShort(Arrays.copyOfRange(data, 34, 36));
	}
	
	public void setBitsPerSample(byte[] bytes) {
		for(int i = 34; i < 36; i++) {
			data[i] = bytes[i -34];
		}
	}
	
	public String getSubchunk2ID() {
		return new String(Arrays.copyOfRange(data, 36, 40));
	}
	
	public void setSubchunk2ID(byte[] bytes) {
		for(int i = 36; i < 40; i++) {
			data[i] = bytes[i - 36];
		}
	}
	
	public int getSubchunk2Size() {
		return AudioHelper.toInt(Arrays.copyOfRange(data, 40, 44));
	}
	
	public void setSubchunk2Size(byte[] bytes) {
		for(int i = 40; i < 44; i++) {
			data[i] = bytes[i - 40];
		}
	}
	
	public byte[] getAudioData() {
		return Arrays.copyOfRange(data, 44, data.length);
	}
	
	public void setAudioData(byte[] bytes) {
		for(int i = 44; i < data.length; i++) {
			data[i] = bytes[i - 44];
		}
	}
	
	public byte[] getData() {
		return data;
	}
	
	public void setData(byte[] bytes) {
		data = bytes;
	}
	
	public void add(WAVSound wavSound) {
		if(getFormat().equals(wavSound.getFormat()) && getAudioFormat() == wavSound.getAudioFormat()) {
			List<Byte> bytes = new ArrayList<Byte>();
			for(int i = 0; i < data.length; i++) {
				bytes.add(data[i]);
			}
			
			byte[] wavSoundData = wavSound.getAudioData();
			for(int i = 0; i < wavSoundData.length; i++) {
				bytes.add(wavSoundData[i]);
			}
			
			data = new byte[bytes.size()];
			for(int i = 0; i < bytes.size(); i++) {
				data[i] = bytes.get(i);
			}
			
			int chunkSize = getChunkSize();
			chunkSize += wavSoundData.length;
			setChunkSize(AudioHelper.toByta(chunkSize));
			
			int subchunk2Size = getSubchunk2Size();
			subchunk2Size += wavSoundData.length;
			setSubchunk2Size(AudioHelper.toByta(subchunk2Size));
			
		}
	}
	
	public void mix(WAVSound wavSound) {
		if(getFormat().equals(wavSound.getFormat()) && getAudioFormat() == wavSound.getAudioFormat()) {
			List<Byte> bytes = new ArrayList<Byte>();
			for(int i = 0; i < 44; i++) {
				bytes.add(data[i]);
			}
			
			byte[] audioData = getAudioData();
			byte[] wavSoundData = wavSound.getAudioData();
			for(int i = 0; i < audioData.length; i++) {
				bytes.add(new Byte(((byte) ((audioData[i] + wavSoundData[i]) / 2))));
			}
			
			data = new byte[bytes.size()];
			for(int i = 0; i < bytes.size(); i++) {
				data[i] = bytes.get(i);
			}
			
		}
	}
	
	public String toString() {
		return "ChunkID: " + getChunkID() + "\n" + 
			   "ChunkSize: " + getChunkSize() + "\n" + 
			   "Format: " + getFormat() + "\n" +
			   "Subchunk1ID: " + getSubchunk1ID() + "\n" + 
			   "Subchunk1Size: " + getSubchunk1Size() + "\n" + 
			   "AudioFormat: " + getAudioFormat() + "\n" +
			   "NumChannels: " + getNumChannels() + "\n" +
			   "SampleRate: " + getSampleRate() + "\n" + 
			   "ByteRate: " + getByteRate() + "\n" + 
			   "BlockAlign: " + getBlockAlign() + "\n" +
			   "BitsPerSample: " + getBitsPerSample() + "\n" +
			   "Subchunk2ID: " + getSubchunk2ID() + "\n" + 
			   "Subchunk2Size: " + getSubchunk2Size();
	}
	
}
