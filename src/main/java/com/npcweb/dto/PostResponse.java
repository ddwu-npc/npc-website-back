package com.npcweb.dto;

import org.springframework.beans.factory.annotation.Autowired;

import com.npcweb.domain.Post;
import com.npcweb.service.UserService;

public class PostResponse {
	@Autowired private UserService userSerivice;
	
	private long postId, boardId;
	private String title, nickname;
	private String createDate, updateDate;
	private int important, havePostfile;
	private String rangePost;
	
	public PostResponse(Post p) {
		super();
		this.postId = p.getPostId();
		this.boardId = p.getBoardId();
		this.nickname = String.valueOf(p.getUserNo());
		this.title = p.getTitle();
		this.createDate = p.getFormattedCreateDate();
		this.updateDate = p.getFormattedUpdateDate();
		this.important = p.getImportant();
		this.havePostfile = p.getHavePostfile();
		this.rangePost = p.getRangePost();
	}
	
	public long getPostId() {
		return postId;
	}
	public long getBoardId() {
		return boardId;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
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
	public int getHavePostfile() {
		return havePostfile;
	}
	public String getRangePost() {
		return rangePost;
	}
}
