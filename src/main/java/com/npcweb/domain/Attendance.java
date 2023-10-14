package com.npcweb.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Attendance")
public class Attendance {
	@Id
	@Column(name="attendance_id")
	long attendanceId;
	
	long userno;
	
	String type, meeting;
	
	@Column(name="attendance_date")
	Date attendanceDate;

	public long getAttendanceId() {
		return attendanceId;
	}

	public void setAttendanceId(long attendanceId) {
		this.attendanceId = attendanceId;
	}

	public long getUserno() {
		return userno;
	}

	public void setUserno(long userno) {
		this.userno = userno;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMeeting() {
		return meeting;
	}

	public void setMeeting(String meeting) {
		this.meeting = meeting;
	}

	public Date getAttendanceDate() {
		return attendanceDate;
	}

	public void setAttendanceDate(Date attendanceDate) {
		this.attendanceDate = attendanceDate;
	}
	
	
}
