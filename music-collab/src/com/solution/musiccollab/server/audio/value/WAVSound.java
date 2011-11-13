package com.solution.musiccollab.server.audio.value;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.solution.musiccollab.server.audio.AudioHelper;
import com.solution.musiccollab.shared.value.MixDetails;

public class WAVSound {

	private byte[] data;
	
	public WAVSound(byte[] data) {
		this.data = data;
	}
	
	/**
	 * This constructor uses the mixDetails object to modify the data argument (trim or add silence)
	 * @param data
	 * @param mixDetails
	 */
	public WAVSound(byte[] data, MixDetails mixDetails) {
		this.data = data;
		trim(mixDetails.getTrimStartTime(), mixDetails.getTrimEndTime());
		if(mixDetails.getStartTime() > 0) {
			addSilence(mixDetails.getStartTime());
		}
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
		for(int i = 44; i < bytes.length; i++) {
			data[i] = bytes[i - 44];
		}
		
		data = Arrays.copyOfRange(data, 0, 44 + bytes.length);
	}
	
	public byte[] getData() {
		return data;
	}
	
	public void setData(byte[] bytes) {
		data = bytes;
	}
	
	public long getLengthMillis() {
		return new Double(((double) getSubchunk2Size() / (double) getByteRate()) * 1000).longValue();
	}
	
	private void trim(long start, long end) {
		int startByteSize = new Double(((double) start * (double) getByteRate()) / 1000).intValue();
		long endDiff = getLengthMillis() - end;
		int endByteSize = new Double(((double) endDiff * (double) getByteRate()) / 1000).intValue();
		
		List<Byte> bytes = new ArrayList<Byte>();
		byte[] audioData = getAudioData();
		
		for(int i = 0; i < 44; i++) {
			bytes.add(data[i]);
		}
		
		for(int i = startByteSize; i < audioData.length - endByteSize; i++) {
			bytes.add(audioData[i]);
		}
		
		data = new byte[bytes.size()];
		for(int i = 0; i < bytes.size(); i++) {
			data[i] = bytes.get(i);
		}
		
		int chunkSize = getChunkSize();
		chunkSize -= startByteSize + endByteSize;
		setChunkSize(AudioHelper.toByta(chunkSize));
		
		int subchunk2Size = getSubchunk2Size();
		subchunk2Size -= startByteSize + endByteSize;
		setSubchunk2Size(AudioHelper.toByta(subchunk2Size));
		
	}
	
	private void addSilence(long timeInMillis) {
		int byteSize = new Double(((double) timeInMillis * (double) getByteRate()) / 1000).intValue();
		byte[] silence = new byte[byteSize];
		Arrays.fill(silence, (byte) 0);
		
		byte[] wavSoundData = new byte[data.length];
		for(int i = 0; i < data.length; i++) {
			wavSoundData[i] = data[i];
		}
		
		setAudioData(silence);
		
		List<Byte> bytes = new ArrayList<Byte>();
		for(int i = 0; i < data.length; i++) {
			bytes.add(data[i]);
		}
		
		for(int i = 0; i < wavSoundData.length; i++) {
			bytes.add(wavSoundData[i]);
		}
		
		data = new byte[bytes.size()];
		for(int i = 0; i < bytes.size(); i++) {
			data[i] = bytes.get(i);
		}
		
		int chunkSize = getChunkSize();
		chunkSize += silence.length;
		setChunkSize(AudioHelper.toByta(chunkSize));
		
		int subchunk2Size = getSubchunk2Size();
		subchunk2Size += silence.length;
		setSubchunk2Size(AudioHelper.toByta(subchunk2Size));
			
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
			
			byte[] longer = getAudioData();
			byte[] shorter = wavSound.getAudioData();
			int difference = 0;
			
			if(longer.length < shorter.length) {
				longer = shorter;
				shorter = getAudioData();
				difference = longer.length - shorter.length;
			}
			
			double a;
			double b;
			byte finalByte;
			for(int i = 0; i < longer.length; i++) {
				if(i < shorter.length) {
					a = ((double) (longer[i] + 128)) / 256;
					b = ((double) (shorter[i] + 128)) / 256;
					
					finalByte = (byte) ((((a + b) - (a * b)) * 256) - 128);
				
					bytes.add(new Byte(finalByte));
				}
				else
					bytes.add(longer[i]);
			}
			
			data = new byte[bytes.size()];
			for(int i = 0; i < bytes.size(); i++) {
				data[i] = bytes.get(i);
			}
			
			int chunkSize = getChunkSize();
			chunkSize += difference;
			setChunkSize(AudioHelper.toByta(chunkSize));
			
			int subchunk2Size = getSubchunk2Size();
			subchunk2Size += difference;
			setSubchunk2Size(AudioHelper.toByta(subchunk2Size));
			
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
