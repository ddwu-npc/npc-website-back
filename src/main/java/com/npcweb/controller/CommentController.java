package com.npcweb.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.npcweb.dao.jpa.JpaCommentDAO;
import com.npcweb.domain.Comment;
import com.npcweb.domain.Post;
import com.npcweb.security.JWTProvider;
import com.npcweb.service.CommentService;

@CrossOrigin(origins = "http://localhost:3000") 
@RestController
@RequestMapping("/comment")
public class CommentController {
	@Autowired JpaCommentDAO commentDao;
	@Autowired CommentService commentService;
	@Autowired private JWTProvider jwtProvider;

	@RequestMapping("/{postId}")
	public List<Comment> commentList(@PathVariable long postId) {
		List<Comment> cList = commentService.getAllCommentList(postId);
		return cList;
	}

	//insert
	@PostMapping("/{postId}")
	public void insertComment(@PathVariable long postId, @RequestBody CommentReq req, @RequestHeader("Authorization") String token) {
		String jwtToken = token.replace("Bearer ", "").replace("\"", "");
		long userNo = jwtProvider.getUsernoFromToken(jwtToken);

		Comment comment = new Comment();

		comment.setPostId(postId);
		comment.setUserNo(userNo);
		comment.setContent(req.getContent());
		comment.setCreateDate(new Date());

		commentService.insertComment(comment);
	}
	
	/*
	 * 기능이 없어져 주석 처리됨. 나중에 필요시 주석 해제하고 쓸 것.
	//update
	@PutMapping("/{comment_id}")
	public void updateComment(@RequestBody CommentReq req, @PathVariable long comment_id) {
		System.out.println("update comment "+req.toString());
		
		Comment comm = new Comment();
		comm.setPostId(req.getPostId());
		comm.setContent(req.getContent());
		comm.setUserNo(req.getUserno());

		commentService.updateComment(comm);
	}
	*/
	//delete
	@DeleteMapping("/{commentId}")
	public void deleteComment(@PathVariable long commentId) {
		commentService.deleteComment(commentId);
	}
	
	@GetMapping("/find/{id}")
	public long findUserByCommentId(@PathVariable long id) {
		return commentDao.findUserByCommentId(id);
	}

}

class CommentReq {
	private long postId;
	private String content;
	private long userno;

	public long getPostId() {
		return postId;
	}
	public void setPostId(long postId) {
		this.postId = postId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public long getUserno() {
		return userno;
	}
	public void setUserno(long userno) {
		this.userno = userno;
	}
}
