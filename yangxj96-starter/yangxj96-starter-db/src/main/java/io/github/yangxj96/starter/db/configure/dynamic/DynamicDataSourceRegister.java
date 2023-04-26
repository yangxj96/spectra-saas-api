package io.github.yangxj96.starter.db.configure.dynamic;

import io.github.yangxj96.starter.db.configure.jdbc.DynamicDataSource;
import io.github.yangxj96.starter.db.holder.DynamicDataSourceContextHolder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class DynamicDataSourceRegister implements ImportBeanDefinitionRegistrar, EnvironmentAware {

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
     * 在这里读取远程数据源进行配置
     */
    private void initCustomDataSources(Environment env) {
        // 读取配置文件获取更多数据源
        for (int i = 0; i < 10; i++) {
            var ds = new HashMap<String, Object>();
            ds.put("driver", env.getProperty("spring.datasource.driver-class-name"));
            ds.put("url", env.getProperty("spring.datasource.url"));
            ds.put("username", env.getProperty("spring.datasource.username"));
            ds.put("password", env.getProperty("spring.datasource.password"));
            customDataSources.put("tenant-" + i, buildDataSource(ds));
        }
    }

    private void initDefaultDataSource(Environment env) {
        // 读取主数据源
        Map<String, Object> dsMap = new HashMap<>();
        dsMap.put("driver", env.getProperty("spring.datasource.driver-class-name"));
        dsMap.put("url", env.getProperty("spring.datasource.url"));
        dsMap.put("username", env.getProperty("spring.datasource.username"));
        dsMap.put("password", env.getProperty("spring.datasource.password"));
        defaultDataSource = buildDataSource(dsMap);
    }

    /**
     * 自定义注册bean
     *
     * @param importingClassMetadata  annotation metadata of the importing class
     * @param registry                current bean definition registry
     * @param importBeanNameGenerator the bean name generator strategy for imported beans:
     *                                {@link ConfigurationClassPostProcessor#IMPORT_BEAN_NAME_GENERATOR} by default, or a
     *                                user-provided one if {@link ConfigurationClassPostProcessor#setBeanNameGenerator}
     *                                has been set. In the latter case, the passed-in strategy will be the same used for
     *                                component scanning in the containing application context (otherwise, the default
     *                                component-scan naming strategy is {@link AnnotationBeanNameGenerator#INSTANCE}).
     */
    @Override
    public void registerBeanDefinitions(@NotNull AnnotationMetadata importingClassMetadata, @NotNull BeanDefinitionRegistry registry, @NotNull BeanNameGenerator importBeanNameGenerator) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        // 将主数据源添加到更多数据源中
        targetDataSources.put("default", defaultDataSource);
        DynamicDataSourceContextHolder.dataSourceIds.add("default");
        // 添加更多数据源
        targetDataSources.putAll(customDataSources);

        // DynamicDataSourceContextHolder.dataSourceIds.addAll(customDataSources.keySet());

        for (Object key : customDataSources.keySet()) {
            DynamicDataSourceContextHolder.dataSourceIds.add(key.toString());
        }

        // 创建DynamicDataSource
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(DynamicDataSource.class);
        beanDefinition.setSynthetic(true);
        MutablePropertyValues mpv = beanDefinition.getPropertyValues();
        mpv.addPropertyValue("defaultTargetDataSource", defaultDataSource);
        mpv.addPropertyValue("targetDataSources", targetDataSources);
        registry.registerBeanDefinition("dataSource", beanDefinition); // 注册到Spring容器中

        log.info("registerBeanDefinitions.动态数据源注册");
    }

    /**
     * 创建DataSource
     *
     * @param dsMap 数据源信息
     * @return 创建好的数据源
     */
    @SuppressWarnings("unchecked")
    public DataSource buildDataSource(Map<String, Object> dsMap) {
        try {
            Object type = dsMap.get("type");
            if (type == null) {
                type = DATASOURCE_TYPE_DEFAULT;// 默认DataSource
            }

            Class<? extends DataSource> dataSourceType = (Class<? extends DataSource>) Class.forName((String) type);
            log.info("构建数据源信息:{}", dsMap);
            String driverClassName = dsMap.get("driver").toString();
            String url = dsMap.get("url").toString();
            String username = dsMap.get("username").toString();
            String password = dsMap.get("password").toString();
            // 自定义DataSource配置

            return DataSourceBuilder.create()
                    .driverClassName(driverClassName)
                    .url(url)
                    .username(username)
                    .password(password)
                    .type(dataSourceType)
                    .build();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
