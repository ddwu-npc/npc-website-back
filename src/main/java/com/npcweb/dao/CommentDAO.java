package com.npcweb.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.npcweb.domain.Comment;

public interface CommentDAO {
	//create
	public void insertComment(Comment comment) throws DataAccessException;

	//delete
	public void deleteComment(long commentId) throws DataAccessException;
		
	//목록보기
	List<Comment> getAllComment(long post_id) throws DataAccessException;
	
	//게시글 삭제 시 해당 게시글의 모든 댓글 삭제
	public void deleteCommentList(long post_id);
}
