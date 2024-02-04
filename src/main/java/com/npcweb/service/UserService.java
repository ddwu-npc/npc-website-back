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
	
	public User insert(User user) {
		return userRepo.save(user);
	}	
	
	public String getNickname(long userNo) {
		return userRepo.findById(userNo).get().getNickname();
	}
	
	public User getUserByUserId(String userId) {
		return userRepo.findByUserId(userId);
	}
	
	public User getUserByUserNo(long userNo) {
		return userRepo.findById(userNo).get();
	}
	
	public User getUserByUserPw(String userId, String userPw) {
		return userRepo.findByUserIdAndUserPw(userId, userPw);
	} 
	
	public User getUserByEmail(String userId, String email) {
		return userRepo.findByUserIdAndEmail(userId, email);
	}
	
	public void update(User user) {
		userRepo.save(user);
	}
	
	public void UpdatePassword(String userId, String userPw) {
		userRepo.updatePassword(userId, userPw);
	}
	
	public int UserIdCheck (String userId) {
		return userDao.UserIdCheck(userId);
	}
	
	public int NicknameCheck (String nickname) {
		return userDao.NicknameCheck(nickname);
	}
	
	public int EmailCheck (String email) {
		return userDao.EmailCheck(email);
	}
	
	public User getUserByNickname(String nickname) {
		return userDao.getUserByNickname(nickname);
	}

	public int fineRankByuserNo(long userNo) {
		return userDao.findRankByuserNo(userNo);
	}
	
	public void calcPoints(long userNo, int point) {
		User u = userRepo.findByUserNo(userNo);
		int currPoint = u.getNpcPoint();
		u.setNpcPoint(currPoint + point);
		userRepo.save(u);
	}
	
	public void withdrawlAccount(long userNo) {
		User u = userRepo.findByUserNo(userNo);
		u.setNickname("탈퇴한 회원");
		u.setNpcPoint(0);
		u.setUserId("탈퇴한 회원");
		userRepo.save(u);
		
		// 프로젝트 탈퇴 로직 추가 필요
		// 1. 리더로 속한 프로젝트가 있을 시 탈퇴 불가
		// 2. 속한 프로젝트 탈퇴 처리
		
		// userId나 nickname이 중복이 되는데 문제가 생길 지 한 번 더 생각
	}
}
