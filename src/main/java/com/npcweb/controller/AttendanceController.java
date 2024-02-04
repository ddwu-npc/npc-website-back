package com.npcweb.controller;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.npcweb.domain.Attendance;
import com.npcweb.domain.Point;
import com.npcweb.domain.Project;
import com.npcweb.security.JWTProvider;
import com.npcweb.service.AttendanceService;
import com.npcweb.service.AttendanceTimerService;
import com.npcweb.service.PointService;
import com.npcweb.service.ProjectService;
import com.npcweb.service.UserService;

@CrossOrigin(origins = "http://localhost:3000") 
@RestController
@RequestMapping("/attendance")
public class AttendanceController {
    @Autowired private AttendanceService attendanceService;
    @Autowired private AttendanceTimerService timerService;
    @Autowired private ProjectService projectService;
    @Autowired private JWTProvider jwtProvider;
    @Autowired private UserService userService;
    @Autowired private PointService pointService;
    
    static int attendPoint = 4; // 출석 포인트
    static int absentPoint = -10; // 결석 포인트
    
    // 프로젝트의 출석 중 현재 가능한 것만 반환
	@GetMapping("quick/{project_id}")
	public long getQuickAttendance(@PathVariable long project_id) {
		Project project = projectService.getProject(project_id);
		List<Attendance> attendances = project.getAttendances();
		
		for (Attendance a : attendances) {
			if (!a.getType().equals("종료"))
				return a.getAttendanceId();
		}
		return -100; // 현재 열린 출석 없음
	}
	
	@GetMapping("/create/{project_id}")
	public long createAttendance(@PathVariable long project_id) {
		Project project = projectService.getProject(project_id);
		Attendance attendance = new Attendance();
		
		int meetingCount = project.getAttendances().size() + 1;
		attendance.setAttendanceDate(new Date());
		attendance.setMeeting(project.getPname() + " " + meetingCount +"차시");
		attendance.setAuthCode(attendanceService.generateRandomAttendanceCode());
		attendance.setType("진행");
		attendance.setProject(project);
		attendanceService.insert(attendance);
		
		// 팀장 자동 출석 
		userService.calcPoints(project.getLeader(), attendPoint);
        // 내역 저장
		long attendanceId = attendance.getAttendanceId();
        Point p = new Point(project.getLeader(), attendanceId, attendPoint, attendance.getMeeting() + " 출석", attendance.getAttendanceDate());
		pointService.insert(p);
		
		// 타이머
		timerService.scheduleTimer(attendance, absentPoint);
		return attendanceId;
	}
	
	@GetMapping("/{attendance_id}")
	public AttendanceReq getAttendanceInfo(@PathVariable long attendance_id, @RequestHeader("Authorization") String token) {
		String jwtToken = token.replace("Bearer ", "").replace("\"", "");
        long userNo = jwtProvider.getUsernoFromToken(jwtToken);;
        
        AttendanceReq req = new AttendanceReq();
        Attendance attendance = attendanceService.getAttendance(attendance_id);
        Project project = attendance.getProject();
        
        req.setAttendance(attendance);
        if (userNo == project.getLeader())
        	req.setLeader(true);
        else req.setLeader(false);
        
		return req;
	}
	
	@GetMapping("/{attendance_id}/{authcode}")
	public int attend(@PathVariable long attendance_id, @PathVariable String authcode, @RequestHeader("Authorization") String token) {
		Attendance attendance = attendanceService.getAttendance(attendance_id);
		String _authcode = Integer.toString(attendance.getAuthCode());
		String jwtToken = token.replace("Bearer ", "").replace("\"", "");
        long userNo = jwtProvider.getUsernoFromToken(jwtToken);
        boolean isAttend = attendanceService.isAttend(userNo, attendance_id);
		
		if (_authcode.equals(authcode) && !isAttend) {
			// 출석
	        userService.calcPoints(userNo, attendPoint);
	        
	        // 내역 저장
	        Point p = new Point(userNo, attendance_id, attendPoint, attendance.getMeeting() + " 출석", attendance.getAttendanceDate());
			pointService.insert(p);
			
			return 1;
		}
		if (isAttend)
			return -200;
		if (!_authcode.equals(authcode))
			return -100;
		
		return 0;
	}
}

class AttendanceReq {
	private Attendance attendance;
	private boolean isLeader;
	
	public Attendance getAttendance() {
		return attendance;
	}
	public boolean isLeader() {
		return isLeader;
	}
	public void setAttendance(Attendance attendance) {
		this.attendance = attendance;
	}
	public void setLeader(boolean isLeader) {
		this.isLeader = isLeader;
	}
}