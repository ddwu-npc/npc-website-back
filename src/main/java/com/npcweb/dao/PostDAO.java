package com.npcweb.dao;

import org.springframework.dao.DataAccessException;
import java.util.List;

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

	public long getBoardIdByPostId(long post_id);
	
	public int getReadCountByPostId(long post_id);

	public void updateReadCount(long post_id, int readCount);

	// 내가 쓴 게시물
	List<Post> getUserPost(long userno) throws DataAccessException;
}
