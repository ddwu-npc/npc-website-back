package com.npcweb;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class JwtTest {

    @Value("${jwt.secretKey}")
    private String secretKeyPlain;

    @Test
    void 시크릿키_값_출력_확인() {
        System.out.println(secretKeyPlain);
    }
    @Test
    void 시크릿키_존재_확인() {
        assertThat(secretKeyPlain).isNotNull();
    }
}