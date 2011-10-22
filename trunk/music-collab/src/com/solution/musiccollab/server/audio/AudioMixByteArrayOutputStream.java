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

public class AudioMixByteArrayOutputStream extends ByteArrayOutputStream {
	private List<ByteArrayInputStream> byteArrayInputStreams;
	private String contentType;

	public AudioMixByteArrayOutputStream(String contentType, List<ByteArrayInputStream> byteArrayInputStreams) {
		this.contentType = contentType;
		this.byteArrayInputStreams = byteArrayInputStreams;
	}
	
	@Override
	public synchronized byte[] toByteArray() {
		//read byteArrayInputStreams, merge, return byte array
		
		//primitive attempt
		
		List<Byte> bytes = new ArrayList<Byte>();
		
		int newByte;
		ByteArrayInputStream input1 = byteArrayInputStreams.get(0);
		ByteArrayInputStream input2 = byteArrayInputStreams.get(1);

		for(int b = 0; b < 44; b++) {
			bytes.add(new Byte((byte) input1.read()));
			input2.read();
		}
		
		while((newByte = input1.read()) != -1) {
			newByte += input2.read();
			bytes.add(new Byte((byte) newByte));
		}
		
		byte[] retArray = new byte[bytes.size()];
		for(int i = 0; i < bytes.size(); i++) {
			retArray[i] = bytes.get(i);
		}
		
		return retArray;
	}

}