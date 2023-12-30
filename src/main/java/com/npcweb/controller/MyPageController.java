package com.npcweb.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PathVariable;

import com.npcweb.domain.User;
import com.npcweb.domain.Post;
import com.npcweb.domain.Comment;
import com.npcweb.domain.Dept;
import com.npcweb.service.UserService;
import com.npcweb.service.PostService;
import com.npcweb.service.CommentService;
import com.npcweb.service.DeptService;
import com.npcweb.service.UserFileService;

@CrossOrigin(origins = "http://localhost:3000") 
@RestController
@RequestMapping("/mypage")
public class MyPageController {
	@Autowired UserService userService;
	@Autowired PostService postService;
	@Autowired CommentService commentService;
	@Autowired DeptService deptService;
	@Autowired UserFileService ufService;
	
	@GetMapping
	public ResponseEntity<MyPageReqRes> readUserInfo(HttpServletRequest request, @RequestParam long userno) {
		MyPageReqRes res = new MyPageReqRes();

		if (userno != 0) {
			User u = userService.getUserByUserNo(userno);
			
			if (u.getBirthday() == null) {
				res.setBirthday("2020-01-01"); // 임시
			}
			else {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		        String birthday = format.format(u.getBirthday());
				res.setBirthday(birthday); 	
			}
			res.setEmail(u.getEmail());
			res.setNickname(u.getNickname());
			res.setNpcPoint(u.getNpcPoint());
			res.setProfile("profile"); // 임시
			res.setDname(u.getDept().getDname());
			res.setUserId(u.getUserId());
			
			return ResponseEntity.ok(res);
		}
		return null;
	}
	
	// 내가 쓴 게시물
	 @PostMapping("/post/{userno}")
	 public ResponseEntity<List<Post>> getPostsByUserId(@PathVariable("userno") long userno) {
	    List<Post> pList = postService.getUserPostList(userno);
	    
	    return ResponseEntity.ok(pList);
	 }
	 
	 // 내가 쓴 댓글
		@PostMapping("/comment/{userno}")
		public ResponseEntity<List<Object>> getCommentsByUserId(@PathVariable("userno") long userno) {
			 List<Object> oList = new ArrayList<>();
			
			 List<Comment> cList = commentService.getUserCommentList(userno);
			 
			 for (int i = 0; i < cList.size(); i++) {
				 Map<String, Object> totalData = new HashMap<>();
				 Post p = postService.findPost(cList.get(i).getPostId());
		         totalData.put("commentId", cList.get(i).getCommentId());
		         totalData.put("content", cList.get(i).getContent());
		         totalData.put("createDate", cList.get(i).getCreateDate());
		         totalData.put("title", p.getTitle());
		         totalData.put("postId", p.getPostId());
		         totalData.put("boardId", p.getBoardId());
		         oList.add(totalData);
			}
			 
			 return ResponseEntity.ok(oList);
		}
	
	// 마이페이지 프로필 정보 수정  
		@PutMapping("/update")
		public ResponseEntity<?> updateUserInfo(
			    @RequestParam("userNo") int userNo,
			    @RequestParam("nickname") String nickname,
			    @RequestParam("email") String email,
			    @RequestParam("birthday") String birthdayTmp,
			    @RequestParam("dname") String dname,
			    @RequestParam(value = "profile", required = false) MultipartFile profileFile) {
			
			
			if (userNo != 0) {
				User user = userService.getUserByUserNo(userNo);
				
				user.setNickname(nickname);
				user.setEmail(email);
				Dept d = deptService.findDeptByDname(dname);
				user.setDept(d);
				
				String str = birthdayTmp;
	            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	            Date birthday = null;
				try {
					birthday = format.parse(str);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				user.setBirthday(birthday);

				if(profileFile != null) {
					user.setProfile(1);
					
//					ufService.deleteFile(userNo);
					ufService.submitFileUpload(profileFile, user);
				}
				
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
	String dname;
	
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
	public String getDname() {
		return dname;
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
	public void setDname(String dname) {
		this.dname = dname;
	}

}