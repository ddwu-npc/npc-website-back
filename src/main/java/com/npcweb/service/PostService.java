package com.npcweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npcweb.dao.PostDAO;
import com.npcweb.domain.Post;
import com.npcweb.repository.PostRepository;

@Service
public class PostService {
	@Autowired
	PostDAO postDao;
	
	@Autowired
	PostRepository postRepo;
	
	//게시글 생성
	public void insertPost(Post post) {
		postDao.insertPost(post);
	}
	//게시글 조회
	public Post readPost(long post_id) {
		return postDao.readPost(post_id);
	}
	//게시글 수정
	public void updatePost(Post post) {
		postDao.updatePost(post);
	}
	//게시글 삭제
	public void deletePost(Post post) {
		postDao.deletePost(post);
	}
}
