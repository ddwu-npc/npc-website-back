package com.npcweb.service;

import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class AttendanceService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static final int RANDOM_NUMBER_RANGE = 1000; // 숫자 범위 설정 (0부터 999까지)
    private static final long ATTENDANCE_EXPIRATION = 60; // 출석 번호의 유효 기간(60초)

    public String createAttendance(String userId) {
        String attendanceNumber;

        // 랜덤 숫자 생성 및 중복 확인
        do {
            attendanceNumber = generateRandomNumber();
        } while (isAttendanceNumberExists(attendanceNumber));

        // 출석 정보 Redis에 저장 및 TTL 설정
        String key = "attendance:" + userId;
        redisTemplate.opsForValue().set(key, attendanceNumber);
        redisTemplate.expire(key, ATTENDANCE_EXPIRATION, TimeUnit.SECONDS);

        return attendanceNumber;
    }

    // 일단은 userId로 했는데 출석 Id?같은 걸로 수정이 필요해보임
	public String getAttendanceNumber(String userId) {
        String key = "attendance:" + userId;
        String attendanceNumber = redisTemplate.opsForValue().get(key);

        return attendanceNumber;
    }

    private String generateRandomNumber() {
        Random random = new Random();
        int randomNumber = random.nextInt(RANDOM_NUMBER_RANGE);
        return String.format("%03d", randomNumber);
    }

    private boolean isAttendanceNumberExists(String attendanceNumber) {
        Set<String> keys = redisTemplate.keys("attendance:*");
        return keys.contains("attendance:" + attendanceNumber);
    }
    
    public void givePoints(String userId) {
    	// User u = userService.getUser();
    }
}
