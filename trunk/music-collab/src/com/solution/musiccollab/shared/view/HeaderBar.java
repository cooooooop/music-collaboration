package com.solution.musiccollab.shared.view;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;
import com.solution.musiccollab.shared.event.NavigationEvent;
import com.solution.musiccollab.shared.event.NavigationEventHandler;
import com.solution.musiccollab.shared.model.Model;

public class HeaderBar extends Composite implements IUpdatable {
	
	private ArrayList<NavigationEventHandler> handlers = new ArrayList<NavigationEventHandler>();
	
	@UiField
	Label titleHeader;

	@UiField
	Label issuesLink;
	
	@UiField
	Label userLabel;
	
	@UiField
	MenuItem logoutMenuItem;
	
	@UiField
	MenuItem loginMembersMenuItem;

	@UiField
	MenuItem myPageMenuItem;
	
	@UiField
	MenuItem uploadMenuItem;
	
	@UiField
	MenuItem accountMenuItem;
	
	@UiField
	MenuBar accountMenuBar;
	
	@UiField
	MenuItem copyrightMenuItem;
	
	@UiField
	MenuItem openSourceMenuItem;
	
	@UiTemplate("uibinder/HeaderBar.ui.xml")
	interface MyUiBinder extends UiBinder<Widget, HeaderBar> { }
	private static final MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	public HeaderBar() {
		initWidget(uiBinder.createAndBindUi(this));	
		
		logoutMenuItem.setCommand(new Command() {
			
			@Override
			public void execute() {
				for(NavigationEventHandler handler : handlers) {
					handler.onLogoutRequest(new NavigationEvent(Model.currentUser));
		        }
			}
		});
		
		uploadMenuItem.setCommand(new Command() {
			
			@Override
			public void execute() {
				for(NavigationEventHandler handler : handlers) {
					handler.onUploadNavigation(new NavigationEvent(Model.currentUser));
		        }
			}
		});
		
		copyrightMenuItem.setCommand(new Command() {
			
			@Override
			public void execute() {
				Window.open("http://creativecommons.org/", "_blank", "");	
			}
		});
		
		openSourceMenuItem.setCommand(new Command() {
			
			@Override
			public void execute() {
				Window.open("http://code.google.com/p/music-collaboration/", "_blank", "");	
			}
		});
		
		issuesLink.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				Window.open("http://code.google.com/p/music-collaboration/issues/list", "_blank", "");	
			}
		});
		
		titleHeader.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				for(NavigationEventHandler handler : handlers) {
					handler.onHomeNavigation(new NavigationEvent(Model.currentUser));
		        }
			}
		});
		
		myPageMenuItem.setCommand(new Command() {
			
			@Override
			public void execute() {
				for(NavigationEventHandler handler : handlers) {
					handler.onMemberPageNavigation(new NavigationEvent(Model.currentUser));
		        }
			}
		});
	}
	
	public void update() {
		if(Model.currentUser != null) {
			userLabel.setText("You are logged in as " + Model.currentUser.getNickname());
			accountMenuItem.setText("ACCOUNT");
			
			accountMenuItem.setCommand(null);
			accountMenuItem.setSubMenu(accountMenuBar);
			if(loginMembersMenuItem != null && loginMembersMenuItem.getParentMenu() != null)
				loginMembersMenuItem.getParentMenu().removeItem(loginMembersMenuItem);
		}
		else {
			userLabel.setText("");
			accountMenuItem.setSubMenu(null);
			accountMenuItem.setText("LOG IN");
			
			accountMenuItem.setCommand(new Command() {
				
				@Override
				public void execute() {
					for(NavigationEventHandler handler : handlers) {
						handler.onLoginRequest(new NavigationEvent(Model.currentUser));
			        }
				}
			});
			
			loginMembersMenuItem.setCommand(new Command() {
				
				@Override
				public void execute() {
					if(Model.currentUser == null) {
						for(NavigationEventHandler handler : handlers) {
							handler.onLoginRequest(new NavigationEvent(Model.currentUser));
				        }
					}
				}
			});
		}
	}
	
    public void addNavigationEventHandler(NavigationEventHandler handler) {
    	handlers.add(handler);
    }

    public void removeNavigationEventHandler(NavigationEventHandler handler) {
    	handlers.remove(handler);
    }

}
