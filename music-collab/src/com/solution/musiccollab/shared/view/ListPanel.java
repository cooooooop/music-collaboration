package com.solution.musiccollab.shared.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ListPanel extends Composite {

	@UiField
	VerticalPanel rootPanel;
	
	@UiField
	Label title;
	
	@UiTemplate("uibinder/ListPanel.ui.xml")
	interface MyUiBinder extends UiBinder<Widget, ListPanel> { }
	private static final MyUiBinder uiBinder = GWT.create(MyUiBinder.class);
	
	public ListPanel() {
		initWidget(uiBinder.createAndBindUi(this));	
	}
	
}
