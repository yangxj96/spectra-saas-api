package io.github.yangxj96.server.gateway;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 前端网关的主启动类
 *
 * @author yangxj96
 */
@MapperScan("io.github.yangxj96.server.gateway.mapper")
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
