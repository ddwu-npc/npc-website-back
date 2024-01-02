package com.npcweb.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class AttendanceService {
	 @Autowired
	    private SimpMessagingTemplate messagingTemplate;

	    private Map<String, LocalDateTime> attendanceMap = new ConcurrentHashMap<>();

	    // 출석 생성
	    public String createAttendance() {
	        String attendanceCode = generateRandomAttendanceCode();
	        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(10); // 10분 후 만료

	        // 생성된 출석 코드를 WebSocket을 통해 클라이언트에게 브로드캐스트
	        messagingTemplate.convertAndSend("/topic/attendance", attendanceCode);

	        // 1분 후에 해당 출석을 파기하는 스케줄링 작업 실행
	        scheduleAttendanceDestroy(attendanceCode, expirationTime);

	        // 출석 코드와 만료 시간을 저장
	        attendanceMap.put(attendanceCode, expirationTime);

	        return attendanceCode;
	    }

	    // 출석 파기
	    public void destroyAttendance(String attendanceCode) {
	        attendanceMap.remove(attendanceCode);
	    }

	    // 출석 파기 스케줄링
	    private void scheduleAttendanceDestroy(String attendanceCode, LocalDateTime expirationTime) {
	        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	        scheduler.schedule(() -> {
	            destroyAttendance(attendanceCode);
	            scheduler.shutdown();
	        }, Duration.between(LocalDateTime.now(), expirationTime).toMillis(), TimeUnit.MILLISECONDS);
	    }

	    // 랜덤한 3자리 수의 출석 코드 생성 (예시)
	    private String generateRandomAttendanceCode() {
	        Random random = new Random();
	        int randomNumber = 100 + random.nextInt(900); // 100부터 999까지의 랜덤 숫자 생성
	        return String.valueOf(randomNumber);
	    }
}
