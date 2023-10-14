package com.npcweb.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.npcweb.domain.User;
import com.npcweb.service.UserService;

@CrossOrigin(origins = "http://localhost:3000") 
@RestController
@RequestMapping("/login")
public class LoginController {
//	private final UserService userService;
//
//	public LoginController(UserService userService) {
//		this.userService = userService;
//	}
//	
//	@GetMapping
//	public Long getLoginedUserId(HttpServletRequest request) {
//		HttpSession session = request.getSession();
//		if(session.getAttribute("userId") != null)
//			return (long) session.getAttribute("userId");
//		return null;
//	}
//	
//	@PostMapping
//    public String login(HttpServletRequest request, @RequestBody LoginRequest requestBody) {
//		String userId = requestBody.getUserId();
//		String userPw = requestBody.getUserPw();
//		
//		User user = userService.getUserByUserPw(userId, userPw);
//		
//		String nickname = user.getNickname();
//		HttpSession session = request.getSession();
//		session.setAttribute("nickname", nickname);
//		
//		return userId;
//    }
//	
//	@PostMapping("/logout")
//	public void logout(HttpServletRequest request) {
//		HttpSession session = request.getSession();
//		session.setAttribute("userId", null);
//	}
}
//
//class LoginRequest {
//	String userId;
//	String userPw;
//	String nickname;
//	
//	public String getUserId() {
//		return userId;
//	}
//	public void setUserId(String userId) {
//		this.userId = userId;
//	}
//	public String getUserPw() {
//		return userPw;
//	}
//	public void setUserPw(String userPw) {
//		this.userPw = userPw;
//	}
//	public String getNickname() {
//		return nickname;
//	}
//	public void setNickname(String nickname) {
//		this.nickname = nickname;
//	}
//
//	
//}
