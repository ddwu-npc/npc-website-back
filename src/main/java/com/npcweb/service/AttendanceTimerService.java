package com.npcweb.service;

import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npcweb.domain.Attendance;
import com.npcweb.domain.User;

@Service
public class AttendanceTimerService {
	@Autowired AttendanceService attendanceService;
	@Autowired PostService postService;
	
	private Timer timer = new Timer();

    public void scheduleTimer(Attendance attendance, int changePoint) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
            	finishedAttendance(attendance, changePoint);
            }
        };

        // 60초 후에 타이머 작업 실행(나중에 시간 늘리기)
        timer.schedule(task, 60000);
    }

    public void cancelTimer() {
        timer.cancel(); 
    }

    private void finishedAttendance(Attendance attendance, int changePoint) {
    	attendance.setType("종료");
    	attendanceService.insert(attendance);
    	Set<User> userList = attendanceService.endAttend(attendance, changePoint);
    	// 정기회의 따로 빼기
    	if (attendance.getProject().getPid() == 1000)
    		postService.insertAttendPost(userList, attendance);
    }

}
