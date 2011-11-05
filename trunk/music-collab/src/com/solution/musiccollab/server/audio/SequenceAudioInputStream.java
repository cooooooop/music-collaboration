package com.solution.musiccollab.server.audio;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import com.solution.musiccollab.server.audio.value.WAVSound;

public class SequenceAudioInputStream extends ByteArrayOutputStream {
	private List<ByteArrayInputStream> byteArrayInputStreams;
	private String contentType;

	public SequenceAudioInputStream(String contentType, List<ByteArrayInputStream> byteArrayInputStreams) {
		this.contentType = contentType;
		this.byteArrayInputStreams = byteArrayInputStreams;
	}
	
	@Override
	public synchronized byte[] toByteArray() {
		//read byteArrayInputStreams, concat, return byte array
		
		//primitive attempt
		
		List<Byte> bytes = new ArrayList<Byte>();
		
		int newByte;
		ByteArrayInputStream input1 = byteArrayInputStreams.get(0);
		while((newByte = input1.read()) != -1) {
			bytes.add(new Byte((byte) newByte));
		}
		
		byte[] array = new byte[bytes.size()];
		for(int i = 0; i < bytes.size(); i++) {
			array[i] = bytes.get(i);
		}
		
		WAVSound wav1 = new WAVSound(array);
		
		ByteArrayInputStream input2 = byteArrayInputStreams.get(1);

		bytes = new ArrayList<Byte>();
		while((newByte = input2.read()) != -1) {
			bytes.add(new Byte((byte) newByte));
		}
		
		array = new byte[bytes.size()];
		for(int i = 0; i < bytes.size(); i++) {
			array[i] = bytes.get(i);
		}
		
		wav1.add(new WAVSound(array));
		
		return wav1.getData();
	}

}