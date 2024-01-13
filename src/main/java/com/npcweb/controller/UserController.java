package com.npcweb.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.npcweb.domain.User;
import com.npcweb.dto.UserResponse;
import com.npcweb.security.JWTProvider;
import com.npcweb.service.ProjectService;
import com.npcweb.service.UserService;

@CrossOrigin(origins = "http://localhost:3000") 
@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired UserService userService;
	@Autowired ProjectService projectService;
	@Autowired JWTProvider jwtProvider;
	
	// 회원 가입
	@PostMapping
    public void signup(@RequestBody User user) {
		// request body에 있는 정보로 user 등록
		User u = userService.insert(user);
		// 나중에 정기회의 id로 바꿔야 함
		projectService.signUpProject(1000, u.getUserNo());
    }
	
	// 닉네임 체크
	@PostMapping("/checkNickname")
	public ResponseEntity<Boolean> checkNickname(@RequestBody Map<String, String> _nickname) {
		String nickname = _nickname.get("nickname");
		int result = userService.NicknameCheck(nickname);
		if (result == 0)
			return ResponseEntity.ok(true);
		return ResponseEntity.ok(false);
	}
	
	// 아이디 체크
	@PostMapping("/checkUserId")
	public ResponseEntity<Boolean> checkUserId(@RequestBody Map<String, String> _userId) {
		String userId = _userId.get("userId");
		int result = userService.UserIdCheck(userId);
		if (result == 0)
			return ResponseEntity.ok(true);
		return ResponseEntity.ok(false);
	}
	
	// 이메일 체크
	@PostMapping("/checkEmail")
	public ResponseEntity<Boolean> checkEmail(@RequestBody Map<String, String> _email) {
		String email = _email.get("email");
		int result = userService.EmailCheck(email);
		if (result == 0)
			return ResponseEntity.ok(true);
		return ResponseEntity.ok(false);
	}
	
	// 비밀번호 분실 시 이용
	@GetMapping("/checkUser/{userId}/{email}")
	public ResponseEntity<Boolean> checkUser(@PathVariable("userId") String userId, @PathVariable("email") String email) {
		User u = userService.getUserByEmail(userId, email);
		if (u != null)
			return ResponseEntity.ok(true);
		return ResponseEntity.ok(false);
	}
	
	// 타인의 정보기 때문에 제한적으로 전달
	@GetMapping("/{userNo}")
	public ResponseEntity<UserResponse> getUser(@PathVariable("userNo") long userNo) {
		User user = userService.getUserByUserNo(userNo);
		UserResponse resUser = new UserResponse(user);
		
		return ResponseEntity.ok(resUser);
	}
	
	// 비밀번호 변경 - 비밀번호 분실
	@PutMapping("/forgetPassword")
	public void changePassword(@RequestBody Map<String, String> passwordReq) {
		String userId = passwordReq.get("userId");
		String password = passwordReq.get("password");
		userService.UpdatePassword(userId, password);
    }

	// 비밀번호 변경 - 마이페이지
	@PutMapping("/changePassword")
	public void changePassword(@RequestBody String password, @RequestHeader("Authorization") String token) {
		String jwtToken = token.replace("Bearer ", "").replace("\"", "");
        long userNo = jwtProvider.getUsernoFromToken(jwtToken);
        
        User u = userService.getUserByUserNo(userNo);
        u.setUserPw(password);
		userService.update(u);
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
