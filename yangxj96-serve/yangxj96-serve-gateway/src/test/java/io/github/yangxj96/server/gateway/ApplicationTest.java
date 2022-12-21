package io.github.yangxj96.server.gateway;


import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
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

}