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

    public void scheduleTimer(Attendance attendance, int absentPoint) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
            	finishedAttendance(attendance, absentPoint);
            }
        };

        // 출석은 10분(600초) 동안 가능
        timer.schedule(task, 600000);
    }

    public void cancelTimer() {
        timer.cancel(); 
    }

    private void finishedAttendance(Attendance attendance, int absentPoint) {
    	attendance.setType("종료");
    	attendanceService.insert(attendance);
    	Set<User> userList = attendanceService.endAttend(attendance, absentPoint);
    	
    	// 정기회의 출석 결과를 공지 게시판에 등록
    	if (attendance.getProject().getPid() == 1)
    		postService.insertAttendPost(userList, attendance);
    }

}
