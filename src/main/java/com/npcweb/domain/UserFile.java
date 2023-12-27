package com.npcweb.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="USERFILE")
public class UserFile{
	@Id
	@Column(name="file_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long fileId;
	
	@Column(name="org_file_name")
	String orgName;
	@Column(name="stored_file_name")
	String sName;
	
	@Column
	String filePath;
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userno")
    private User user;

	public long getFileId() {
		return fileId;
	}

	public void setFileId(long fileId) {
		this.fileId = fileId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getsName() {
		return sName;
	}

	public void setsName(String sName) {
		this.sName = sName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}