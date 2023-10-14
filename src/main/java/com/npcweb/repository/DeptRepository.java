package com.npcweb.repository;

import org.springframework.data.repository.CrudRepository;

import com.npcweb.domain.Dept;

public interface DeptRepository extends CrudRepository<Dept, Long> {
}
