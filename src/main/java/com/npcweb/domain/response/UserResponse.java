package com.npcweb.domain.response;

import com.npcweb.domain.User;

public class UserResponse {
	long userNo;
	String nickname; int profile; String dept;
	
	public UserResponse(User u) {
		super();
		this.userNo = u.getUserNo();
		this.nickname = u.getNickname();
		this.profile = u.getProfile();
		this.dept = u.getDept().getDname();
	}

	public long getUserNo() {
		return userNo;
	}

	public String getNickname() {
		return nickname;
	}

	public int getProfile() {
		return profile;
	}

	public String getDept() {
		return dept;
	}
	
}
