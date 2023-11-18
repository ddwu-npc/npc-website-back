package com.npcweb.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.npcweb.dao.jpa.JpaCommentDAO;
import com.npcweb.domain.Comment;
import com.npcweb.service.CommentService;

@CrossOrigin(origins = "http://localhost:3000") 
@RestController
@RequestMapping("/comment")
public class CommentController {
	@Autowired JpaCommentDAO commentDao;
	@Autowired CommentService commentService;
	
	@RequestMapping("/{postId}")
	public List<Comment> commentList(@PathVariable long postId, Model model) {
		System.out.println("commentList");
		List<Comment> cList = commentService.getAllCommentList(postId);
		return cList;
	}
	
	/*
	//delete
	@DeleteMapping("/{commentId}")
	public void deleteComment(@PathVariable long commentId) {
		Comment comment = commentService.findComment(commentId);
		commentService.deleteComment(comment);
	}
	*/
	//insert
	@PostMapping("/{post_id}")
	public void insertComment(HttpServletRequest request, @PathVariable long post_id, @RequestBody CommentReq req) {
		HttpSession session = (HttpSession) request.getSession();
		long userNo = (long) session.getAttribute("user_id");

		Comment comment = new Comment();

		comment.setPostId(post_id);
		comment.setUserNo(userNo);
		comment.setContent(req.getContent());
		comment.setCreateDate(new Date());

		commentService.insertComment(comment);
	}
}

class CommentReq {
	private long postId;
	private String content;

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
}
