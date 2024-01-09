package com.npcweb.service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npcweb.dao.PostDAO;
import com.npcweb.domain.Attendance;
import com.npcweb.domain.Point;
import com.npcweb.domain.Post;
import com.npcweb.domain.Project;
import com.npcweb.domain.User;
import com.npcweb.repository.PostRepository;
import com.npcweb.repository.PointRepository;

@Service
public class PostService {
	@Autowired
	PostDAO postDao;
	@Autowired
	PostRepository postRepo;
	@Autowired
	PointRepository pointRepo;
	
	//게시글 생성
	public void insertPost(Post post) {
		postDao.insertPost(post);
	}
	//게시글 조회
	public Post readPost(long post_id) {
		return postDao.readPost(post_id);
	}
	
	public Post findPost(long post_id) {
		return postRepo.findById(post_id).get();
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
	
	// 정기회의 출석
	public void insertAttendPost(Set<User> userList, Attendance attendance) {
		Project project = attendance.getProject();
		Post p = new Post();
		p.setBoardId(1); // 공지사항
		p.setCreateDate(new Date());
		p.setHavePostfile(0);
		p.setImportant(0);
		p.setTitle(attendance.getMeeting() + " 출결");
		p.setRangePost("전체");
		p.setUserNo(project.getLeader());
		
		Set<String> attendUserList = new HashSet<String>(); Set<String> notAttendUserList = new HashSet<String>();
		for (User u : userList) {
			Point point = pointRepo.findByUsernoAndAttendanceId(u.getUserNo(), attendance.getAttendanceId());
			if (point.getChangePoint() < 0)
				notAttendUserList.add(u.getNickname());
			else attendUserList.add(u.getNickname());
		}
		p.setContent("출석" + attendUserList + "\n결석\n" + notAttendUserList);
		postRepo.save(p);
	}
}
