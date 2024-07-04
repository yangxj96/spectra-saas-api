package com.yangxj96.saas.starter.db.configure.dynamic;

import com.zaxxer.hikari.HikariDataSource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 初始化注册数据源信息
 */
@Getter
public class DynamicDataSourceRegister implements EnvironmentAware {


    /**
     * 指定默认数据源(springboot2.0默认数据源是hikari如何想使用其他数据源可以自己配置)
     */
    private static final Class<HikariDataSource> DATASOURCE_TYPE_DEFAULT = HikariDataSource.class;

    /**
     * 默认数据源
     */
    private DataSource defaultDataSource = null;

    /**
     * 用户自定义数据源
     */
    private final Map<Object, Object> customDataSources = new HashMap<>();


    @Override
    public void setEnvironment(@NotNull Environment environment) {
        // 初始化数据源注册
        initDefaultDataSource(environment);
        initCustomDataSources(environment);
    }

    /**
     * 初始化自定义数据源<br></br>
     * 在这里读取远程数据源信息后配置
     *
     * @param env 上下文环境
     */
    private void initCustomDataSources(Environment env) {
        // 读取配置文件获取更多数据源
        for (int i = 0; i < 1; i++) {
            var datum = new DataSourceEntity();
            datum.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
            datum.setUrl("jdbc:postgresql://localhost:5432/VJVDQIWBMEJSMYAJX");
            datum.setUsername(env.getProperty("spring.datasource.username"));
            datum.setPassword(env.getProperty("spring.datasource.password"));
            customDataSources.put("tenant-" + i, buildDataSource(datum));
        }
    }

    /**
     * 初始化默认数据源
     *
     * @param env 上下文环境
     */
    private void initDefaultDataSource(Environment env) {
        // 读取主数据源
        var datum = new DataSourceEntity();
        datum.driverClassName = env.getProperty("spring.datasource.driver-class-name");
        datum.url = env.getProperty("spring.datasource.url");
        datum.username = env.getProperty("spring.datasource.username");
        datum.password = env.getProperty("spring.datasource.password");
        defaultDataSource = buildDataSource(datum);
    }

    /**
     * 创建DataSource
     *
     * @param entity 数据源信息
     * @return 创建好的数据源
     */
    private DataSource buildDataSource(DataSourceEntity entity) {
        var type = entity.getType();
        if (entity.getType() == null) {
            entity.setType(DATASOURCE_TYPE_DEFAULT);
        }

        // 构建datasource
        return DataSourceBuilder.create()
                .driverClassName(entity.driverClassName)
                .url(entity.url)
                .username(entity.username)
                .password(entity.password)
                .type(type)
                .build();

    }

    /**
     * 数据源信息实体
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class DataSourceEntity {
        private Class<HikariDataSource> type;
        private String driverClassName;
        private String url;
        private String username;
        private String password;
    }


}
