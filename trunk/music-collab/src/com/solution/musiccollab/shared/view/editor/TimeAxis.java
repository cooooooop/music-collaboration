package com.solution.musiccollab.shared.view.editor;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.solution.musiccollab.shared.value.MixDetails;

public class TimeAxis {
	
	private long length;
	private int width;
	private int height = 30;
	
	private CssColor axisBGColor = CssColor.make("#333333");
	private CssColor axisFGColor = CssColor.make("#AAAAAA");
	
	public TimeAxis(long length, int width) {
		this.length = length;
		this.width = width;
	}
	
	public void update(long length) {
		this.length = length;
	}
	
	public void draw(Context2d context) {
		context.setFillStyle(axisBGColor);
		context.fillRect(0, 0, width, height);
		
		context.setFillStyle(axisFGColor);
		long timeInterval = length / 6;
		double spaceInterval = ((double) width) / 6;
		double x = 0;
		for(long i = 0; i < length; i += timeInterval) {
			String time = String.valueOf(((double) i) / 1000);
			context.fillText(time, x, 10);
			context.fillRect(x, 11, 1, 19);
			x += spaceInterval;
		}
		context.fillRect(0, 11, width, 1);
	}
	
	public double timePerPixel() {
		return (double) length / (double) width;
	}
	
	public double sampleStart(MixDetails mixDetails) {
		return ((double) mixDetails.getStartTime()) / timePerPixel();
	}
	
	public double sampleWidth(MixDetails mixDetails) {
		return ((double) mixDetails.getTrimEndTime() - (double) mixDetails.getTrimStartTime()) / timePerPixel();
	}
	
	public int getHeight() {
		return height;
	}

}
