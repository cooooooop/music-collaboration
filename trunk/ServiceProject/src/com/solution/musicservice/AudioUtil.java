package com.solution.musicservice;

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

//import org.tritonus.sampled.convert.vorbis.VorbisFormatConversionProvider.EncodedVorbisAudioInputStream;

import com.solution.musicservice.audio.MixingFloatAudioInputStream;
import com.solution.musicservice.audio.SequenceAudioInputStream;

public class AudioUtil {
	
	public static byte[] decodeOgg(byte[] sample) {
//		try {
//			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(sample);
//			AudioInputStream source = new AudioInputStream(byteArrayInputStream, , length)
//			AudioInputStream pcm = AudioSystem.getAudioInputStream(AudioFormat.Encoding.PCM_SIGNED, source);
//			AudioInputStream ulaw = AudioSystem.getAudioInputStream(AudioFormat.Encoding.ULAW, pcm);
//			File tempFile = File.createTempFile("wav", "tmp");
//			AudioSystem.write(ulaw, AudioFileFormat.Type.WAVE, tempFile);
//
//			byte[] bytes = Main.getBytesFromFile(tempFile);
//			tempFile.delete();
//			return bytes;
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//		}
//		
		return null;
	}
	
	public static byte[] decodeMp3(byte[] sample) {
		try {
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(sample);
			AudioInputStream source = AudioSystem.getAudioInputStream(byteArrayInputStream);
			AudioInputStream pcm = AudioSystem.getAudioInputStream(AudioFormat.Encoding.PCM_SIGNED, source);
			AudioInputStream ulaw = AudioSystem.getAudioInputStream(AudioFormat.Encoding.ULAW, pcm);
			File tempFile = File.createTempFile("wav", "tmp");
			AudioSystem.write(ulaw, AudioFileFormat.Type.WAVE, tempFile);

			byte[] bytes = Main.getBytesFromFile(tempFile);
			tempFile.delete();
			return bytes;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static float decibel2linear(float decibels) {
		return (float) Math.pow(10.0, decibels / 20.0);
	}
	
	public static byte[] concat(byte[] sample1, byte[] sample2) {
		List<AudioInputStream> audioInputStreamList = new ArrayList<AudioInputStream>();
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(sample1);
		AudioInputStream audioInputStream = null;
		
		try
		{
			audioInputStream = AudioSystem.getAudioInputStream(byteArrayInputStream);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		audioInputStreamList.add(audioInputStream);
		
		byteArrayInputStream = new ByteArrayInputStream(sample2);
		audioInputStream = null;
		try
		{
			audioInputStream = AudioSystem.getAudioInputStream(byteArrayInputStream);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		audioInputStreamList.add(audioInputStream);
		
		AudioInputStream audioSequenceInputStream = new SequenceAudioInputStream(audioInputStream.getFormat(), audioInputStreamList);
		
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		try {
			AudioSystem.write(audioSequenceInputStream, AudioFileFormat.Type.WAVE, byteArrayOutputStream);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return byteArrayOutputStream.toByteArray();
	}
	
	public static byte[] merge(byte[] sample1, byte[] sample2) {
		List<AudioInputStream> audioInputStreamList = new ArrayList<AudioInputStream>();
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(sample1);
		AudioInputStream audioInputStream = null;
		try
		{
			audioInputStream = AudioSystem.getAudioInputStream(byteArrayInputStream);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		audioInputStreamList.add(audioInputStream);
		
		byteArrayInputStream = new ByteArrayInputStream(sample2);
		audioInputStream = null;
		try
		{
			audioInputStream = AudioSystem.getAudioInputStream(byteArrayInputStream);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		audioInputStreamList.add(audioInputStream);
		
		AudioInputStream audioMixFloatInputStream = new MixingFloatAudioInputStream(audioInputStream.getFormat(), audioInputStreamList);
		
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		try {
			AudioSystem.write(audioMixFloatInputStream, AudioFileFormat.Type.WAVE, byteArrayOutputStream);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return byteArrayOutputStream.toByteArray();
	}
	
}
