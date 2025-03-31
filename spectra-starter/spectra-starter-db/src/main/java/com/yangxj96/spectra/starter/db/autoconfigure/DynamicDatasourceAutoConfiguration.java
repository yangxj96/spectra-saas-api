package com.yangxj96.spectra.starter.db.autoconfigure;

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.autoconfigure.SpringBootVFS;
import com.baomidou.mybatisplus.autoconfigure.SqlSessionFactoryBeanCustomizer;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.handlers.PostInitTableInfoHandler;
import com.baomidou.mybatisplus.core.incrementer.IKeyGenerator;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.yangxj96.spectra.starter.db.configure.dynamic.DynamicDataSourceRegister;
import com.yangxj96.spectra.starter.db.configure.jdbc.DynamicDataSource;
import com.yangxj96.spectra.starter.db.filters.DynamicDatasourceFilter;
import com.yangxj96.spectra.starter.db.holder.DynamicDataSourceContextHolder;
import com.yangxj96.spectra.starter.db.props.DBProperties;
import jakarta.servlet.Filter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.type.TypeHandler;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ResourceLoader;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

/**
 * 动态数据源配置
 */
@Slf4j
@EnableTransactionManagement
@EnableConfigurationProperties(DBProperties.class)
public class DynamicDatasourceAutoConfiguration {

    private static final String PREFIX = "[动态数据源]:";

    private final MybatisPlusProperties properties;
    private final ResourceLoader resourceLoader;
    private final List<ConfigurationCustomizer> configurationCustomizers;
    private final Interceptor[] interceptors;
    private final DatabaseIdProvider databaseIdProvider;
    private final TypeHandler<?>[] typeHandlers;
    private final LanguageDriver[] languageDrivers;
    private final ApplicationContext applicationContext;
    private final List<SqlSessionFactoryBeanCustomizer> sqlSessionFactoryBeanCustomizers;

    public DynamicDatasourceAutoConfiguration(
            MybatisPlusProperties properties,
            ResourceLoader resourceLoader,
            ObjectProvider<List<ConfigurationCustomizer>> configurationCustomizersProvider,
            ObjectProvider<Interceptor[]> interceptorsProvider,
            ObjectProvider<DatabaseIdProvider> databaseIdProvider,
            ObjectProvider<TypeHandler<?>[]> typeHandlersProvider,
            ObjectProvider<LanguageDriver[]> languageDriversProvider,
            ObjectProvider<List<SqlSessionFactoryBeanCustomizer>> sqlSessionFactoryBeanCustomizers,
            ApplicationContext applicationContext
    ) {
        log.atDebug().log("{}开始自动装配动态数据源", PREFIX);
        this.properties = properties;
        this.resourceLoader = resourceLoader;
        this.configurationCustomizers = configurationCustomizersProvider.getIfAvailable();
        this.interceptors = interceptorsProvider.getIfAvailable();
        this.databaseIdProvider = databaseIdProvider.getIfAvailable();
        this.typeHandlers = typeHandlersProvider.getIfAvailable();
        this.languageDrivers = languageDriversProvider.getIfAvailable();
        this.sqlSessionFactoryBeanCustomizers = sqlSessionFactoryBeanCustomizers.getIfAvailable();
        this.applicationContext = applicationContext;
    }


    /**
     * 数据源注册<br></br>
     * 需要在前面进行注册,不然后面启动的bean会循环查找
     *
     * @return 注册后的数据源
     */
    @Bean
    @Order(Integer.MIN_VALUE)
    public DynamicDataSourceRegister dataSourceRegister() {
        return new DynamicDataSourceRegister();
    }

    /**
     * 注册数据源<br></br>
     * 需要在这里进行注册并指定是主数据源
     *
     * @param register 数据源信息
     * @return 数据源
     */
    @Bean
    @Primary
    public DataSource dataSource(DynamicDataSourceRegister register) {
        var source = new DynamicDataSource();
        var targetDataSources = new HashMap<>();
        // 将主数据源添加到更多数据源中
        targetDataSources.put("default", register.getDefaultDataSource());
        DynamicDataSourceContextHolder.getDataSourceIds().add("default");
        // 添加更多数据源
        targetDataSources.putAll(register.getCustomDataSources());
        for (var key : register.getCustomDataSources().keySet()) {
            DynamicDataSourceContextHolder.getDataSourceIds().add(key.toString());
        }
        source.setTargetDataSources(targetDataSources);
        source.setDefaultTargetDataSource(register.getDefaultDataSource());
        return source;
    }

    /**
     * 载入动态数据源的拦截器设置数据源
     *
     * @return 动态数据源过滤器
     */
    @Bean
    public Filter dynamicDatasourceInterceptor() {
        log.info("{}载入动态数据源", PREFIX);
        return new DynamicDatasourceFilter();
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        // 使用 MybatisSqlSessionFactoryBean 而不是 SqlSessionFactoryBean
        log.info("{}开始初始化SqlSessionFactory", PREFIX);
        var factory = new MybatisSqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        factory.setVfs(SpringBootVFS.class);
        if (StringUtils.hasText(properties.getConfigLocation())) {
            factory.setConfigLocation(resourceLoader.getResource(properties.getConfigLocation()));
        }
        applyConfiguration(factory);
        if (properties.getConfigurationProperties() != null) {
            factory.setConfigurationProperties(properties.getConfigurationProperties());
        }
        if (!ObjectUtils.isEmpty(interceptors)) {
            factory.setPlugins(interceptors);
        }
        if (databaseIdProvider != null) {
            factory.setDatabaseIdProvider(databaseIdProvider);
        }
        if (StringUtils.hasLength(properties.getTypeAliasesPackage())) {
            factory.setTypeAliasesPackage(properties.getTypeAliasesPackage());
        }
        if (properties.getTypeAliasesSuperType() != null) {
            factory.setTypeAliasesSuperType(properties.getTypeAliasesSuperType());
        }
        if (StringUtils.hasLength(properties.getTypeHandlersPackage())) {
            factory.setTypeHandlersPackage(properties.getTypeHandlersPackage());
        }
        if (!ObjectUtils.isEmpty(typeHandlers)) {
            factory.setTypeHandlers(typeHandlers);
        }
        if (!ObjectUtils.isEmpty(properties.resolveMapperLocations())) {
            factory.setMapperLocations(properties.resolveMapperLocations());
        }
        //  修改源码支持定义 TransactionFactory
        getBeanThen(TransactionFactory.class, factory::setTransactionFactory);
        //  对源码做了一定的修改(因为源码适配了老旧的mybatis版本,但我们不需要适配)
        var defaultLanguageDriver = properties.getDefaultScriptingLanguageDriver();
        if (!ObjectUtils.isEmpty(languageDrivers)) {
            factory.setScriptingLanguageDrivers(languageDrivers);
        }
        if (defaultLanguageDriver != null) {
            factory.setDefaultScriptingLanguageDriver(defaultLanguageDriver);
        }
        applySqlSessionFactoryBeanCustomizers(factory);
        //  此处必为非 NULL
        var globalConfig = properties.getGlobalConfig();
        //  注入填充器
        getBeanThen(MetaObjectHandler.class, globalConfig::setMetaObjectHandler);
        //  注入参与器
        getBeanThen(PostInitTableInfoHandler.class, globalConfig::setPostInitTableInfoHandler);
        //  注入主键生成器
        getBeansThen(IKeyGenerator.class, it -> globalConfig.getDbConfig().setKeyGenerators(it));
        //  注入sql注入器
        getBeanThen(ISqlInjector.class, globalConfig::setSqlInjector);
        //  注入ID生成器
        getBeanThen(IdentifierGenerator.class, globalConfig::setIdentifierGenerator);
        //  设置 GlobalConfig 到 MybatisSqlSessionFactoryBean
        factory.setGlobalConfig(globalConfig);
        return factory.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        var executorType = properties.getExecutorType();
        if (executorType != null) {
            return new SqlSessionTemplate(sqlSessionFactory, executorType);
        } else {
            return new SqlSessionTemplate(sqlSessionFactory);
        }
    }

    /**
     * 入参使用 MybatisSqlSessionFactoryBean
     *
     * @param factory 工厂类
     */
    private void applyConfiguration(MybatisSqlSessionFactoryBean factory) {
        var coreConfiguration = properties.getConfiguration();
        MybatisConfiguration configuration = null;
        if (coreConfiguration != null || !StringUtils.hasText(properties.getConfigLocation())) {
            configuration = new MybatisConfiguration();
        }
        if (configuration != null && coreConfiguration != null) {
            coreConfiguration.applyTo(configuration);
        }
        if (configuration != null && !CollectionUtils.isEmpty(configurationCustomizers)) {
            for (var customizer : configurationCustomizers) {
                customizer.customize(configuration);
            }
        }
        factory.setConfiguration(configuration);
    }

    /**
     * 检查spring容器里是否有对应的bean,有则进行消费
     *
     * @param clazz    class
     * @param consumer 消费
     * @param <T>      泛型
     *                 </T>
     */
    private <T> void getBeanThen(Class<T> clazz, Consumer<T> consumer) {
        if (applicationContext.getBeanNamesForType(clazz, false, false).length > 0) {
            consumer.accept(applicationContext.getBean(clazz));
        }
    }

    /**
     * 检查spring容器里是否有对应的bean,有则进行消费
     *
     * @param clazz    class
     * @param consumer 消费
     * @param <T>      泛型
     *                 </T>
     */
    private <T> void getBeansThen(Class<T> clazz, Consumer<List<T>> consumer) {
        if (applicationContext.getBeanNamesForType(clazz, false, false).length > 0) {
            var beansOfType = applicationContext.getBeansOfType(clazz);
            var clazzList = new ArrayList<T>();
            beansOfType.forEach((k, v) -> clazzList.add(v));
            consumer.accept(clazzList);
        }
    }

    private void applySqlSessionFactoryBeanCustomizers(MybatisSqlSessionFactoryBean factory) {
        if (!CollectionUtils.isEmpty(sqlSessionFactoryBeanCustomizers)) {
            for (var customizer : sqlSessionFactoryBeanCustomizers) {
                customizer.customize(factory);
            }
        }
    }


}
