package com.npcweb.repository;

import org.springframework.data.repository.CrudRepository;

import com.npcweb.domain.Comment;

public interface CommentRepository extends CrudRepository<Comment, Long> {

}
