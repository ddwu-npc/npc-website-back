package com.npcweb.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	public List<Post> postList(@PathVariable long board_id) {
		List<Post> pList = boardService.getAllPostList(board_id);
		return pList;
	}
	
	//게시글 검색
	@PostMapping("/{board_id}/search")
	public List<Post> postList(@PathVariable long board_id, @RequestBody Map<String, String> requestBody ) {
		long rangePost = Long.parseLong(requestBody.get("rangePost"));		
		long searchRange = Long.parseLong(requestBody.get("searchRange"));
		String text = requestBody.get("text");
		
		//System.out.println("확인용 "+board_id+" "+rangeId+" "+searchRange+" "+text);
		List<Post> pList = boardService.getAllPostListByKeyword(board_id, rangePost, searchRange, text);
		return pList;
	}
}