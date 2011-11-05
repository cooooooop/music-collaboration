package com.solution.musiccollab.server.audio;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

public class AudioUtil {
	
	public static byte[] merge(byte[] sample1, byte[] sample2, String contentType) {
		List<ByteArrayInputStream> byteArrayInputStreamList = new ArrayList<ByteArrayInputStream>();
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(sample1);
		byteArrayInputStreamList.add(byteArrayInputStream);
		byteArrayInputStream = new ByteArrayInputStream(sample2);
		byteArrayInputStreamList.add(byteArrayInputStream);
		
		ByteArrayOutputStream audioMixByteArrayOutputStream = new AudioMixByteArrayOutputStream(contentType, byteArrayInputStreamList);
		
		return audioMixByteArrayOutputStream.toByteArray();
	}
	
	public static byte[] concat(byte[] sample1, byte[] sample2, String contentType) {
		List<ByteArrayInputStream> byteArrayInputStreamList = new ArrayList<ByteArrayInputStream>();
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(sample1);
		byteArrayInputStreamList.add(byteArrayInputStream);
		byteArrayInputStream = new ByteArrayInputStream(sample2);
		byteArrayInputStreamList.add(byteArrayInputStream);
		
		ByteArrayOutputStream audioSequenceInputStream = new SequenceAudioInputStream(contentType, byteArrayInputStreamList);
		
		return audioSequenceInputStream.toByteArray();

	}
	
}
