package com.npcweb.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.npcweb.domain.Post;
import com.npcweb.domain.User;
import com.npcweb.domain.response.PostResponse;
import com.npcweb.service.BoardService;
import com.npcweb.service.PostService;
import com.npcweb.service.UserService;

@CrossOrigin(origins = "http://localhost:3000") 
@RestController
@RequestMapping("/board")
public class BoardController {
	@Autowired BoardService boardService;
	@Autowired PostService postService;
	@Autowired UserService userService;
	
	// 게시글 목록
	@GetMapping("/{board_id}")
	public List<PostResponse> page(@PathVariable long board_id, @PageableDefault(page = 1) Pageable pageable) {
	    int adjustedPage = pageable.getPageNumber() - 1;
	    PageRequest pageRequest = PageRequest.of(adjustedPage, pageable.getPageSize(), pageable.getSort());
	    
	    Page<PostResponse> postsPages = postService.pagingByBoard(pageRequest, board_id);
	    
	    int blockLimit = 5;
	    int startPage = (adjustedPage / blockLimit) * blockLimit + 1;
	    int endPage = Math.min(startPage + blockLimit - 1, postsPages.getTotalPages());

	    List<PostResponse> postsList = postsPages.getContent();
	    for (PostResponse p : postsList) {
	    	User u = userService.getUserByUserNo(Long.parseLong(p.getNickname()));
	    	p.setNickname(u.getNickname());
	    }
	    return postsList;
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