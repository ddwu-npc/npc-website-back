package com.npcweb.repository;

import org.springframework.data.repository.CrudRepository;

import com.npcweb.domain.File;

public interface FileRepository extends CrudRepository<File, Long> {
}
