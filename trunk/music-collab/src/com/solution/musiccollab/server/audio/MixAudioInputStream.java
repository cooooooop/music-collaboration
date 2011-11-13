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
import com.solution.musiccollab.shared.value.MixDAO;
import com.solution.musiccollab.shared.value.MixDetails;

public class MixAudioInputStream extends ByteArrayOutputStream {
	private List<ByteArrayInputStream> byteArrayInputStreams;
	private MixDAO mixDAO;

	public MixAudioInputStream(MixDAO mixDAO, List<ByteArrayInputStream> byteArrayInputStreams) {
		this.byteArrayInputStreams = byteArrayInputStreams;
		this.mixDAO = mixDAO;
	}
	
	@Override
	public synchronized byte[] toByteArray() {
		//read byteArrayInputStreams, mix, return byte array
		
		//primitive attempt
		
		WAVSound finalOutput = null;
		for(int i = 0; i < byteArrayInputStreams.size(); i++) {
			ByteArrayInputStream input = byteArrayInputStreams.get(i);
			MixDetails details = mixDAO.getMixDetailsList().get(i);
			
			List<Byte> bytes = new ArrayList<Byte>();
			
			int newByte;
			while((newByte = input.read()) != -1) {
				bytes.add(new Byte((byte) newByte));
			}
			
			byte[] array = new byte[bytes.size()];
			for(int j = 0; j < bytes.size(); j++) {
				array[j] = bytes.get(j);
			}
			
			if(finalOutput == null)
				finalOutput = new WAVSound(array, details);
			else
				finalOutput.mix(new WAVSound(array, details));
				
				
		}
		
		return finalOutput.getData();
	}

}