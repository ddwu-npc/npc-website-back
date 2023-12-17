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

	public void updateComment(Comment comm) {
		commentDao.updateComment(comm);
	}
	
	public void deleteComment(long commentId) {
		commentDao.deleteComment(commentId);
	}
	
	public void deleteCommentList(long post_id) {
		commentDao.deleteCommentList(post_id);
	}
	
	// 내가 쓴 뎃글
	public List<Comment> getUserCommentList(long userno){
		return commentDao.getUserComment(userno);
	}
}
