package com.npcweb.service;

import java.util.List;
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
	public long getBoardIdByPostId(long post_id) {
		return postDao.getBoardIdByPostId(post_id);
	}
	
	// 조회수 조회
	public int getReadCount(long post_id) {
		return postDao.getReadCountByPostId(post_id);
	}
	
	// 조회수 업데이트
	public void updateReadCount(long post_id, int readCount) {
		postDao.updateReadCount(post_id, readCount);
	}
	
	// 내가 쓴 게시물
	public List<Post> getUserPostList(long userno){
		return postDao.getUserPost(userno);
	}
	
	// 내가 쓴 댓글의 게시물 정보
	public List<Post> getUserCommentPost(long userno){
		return postDao.getUserCommentPost(userno);
	}
	public long findUserByPostId(long postId) {
		return postDao.findUserByPostId(postId);
	}
}
