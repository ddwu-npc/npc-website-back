package com.npcweb.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.npcweb.domain.User;
import com.npcweb.service.UserService;

@CrossOrigin(origins = "http://localhost:3000") 
@RestController
@RequestMapping("/mypage")
public class MyPageController {
	private final UserService userService;

	public MyPageController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping
	public ResponseEntity<MyPageResponse> readUserInfo(HttpServletRequest request) {
		MyPageResponse res = new MyPageResponse();
		HttpSession session = request.getSession();

		if (session.getAttribute("userno") != null) {
			long userNo = (long) session.getAttribute("userno");
			
			User u = userService.getUserByUserNo(userNo);
			res.setBirthday("2020-01-01"); // 임시
			res.setEmail(u.getEmail());
			res.setNickname(u.getNickname());
			res.setNpcPoint(u.getNpcPoint());
			res.setProfile("profile"); // 임시
			res.setRank(u.getRank());
			res.setUserId(u.getUserId());
			
			return ResponseEntity.ok(res);
		}
		return null;
	}
}

class MyPageResponse {
	String userId, nickname, profile, email, birthday;
	int npcPoint, rank;
	long userNo;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public int getNpcPoint() {
		return npcPoint;
	}
	public void setNpcPoint(int npcPoint) {
		this.npcPoint = npcPoint;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public long getUserNo() {
		return userNo;
	}
	public void setUserNo(long userNo) {
		this.userNo = userNo;
	}
}