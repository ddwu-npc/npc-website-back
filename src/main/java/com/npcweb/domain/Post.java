package com.npcweb.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="POST")
public class Post implements Serializable {
	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="post_id")
	long postId;
	
	@Column(name="board_id")
	long boardId;
	
	long userNo;
	String title, content;
	
	@Column(name="create_date")
	Date createDate;
	
	@Column(name="update_date")
	Date updateteDate;
	
	@Column(name="delete_date")
	Date deleteDate;
	
	int important;
	
	@Column(name="read_count")
	int readCount;
	
	@Column(name="range_post")
	String rangePost;

	public long getPostId() {
		return postId;
	}

	public void setPostId(long postId) {
		this.postId = postId;
	}

	public long getBoardId() {
		return boardId;
	}

	public void setBoardId(long boardId) {
		this.boardId = boardId;
	}

	public long getUserNo() {
		return userNo;
	}

	public void setUserNo(long userNo) {
		this.userNo = userNo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateteDate() {
		return updateteDate;
	}

	public void setUpdateteDate(Date updateteDate) {
		this.updateteDate = updateteDate;
	}

	public Date getDeleteDate() {
		return deleteDate;
	}

	public void setDeleteDate(Date deleteDate) {
		this.deleteDate = deleteDate;
	}

	public int getImportant() {
		return important;
	}

	public void setImportant(int important) {
		this.important = important;
	}

	public int getReadCount() {
		return readCount;
	}

	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}

	public String getRangePost() {
		return rangePost;
	}

	public void setRangePost(String rangePost) {
		this.rangePost = rangePost;
	}
	
	
}
