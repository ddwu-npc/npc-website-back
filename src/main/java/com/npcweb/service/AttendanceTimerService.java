package com.npcweb.service;

import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npcweb.domain.Attendance;

@Service
public class AttendanceTimerService {
	@Autowired AttendanceService attendanceService;
	private Timer timer = new Timer();

    public void scheduleTimer(Attendance attendance) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
            	finishedAttendance(attendance);
            }
        };

        // 5분(300초) 후에 타이머 작업 실행
        // 예시로 30초
        timer.schedule(task, 30000);
    }

    public void cancelTimer() {
        timer.cancel(); 
    }

    private void finishedAttendance(Attendance attendance) {
    	attendance.setType("종료");
    	attendanceService.insert(attendance);
    	System.out.println("출석 종료입니당");
    }

}
