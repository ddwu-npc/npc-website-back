package com.npcweb.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import com.npcweb.domain.PostResponse;
import com.npcweb.service.BoardService;
import com.npcweb.service.PostService;

@CrossOrigin(origins = "http://localhost:3000") 
@RestController
@RequestMapping("/board")
public class BoardController {
	@Autowired BoardService boardService;
	@Autowired PostService postService;
	
	@GetMapping("/{board_id}/page")
	public List<PostResponse> page(@PathVariable long board_id, @PageableDefault(page = 1) Pageable pageable) {
		Page<PostResponse> postsPages = postService.paging(pageable);
	 
		/**
		 * blockLimit : page 개수 설정
		 * 현재 사용자가 선택한 페이지 앞 뒤로 3페이지씩만 보여준다.
		 * ex : 현재 사용자가 4페이지라면 2, 3, (4), 5, 6
		 */
		int blockLimit = 5;
		int startPage = (((int) Math.ceil(((double) pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
	    int endPage = Math.min((startPage + blockLimit - 1), postsPages.getTotalPages());
	    
	    List<PostResponse> postsList = postsPages.getContent();
		return postsList;
	}
	
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