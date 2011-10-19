package com.solution.musicservice;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Main {

	private static String fileRoot = "C:\\Documents and Settings\\Curtis Cooper\\My Documents\\workspace\\ServiceProject\\resource\\";
	
	private static byte[] motha_reverse_ogg = getBytesFromFile(new File(fileRoot + "motha_reverse.ogg"));
	private static byte[] motha_ogg = getBytesFromFile(new File(fileRoot + "motha.ogg"));
	private static byte[] motha_reverse_wav = getBytesFromFile(new File(fileRoot + "motha_reverse.wav"));
	private static byte[] motha_wav = getBytesFromFile(new File(fileRoot + "motha.wav"));
	private static byte[] motha_mp3 = getBytesFromFile(new File(fileRoot + "motha.mp3"));
	
	/**
	 * This is the start of the program if you run/debug this file.
	 */
	public static void main(String[] args) throws Exception {
		//System.out.println("Hello World");
		
		//writeBytesToFile("mothaMerge.wav", AudioUtil.merge(motha_wav, motha_reverse_wav));
		//writeBytesToFile("mothaConcat.wav", AudioUtil.concat(motha_wav, motha_reverse_wav));
		//AudioUtil.decodeOgg(motha_ogg);
		AudioUtil.decodeMp3(motha_mp3);
	}
	
	// Returns the contents of the file in a byte array.
	public static byte[] getBytesFromFile(File file) {
		InputStream is = null;
		byte[] bytes = null;
		try {
			is = new FileInputStream(file);

		    // Get the size of the file
		    long length = file.length();
	
		    // You cannot create an array using a long type.
		    // It needs to be an int type.
		    // Before converting to an int type, check
		    // to ensure that file is not larger than Integer.MAX_VALUE.
		    if (length > Integer.MAX_VALUE) {
		        // File is too large
		    }
	
		    // Create the byte array to hold the data
		    bytes = new byte[(int)length];
	
		    // Read in the bytes
		    int offset = 0;
		    int numRead = 0;
		    while (offset < bytes.length
		           && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
		        offset += numRead;
		    }
	
		    // Ensure all the bytes have been read in
		    if (offset < bytes.length) {
		        throw new IOException("Could not completely read file "+file.getName());
		    }
	
		    // Close the input stream and return bytes
		    is.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	    return bytes;
	}
	
	public static void writeBytesToFile(String strFilePath, byte[] bytes) {
		try {
			FileOutputStream fos = new FileOutputStream(strFilePath);
			fos.write(bytes);
	 
			fos.close(); 
	 
		}
	    catch(Exception e) {
	    	e.printStackTrace();
	    }
	}
}
