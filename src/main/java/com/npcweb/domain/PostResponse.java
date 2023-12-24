package com.npcweb.domain;

public class PostResponse {
	private long postId, boardId, userNo;
	private String title;
	private String createDate, updateDate;
	private int important;
	private String rangePost;
	
	public PostResponse(Post p) {
		super();
		this.postId = p.getPostId();
		this.boardId = p.getBoardId();
		this.userNo = p.getUserNo();
		this.title = p.getTitle();
		this.createDate = p.getFormattedCreateDate();
		this.updateDate = p.getFormattedUpdateDate();
		this.important = p.getImportant();
		this.rangePost = p.getRangePost();
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
	public String getCreateDate() {
		return createDate;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public int getImportant() {
		return important;
	}
	public String getRangePost() {
		return rangePost;
	}
}
