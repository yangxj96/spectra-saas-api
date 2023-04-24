package io.github.yangxj96.commonsaas.yangxj96serve.flow;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 流程控制
 *
 */
@MapperScan("io.github.yangxj96.server.flow.mapper")
@EnableDiscoveryClient
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        var application = new SpringApplication(Application.class);
        application.setAllowBeanDefinitionOverriding(true);
        application.run(args);
    }

}
