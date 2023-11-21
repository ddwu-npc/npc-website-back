package com.npcweb.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.npcweb.domain.User;
import com.npcweb.service.UserService;

@CrossOrigin(origins = "http://localhost:3000") 
@RestController
@RequestMapping("/users")
public class UserController {

	private final UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	// 회원 가입
	@PostMapping
    public void signup(@RequestBody User user) {
		// request body에 있는 정보로 user 등록
		System.out.println(user.getUserId());
		userService.insert(user);
    }
	
	@GetMapping("/{userId}")
	public User getUser(@PathVariable String userId) {
		return userService.getUserByUserId(userId);
	}

	@PutMapping("/{userId}/password")
	public void changePassword(@PathVariable String userId, @RequestBody ChangePasswordRequest request) {
		// request body에 있는 password로 변경
		System.out.println("PW : " + request.getPassword());
		userService.UpdatePassword(userId, request.getPassword());
    }
}

class ChangePasswordRequest {
	String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}