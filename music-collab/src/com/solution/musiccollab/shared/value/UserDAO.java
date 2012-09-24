package com.solution.musiccollab.shared.value;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.googlecode.objectify.annotation.Indexed;

@Entity
public class UserDAO implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Id
	private String userid;
	private String email;
	private String nickname;
	@Indexed
	private Date lastLogin;
	
	//number of clicks related to User
	private Integer rank;

	public UserDAO() { /*empty constructor required for objectify*/ }
    
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

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

}