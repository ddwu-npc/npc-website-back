package com.npcweb.repository;

import org.springframework.data.repository.CrudRepository;

import com.npcweb.domain.User;

public interface UserRepository extends CrudRepository<User, Long> {

}
