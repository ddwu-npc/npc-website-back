package com.npcweb.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ReadCountService {
	@Autowired
	private PostService postService;
    @Autowired
    private StringRedisTemplate redisTemplate;
	
    public String getData(String key) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(key);
    }
    
    public void setData(String key, String value) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value);
    }

    public void setDataWithDuration(String key, String value, Duration duration) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value, duration);
    }
    
    // 하루에 1번 조회수를 올릴 수 있음(중복 방지)
    @Transactional
    public void getReadCountByUser(long post_id, long userNo) {
    	// Redis: Read Count Key 생성
    	String readCountKey = "read by user:" + String.valueOf(userNo);
    	String readCountValue = getData(readCountKey);
    	boolean isView = false;

    	if (readCountValue == null) {
    		// 게시글을 아예 읽은 적 없을 때
    	    setDataWithDuration(readCountKey, post_id + " ", Duration.ofSeconds(setWithExpirationAtMidnight()));
    	    getReadCountByPost(post_id);
    	} else {
    		// 유저가 읽은 게시글 ID 조회
    	    String[] readCountArray = readCountValue.split(" ");
    	    List<String> existingReads = Arrays.asList(readCountArray);
    	    
    	    if (!existingReads.isEmpty()) {
    	        for (String existingId : existingReads) {
    	            if (String.valueOf(post_id).equals(existingId)) {
    	            	// 해당 post를 찾았을 때
    	                isView = true;
    	                break;
    	            }
    	        }
    	        if (!isView) {
    	        	// 해당 post가 없을 때
    	            readCountValue += post_id + " ";
    	            setDataWithDuration(readCountKey, readCountValue, Duration.ofSeconds(setWithExpirationAtMidnight()));
    	            getReadCountByPost(post_id);
    	        }
    	    }
    	}
    }
    
    // 조회수 불러오기
    @Transactional
    public int getReadCountByPost(long post_id) {
    	String readCountKey = "read by post:" + String.valueOf(post_id);
    	String readCount = "0";
    	if (getData(readCountKey) == null) {
    		readCount = Integer.toString(postService.getReadCount(post_id));
    		setData(readCountKey, readCount);
    	}
    	else 
    		readCount = getData(readCountKey);
       	int readCountFromRedis = Integer.parseInt(readCount);

        // Redis: 조회수를 1 증가시켜 update
        int updatedCount = readCountFromRedis + 1;
        setData(readCountKey, String.valueOf(updatedCount));
        return updatedCount;
    }
    
    // 15초마다 Redis -> DB
 	@Scheduled(fixedDelay = 1000L*15L)
    @Transactional
    public void deleteReadCount() {
    	 String readCountKey = "read by post:";
         Set<String> redisKey = redisTemplate.keys("read by post" + "*");
         for (String key : redisKey) {
        	 String post_id = key.split(":")[1];
        	 
             int readCount = Integer.parseInt(getData(readCountKey + post_id));
             postService.updateReadCount(Long.parseLong(post_id), readCount);
             redisTemplate.delete(readCountKey + post_id);
         }
    }
 	
 	public long setWithExpirationAtMidnight() {
 		LocalDateTime now = LocalDateTime.now();
        LocalDateTime midnight = now.toLocalDate().atTime(LocalTime.MAX);

        return Duration.between(now, midnight).getSeconds();
 	}
}