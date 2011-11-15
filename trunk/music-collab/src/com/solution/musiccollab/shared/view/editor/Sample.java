package com.solution.musiccollab.shared.view.editor;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.solution.musiccollab.shared.value.MixDetails;

public class Sample {
	
	private MixDetails mixDetails;
	private CssColor sampleBGColor = CssColor.make("rgb(80,202,63)");
	private CssColor sampleFGColor = CssColor.make("rgb(0,0,0)");
	private TimeAxis timeAxis;
	private int track;
	private int yOffset;
	private int xOffset;
	private boolean selected = false;
	
	private int height = 50;
	private int interval = 2;
	
	public Sample(MixDetails mixDetails, int track, int yOffset, int xOffset, TimeAxis timeAxis) {
		this.mixDetails = mixDetails;
		this.track = track;
		this.yOffset = yOffset;
		this.xOffset = xOffset;
		this.timeAxis = timeAxis;
	}
	
	public void draw(Context2d context) {
		
		double x1 = xOffset + timeAxis.sampleStart(mixDetails);
		double x2 = x1 + timeAxis.sampleWidth(mixDetails);

		context.setFillStyle(sampleBGColor);

		CanvasUtil.drawRoundedRect(context, x1, yOffset + height * (track - 1) + interval * (track - 1), timeAxis.sampleWidth(mixDetails), height, 5);
		
		if(selected) {
			context.setFillStyle(sampleFGColor);
			CanvasUtil.drawRoundedRectStroke(context, x1, yOffset + height * (track - 1) + interval * (track - 1), timeAxis.sampleWidth(mixDetails), height, 5);
			context.fillText("[x]", x1 + timeAxis.sampleWidth(mixDetails) - 20, yOffset + height * (track - 1) + interval * (track - 1) + 15);
		}
		
		//TODO: draw the wave form - probably need to generate image on server instead of sending back data points
//		if(mixDetails.getData() != null) {
//			double x = x1;
//			context.beginPath();
//			context.setStrokeStyle(sampleFGColor);
//			context.setLineWidth(0.5);
//			double lastX = x;
//			double lastY = yOffset + height * (track - 1) + interval * (track - 1) + 25;
//			for(byte aByte : mixDetails.getData()) {
//				if(x2 < x)
//					break;
//				
//				double normalizedY = yOffset + height * (track - 1) + interval * (track - 1) + 25 + (aByte * 25) / 128;
//				
//				context.moveTo(lastX, lastY);
//				context.lineTo(x, normalizedY);
//				
//				lastX = x;
//				lastY = normalizedY;
//				
//				x++;
//			}
//			context.closePath();
//			context.stroke();
//		}
	}
	
	public String getCursor(int mouseX, int mouseY) {
		double x1 = xOffset + timeAxis.sampleStart(mixDetails);
		double y1 = yOffset + height * (track - 1) + interval * (track - 1);
		double x2 = x1 + timeAxis.sampleWidth(mixDetails);
		double y2 = y1 + height;
		
		if(isOverRemove(mouseX, mouseY))
			return "default";
		
		if(x1 <= mouseX && y1 <= mouseY	&& x2 >= mouseX && y2 >= mouseY) {
			if(Math.abs(x1 - mouseX) < 5 || Math.abs(x2 - mouseX) < 5) 
				return "col-resize";
			return "move";
		}
		
		return null;
	}
	
	public boolean isMove(int mouseX, int mouseY) {
		double x1 = xOffset + timeAxis.sampleStart(mixDetails);
		double y1 = yOffset + height * (track - 1) + interval * (track - 1);
		double x2 = x1 + timeAxis.sampleWidth(mixDetails);
		double y2 = y1 + height;
		
		if(x1 <= mouseX && y1 <= mouseY	&& x2 >= mouseX && y2 >= mouseY) {

			if(Math.abs(x1 - mouseX) < 5 || Math.abs(x2 - mouseX) < 5) 
				return false;
			return true;
		}
		
		return false;
	}
	
	public boolean isTrimStart(int mouseX, int mouseY) {
		double x1 = xOffset + timeAxis.sampleStart(mixDetails);
		double y1 = yOffset + height * (track - 1) + interval * (track - 1);
		double x2 = x1 + timeAxis.sampleWidth(mixDetails);
		double y2 = y1 + height;
		
		if(x1 <= mouseX && y1 <= mouseY	&& x2 >= mouseX && y2 >= mouseY) {
			if(Math.abs(x1 - mouseX) < 5) 
				return true;
		}
		
		return false;
	}
	
	public boolean isTrimEnd(int mouseX, int mouseY) {
		double x1 = xOffset + timeAxis.sampleStart(mixDetails);
		double y1 = yOffset + height * (track - 1) + interval * (track - 1);
		double x2 = x1 + timeAxis.sampleWidth(mixDetails);
		double y2 = y1 + height;
		
		if(x1 <= mouseX && y1 <= mouseY	&& x2 >= mouseX && y2 >= mouseY) {
			if(Math.abs(x2 - mouseX) < 5) 
				return true;
		}
		
		return false;
	}
	
	public boolean isOverRemove(int mouseX, int mouseY) {
		double x1 = xOffset + timeAxis.sampleStart(mixDetails);
		double y1 = yOffset + height * (track - 1) + interval * (track - 1);
		double x2 = x1 + timeAxis.sampleWidth(mixDetails);
		double y2 = y1 + height;
		
		if(x1 + timeAxis.sampleWidth(mixDetails) - 20 <= mouseX && y1 + 5 <= mouseY	&& x2 >= mouseX && yOffset + height * (track - 1) + interval * (track - 1) + 15 >= mouseY) {
			if(Math.abs(x2 - mouseX) < 5) 
				return false;
			return true;
		}
		
		return false;
	}

	public MixDetails getMixDetails() {
		return mixDetails;
	}

	public void setMixDetails(MixDetails mixDetails) {
		this.mixDetails = mixDetails;
	}

	public int getTrack() {
		return track;
	}

	public void setTrack(int track) {
		this.track = track;
	}

	public int getyOffset() {
		return yOffset;
	}

	public void setyOffset(int yOffset) {
		this.yOffset = yOffset;
	}

	public int getxOffset() {
		return xOffset;
	}

	public void setxOffset(int xOffset) {
		this.xOffset = xOffset;
	}
	
	public boolean isSelected() {
		return selected;
	}
	
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}
