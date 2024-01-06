package com.npcweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.npcweb.domain.response.UserResponse;
import com.npcweb.service.UserService;

@CrossOrigin(origins = "http://localhost:3000") 
@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired UserService userService;
	
	// 회원 가입
	@PostMapping
    public void signup(@RequestBody User user) {
		// request body에 있는 정보로 user 등록
		userService.insert(user);
    }
	
	// 타인의 정보기 때문에 제한적으로 전달
	@GetMapping("/{userNo}")
	public ResponseEntity<UserResponse> getUser(@PathVariable("userNo") long userNo) {
		User user = userService.getUserByUserNo(userNo);
		UserResponse resUser = new UserResponse(user);
		
		return ResponseEntity.ok(resUser);
	}

	@PutMapping("/{userId}/password")
	public void changePassword(@PathVariable String userId, @RequestBody ChangePasswordRequest request) {
		userService.UpdatePassword(userId, request.getPassword());
    }

	// 프로젝트 팀원 추가를 위한 팀원명 찾기
	@GetMapping("/find/{nickname}")
	public ResponseEntity<UserResponse> addProjectUser(@PathVariable("nickname") String nickname) {
	    User user = userService.getUserByNickname(nickname);
	    
	    if(user != null) {
	        UserResponse resUser = new UserResponse(user);
	        return ResponseEntity.ok(resUser);
	    } else {
	    	User u = new User();
	    	u.setUserNo(-1);
	        UserResponse failUser = new UserResponse(u);
	        return ResponseEntity.ok(failUser); 
	    }
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