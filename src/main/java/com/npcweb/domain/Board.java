package com.npcweb.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="BOARD")
public class Board {
	@Id
	@Column(name="board_id")
	long boardId;
	String bname;
	
	public long getBoardId() {
		return boardId;
	}
	public void setBoardId(long boardId) {
		this.boardId = boardId;
	}
	public String getBname() {
		return bname;
	}
	public void setBname(String bname) {
		this.bname = bname;
	}
	
	
}
