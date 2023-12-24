package com.npcweb.domain;

import java.util.Date;

public class PostResponse {
	private long postId, boardId, userNo;
	private String title, content;
	private Date createDate, updateDate;
	private int important, readCount;
	private String rangePost;
	private int havePostfile;
	
	public PostResponse(Post p) {
		super();
		this.postId = p.getPostId();
		this.boardId = p.getBoardId();
		this.userNo = p.getUserNo();
		this.title = p.getTitle();
		this.content = p.getContent();
		this.createDate = p.getCreateDate();
		this.updateDate = p.getUpdateDate();
		this.important = p.getImportant();
		this.readCount = p.getReadCount();
		this.rangePost = p.getRangePost();
		this.havePostfile = p.getHavePostfile();
	}
	
	public long getPostId() {
		return postId;
	}
	public long getBoardId() {
		return boardId;
	}
	public long getUserNo() {
		return userNo;
	}
	public String getTitle() {
		return title;
	}
	public String getContent() {
		return content;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public int getImportant() {
		return important;
	}
	public int getReadCount() {
		return readCount;
	}
	public String getRangePost() {
		return rangePost;
	}
	public int getHavePostfile() {
		return havePostfile;
	}
}
