package com.npcweb.config;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.*;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

    private static final ConcurrentHashMap<String, WebSocketSession> CLIENTS = new ConcurrentHashMap<String, WebSocketSession>();
    private final Map<String, LocalDateTime> attendanceMap = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        CLIENTS.put(session.getId(), session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        CLIENTS.remove(session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String id = session.getId();  //메시지를 보낸 아이디
        String payload = message.getPayload();
        // 받은 메시지 처리
        System.out.println("Received message: " + payload);
        CLIENTS.entrySet().forEach( arg->{
            if(!arg.getKey().equals(id)) {  //같은 아이디가 아니면 메시지를 전달
                try {
                    arg.getValue().sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    /*

    // 출석 생성
    public String createAttendance() {
        String attendanceCode = generateRandomAttendanceCode();
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(10); // 10분 후 만료

        // WebSocket을 통해 클라이언트에게 출석 코드를 브로드캐스트
        // (WebSocket 세션을 관리하여 모든 연결된 클라이언트에게 메시지를 보낼 수 있도록 추가적인 구현이 필요할 수 있습니다.)
        // 이 예제에서는 TextMessage를 통해 출석 코드를 전송하도록 하였습니다.
        TextMessage message = new TextMessage(attendanceCode);
        
        for (WebSocketSession session : sessions.values()) {
            session.sendMessage(message);
        }
        

        // 출석 코드와 만료 시간 저장
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

    // 랜덤한 3자리 수의 출석 코드 생성
    private String generateRandomAttendanceCode() {
        Random random = new Random();
        int randomNumber = 100 + random.nextInt(900); // 100부터 999까지의 랜덤 숫자 생성
        return String.valueOf(randomNumber);
    }
    */
}
