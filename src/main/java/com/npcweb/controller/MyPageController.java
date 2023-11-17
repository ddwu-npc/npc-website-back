package com.npcweb.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.npcweb.domain.User;
import com.npcweb.service.UserService;
import com.npcweb.service.PostService;

@CrossOrigin(origins = "http://localhost:3000") 
@RestController
@RequestMapping("/mypage")
public class MyPageController {
	private final UserService userService;
	//private final PostService postService;
	
	public MyPageController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping
	public ResponseEntity<MyPageReqRes> readUserInfo(HttpServletRequest request) {
		MyPageReqRes res = new MyPageReqRes();
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
	
	// 마이페이지 프로필 정보 수정  
//	@PostMapping("/mypage/update")
//	public String updateUserInfo(HttpServletRequest request, @RequestBody MyPageReqRes req) {
//		
//		HttpSession session = request.getSession();
//		
//		if (session.getAttribute("userno") != null) {
//			long userNo = (long) session.getAttribute("userno");
//			User user = userService.getUserByUserNo(userNo);
//			
//			user.setNickname(req.getNickname());
//			user.setEmail(req.getEmail());
//	//		user.setBirthday(req.getBirthday());
//			user.setNpcPoint(req.getNpcPoint());
//	//		user.setDeptNo(req.getDeptno());
//	//		user.setPid(req.getPid());
//			userService.update(user);
//			
//			return "/mypage";
//		}
//		return null;
//	}
}

class MyPageReqRes {
	String userId, nickname, profile, email, birthday;
	int npcPoint, rank;
	long userNo;
//	int deptno, pid;
	
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