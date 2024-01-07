package com.npcweb.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="NPC_POINT")
public class Point {
	@Id
	@Column(name="point_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	long pointId;
	long userno;
	@Column(name="attendance_id")
	long attendanceId;
	
	@Column(name="change_point")
	int changePoint;
	
	String content;
	
	@Column(name="point_date")
	Date pointDate;

	public Point() {
		
	}
	
	public Point(long userno, long attendanceId, int changePoint, String content, Date pointDate) {
		this.attendanceId = attendanceId;
		this.userno = userno;
		this.changePoint = changePoint;
		this.content = content;
		this.pointDate = pointDate;
	}
	
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

	public long getAttendanceId() {
		return attendanceId;
	}

	public void setAttendanceId(long attendanceId) {
		this.attendanceId = attendanceId;
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
