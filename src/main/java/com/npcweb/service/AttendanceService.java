package com.npcweb.service;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npcweb.domain.Attendance;
import com.npcweb.domain.Point;
import com.npcweb.domain.UserProject;
import com.npcweb.repository.AttendanceRepository;
import com.npcweb.repository.PointRepository;
import com.npcweb.repository.UserProjectRepository;

@Service
public class AttendanceService {
	@Autowired AttendanceRepository attendanceRepo;
	@Autowired PointRepository pointRepo;
	@Autowired UserProjectRepository userProjectRepo;
	@Autowired UserService userService;
	
	public void insert(Attendance a) {
		attendanceRepo.save(a);
	}
	
	public Attendance getAttendance(long attendance_id) {
		return attendanceRepo.findById(attendance_id).get();
	}
	
    public int generateRandomAttendanceCode() {
        Random random = new Random();
        int randomNumber = 100 + random.nextInt(900); // 100부터 999까지의 랜덤 숫자 생성
        return randomNumber;
    }
    
    public boolean isAttend(long userNo, long attendanceId) {
    	List<Point> p = pointRepo.findByUsernoAndAttendanceId(userNo, attendanceId);
    	if (p.size() == 0)
    		return false;
    	return true;
    }

	public void endAttend(Attendance attendance, int changePoint) {
		long pid = attendance.getProject().getPid();
		List<UserProject> list = userProjectRepo.findByid_Pid(pid);
		
		for (UserProject u : list) {
			long userno = u.getId().getUserno();
			if (isAttend(userno, attendance.getAttendanceId()) == false) {
				userService.calcPoints(userno, -changePoint);
		        Point p = new Point(userno, attendance.getAttendanceId(), -changePoint, attendance.getMeeting() + " 결석", attendance.getAttendanceDate());
				pointRepo.save(p);
			}
		}
	}
}
