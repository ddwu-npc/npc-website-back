package com.npcweb.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.npcweb.domain.Comment;

public interface CommentDAO {
	//create
	public void insertComment(Comment comment) throws DataAccessException;

	//delete
	public void deleteComment(Comment comment) throws DataAccessException;
		
	//목록보기
	List<Comment> getAllComment(long post_id) throws DataAccessException;

	public Comment readComment(long commentId);
}
