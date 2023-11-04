package com.npcweb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.npcweb.domain.Post;
import com.npcweb.service.BoardService;

@CrossOrigin(origins = "http://localhost:3000") 
@RestController
@RequestMapping("/board")
public class BoardController {
	@Autowired	BoardService boardService;

	//게시글 목록보기
	@RequestMapping("/{board_id}")
	public List<Post> postList(@PathVariable long board_id, Model model) {
		List<Post> pList = boardService.getAllPostList(board_id);
		return pList;
	}
}