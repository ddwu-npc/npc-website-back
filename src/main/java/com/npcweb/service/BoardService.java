package com.npcweb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npcweb.dao.BoardDAO;
import com.npcweb.domain.Post;

@Service
public class BoardService {
	@Autowired
	BoardDAO boardDao;
	
	//게시글 목록보기
	public List<Post> getAllPostList(long board_id){
		return boardDao.getAllPost(board_id);
	}
}
