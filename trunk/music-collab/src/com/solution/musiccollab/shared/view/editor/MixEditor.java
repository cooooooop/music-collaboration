package com.solution.musiccollab.shared.view.editor;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootPanel;
import com.solution.musiccollab.shared.value.MixDAO;
import com.solution.musiccollab.shared.value.MixDetails;

public class MixEditor extends Composite {

	private Canvas canvas;
	private Context2d context;
	
	private MixDAO mixDAO;
	
	private CssColor redrawColor = CssColor.make("rgba(255,255,255,0.6)");
	private CssColor sampleBGColor = CssColor.make("rgb(80,202,63)");
	private CssColor textColor = CssColor.make("rgb(0,0,0)");
	private int height = 400;
	private int width = 300;
	private int mouseX, mouseY, lastX = -1;
	private MixDetails editingDetails;
	private boolean mouseDown = false;
	private boolean mouseDownOverRemove = false;
	private int action = -1; //0 is move, 1 is trim start, 2 is trim end
	
	private TimeAxis timeAxis = new TimeAxis(30000, width);
	private List<Sample> samples;
	
	public MixEditor() {
		canvas = Canvas.createIfSupported();
		initWidget(canvas);
		
		context = canvas.getContext2d();
		
		canvas.setWidth(width + "px");
	    canvas.setHeight(height + "px");
	    canvas.setCoordinateSpaceWidth(width);
	    canvas.setCoordinateSpaceHeight(height);
		
		final Timer timer = new Timer() {
			@Override
			public void run() {
				doUpdate();
			}
	    };
	    
	    timer.scheduleRepeating(25);
	    
	    canvas.addMouseMoveHandler(new MouseMoveHandler() {
	    	public void onMouseMove(MouseMoveEvent event) {
	    		mouseX = event.getRelativeX(canvas.getElement());
	    		mouseY = event.getRelativeY(canvas.getElement());

	    		if(editingDetails != null && action != -1) {
	    			long additionalTime = new Double(((double) (mouseX - lastX)) * timeAxis.timePerPixel()).longValue();
	    			if(action == 0) {
	    				long startTime = editingDetails.getStartTime() + additionalTime;
	    				if(startTime >= 0)
	    					editingDetails.setStartTime(startTime);
	    			}
	    			else if(action == 1) {
	    				long startTrim = editingDetails.getTrimStartTime() + additionalTime;
	    				if(startTrim >= 0) {
	    					editingDetails.setTrimStartTime(startTrim);
	    				
		    				long startTime = editingDetails.getStartTime();
		    				editingDetails.setStartTime(startTime + additionalTime);
	    				}
	    			}
	    			else if(action == 2) {
	    				long endTrim = editingDetails.getTrimEndTime() + additionalTime;
	    				if(editingDetails.getAudioLength() >= endTrim)
	    					editingDetails.setTrimEndTime(endTrim);
	    			}
	    			lastX = mouseX;
	    		}
	    		
	    		boolean specialCursor = false;
	    		for(Sample sample : samples) {
			    	String cursor = sample.getCursor(mouseX, mouseY);
			    	if(cursor != null) {
			    		specialCursor = true;
		    		    DOM.setStyleAttribute(RootPanel.getBodyElement(), "cursor", cursor);
		    		    sample.setSelected(true);
			    	}
			    	else
			    		sample.setSelected(false);
			    }
	    		
	    		if(!specialCursor)
	    		    DOM.setStyleAttribute(RootPanel.getBodyElement(), "cursor", "default");
	        }
	    });
		
		canvas.addMouseDownHandler(new MouseDownHandler() {
			
			@Override
			public void onMouseDown(MouseDownEvent event) {
				mouseDown = true;

				boolean found = false;
	    		for(Sample sample : samples) {
			    	if(sample.isMove(mouseX, mouseY) || sample.isTrimEnd(mouseX, mouseY) || sample.isTrimStart(mouseX, mouseY)) {
			    		if(sample.isOverRemove(mouseX, mouseY)) {
			    			mouseDownOverRemove = true;
			    		}
			    		else {
			    			mouseDownOverRemove = false;
				    		lastX = event.getRelativeX(canvas.getElement());
				    		if(sample.isMove(mouseX, mouseY))
				    			action = 0;
				    		else if(sample.isTrimStart(mouseX, mouseY))
				    			action = 1;
				    		else if(sample.isTrimEnd(mouseX, mouseY))
				    			action = 2;
			    		}
			    		editingDetails = sample.getMixDetails();
			    		found = true;
			    		break;
			    	}
			    }
	    		
	    		if(!found) {
	    			lastX = -1;
		    		editingDetails = null;
		    		action = -1;
	    		}
			}
		});
		
		canvas.addMouseUpHandler(new MouseUpHandler() {
			
			@Override
			public void onMouseUp(MouseUpEvent event) {
				for(Sample sample : samples) {
		    		if(sample.isOverRemove(mouseX, mouseY) && mouseDownOverRemove) {
		    			mixDAO.removeMixDetail(sample.getMixDetails());
		    			update();
		    			break;
		    		}
				}
				
				mouseDown = false;
				mouseDownOverRemove = false;
				
				lastX = -1;
	    		editingDetails = null;
	    		action = -1;
			}
		});
		
		canvas.addMouseOutHandler(new MouseOutHandler() {
			
			@Override
			public void onMouseOut(MouseOutEvent event) {
				for(Sample sample : samples) {
		    		sample.setSelected(false);
				}
				
				mouseDown = false;
				mouseDownOverRemove = false;
				
				lastX = -1;
	    		editingDetails = null;
	    		action = -1;
				
				DOM.setStyleAttribute(RootPanel.getBodyElement(), "cursor", "default");
			}
		});
		
	}
	
	public void setMixDAO(MixDAO mixDAO) {
		this.mixDAO = mixDAO;
		update();
	}
	
	private void update() {
		if(mixDAO != null) {
			int track = 1;
			samples = new ArrayList<Sample>();
		    for(MixDetails mixDetails : mixDAO.getMixDetailsList()) {
		    	samples.add(new Sample(mixDetails, track, timeAxis.getHeight(), 0, timeAxis));
		    	track++;
		    }
		}
	}

	public void doUpdate() {
		context.setFillStyle(redrawColor);
	    context.fillRect(0, 0, width, height);
	    
	    if(mixDAO != null) {
	    	timeAxis.draw(context);
		    
		    for(Sample sample : samples) {
		    	sample.draw(context);
		    }
		    
	    }
	}
}
