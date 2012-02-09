package com.solution.musiccollab.shared.view;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FormPanel.*;
import com.solution.musiccollab.client.interfaces.AudioService;
import com.solution.musiccollab.client.interfaces.AudioServiceAsync;
import com.solution.musiccollab.shared.value.AudioFileDAO;

public class FileUploadPanel extends Composite implements IUpdatable{

	@UiField
	FormPanel form;
	
	@UiField
	FileUpload fileUpload;
	
	@UiField
	Button submitButton;
	
	@UiField
	TextBox txtTitle;
	
	@UiField
	HorizontalPanel radioButtonPanel;
	
	@UiField
	AudioFilesList audioFilesList;
	
	@UiField
	VerticalPanel parentPanel;
	
	@UiField
	Label infoLabel;
	
	@UiTemplate("uibinder/FileUploadPanel.ui.xml")
	interface MyUiBinder extends UiBinder<Widget, FileUploadPanel> { }
	private static final MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	private List<AudioFileDAO> files = new ArrayList<AudioFileDAO>();
	
	public FileUploadPanel() {
		initWidget(uiBinder.createAndBindUi(this));
		form.setEncoding(FormPanel.ENCODING_MULTIPART);
		form.setMethod(FormPanel.METHOD_POST);
		 
	    form.addSubmitHandler(new FormPanel.SubmitHandler() {
	    	public void onSubmit(SubmitEvent event) {
	    		if (txtTitle.getText().length() == 0) {
	    			Window.alert("The text box must not be empty");
	    			event.cancel();
	    		}
	    	}
	    });
	    
	    form.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
	    	public void onSubmitComplete(SubmitCompleteEvent event) {
	    		String value = event.getResults();
	    		if(value == null || !value.equals("")) {
	    			if(value != null)
	    				getAudioFile(value);
	    			else
	    				getAudioFile("njTsYBcvMlCLT5Cyq7MHdw");
    			}
	    		else {
	    			Window.alert("There was an error in uploading your file.");
	    		}
	    		
	    	}
	    });		
	    
	    fileUpload.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				txtTitle.setText(fileUpload.getFilename());
			}
		});
		
	    submitButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				form.submit();
			}
		});
	    
	    RadioButton rbNo = new RadioButton("group", "<a rel=\"license\" href=\"http://creativecommons.org/licenses/by-nc/3.0/\" target=\"_blank\"><img alt=\"Creative Commons License\" style=\"border-width:0\" src=\"http://i.creativecommons.org/l/by-nc/3.0/80x15.png\" /></a>", true);
	    rbNo.setValue(true);
	    rbNo.setName("ccRadio");
	    rbNo.setFormValue("no");
	    radioButtonPanel.add(rbNo);
	    RadioButton rbYes = new RadioButton("group", "<a rel=\"license\" href=\"http://creativecommons.org/licenses/by/3.0/\" target=\"_blank\"><img alt=\"Creative Commons License\" style=\"border-width:0\" src=\"http://i.creativecommons.org/l/by/3.0/80x15.png\" /></a>", true);
		rbYes.setName("ccRadio");
		rbYes.setFormValue("yes");
	    radioButtonPanel.add(rbYes);
	}
	
	private void getAudioFile(String filePath) {
		((AudioServiceAsync) GWT.create(AudioService.class)).getAudioFile(filePath, new AsyncCallback<AudioFileDAO>() {
			
			@Override
			public void onSuccess(AudioFileDAO result) {
				files.add(result);
				audioFilesList.setItems(files, "AudioFileItemPanel");
				update();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error");
			}
		});
	}
	
	@Override
	public void update() {
		audioFilesList.title.setText("Uploads");
		audioFilesList.setVisible(audioFilesList.getSize() > 0);
		form.reset();
	}
	
	public AudioFilesList getAudioFilesList() {
		return audioFilesList;
	}
	
	public void setUploadURL(String url) {
		form.setAction(url);
	}

}
