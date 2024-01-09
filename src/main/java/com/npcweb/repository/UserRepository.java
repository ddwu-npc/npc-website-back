package com.npcweb.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.npcweb.domain.User;

public interface UserRepository extends CrudRepository<User, Long> {
	User findByUserId(String userId);
	User findByUserNo(long userNo);
    User findByUserIdAndUserPw(String userId, String userPw); 
    User findByUserIdAndEmail(String userId, String email);
    
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update User u set u.userPw = :newPassword where u.userId = :userId")
    void updatePassword(@Param("userId") String userId, @Param("newPassword") String newPassword);
}
