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

import com.npcweb.dao.BoardDAO;
import com.npcweb.domain.Post;
import com.npcweb.domain.response.PostResponse;
import com.npcweb.repository.PostPagingRepository;

@Service
public class BoardService {
	@Autowired BoardDAO boardDao;
	@Autowired PostPagingRepository postPagingRepo;
	
	//게시글 목록보기
	public List<Post> getAllPostList(long board_id){
		return boardDao.getAllPost(board_id);
	}
	
	//게시글 검색
	public List<Post> getAllPostListByKeyword(long board_id, long rangeId, long searchRange, String text, long userRank){
		return boardDao.getAllPostListByKeyword(board_id, rangeId, searchRange, text, userRank);
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
	public Page<PostResponse> pagingByBoard(Pageable pageable, long boardId, String rangePost) {
	    int page = pageable.getPageNumber(); // page 위치에 있는 값은 0부터 시작한다.
	    int pageLimit = 11; // 한 페이지에 보여줄 글 개수

	    Pageable p = PageRequest.of(page, pageLimit, Sort.by(Direction.DESC, "postId"));
	    Page<Post> postsPages = null;
	    
	    if(rangePost.equals("임원"))
	    	postsPages = postPagingRepo.findAllByBoardIdWithImportantSorting(boardId, p);
	    else
	    	postsPages = postPagingRepo.findAllByBoardIdWithImportantSorting(boardId, rangePost, p);

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