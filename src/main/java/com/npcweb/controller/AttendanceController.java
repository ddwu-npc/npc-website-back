package com.npcweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.npcweb.domain.Attendance;
import com.npcweb.service.AttendanceService;

@CrossOrigin(origins = "http://localhost:3000") 
@RestController
@RequestMapping("/attendance")
public class AttendanceController {
    @Autowired
    private AttendanceService attendanceService;

    // 생성
	@GetMapping("/{project_id}")
	public long getQuickAttendance(@PathVariable long project_id) {
		/*
		 * 1. 팀장이 생성한 출석이 있다면 
		 *  - 팀원을 그 출석을 봐야함
		 *  1-1. 출석을 했다면
		 *   - 반영
		 *   - 10분이 지나면 종료하고 프로젝트에 붙은 출석을 null로
		 * 2. 없다면
		 *  - 현재 출석 불가
		 * */
	}
}
