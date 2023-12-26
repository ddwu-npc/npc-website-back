package com.npcweb.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="USER")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	long userNo;
	
	@Column(name="user_id")
	String userId;
	@Column(name="user_pw")
	String userPw; 
	String nickname, email, profile;
	@Column(name="recent_date")
	Date recentDate;
	Date birthday;
	int rank;
	@Column(name="npc_point")
	int npcPoint;
	
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "deptno")
	private Dept dept;
	
	@ManyToMany
	@JoinTable(
			name = "USER_PROJECT",
	        joinColumns = @JoinColumn(name = "userNo"),
	        inverseJoinColumns = @JoinColumn(name = "pid")
	)
	private Set<Project> PROJECT = new HashSet<>();
	
	public User() {
        this.dept = new Dept(); 
        this.dept.setDeptno(0); 
	}
	
	public User(String userId, String nickname, String userPw, String email) {
		this.userId = userId;
		this.nickname = nickname;
		this.userPw = userPw;
		this.email = email;
	}
	
	public User(long userNo, String userId, String userPw, String nickname, String email, Date recentDate,
			Date birthday, int rank, int npcPoint, Dept dept) {
		super();
		this.userNo = userNo;
		this.userId = userId;
		this.userPw = userPw;
		this.nickname = nickname;
		this.email = email;
		this.recentDate = recentDate;
		this.birthday = birthday;
		this.rank = rank;
		this.npcPoint = npcPoint;
		this.dept = dept;
	}

	public long getUserNo() {
		return userNo;
	}
	public void setUserNo(long userNo) {
		this.userNo = userNo;
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
	
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}

	public Dept getDept() {
		return dept;
	}

	public void setDept(Dept dept) {
        this.dept = dept;
        dept.getUsers().add(this);
	}

	public Set<Project> getProjects() {
		return PROJECT;
	}

	public void setProjects(Set<Project> PROJECT) {
		this.PROJECT = PROJECT;
	}
	
}
