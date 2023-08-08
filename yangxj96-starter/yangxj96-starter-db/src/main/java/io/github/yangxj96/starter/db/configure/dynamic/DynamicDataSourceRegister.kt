package io.github.yangxj96.starter.db.configure.dynamic;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class DynamicDataSourceRegister implements EnvironmentAware {

    /**
     * 指定默认数据源(springboot2.0默认数据源是hikari如何想使用其他数据源可以自己配置)
     */
    private static final String DATASOURCE_TYPE_DEFAULT = "com.zaxxer.hikari.HikariDataSource";

    /**
     * 默认数据源
     **/
    @Getter
    public DataSource defaultDataSource;

    /**
     * 用户自定义数据源
     **/
    @Getter
    public Map<Object, Object> customDataSources = new HashMap<>();

    @Override
    public void setEnvironment(@NotNull Environment environment) {
        // 初始化数据源注册
        initDefaultDataSource(environment);
        initCustomDataSources(environment);
    }

    /**
     * 初始化自定义数据源<br/>
     * 在这里读取远程数据源信息后配置
     *
     * @param env 上下文环境
     */
    private void initCustomDataSources(Environment env) {
        // 读取配置文件获取更多数据源
//        for (int i = 0; i < 1; i++) {
//            var datum = new DataSourceEntity();
//            datum.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
//            datum.setUrl("jdbc:postgresql://localhost:5432/VJVDQIWBMEJSMYAJX");
//            datum.setUsername(env.getProperty("spring.datasource.username"));
//            datum.setPassword(env.getProperty("spring.datasource.password"));
//            customDataSources.put("tenant-" + i, buildDataSource(datum));
//        }
    }

    /**
     * 初始化默认数据源
     * @param env 上下文环境
     */
    private void initDefaultDataSource(Environment env) {
        // 读取主数据源
        var datum = new DataSourceEntity();
        datum.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
        datum.setUrl(env.getProperty("spring.datasource.url"));
        datum.setUsername(env.getProperty("spring.datasource.username"));
        datum.setPassword(env.getProperty("spring.datasource.password"));
        defaultDataSource = buildDataSource(datum);
    }

    /**
     * 创建DataSource
     *
     * @param entity 数据源信息
     * @return 创建好的数据源
     */
    @SuppressWarnings("unchecked")
    public DataSource buildDataSource(DataSourceEntity entity) {
        try {
            String type = entity.getType();
            if (type == null) {
                type = DATASOURCE_TYPE_DEFAULT;
            }

            // 构建datasource
            return DataSourceBuilder.create()
                    .driverClassName(entity.getDriverClassName())
                    .url(entity.getUrl())
                    .username(entity.getUsername())
                    .password(entity.getPassword())
                    .type((Class<? extends DataSource>) Class.forName(type))
                    .build();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 数据源信息实体
     */
    @Data
    @EqualsAndHashCode
    @ToString
    public static class DataSourceEntity {
        private String type;
        private String driverClassName;
        private String url;
        private String username;
        private String password;
    }
}
