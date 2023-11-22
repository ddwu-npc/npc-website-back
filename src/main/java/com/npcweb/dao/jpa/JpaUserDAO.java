package com.npcweb.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.npcweb.domain.User;

@Repository
@org.springframework.transaction.annotation.Transactional
public class JpaUserDAO {
	@PersistenceContext
    private EntityManager em;

	public User getUser(String userId) throws DataAccessException {
		TypedQuery<User> query = em.createQuery(
				"select u from USER u where u.user_id=:user_id",
				User.class);
		query.setParameter("user_id", userId);
		
		User user = null;
		
		try {
			user = query.getSingleResult();
		} catch(NoResultException ex) {
			return null;
		}
		return user;
	}
	
	// 로그인
	public User getUserByUserPw(String userId, String userPw) throws DataAccessException {
		TypedQuery<User> query = em.createQuery(
				"select u from User u where u.userId=:userId and u.userPw=:userPw",
				User.class);
		query.setParameter("userId", userId);
		query.setParameter("userPw", userPw);
		
		User user = null;
		
		try {
			user = query.getSingleResult();
		} catch(NoResultException ex) {
			return null;
		}
		return user;
	}
	
	public void updateUser(User user) throws DataAccessException {
        em.merge(user);
	}
	
	public void updatePassword(String userId, String userPw) throws DataAccessException {
		User u = em.find(User.class, userId);
		u.setUserPw(userPw);
	}
	
	// 아이디 중복 검사 (0:없음, 1:존재)
	public int UserIdCheck(String userId) throws DataAccessException {
    	Query query = em.createQuery("select u From User u WHERE userId = ?1");
    	query.setParameter(1, userId);
    		
    	int count = query.getFirstResult();
    	return count;
	}
        
	// 닉네임 중복 검사 (0:없음, 1:존재)
	public int NicknameCheck(String nickname) throws DataAccessException {
    	Query query = em.createQuery("select u From User u WHERE nickname = ?1");
    	query.setParameter(1, nickname);
    		
    	int count = query.getFirstResult();
    	return count;
	}
}