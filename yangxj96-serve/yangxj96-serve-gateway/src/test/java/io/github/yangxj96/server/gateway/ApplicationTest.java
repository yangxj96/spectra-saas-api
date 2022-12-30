package io.github.yangxj96.server.gateway;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@SpringBootTest
class ApplicationTest {

    public static void main(String[] args) {
        LocalDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Shanghai")).toLocalDateTime();
        var newYork = now.atZone(ZoneId.of("America/New_York"));
        var shangHai = now.atZone(ZoneId.of("Asia/Shanghai"));
        System.out.println("当前时间:" + now);
        System.out.println("Zone:" + newYork + " Timestamp = " + Timestamp.from(newYork.toInstant()));
        System.out.println("Zone:" + shangHai + " Timestamp = " + Timestamp.from(shangHai.toInstant()));
    }

    @Test
    void test01(){
        Mono
                .defer(()-> Mono.just("hello world"))
                .delayElement(Duration.ofMillis(5))
                .subscribe()
        ;
        Assertions.assertTrue(true);
    }

}