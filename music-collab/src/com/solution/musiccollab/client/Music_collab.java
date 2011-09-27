package com.solution.musiccollab.client;

import java.util.ArrayList;
import java.util.List;

import com.allen_sauer.gwt.voices.client.Sound;
import com.allen_sauer.gwt.voices.client.SoundController;
import com.allen_sauer.gwt.voices.client.handler.PlaybackCompleteEvent;
import com.allen_sauer.gwt.voices.client.handler.SoundHandler;
import com.allen_sauer.gwt.voices.client.handler.SoundLoadStateChangeEvent;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DeckLayoutPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.solution.musiccollab.client.interfaces.AudioService;
import com.solution.musiccollab.client.interfaces.AudioServiceAsync;
import com.solution.musiccollab.client.interfaces.UsersService;
import com.solution.musiccollab.client.interfaces.UsersServiceAsync;
import com.solution.musiccollab.shared.event.FileSelectEvent;
import com.solution.musiccollab.shared.event.FileSelectEventHandler;
import com.solution.musiccollab.shared.event.NavigationEvent;
import com.solution.musiccollab.shared.event.NavigationEventHandler;
import com.solution.musiccollab.shared.model.Model;
import com.solution.musiccollab.shared.value.AudioFileDAO;
import com.solution.musiccollab.shared.value.UserDAO;
import com.solution.musiccollab.shared.view.AudioFilesList;
import com.solution.musiccollab.shared.view.BodyPanel;
import com.solution.musiccollab.shared.view.HeaderBar;
import com.solution.musiccollab.shared.view.IUpdatable;
import com.solution.musiccollab.shared.view.MemberPage;
import com.solution.musiccollab.shared.view.UsersList;

public class Music_collab implements EntryPoint, NavigationEventHandler, FileSelectEventHandler {
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
	private MemberPage memberPage = new MemberPage();
	private DeckLayoutPanel bodyDeck = new DeckLayoutPanel();
	
	public void onModuleLoad() {
		updatableWidgets = new ArrayList<IUpdatable>();
		updatableWidgets.add(headerBar);
		updatableWidgets.add(bodyPanel);
		updatableWidgets.add(memberPage);
		headerBar.addNavigationEventHandler(this);
		
		rootLayoutPanel = (RootLayoutPanel) RootLayoutPanel.get();
		rootLayoutPanel.add(headerBar);
		
		bodyDeck.add(bodyPanel);
		bodyDeck.add(memberPage);
		
		bodyPanel.getAudioFilesList().addFileSelectEventHandler(this);
		bodyPanel.getUsersList().addNavigationEventHandler(this);
		memberPage.addNavigationEventHandler(this);
		
		memberPage.getAudioFilesList().addFileSelectEventHandler(this);
		
		rootLayoutPanel.add(bodyDeck);
		rootLayoutPanel.setWidgetTopBottom(bodyDeck, headerBar.getOffsetHeight(), Unit.PX, 0, Unit.PX);

		onHomeNavigation(null);
		
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
	
	private void getAudioFilesList(final AudioFilesList list) {
		audioService.getAll(new AsyncCallback<List<AudioFileDAO>>() {
			
			@Override
			public void onSuccess(List<AudioFileDAO> result) {
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
				list.setItems(result);
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
				list.setItems(result);
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
				updateUI();
			}
		});
	}
	
	private void updateUI() {
		for(IUpdatable updatableWidget : updatableWidgets) {
			updatableWidget.update();
		}
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
		getAudioFilesList(bodyPanel.getAudioFilesList());
		bodyPanel.update();
	}
	
	@Override
	public void onFileSelected(FileSelectEvent event) {
		Window.open("downloadFile?filePath=" + event.getSelectedFile().getFilePath() + "&action=download", "_self", "");									
	}
	
	private void stopPlaying() {
		if(Model.currentPlayingAudioPanel != null) {
			Model.currentPlayingAudioPanel.setPlayStopStatus(false);
			Model.currentPlayingAudioPanel = null;
			
			if(Model.currentSound != null) {
				Model.currentSound.stop();
				Model.currentSound = null;
			}
		}
	}
	
	@Override
	public void onFilePlay(final FileSelectEvent fileSelectEvent) {
		stopPlaying();
		
		Model.currentPlayingAudioPanel = fileSelectEvent.getOriginator();
	    Model.currentSound = soundController.createSound(Sound.MIME_TYPE_AUDIO_MPEG_MP3, "downloadFile?filePath=" + fileSelectEvent.getSelectedFile().getFilePath());
	    Model.currentSound.setLooping(fileSelectEvent.isLooping());
	    Model.currentSound.play();
	    
	    Model.currentSound.addEventHandler(new SoundHandler() {
			
			@Override
			public void onSoundLoadStateChange(SoundLoadStateChangeEvent event) {
				event.getLoadState();
			}
			
			@Override
			public void onPlaybackComplete(PlaybackCompleteEvent event) {
				fileSelectEvent.getOriginator().setPlayStopStatus(false);
				Model.currentSound = null;
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
		memberPage.update();
	}
}