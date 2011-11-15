package com.solution.musiccollab.shared.view.editor;

import com.google.gwt.canvas.dom.client.Context2d;

public class CanvasUtil {
	
	public static void drawRoundedRect(Context2d context, double x, double y, double w, double h, double r) {
	    context.beginPath();
	    context.moveTo(x + r, y);
	    context.lineTo(x + w - r, y);
	    context.quadraticCurveTo(x + w, y, x + w, y + r);
	    context.lineTo(x + w, y + h - r);
	    context.quadraticCurveTo(x + w, y + h, x + w - r, y + h);
	    context.lineTo(x + r, y + h);
	    context.quadraticCurveTo(x, y + h, x, y + h - r);
	    context.lineTo(x, y + r);
	    context.quadraticCurveTo(x, y, x + r, y);
	    context.closePath();
	    context.fill();
	}
	
	public static void drawRoundedRectStroke(Context2d context, double x, double y, double w, double h, double r) {
		context.beginPath();
	    context.moveTo(x + r, y);
	    context.lineTo(x + w - r, y);
	    context.quadraticCurveTo(x + w, y, x + w, y + r);
	    context.lineTo(x + w, y + h - r);
	    context.quadraticCurveTo(x + w, y + h, x + w - r, y + h);
	    context.lineTo(x + r, y + h);
	    context.quadraticCurveTo(x, y + h, x, y + h - r);
	    context.lineTo(x, y + r);
	    context.quadraticCurveTo(x, y, x + r, y);
	    context.closePath();
	    context.stroke();
	}
}
