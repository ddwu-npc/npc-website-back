package com.npcweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.npcweb.service.AttendanceService;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @PostMapping("/create")
    public String createAttendance(@RequestParam("userId") String userId) {
        String attendanceNumber = attendanceService.createAttendance(userId);
        return "출석 코드: " + attendanceNumber;
    }

    @GetMapping("/get")
    public String getAttendanceNumber(@RequestParam("userId") String userId) {
        String attendanceNumber = attendanceService.getAttendanceNumber(userId);
        if (attendanceNumber != null) {
            return "출석 기록: " + attendanceNumber;
        } else {
            return "출석 기록 없음: " + userId;
        }
    }
    
    @PostMapping("/check")
    public String checkAttendance(@RequestParam("userId") String userId, @RequestParam("attendanceCode") String attendanceCode) {
        String storedAttendanceNumber = attendanceService.getAttendanceNumber(userId);

        if (storedAttendanceNumber != null && storedAttendanceNumber.equals(attendanceCode)) {
            // 출석 코드가 일치하면 포인트를 지급
            int pointsEarned = 10; // 포인트 지급량
            attendanceService.givePoints(userId);
            return "출석 코드 일치 +" + pointsEarned + "포인트";
        } else {
            return "출석 코드 불일치";
        }
    }
}
