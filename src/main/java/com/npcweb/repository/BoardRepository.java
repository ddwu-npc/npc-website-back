package com.npcweb.repository;

import org.springframework.data.repository.CrudRepository;

import com.npcweb.domain.Board;

public interface BoardRepository extends CrudRepository<Board, Long>{

}
