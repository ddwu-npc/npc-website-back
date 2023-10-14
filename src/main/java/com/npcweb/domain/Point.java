package com.npcweb.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="NPC_POINT")
public class Point {
	@Id
	@Column(name="point_id")
	long pointId;
	long userno, deptno;
	
	@Column(name="change_point")
	int changePoint;
	
	String content;
	
	@Column(name="point_date")
	Date pointDate;

	public long getPointId() {
		return pointId;
	}

	public void setPointId(long pointId) {
		this.pointId = pointId;
	}

	public long getUserno() {
		return userno;
	}

	public void setUserno(long userno) {
		this.userno = userno;
	}

	public long getDeptno() {
		return deptno;
	}

	public void setDeptno(long deptno) {
		this.deptno = deptno;
	}

	public int getChangePoint() {
		return changePoint;
	}

	public void setChangePoint(int changePoint) {
		this.changePoint = changePoint;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getPointDate() {
		return pointDate;
	}

	public void setPointDate(Date pointDate) {
		this.pointDate = pointDate;
	}
	
	
}
