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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.npcweb.domain.Attendance;
import com.npcweb.domain.Project;
import com.npcweb.service.AttendanceService;
import com.npcweb.service.ProjectService;

@CrossOrigin(origins = "http://localhost:3000") 
@RestController
@RequestMapping("/attendance")
public class AttendanceController {
    @Autowired
    private AttendanceService attendanceService;
    @Autowired
    private ProjectService projectService;
    
    private Map<String, LocalDateTime> attendanceMap = new ConcurrentHashMap<>(); // 출석 정보를 저장할 Map (출석 번호, 생성 시간)

    // 프로젝트의 출석 중 현재 가능한 것만 반환
	@GetMapping("quick/{project_id}")
	public long getQuickAttendance(@PathVariable long project_id) {
		Project project = projectService.getProject(project_id);
		List<Attendance> attendances = project.getAttendances();
			
		for (Attendance a : attendances) {
			if (!a.getType().equals("종료"))
				return a.getAttendanceId();
		}
		return 0; // 현재 출석이 없다고 띄워야 함
	}
	
	@GetMapping("/create/{project_id}")
	public long createAttendance(@PathVariable long project_id) {
		Project project = projectService.getProject(project_id);
		Attendance attendance = new Attendance();
		
		attendance.setAttendanceDate(new Date());
		attendance.setMeeting(new Date() + project.getPname());
		attendance.setAuthCode(generateRandomAttendanceCode());
		attendance.setType("진행");
		attendance.setProject(project);
		attendanceService.insert(attendance);
		
		return attendance.getAttendanceId();
	}
	
	@GetMapping("/{attendance_id}")
	public Attendance getAttendanceInfo(@PathVariable long attendance_id) {
		Attendance attendance = attendanceService.getAttendance(attendance_id);

		return attendance;
	}
	
    private int generateRandomAttendanceCode() {
        Random random = new Random();
        int randomNumber = 100 + random.nextInt(900); // 100부터 999까지의 랜덤 숫자 생성
        return randomNumber;
    }
}
