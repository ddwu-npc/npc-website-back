package com.npcweb.repository;

import org.springframework.data.repository.CrudRepository;

import com.npcweb.domain.UserFile;

public interface UserFileRepository extends CrudRepository<UserFile, Long> {
}
