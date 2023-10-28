package com.npcweb.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.npcweb.service.RedisService;

@RestController
public class RedisController {
    @Autowired
    private RedisService redisService;
 
    // hashmap 형태로 들어가 있는 session 관련된 정보들은 hget 이라는 명령어를 통해서 가지고 올 수 있다. 
    // 다른 서버여도 하나의 redis 서버로 통일됨
    @GetMapping("/getSessionId")
    public String getSessionId(HttpSession session) {
        return session.getId();
    }
    
    @PostMapping(value = "/getRedisStringValue")
    public void getRedisStringValue(String key) {
        redisService.getRedisStringValue(key);
    }
    
    @PostMapping(value = "/setRedisStringValue")
    public void setRedisStringValue(String key, String value) {
    	redisService.setRedisStringValue(key, value);
    }
}
