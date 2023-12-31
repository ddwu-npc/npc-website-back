package com.npcweb.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
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
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	@Column(name="post_id")
	long postId;
	
	@Column(name="board_id")
	long boardId;
	
	@Column(name="userno")
	long userNo;
	String title, content;
	
	@Column(name="create_date")
	Date createDate;
	
	@Column(name="update_date")
	Date updateDate;
	
	int important;
	
	@Column(name="read_count")
	int readCount;
	
	@Column(name="rangePost")
	String rangePost;
	
	@Column(name="postfile")
	int havePostfile;

	public int getHavePostfile() {
		return havePostfile;
	}

	public void setHavePostfile(int havePostfile) {
		this.havePostfile = havePostfile;
	}

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

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateteDate) {
		this.updateDate = updateteDate;
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
	
	// 날짜 변환
	public String getFormattedCreateDate() {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    return dateFormat.format(createDate);
	}
	public String getFormattedUpdateDate() {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    if (updateDate != null)
	    	return dateFormat.format(updateDate);
	    else return null;
	}
}
