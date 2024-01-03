package com.npcweb.controller;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.npcweb.domain.Attendance;
import com.npcweb.domain.Point;
import com.npcweb.domain.Project;
import com.npcweb.domain.User;
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
    
    private Map<String, LocalDateTime> attendanceMap = new ConcurrentHashMap<>(); // 출석 정보를 저장할 Map (출석 번호, 생성 시간)

    // 프로젝트의 출석 중 현재 가능한 것만 반환
	@GetMapping("quick/{project_id}")
	public long getQuickAttendance(@PathVariable long project_id) {
		Project project = projectService.getProject(project_id);
		List<Attendance> attendances = project.getAttendances();
			
		// 프로젝트에 속한 인원만 출석할 수 있도록 추가 필요
		// 여기서 jwttoken을 받아서 project의 users와 비교
		
		for (Attendance a : attendances) {
			if (!a.getType().equals("종료"))
				return a.getAttendanceId();
		}
		return -1; // 현재 열린 출석 없음
	}
	
	@GetMapping("/create/{project_id}")
	public long createAttendance(@PathVariable long project_id) {
		Project project = projectService.getProject(project_id);
		Attendance attendance = new Attendance();
		
		int meetingCount = project.getAttendances().size() + 1;
		attendance.setAttendanceDate(new Date());
		attendance.setMeeting(project.getPname() + " " + meetingCount +"차시 회의");
		attendance.setAuthCode(attendanceService.generateRandomAttendanceCode());
		attendance.setType("진행");
		attendance.setProject(project);
		attendanceService.insert(attendance);
		
		// 타이머
		timerService.scheduleTimer(attendance);
		return attendance.getAttendanceId();
	}
	
	@GetMapping("/{attendance_id}")
	public Attendance getAttendanceInfo(@PathVariable long attendance_id) {
		Attendance attendance = attendanceService.getAttendance(attendance_id);

		return attendance;
	}
	
	@GetMapping("/{attendance_id}/{authcode}")
	public boolean attend(@PathVariable long attendance_id, @PathVariable String authcode, @RequestHeader("Authorization") String token) {
		Attendance attendance = attendanceService.getAttendance(attendance_id);
		String _authcode = Integer.toString(attendance.getAuthCode());
		
		String jwtToken = token.replace("Bearer ", "").replace("\"", "");
        long userNo = jwtProvider.getUsernoFromToken(jwtToken);
        
		// 포인트 적립
        User u = userService.getUserByUserNo(userNo);
        int currPoint = u.getNpcPoint();
        u.setNpcPoint(currPoint + 10);
        userService.update(u);
        
        // 내역 저장
        Point p = new Point(userNo, 10, attendance.getMeeting() + " 출석", attendance.getAttendanceDate());
		pointService.insert(p);
		
		if (_authcode.equals(authcode))
			return true;
		return false;
	}
}