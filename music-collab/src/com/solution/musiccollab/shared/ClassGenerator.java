package com.solution.musiccollab.shared;

import com.solution.musiccollab.shared.view.AudioFileItemPanel;
import com.solution.musiccollab.shared.view.AudioFileLiteItemPanel;
import com.solution.musiccollab.shared.view.MixerItemPanel;

/**
 * This is bad... Don't do this. GWT cannot use Class.newInstance, 
 * or Class.getName so this is required when using generics/reflection
 *
 */

public class ClassGenerator {
	public static Object newInstance(String name) {
		if(name.equals("AudioFileItemPanel"))
			return new AudioFileItemPanel();
		else if(name.equals("AudioFileLiteItemPanel"))
			return new AudioFileLiteItemPanel();
		else if(name.equals("MixerItemPanel"))
			return new MixerItemPanel();
		
		return null;
		
	}
}
