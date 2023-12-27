package com.npcweb.repository;

import org.springframework.data.repository.CrudRepository;

import com.npcweb.domain.User;

public interface UserRepository extends CrudRepository<User, Long> {
	User findByUserId(String userId);
	User findByUserNo(long userNo);
    User findByUserIdAndUserPw(String userId, String userPw); 
}
