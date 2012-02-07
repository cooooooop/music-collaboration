package com.solution.musiccollab.client;

import java.util.ArrayList;
import java.util.List;

import com.allen_sauer.gwt.voices.client.Sound;
import com.allen_sauer.gwt.voices.client.SoundController;
import com.allen_sauer.gwt.voices.client.handler.PlaybackCompleteEvent;
import com.allen_sauer.gwt.voices.client.handler.SoundHandler;
import com.allen_sauer.gwt.voices.client.handler.SoundLoadStateChangeEvent;
import com.google.appengine.api.datastore.Text_CustomFieldSerializer;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DeckLayoutPanel;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.solution.musiccollab.client.interfaces.AudioService;
import com.solution.musiccollab.client.interfaces.AudioServiceAsync;
import com.solution.musiccollab.client.interfaces.UsersService;
import com.solution.musiccollab.client.interfaces.UsersServiceAsync;
import com.solution.musiccollab.shared.event.DAOEvent;
import com.solution.musiccollab.shared.event.DAOEventHandler;
import com.solution.musiccollab.shared.event.FileSelectEvent;
import com.solution.musiccollab.shared.event.FileSelectEventHandler;
import com.solution.musiccollab.shared.event.NavigationEvent;
import com.solution.musiccollab.shared.event.NavigationEventHandler;
import com.solution.musiccollab.shared.model.Model;
import com.solution.musiccollab.shared.value.AudioFileDAO;
import com.solution.musiccollab.shared.value.MixDAO;
import com.solution.musiccollab.shared.value.MixDetails;
import com.solution.musiccollab.shared.value.UserDAO;
import com.solution.musiccollab.shared.view.AudioFileItemPanel;
import com.solution.musiccollab.shared.view.AudioFileLiteItemPanel;
import com.solution.musiccollab.shared.view.AudioFilesList;
import com.solution.musiccollab.shared.view.BodyPanel;
import com.solution.musiccollab.shared.view.DirectoryPage;
import com.solution.musiccollab.shared.view.FilePage;
import com.solution.musiccollab.shared.view.FileUploadPanel;
import com.solution.musiccollab.shared.view.HeaderBar;
import com.solution.musiccollab.shared.view.IAudioList;
import com.solution.musiccollab.shared.view.IUpdatable;
import com.solution.musiccollab.shared.view.MemberPage;
import com.solution.musiccollab.shared.view.MixPage;
import com.solution.musiccollab.shared.view.MixerItemPanel;
import com.solution.musiccollab.shared.view.MixerList;
import com.solution.musiccollab.shared.view.MixerPanel;
import com.solution.musiccollab.shared.view.MixesList;
import com.solution.musiccollab.shared.view.UsersList;

public class Music_collab implements EntryPoint, NavigationEventHandler, FileSelectEventHandler, DAOEventHandler {
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	private final UsersServiceAsync usersService = GWT.create(UsersService.class);
	private final AudioServiceAsync audioService = GWT.create(AudioService.class);
	private final SoundController soundController = new SoundController();
	
	private List<IUpdatable> updatableWidgets;
	private HeaderBar headerBar = new HeaderBar();
	private RootLayoutPanel rootLayoutPanel;
	private BodyPanel bodyPanel = new BodyPanel();
	private FileUploadPanel fileUploadPanel = new FileUploadPanel();
	private MemberPage memberPage = new MemberPage();
	private FilePage filePage = new FilePage();
	private MixPage mixPage = new MixPage();
	private DirectoryPage directoryPage = new DirectoryPage();
	private MixerPanel mixerPanel = new MixerPanel();
	private DeckLayoutPanel bodyDeck = new DeckLayoutPanel();
	private ScrollPanel scrollPanel = new ScrollPanel();
	private VerticalPanel innerScrollPanel = new VerticalPanel();
	
	public void onModuleLoad() {
		updatableWidgets = new ArrayList<IUpdatable>();
		updatableWidgets.add(headerBar);
		updatableWidgets.add(bodyPanel);
		updatableWidgets.add(fileUploadPanel);
		updatableWidgets.add(memberPage);
		updatableWidgets.add(filePage);
		updatableWidgets.add(mixPage);
		updatableWidgets.add(directoryPage);
		updatableWidgets.add(mixerPanel);
		headerBar.addNavigationEventHandler(this);
		
		rootLayoutPanel = (RootLayoutPanel) RootLayoutPanel.get();
		rootLayoutPanel.add(scrollPanel);
		rootLayoutPanel.setWidgetLeftRight(scrollPanel, 0, Unit.PX, 0, Unit.PX);
		rootLayoutPanel.setWidgetTopBottom(scrollPanel, 0, Unit.PX, 0, Unit.PX);
		scrollPanel.add(innerScrollPanel);
		scrollPanel.getElement().setAttribute("style", scrollPanel.getElement().getAttribute("style") + "background-color:#4E6BAE;");

		innerScrollPanel.add(headerBar);
		innerScrollPanel.setWidth("100%");
		innerScrollPanel.setHeight("100%");
		innerScrollPanel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		
		bodyDeck.add(bodyPanel);
		bodyDeck.add(fileUploadPanel);
		bodyDeck.add(memberPage);
		bodyDeck.add(filePage);
		bodyDeck.add(mixPage);
		bodyDeck.add(directoryPage);
		bodyDeck.add(mixerPanel);
		
		bodyPanel.getAudioFilesList().addFileSelectEventHandler(this);
		bodyPanel.getAudioFilesList().addNavigationEventHandler(this);
		bodyPanel.getAudioFilesList().addDAOEventHandler(this);
		bodyPanel.getMixesList().addFileSelectEventHandler(this);
		bodyPanel.getMixesList().addNavigationEventHandler(this);
		bodyPanel.getMixesList().addDAOEventHandler(this);
		bodyPanel.getUsersList().addNavigationEventHandler(this);
		memberPage.addNavigationEventHandler(this);
		memberPage.getAudioFilesList().addNavigationEventHandler(this);
		directoryPage.addNavigationEventHandler(this);
		mixerPanel.getMixerList().addNavigationEventHandler(this);
		mixerPanel.getMixerList().addFileSelectEventHandler(this);
		mixerPanel.getMixerList().addDAOEventHandler(this);
		mixerPanel.getAudioList().addNavigationEventHandler(this);
		mixerPanel.getAudioList().addFileSelectEventHandler(this);
		fileUploadPanel.getAudioFilesList().addFileSelectEventHandler(this);
		fileUploadPanel.getAudioFilesList().addNavigationEventHandler(this);
		fileUploadPanel.getAudioFilesList().addDAOEventHandler(this);
		filePage.getAudioFilesList().addFileSelectEventHandler(this);
		filePage.getAudioFilesList().addNavigationEventHandler(this);
		filePage.getAudioFilesList().addDAOEventHandler(this);
		filePage.addNavigationEventHandler(this);
		filePage.addDAOEventHandler(this);
		mixPage.getAudioFilesList().addFileSelectEventHandler(this);
		mixPage.getAudioFilesList().addNavigationEventHandler(this);
		mixPage.getAudioFilesList().addDAOEventHandler(this);
		mixPage.addNavigationEventHandler(this);
		mixPage.addDAOEventHandler(this);
		
		memberPage.getAudioFilesList().addFileSelectEventHandler(this);
		directoryPage.getUsersList().addNavigationEventHandler(this);
		
		innerScrollPanel.add(bodyDeck);
		bodyDeck.setWidth("55em");
		
		loadCurrentUser();
		
	}
	
	private void getUsersList(final UsersList list) {
		usersService.getAll(new AsyncCallback<List<UserDAO>>() {
			
			@Override
			public void onSuccess(List<UserDAO> result) {
				list.setItems(result);
				updateUI();
			}
			
			@Override
			public void onFailure(Throwable caught) {
			}
		});
	}
	
	private void getMixDetailsList(final MixDAO mix, final MixerList list) {
		audioService.getMixDetailsList(mix, new AsyncCallback<List<MixDetails>>() {
			
			@Override
			public void onSuccess(List<MixDetails> result) {
				mix.setMixDetailsList(result);
				list.setItems(result);
				updateUI();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	
	private void getMixDetailsList(final MixDAO mix, final AudioFilesList list) {
		audioService.getMixDetailsList(mix, new AsyncCallback<List<MixDetails>>() {
			
			@Override
			public void onSuccess(List<MixDetails> result) {
				List<AudioFileDAO> audioFiles = new ArrayList<AudioFileDAO>();
				for (MixDetails details : result) {
					audioFiles.add(details.getAudioFile());
				}
				
				list.setItems(audioFiles, "AudioFileItemPanel");
				updateUI();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	
	private void saveMix(final MixDAO mix) {
		audioService.saveMix(mix, new AsyncCallback<MixDAO>() {
			
			@Override
			public void onSuccess(MixDAO result) {
				getMixDetailsList(result, mixerPanel.getMixerList());
				mixerPanel.setMixDAO(result);
				updateUI();
			}
			
			@Override
			public void onFailure(Throwable caught) {
			}
		});
	}
	
	private void getAudioFilesList(final IAudioList list, final String itemPanelType, final String contentType) {
		audioService.getAll(contentType, new AsyncCallback<List<AudioFileDAO>>() {
			
			@Override
			public void onSuccess(List<AudioFileDAO> result) {
				list.setItems(result, itemPanelType);
				updateUI();
			}
			
			@Override
			public void onFailure(Throwable caught) {
			}
		});
	}
	
	private void getMixesList(final MixesList list) {
		audioService.getAllMixes(new AsyncCallback<List<MixDAO>>() {
			
			@Override
			public void onSuccess(List<MixDAO> result) {
				list.setItems(result);
				updateUI();
			}
			
			@Override
			public void onFailure(Throwable caught) {
			}
		});
	}
	
	private void getAudioFilesList(final AudioFilesList list, UserDAO userDAO) {
		audioService.getAudioByUser(userDAO, new AsyncCallback<List<AudioFileDAO>>() {
			
			@Override
			public void onSuccess(List<AudioFileDAO> result) {
				list.setItems(result, "AudioFileItemPanel");
				updateUI();
			}
			
			@Override
			public void onFailure(Throwable caught) {
			}
		});
	}
	
	private void getRelatedAudioFilesList(final AudioFilesList list, AudioFileDAO audioFile) {
		audioService.getRelatedAudio(audioFile, new AsyncCallback<List<AudioFileDAO>>() {
			
			@Override
			public void onSuccess(List<AudioFileDAO> result) {
				list.setItems(result, "AudioFileItemPanel");
				updateUI();
			}
			
			@Override
			public void onFailure(Throwable caught) {
			}
		});
	}
	
	private void getLastTenUsers(final UsersList list) {
		usersService.getUsersLimit(10, new AsyncCallback<List<UserDAO>>() {
			
			@Override
			public void onSuccess(List<UserDAO> result) {
				list.setItems(result);
				updateUI();
			}
			
			@Override
			public void onFailure(Throwable caught) {
			}
		});
	}
	
	private void getLastFiveAudioFiles(final AudioFilesList list) {
		audioService.getAudioFilesLimit(5, new AsyncCallback<List<AudioFileDAO>>() {
			
			@Override
			public void onSuccess(List<AudioFileDAO> result) {
				list.setItems(result, "AudioFileItemPanel");
				updateUI();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				System.out.println("Help");
			}
		});
	}
	
	private void loadCurrentUser() {
		usersService.getCurrentUser(new AsyncCallback<UserDAO>() {

			@Override
			public void onFailure(Throwable caught) {
				//label.setText(SERVER_ERROR);				
			}

			@Override
			public void onSuccess(UserDAO result) {
				Model.currentUser = result;
				onHomeNavigation(null);
				updateUI();
			}
		});
	}
	
	private void stopPlaying() {
		if(Model.currentPlayingAudioFileItemPanel != null) {
			Model.currentPlayingAudioFileItemPanel.setPlayStopStatus(false);
			Model.currentPlayingAudioFileItemPanel = null;
			
			if(Model.currentSound != null) {
				Model.currentSound.stop();
				Model.currentSound = null;
			}
		}
		
		if(Model.currentPlayingMixerPanel != null) {
			Model.currentPlayingMixerPanel.setPlayStopStatus(false);
			Model.currentPlayingMixerPanel = null;
			
			if(Model.currentSound != null) {
				Model.currentSound.stop();
				Model.currentSound = null;
			}
		}
		
		if(Model.currentPlayingMixItemPanel != null) {
			Model.currentPlayingMixItemPanel.setPlayStopStatus(false);
			Model.currentPlayingMixItemPanel = null;
			
			if(Model.currentSound != null) {
				Model.currentSound.stop();
				Model.currentSound = null;
			}
		}
	}
	
	private void updateUI() {
		for(IUpdatable updatableWidget : updatableWidgets) {
			updatableWidget.update();
		}
		
//		Object temp = bodyDeck.getVisibleWidget();
//		if(temp instanceof FileUploadPanel) {
//			int try1 = ((FileUploadPanel)temp).getOffsetHeight();
//			System.out.println(try1);
//		}
		
		if(!(bodyDeck.getVisibleWidget() instanceof FileUploadPanel)) //weird issue for file upload panel
			bodyDeck.setHeight(String.valueOf(bodyDeck.getVisibleWidget().getOffsetHeight()) + "px");
		else
			bodyDeck.setHeight(String.valueOf(Window.getClientHeight()) +"px");
	}

	@Override
	public void onLoginRequest(NavigationEvent event) {
		Window.open("login", "_self", "");
	}

	@Override
	public void onLogoutRequest(NavigationEvent event) {
		Window.open("logout", "_self", "");
	}

	@Override
	public void onHomeNavigation(NavigationEvent event) {
		stopPlaying();
		
		bodyDeck.showWidget(bodyPanel);
		getUsersList(bodyPanel.getUsersList());
		getAudioFilesList(bodyPanel.getAudioFilesList(), "AudioFileItemPanel", null);
		getMixesList(bodyPanel.getMixesList());
		updateUI();
	}
	
	@Override
	public void onFileSelected(FileSelectEvent event) {
		String useridArg = "";
		if(Model.currentUser != null)
			useridArg = "&userid=" + Model.currentUser.getUserid();
		Window.open("downloadFile?audioid=" + event.getSelectedFile().getFilePath() + "&action=download" + useridArg, "_self", "");									
	}
	
	@Override
	public void onFilePlay(final FileSelectEvent fileSelectEvent) {
		stopPlaying();
		
		String useridArg = "";
		if(Model.currentUser != null)
			useridArg = "&userid=" + Model.currentUser.getUserid();
		
		Model.currentPlayingAudioFileItemPanel = fileSelectEvent.getAudioItemPanel();
	    Model.currentSound = soundController.createSound(fileSelectEvent.getSelectedFile().getContentType(), "downloadFile?audioid=" + fileSelectEvent.getSelectedFile().getFilePath() + useridArg);
	    Model.currentSound.setLooping(fileSelectEvent.isLooping());
	    Model.currentSound.play();
	    
	    Model.currentSound.addEventHandler(new SoundHandler() {
			
			@Override
			public void onSoundLoadStateChange(SoundLoadStateChangeEvent event) {
				event.getLoadState();
			}
			
			@Override
			public void onPlaybackComplete(PlaybackCompleteEvent event) {
				stopPlaying();
			}
		});
	}
	
	@Override
	public void onFileStop(FileSelectEvent event) {
		stopPlaying();
	}

	@Override
	public void onMemberPageNavigation(NavigationEvent event) {
		stopPlaying();
		
		bodyDeck.showWidget(memberPage);
		getAudioFilesList(memberPage.getAudioFilesList(), event.getUserDAO());
		memberPage.setUserDAO(event.getUserDAO());
		updateUI();
	}

	@Override
	public void onUploadNavigation(NavigationEvent event) {
		stopPlaying();
		usersService.getUploadURL("/uploadFile?userid=" + Model.currentUser.getUserid(), new AsyncCallback<String>() {
			
			@Override
			public void onSuccess(String result) {
				fileUploadPanel.setUploadURL(result);
				bodyDeck.showWidget(fileUploadPanel);
				updateUI();
			}
			
			@Override
			public void onFailure(Throwable caught) {
			}
		});
		
	}

	@Override
	public void onMixerNavigation(final NavigationEvent event) {
		if(event.getMixDAO() != null) {
			stopPlaying();
			getMixDetailsList(event.getMixDAO(), mixerPanel.getMixerList());
			getAudioFilesList(mixerPanel.getAudioList(), "AudioFileLiteItemPanel", "audio/wav");
			mixerPanel.setMixDAO(event.getMixDAO());
			bodyDeck.showWidget(mixerPanel);
		}
		else if(event.getAudioFileDAO() != null) {
			//TODO: this popup panel could be used elsewhere, might deserve it's own file
			//if this request comes from an AudioFileItemPanel then we need to make a new MixDAO
			//however, we first must verify there is someone logged in and we should notify
			//the user that this action is creating a new Mix
			
			final PopupPanel dialog = new PopupPanel(true, true);
			dialog.setPopupPositionAndShow(new PopupPanel.PositionCallback() {
		          public void setPosition(int offsetWidth, int offsetHeight) {
		            int left = (Window.getClientWidth() - offsetWidth) / 3;
		            int top = (Window.getClientHeight() - offsetHeight) / 3;
		            dialog.setPopupPosition(left, top);
		          }
		        });
			VerticalPanel contents = new VerticalPanel();
			contents.getElement().setAttribute("style", "background-color:#C5A159; border-style:solid; border-color:#FFF;");
			contents.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
			contents.setSpacing(5);
			
			dialog.setWidget(contents);
			
			if(Model.currentUser == null) {
				contents.add(new Label("You must be logged in to use this feature."));
				Button ok = new Button("OK");
			    ok.addClickHandler(new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent e) {
						dialog.hide();
					}
			    });
				contents.add(ok);
				dialog.show();
				return;
			}
			else {
				contents.add(new Label("This will create a new Mix Project using the selected Audio File."));
				HorizontalPanel panel1 = new HorizontalPanel();
				HorizontalPanel panel2 = new HorizontalPanel();
				panel1.setWidth("100%");
				panel1.add(new Label("Please provide a name for the Mix Project."));
				final Button ok = new Button("OK");
				final TextBox mixNameTextInput = new TextBox();
				mixNameTextInput.setText(event.getAudioFileDAO().getFileName());
				mixNameTextInput.addChangeHandler(new ChangeHandler() {
					
					@Override
					public void onChange(ChangeEvent event) {
						if(((TextBox)event.getSource()).getText().isEmpty())
							ok.setEnabled(false);
						else if(!ok.isEnabled())
							ok.setEnabled(true);
					}
				});
				Button cancel = new Button("Cancel");
			    ok.addClickHandler(new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent e) {
						MixDAO mixDAO = new MixDAO(null);
						mixDAO.addMixDetail(event.getAudioFileDAO());
						mixDAO.setOwnerUserDAO(event.getUserDAO());
						mixDAO.setMixName(mixNameTextInput.getText());
						saveMix(mixDAO);
						dialog.hide();

						stopPlaying();
						getAudioFilesList(mixerPanel.getAudioList(), "AudioFileLiteItemPanel", "audio/wav");
						mixerPanel.setMixDAO(event.getMixDAO());
						bodyDeck.showWidget(mixerPanel);
					}
			    });
			    cancel.addClickHandler(new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent e) {
						dialog.hide();
					}
			    });
			    panel2.add(ok);
			    panel2.add(cancel);
			    contents.add(panel1);
			    contents.add(mixNameTextInput);
			    contents.add(panel2);
				dialog.show();
				mixNameTextInput.selectAll();
			}
		}
		updateUI();
	}
	
	@Override
	public void onMixSelected(final FileSelectEvent event) {
		audioService.saveMix(event.getSelectedMix(), new AsyncCallback<MixDAO>() {

			@Override
			public void onFailure(Throwable caught) {
				//label.setText(SERVER_ERROR);				
			}

			@Override
			public void onSuccess(MixDAO result) {
				String useridArg = "";
				if(Model.currentUser != null)
					useridArg = "&userid=" + Model.currentUser.getUserid();
				Window.open("downloadFile?audioid=" + event.getSelectedMix().getUniqueID() + "&action=download" + useridArg, "_self", "");									
			}
		});
	}

	@Override
	public void onMixPlay(final FileSelectEvent event) {
		audioService.saveMix(event.getSelectedMix(), new AsyncCallback<MixDAO>() {

			@Override
			public void onFailure(Throwable caught) {
				//label.setText(SERVER_ERROR);				
			}

			@Override
			public void onSuccess(MixDAO result) {
				String useridArg = "";
				if(Model.currentUser != null)
					useridArg = "&userid=" + Model.currentUser.getUserid();
				
				Model.currentPlayingMixerPanel = event.getMixerPanel();
				Model.currentPlayingMixItemPanel = event.getMixItemPanel();
				Model.currentSound = soundController.createSound(event.getSelectedMix().getContentType(), "downloadFile?audioid=" + event.getSelectedMix().getUniqueID() + useridArg);
			    Model.currentSound.play();
			    
			    Model.currentSound.addEventHandler(new SoundHandler() {
					
					@Override
					public void onSoundLoadStateChange(SoundLoadStateChangeEvent event) {
						event.getLoadState();
					}
					
					@Override
					public void onPlaybackComplete(PlaybackCompleteEvent event) {
						stopPlaying();
					}
				});
			}
		});
	}
	
	@Override
	public void onMixStop(FileSelectEvent event) {
		stopPlaying();
	}

	@Override
	public void onDirectoryPageNavigation(NavigationEvent event) {
		stopPlaying();
		
		bodyDeck.showWidget(directoryPage);
		getUsersList(directoryPage.getUsersList());
		updateUI();
	}

	@Override
	public void onSaveMix(final DAOEvent event) {
		audioService.saveMix((MixDAO) event.getDao(), new AsyncCallback<MixDAO>() {

			@Override
			public void onFailure(Throwable caught) {
				//label.setText(SERVER_ERROR);				
			}

			@Override
			public void onSuccess(MixDAO result) {
				event.getDaoEditor().updateDAO(event.getDao());
				updateUI();
			}
		});
	}

	@Override
	public void onDeleteMix(final DAOEvent event) {
		audioService.deleteMix((MixDAO) event.getDao(), new AsyncCallback<Boolean>() {

			@Override
			public void onFailure(Throwable caught) {
				//label.setText(SERVER_ERROR);				
			}

			@Override
			public void onSuccess(Boolean result) {
				event.getDaoEditor().removeDAO(event.getDao());
				updateUI();
			}
		});
	}

	@Override
	public void onSaveAudio(final DAOEvent event) {
		audioService.saveAudioFile((AudioFileDAO) event.getDao(), new AsyncCallback<AudioFileDAO>() {

			@Override
			public void onFailure(Throwable caught) {
				//label.setText(SERVER_ERROR);				
			}

			@Override
			public void onSuccess(AudioFileDAO result) {
				event.getDaoEditor().updateDAO(event.getDao());
				updateUI();
			}
		});
	}

	@Override
	public void onDeleteAudio(final DAOEvent event) {
		audioService.deleteAudioFile((AudioFileDAO) event.getDao(), new AsyncCallback<Boolean>() {

			@Override
			public void onFailure(Throwable caught) {
				//label.setText(SERVER_ERROR);				
			}

			@Override
			public void onSuccess(Boolean result) {
				event.getDaoEditor().removeDAO(event.getDao());
				updateUI();
			}
		});
	}

	@Override
	public void onSaveUser(final DAOEvent event) {
		usersService.saveUser((UserDAO) event.getDao(), new AsyncCallback<UserDAO>() {

			@Override
			public void onFailure(Throwable caught) {
				//label.setText(SERVER_ERROR);				
			}

			@Override
			public void onSuccess(UserDAO result) {
				event.getDaoEditor().updateDAO(event.getDao());
				updateUI();
			}
		});		
	}

	@Override
	public void onDeleteUser(final DAOEvent event) {
		usersService.deleteUser((UserDAO) event.getDao(), new AsyncCallback<Boolean>() {

			@Override
			public void onFailure(Throwable caught) {
				//label.setText(SERVER_ERROR);
			}

			@Override
			public void onSuccess(Boolean result) {
				event.getDaoEditor().removeDAO(event.getDao());
				updateUI();
			}
		});
	}

	@Override
	public void onFilePageNavigation(NavigationEvent event) {
		getRelatedAudioFilesList(filePage.getAudioFilesList(), event.getAudioFileDAO());
		filePage.setAudioDAO(event.getAudioFileDAO());
		bodyDeck.showWidget(filePage);
		updateUI();
	}
	
	@Override
	public void onMixPageNavigation(NavigationEvent event) {
		getMixDetailsList(event.getMixDAO(), mixPage.getAudioFilesList());
		
		mixPage.setMixDAO(event.getMixDAO());
		bodyDeck.showWidget(mixPage);
		updateUI();
	}

}