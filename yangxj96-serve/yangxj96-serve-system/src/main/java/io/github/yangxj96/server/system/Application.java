package io.github.yangxj96.server.system;

import io.github.yangxj96.starter.db.annotation.EnableDynamicDatasource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 系统服务的主启动类
 */
@MapperScan("io.github.yangxj96.server.system.mapper")
@EnableDynamicDatasource
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
