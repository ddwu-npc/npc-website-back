package com.npcweb.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.npcweb.domain.Post;

public interface PostPagingRepository extends PagingAndSortingRepository<Post, Long> {
    @Query("SELECT p FROM Post p WHERE p.boardId = ?1 AND (p.rangePost = '전체' OR p.rangePost = ?2) ORDER BY p.important DESC, p.postId DESC")
    Page<Post> findAllByBoardIdWithImportantSorting(long boardId, String rangePost, Pageable pageable);
    
    @Query("SELECT p FROM Post p WHERE p.boardId = ?1 ORDER BY p.important DESC, p.postId DESC")
	Page<Post> findAllByBoardIdWithImportantSorting(long boardId, Pageable p);
}