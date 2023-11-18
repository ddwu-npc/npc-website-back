package com.npcweb.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.npcweb.domain.User;
import com.npcweb.service.UserService;
import com.npcweb.service.PostService;

@CrossOrigin(origins = "http://localhost:3000") 
@RestController
@RequestMapping("/mypage")
public class MyPageController {
	private final UserService userService;
	private JWTProvider jwtProvider = new JWTProvider();
	//private final PostService postService;
	
	public MyPageController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping
	public ResponseEntity<MyPageReqRes> readUserInfo(HttpServletRequest request, @RequestParam long userno) {
		MyPageReqRes res = new MyPageReqRes();

		if (userno != 0) {
			User u = userService.getUserByUserNo(userno);
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
	
	// 마이페이지 프로필 정보 수정  
		@PutMapping("/update")
		public ResponseEntity<?> updateUserInfo(HttpServletRequest request, @RequestBody MyPageReqRes req) {
			
			HttpSession session = request.getSession();
			
			if (session.getAttribute("userno") != null) {
				long userNo = (long) session.getAttribute("userno");
				User user = userService.getUserByUserNo(userNo);
				
				user.setNickname(req.getNickname());
				user.setEmail(req.getEmail());
		//		user.setBirthday(req.getBirthday());
				
				userService.update(user);

				return ResponseEntity.ok().build();
			}
			return null;
		}
}

class MyPageReqRes {
	String userId, nickname, profile, email, birthday;
	int npcPoint, rank;
	long userNo;
//	int deptno, pid;
	public String getUserId() {
		return userId;
	}
	public String getNickname() {
		return nickname;
	}
	public String getProfile() {
		return profile;
	}
	public String getEmail() {
		return email;
	}
	public String getBirthday() {
		return birthday;
	}
	public int getNpcPoint() {
		return npcPoint;
	}
	public int getRank() {
		return rank;
	}
	public long getUserNo() {
		return userNo;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public void setNpcPoint(int npcPoint) {
		this.npcPoint = npcPoint;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public void setUserNo(long userNo) {
		this.userNo = userNo;
	}
	

//	public int getDeptno() {
//		return deptno;
//	}
//	public void setDeptno(int deptno) {
//		this.deptno = deptno;
//	}
//	public int getPid() {
//		return pid;
//	}
//	public void setPid(int pid) {
//		this.pid = pid;
//	}
}