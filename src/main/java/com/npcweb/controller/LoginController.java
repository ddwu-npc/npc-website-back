package com.npcweb.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.npcweb.domain.User;
import com.npcweb.security.JWTProvider;
import com.npcweb.service.UserService;

@CrossOrigin(origins = "http://localhost:3000") 
@RestController
@RequestMapping("/login")
public class LoginController {
	private final UserService userService;
	@Autowired
	private JWTProvider jwtProvider;
	
	public LoginController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public Long getUserno(HttpServletRequest request, @RequestHeader("Authorization") String token) {
		String jwtToken = token.replace("Bearer ", "").replace("\"", "");
        long userno = jwtProvider.getUsernoFromToken(jwtToken);
		return userno;
	}
	
	@PostMapping
    public String login(HttpServletRequest request, @RequestBody LoginRequest requestBody) {
		String userId = requestBody.getUserId();
		String userPw = requestBody.getUserPw();
		
		User user = userService.getUserByUserPw(userId, userPw);
		
		if (user != null) {
			long userno = user.getUserNo();
			String token = jwtProvider.generateToken(userno);
			return token;
		}
		return null;
    }

	@PostMapping("/logout")
	public void logout(HttpServletRequest request) {
		//HttpSession session = request.getSession();
		//session.setAttribute("userId", null);
	}
}

class LoginRequest {
	String userId;
	String userPw;
	String nickname;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserPw() {
		return userPw;
	}
	public void setUserPw(String userPw) {
		this.userPw = userPw;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
}
