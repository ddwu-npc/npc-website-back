package com.npcweb.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
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
import com.npcweb.service.UserService;

@CrossOrigin(origins = "http://localhost:3000") 
@RestController
@RequestMapping("/board")
public class BoardController {
	@Autowired BoardService boardService;
	@Autowired UserService userService;
	
	// 게시글 목록
	@GetMapping("/{board_id}")
	public ResponseEntity<Map<String, Object>> page(@PathVariable long board_id, @PageableDefault(page = 1) Pageable pageable) {
	    int adjustedPage = pageable.getPageNumber() - 1;
	    PageRequest pageRequest = PageRequest.of(adjustedPage, pageable.getPageSize(), pageable.getSort());
	    
	    // 페이징
	    Page<PostResponse> postsPages = boardService.pagingByBoard(pageRequest, board_id);
	    
	    int endPage = postsPages.getTotalPages();

        Map<String, Object> response = new HashMap<>();
        // post 데이터
	    List<PostResponse> postsList = postsPages.getContent();
	    for (PostResponse p : postsList) {
	    	User u = userService.getUserByUserNo(Long.parseLong(p.getNickname()));
	    	p.setNickname(u.getNickname());
	    }
	    // 페이지 데이터
	    List<Integer> pageInfo = new ArrayList<Integer>();
	    pageInfo.add(adjustedPage); pageInfo.add(endPage);
	   
        response.put("postList", postsList);
        response.put("pageInfo", pageInfo);

        return ResponseEntity.ok().body(response);
	}
	
	//게시글 검색
	@PostMapping("/{board_id}/search")
	public ResponseEntity<Map<String, Object>> pageBySearch(@PathVariable long board_id, @RequestBody Map<String, String> requestBody, @PageableDefault(page = 1) Pageable pageable) {
		long rangePost = Long.parseLong(requestBody.get("rangePost"));		
		long searchRange = Long.parseLong(requestBody.get("searchRange"));
		String text = requestBody.get("text");
		
		// 검색 결과 리스트
		List<Post> pList = boardService.getAllPostListByKeyword(board_id, rangePost, searchRange, text);
		
	    int adjustedPage = pageable.getPageNumber() - 1;
	    PageRequest pageRequest = PageRequest.of(adjustedPage, pageable.getPageSize(), pageable.getSort());
	    
	    // 페이징
	    Page<PostResponse> postsPages = boardService.pagingBySearch(pageRequest, pList);
	    int endPage = postsPages.getTotalPages();

        Map<String, Object> response = new HashMap<>();
        // post 데이터
	    List<PostResponse> postsList = postsPages.getContent();
	    for (PostResponse p : postsList) {
	    	User u = userService.getUserByUserNo(Long.parseLong(p.getNickname()));
	    	p.setNickname(u.getNickname());
	    }
	    // 페이지 데이터
	    List<Integer> pageInfo = new ArrayList<Integer>();
	    pageInfo.add(adjustedPage); pageInfo.add(endPage);
	   
        response.put("postList", postsList);
        response.put("pageInfo", pageInfo);

        return ResponseEntity.ok().body(response);
	}
}