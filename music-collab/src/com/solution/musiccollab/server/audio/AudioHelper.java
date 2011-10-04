package com.solution.musiccollab.server.audio;

public class AudioHelper {
	
	public static byte[] loopSample(byte[] sample, int loops) {
		byte[] loopedSample = new byte[sample.length * loops];
		for(int i = 0; i < loops; i++) {
			for(int j = 0; j < sample.length; j++) {
				loopedSample[i*sample.length + j] = sample[j];
			}
		}
		
		return loopedSample;
	}
	
}
