package com.npcweb.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.npcweb.dao.PostDAO;
import com.npcweb.domain.Post;
import com.npcweb.domain.response.PostResponse;
import com.npcweb.repository.PostPagingRepository;
import com.npcweb.repository.PostRepository;

@Service
public class PostService {
	@Autowired
	PostDAO postDao;
	@Autowired
	PostRepository postRepo;
	@Autowired
	PostPagingRepository postPagingRepo;
	
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
	
	/* 전체 페이징
	public Page<PostResponse> paging(Pageable pageable) {
        int page = pageable.getPageNumber() - 1; // page 위치에 있는 값은 0부터 시작한다.
        int pageLimit = 11; // 한페이지에 보여줄 글 개수
 
        Page<Post> postsPages = postRepo.findAll(PageRequest.of(page, pageLimit, Sort.by(Direction.DESC, "postId")));
 
        Page<PostResponse> postsResponseDtos = postsPages.map(
                postPage -> new PostResponse(postPage));
 
        return postsResponseDtos;
    }
    */
	
	// 게시판별 페이징
	public Page<PostResponse> pagingByBoard(Pageable pageable, long boardId) {
	    int page = pageable.getPageNumber(); // page 위치에 있는 값은 0부터 시작한다.
	    int pageLimit = 11; // 한 페이지에 보여줄 글 개수

	    Pageable p = PageRequest.of(page, pageLimit, Sort.by(Direction.DESC, "postId"));
	    Page<Post> postsPages = postPagingRepo.findAllByBoardIdWithImportantSorting(boardId, p);

	    Page<PostResponse> postsResponseDtos = postsPages.map(PostResponse::new);

	    return postsResponseDtos;
	}
	
	// 검색 결과 페이징
	public Page<PostResponse> pagingBySearch(Pageable pageable, List<Post> searchResult) {
	    int page = pageable.getPageNumber();
	    int pageLimit = 11;

	    int startIdx = page * pageLimit;
	    int endIdx = Math.min(startIdx + pageLimit, searchResult.size());
	    List<Post> paginatedResults = searchResult.subList(startIdx, endIdx);

	    paginatedResults.sort(Comparator.comparing(Post::getPostId).reversed());

	    Page<PostResponse> pageResponse = new PageImpl<>(
	            paginatedResults.stream().map(PostResponse::new).collect(Collectors.toList()),
	            pageable,
	            searchResult.size()
	    );

	    return pageResponse;
	}
}
