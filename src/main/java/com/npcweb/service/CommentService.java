package com.npcweb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npcweb.dao.CommentDAO;
import com.npcweb.domain.Comment;

@Service
public class CommentService {
	@Autowired CommentDAO commentDao;
	
	public List<Comment> getAllCommentList(long post_id){
		return commentDao.getAllComment(post_id);
	}
	
	public void insertComment(Comment comment) {
		commentDao.insertComment(comment);
	}
	
	public void deleteComment(Comment comment) {
		commentDao.deleteComment(comment);
	}

	public Comment findComment(long commentId) {
		return commentDao.readComment(commentId);
	}
}
