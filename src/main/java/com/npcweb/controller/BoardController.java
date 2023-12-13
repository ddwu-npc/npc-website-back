package com.npcweb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	public List<Post> postList(@PathVariable long board_id) {
		List<Post> pList = boardService.getAllPostList(board_id);
		return pList;
	}
	/*
	//게시글 검색 - 제목
	@GetMapping("/{board_id}")
	public List<Post> postList(@PathVariable long board_id,@RequestParam(name="range") long rangeId, @RequestParam(name="searchRange") long searchRange, @RequestParam(name="text") String text ) {
		List<Post> pList = boardService.getAllPostListByKeyword(board_id, rangeId, searchRange, text);
		return pList;
	}
	*/
}