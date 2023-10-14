package com.npcweb.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="USER")
public class User {
	@Id
	long userNo;
	long pid, deptNo;
	
	@Column(name="user_id")
	String userId;
	@Column(name="user_pw")
	String userPw; 
	String nickname, email;
	Date recentDate, birthday;
	int rank, npcPoint;
	
	public long getUserNo() {
		return userNo;
	}
	public void setUserNo(long userNo) {
		this.userNo = userNo;
	}
	
	public long getPid() {
		return pid;
	}
	public void setPid(long pid) {
		this.pid = pid;
	}
	
	public long getDeptNo() {
		return deptNo;
	}
	public void setDeptNo(long deptNo) {
		this.deptNo = deptNo;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getUserPw() {
		return userPw;
	}
	public void setUserPw(String userPw) {
		this.userPw = userPw;
	}
	
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Date getRecentDate() {
		return recentDate;
	}
	public void setRecentDate(Date recentDate) {
		this.recentDate = recentDate;
	}
	
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	
	public int getNpcPoint() {
		return npcPoint;
	}
	public void setNpcPoint(int npcPoint) {
		this.npcPoint = npcPoint;
	}
	
	
}
