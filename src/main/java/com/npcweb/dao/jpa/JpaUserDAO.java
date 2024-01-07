package com.npcweb.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.npcweb.domain.Dept;
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
			user.setDept(new Dept());;
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
	
	public int UserIdCheck(String userId) throws DataAccessException {
    	Query query = em.createQuery("select u From User u where userId = ?1");
    	query.setParameter(1, userId);
    		
    	int count = query.getResultList().size();
    	return count;
	}
        
	public int NicknameCheck(String nickname) throws DataAccessException {
    	Query query = em.createQuery("select u From User u where nickname = ?1");
    	query.setParameter(1, nickname);
    		
    	int count = query.getResultList().size();
    	return count;
	}

	public int EmailCheck(String email) {
    	Query query = em.createQuery("select u From User u where email = ?1");
    	query.setParameter(1, email);
    		
    	int count = query.getResultList().size();
    	return count;
	}
	
	public User getUserByNickname(String nickname) {
		TypedQuery<User> query = em.createQuery(
				"select u from User u where u.nickname=:nickname",
				User.class);
		query.setParameter("nickname", nickname);
		
		User user = null;
		
		try {
			user = query.getSingleResult();
		} catch(NoResultException ex) {
			return null;
		}
		return user;
	}

	public int findRankByuserNo(long userNo) {
		TypedQuery<User> query = em.createQuery(
				"select u from User u where u.userNo=:userNo",
				User.class);
		query.setParameter("userNo", userNo);
		
		User user = query.getSingleResult();
		
		return user.getRank();
	}
}