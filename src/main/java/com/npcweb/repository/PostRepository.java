package com.npcweb.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.npcweb.domain.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

	//게시글 목록 보기 -> 오류나서 주석처리
	// List<Post> getAllPost(long board_id);
	//게시글 검색 - 게시글 기능
	//List<Post> findPostByTitle(String title);
	//게시글 검색 - 마이페이지(도경님)
}
