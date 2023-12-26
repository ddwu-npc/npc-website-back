package com.npcweb.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.npcweb.domain.Post;

@Repository
public interface PostResRepository extends JpaRepository<Post, Long> {

	Page<Post> findAll(Pageable pageable);
	Page<Post> findAllByBoardId(long boardId, Pageable pageable);
}