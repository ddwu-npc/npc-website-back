package com.npcweb.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.npcweb.dao.jpa.JpaPostDAO;
import com.npcweb.domain.Post;
import com.npcweb.security.JWTProvider;
import com.npcweb.service.PostService;
import com.npcweb.service.CommentService;

@CrossOrigin(origins = "http://localhost:3000") 
@RestController
@RequestMapping("/post")
public class PostController {
	@Autowired JpaPostDAO postDao;
	@Autowired PostService postService;
	@Autowired CommentService commentService;
	@Autowired
	private JWTProvider jwtProvider;

	//read
	@GetMapping("/{post_id}")
	public ResponseEntity<Post> readPost(@PathVariable long post_id) {
	    try {
	        Post post = postService.readPost(post_id);
	        if (post != null) {
	            return ResponseEntity.ok(post);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    } catch (Exception e) {
	        // Handle the exception and return an error response
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}
	//create
	@PostMapping("/{board_id}")
	public void createPost(HttpServletRequest request, @PathVariable long board_id, @RequestBody PostReq req, @RequestHeader("Authorization") String token) {
		String jwtToken = token.replace("Bearer ", "").replace("\"", "");
		//System.out.println("content:"+req.getContent());
		
        long userNo = jwtProvider.getUsernoFromToken(jwtToken);
		
		Post post = new Post();
		post.setBoardId(board_id);
		post.setContent(req.getContent());
		post.setCreateDate(new Date());
		post.setImportant(req.getImportant());
		post.setRangePost(req.getRangePost());
		post.setTitle(req.getTitle());
		post.setUserNo(userNo);

		postService.insertPost(post);
	}
	//update
	@PutMapping("/{post_id}")
	public void updatePost(@RequestBody PostReq req, @PathVariable long post_id) {
		Post post = postService.readPost(post_id);
		post.setPostId(post_id);
		post.setBoardId(post.getBoardId());
		post.setContent(req.getContent());
		post.setImportant(req.getImportant());
		post.setUpdateDate(new Date());
		post.setRangePost(req.getRangePost());
		post.setTitle(req.getTitle());
		post.setUserNo(post.getUserNo());

		postService.updatePost(post);
	}
	//delete
	@DeleteMapping("/{post_id}")
	public long deletePost(@PathVariable long post_id) {
		Post post = postService.readPost(post_id);
		postService.deletePost(post);
		commentService.deleteCommentList(post_id);
		
		return post.getBoardId();
	}
	
	@GetMapping("/findBoard/{post_id}")
	public long findBoard(@PathVariable long post_id) {
		return postService.getBoardIdByPostId(post_id);
	}
}

class PostReq {
	private String title, content, rangePost;
	private long userNo, boardId;
	private int important, readCount;
	private Date create_date;

	public PostReq() {}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public String getRangePost() {
		return rangePost;
	}

	public long getUserNo() {
		return userNo;
	}

	public long getBoardId() {
		return boardId;
	}

	public int getImportant() {
		return important;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public int getReadCount() {
		return readCount;
	}

	@Override
	public String toString() {
		return "PostReq [title=" + title + ", content=" + content + ", rangePost=" + rangePost + ", userNo=" + userNo
				+ ", boardId=" + boardId + ", important=" + important + ", readCount=" + readCount + ", create_date="
				+ create_date + "]";
	}
}
