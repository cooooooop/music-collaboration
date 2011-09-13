package com.solution.musiccollab.shared.value;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UserDAO implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Id
	private String userid;
	private String email;
	private String nickname;

	public UserDAO() {
    	super();
    }
    
    public UserDAO(String userid) {
    	this.setUserid(userid);
    }
    
    public String getEmail() {
    	return email;
    }
    
    public void setEmail(String email) {
    	this.email = email;
    }
    
    public String getNickname() {
    	return nickname;
    }
    
    public void setNickname(String nickname) {
    	this.nickname = nickname;
    }

    public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

}