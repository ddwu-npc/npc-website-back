package com.npcweb.repository;

import org.springframework.data.repository.CrudRepository;

import com.npcweb.domain.PostFile;

public interface FileRepository extends CrudRepository<PostFile, Long> {
}
