package com.npcweb.controller;

import java.util.ArrayList;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.npcweb.domain.Post;
import com.npcweb.security.JWTProvider;
import com.npcweb.service.PostService;
import com.npcweb.service.ReadCountService;
import com.npcweb.service.CommentService;
import com.npcweb.service.PostFileService;

@CrossOrigin(origins = "http://localhost:3000") 
@RestController
@RequestMapping("/post")
public class PostController {
	@Autowired PostService postService;
	@Autowired CommentService commentService;
	@Autowired private JWTProvider jwtProvider;
	@Autowired private ReadCountService readCountService;
	@Autowired PostFileService pfService;

	//read
	@GetMapping("/{post_id}")
	public ResponseEntity<Post> readPost(@PathVariable long post_id, @RequestHeader("Authorization") String token) {
		String jwtToken = token.replace("Bearer ", "").replace("\"", "");
        long userNo = jwtProvider.getUsernoFromToken(jwtToken);
		readCountService.getReadCountByUser(post_id, userNo);
	    try {
	        Post post = postService.readPost(post_id);
	        if (post != null) {
	            return ResponseEntity.ok(post);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}
	//create
	@PostMapping(value="/{board_id}")
	public void createPost(@PathVariable long board_id, 
			@RequestParam(value = "attachment_0", required = false) MultipartFile file0,
			@RequestParam(value = "attachment_1", required = false) MultipartFile file1,
			@RequestParam(value = "attachment_2", required = false) MultipartFile file2,
		    @RequestParam("title") String title,
		    @RequestParam(value="content") String content,
		    @RequestParam("rangePost") String rangePost,
		    @RequestParam("important") String important,
		    @RequestHeader("Authorization") String token) {
		
		String jwtToken = token.replace("Bearer ", "").replace("\"", "");
        long userNo = jwtProvider.getUsernoFromToken(jwtToken);
        
		Post post = new Post();
		post.setBoardId(board_id);		
		if(content==null)
			post.setContent("");
		else
			post.setContent(content);
		post.setCreateDate(new Date());
		
		if(important.equals("on"))
			post.setImportant(1);
		else
			post.setImportant(0);
		
		post.setReadCount(0);
		post.setRangePost(rangePost);
		
		if(title==null || title.equals(""))
			post.setTitle("제목이 없습니다.");
		else
			post.setTitle(title);
		post.setUserNo(userNo);
		
		postService.insertPost(post);
		
		ArrayList<MultipartFile> files = new ArrayList<>();
		if(file0 != null)
			files.add(file0);
		if(file1 != null)
			files.add(file1);
		if(file2 != null)
			files.add(file2);
		
		
		
		if (file0 != null) {
			post.setHavePostfile(1);
			
			for(MultipartFile file : files)
				pfService.submitFileUpload(file, post);
	    }
	}
	//update
	@PutMapping("/{post_id}")
	public void updatePost(@PathVariable long post_id, 
			@RequestParam(value = "attachment_0", required = false) MultipartFile file0,
			@RequestParam(value = "attachment_1", required = false) MultipartFile file1,
			@RequestParam(value = "attachment_2", required = false) MultipartFile file2,
		    @RequestParam("title") String title,
		    @RequestParam(value="content") String content,
		    @RequestParam("rangePost") String rangePost,
		    @RequestParam("important") String important) {
		Post post = postService.readPost(post_id);
		post.setPostId(post_id);
		post.setBoardId(post.getBoardId());
		if(content==null)
			post.setContent("");
		else
			post.setContent(content);
		
		if(important==null)
			post.setImportant(0);
		else
			post.setImportant(1);
		post.setUpdateDate(new Date());
		post.setRangePost(rangePost);
		if(title==null || title.equals(""))
			post.setTitle("제목이 없습니다.");
		else
			post.setTitle(title);
		post.setUserNo(post.getUserNo());
		
		
		if(post.getHavePostfile()==1) {
			pfService.deleteFiles(post_id);
		}
		
		if (file0 != null) {
			post.setHavePostfile(1);
			ArrayList<MultipartFile> files = new ArrayList<>();
			files.add(file0);
			if(file1 != null)
				files.add(file1);
			if(file2 != null)
				files.add(file2);
			
			for(MultipartFile file : files)
				pfService.submitFileUpload(file, post);
	    }else
	    	post.setHavePostfile(0);

		postService.updatePost(post);
	}
	//delete
	@DeleteMapping("/{post_id}")
	public long deletePost(@PathVariable long post_id) {
		Post post = postService.readPost(post_id);
		postService.deletePost(post);
		commentService.deleteCommentList(post_id);
		if(post.getHavePostfile()==1)
			pfService.deleteFiles(post_id);
		
		return post.getBoardId();
	}
	
	@GetMapping("/findBoard/{post_id}")
	public long findBoard(@PathVariable long post_id) {
		return postService.getBoardIdByPostId(post_id);
	}
	
	@GetMapping("/find/{id}")
	public long findUserByCommentId(@PathVariable long id) {
		return postService.findUserByPostId(id);
	}
}