package com.npcweb.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.npcweb.domain.Post;

public interface PostDAO {
	//create
	public void insertPost(Post post) throws DataAccessException;
	
	//read
	public Post readPost(long post_id) throws DataAccessException;
	
	//update
	public void updatePost(Post post) throws DataAccessException;
	
	//delete
	public void deletePost(Post post) throws DataAccessException;
	
	//목록보기
	List<Post> getAllPost(long board_id) throws DataAccessException;
}
