package com.npcweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npcweb.dao.jpa.JpaUserDAO;
import com.npcweb.domain.User;
import com.npcweb.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private JpaUserDAO userDao;
	@Autowired
	private UserRepository userRepo;
	
	public void insert(User user) {
		userRepo.save(user);
	}	
	public User getUserByUserId(String userId) {
		return userRepo.findByUserId(userId);
	}
	
	public User getUserByUserNo(long userNo) {
		return userRepo.findByUserNo(userNo);
	}
	
	public User getUserByUserPw(String userId, String userPw) {
		return userDao.getUserByUserPw(userId, userPw);
	}
	
	public void update(User user) {
		userDao.updateUser(user);;
	}
	
	public void UpdatePassword(String userId, String userPw) {
		userDao.updatePassword(userId, userPw);
	}
	
	public int UserIdCheck (String userId) {
		return userDao.UserIdCheck(userId);
	}
	
	public int NicknameCheck (String nickname) {
		return userDao.NicknameCheck(nickname);
	}
}
