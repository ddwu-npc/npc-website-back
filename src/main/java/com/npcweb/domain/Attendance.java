package com.npcweb.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="ATTENDANCE")
public class Attendance {
	@Id
	@Column(name="attendance_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	long attendanceId;
	
	long userno;
	String type, meeting;
	
	@Column(name="attendance_date")
	Date attendanceDate;
	
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pid")
    private Project project;
    
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

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
	
	
}
