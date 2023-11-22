package com.npcweb.controller;

import org.springframework.http.ResponseEntity;
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
		userService.insert(user);
    }
	
	// 타인의 정보기 때문에 제한적으로 전달
	@GetMapping("/{userNo}")
	public ResponseEntity<UserRes> getUser(@PathVariable("userNo") long userNo) {
		User u = userService.getUserByUserNo(userNo);
		UserRes res = new UserRes();
		res.setNickname(u.getNickname());
		res.setProfile(u.getNickname());
		res.setUserNo(userNo);
		
		return ResponseEntity.ok(res);
	}

	@PutMapping("/{userId}/password")
	public void changePassword(@PathVariable String userId, @RequestBody ChangePasswordRequest request) {
		// request body에 있는 password로 변경
		// 암호화가 필요하여 추후 변경 필요
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

class UserRes {
	long userNo;
	String nickname; String profile;

	public long getUserNo() {
		return userNo;
	}
	public String getNickname() {
		return nickname;
	}
	public String getProfile() {
		return profile;
	}
	public void setUserNo(long userNo) {
		this.userNo = userNo;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	
}