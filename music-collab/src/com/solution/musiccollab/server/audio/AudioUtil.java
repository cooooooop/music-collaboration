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
	
//	public static byte[] concat(byte[] sample1, byte[] sample2) {
//		List<AudioInputStream> audioInputStreamList = new ArrayList<AudioInputStream>();
//		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(sample1);
//		AudioInputStream audioInputStream = null;
//		
//		try
//		{
//			audioInputStream = AudioSystem.getAudioInputStream(byteArrayInputStream);
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//		}
//		
//		audioInputStreamList.add(audioInputStream);
//		
//		byteArrayInputStream = new ByteArrayInputStream(sample2);
//		audioInputStream = null;
//		try
//		{
//			audioInputStream = AudioSystem.getAudioInputStream(byteArrayInputStream);
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//		}
//		
//		audioInputStreamList.add(audioInputStream);
//		
//		AudioInputStream audioSequenceInputStream = new SequenceAudioInputStream(audioInputStream.getFormat(), audioInputStreamList);
//		
//		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//		
//		try {
//			AudioSystem.write(audioSequenceInputStream, AudioFileFormat.Type.WAVE, byteArrayOutputStream);
//		}
//		catch (IOException e)
//		{
//			e.printStackTrace();
//		}
//		
//		return byteArrayOutputStream.toByteArray();
//	}
	
}
