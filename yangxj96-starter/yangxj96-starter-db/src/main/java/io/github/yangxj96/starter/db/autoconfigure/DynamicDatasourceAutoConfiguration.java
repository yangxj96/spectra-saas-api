package io.github.yangxj96.starter.db.autoconfigure;

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.autoconfigure.SpringBootVFS;
import com.baomidou.mybatisplus.autoconfigure.SqlSessionFactoryBeanCustomizer;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.handlers.PostInitTableInfoHandler;
import com.baomidou.mybatisplus.core.incrementer.IKeyGenerator;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import io.github.yangxj96.starter.db.configure.dynamic.DynamicDataSourceRegister;
import io.github.yangxj96.starter.db.configure.jdbc.DynamicDataSource;
import io.github.yangxj96.starter.db.filters.DynamicDatasourceFilter;
import io.github.yangxj96.starter.db.properties.DBProperties;
import jakarta.servlet.Filter;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.type.TypeHandler;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * 动态数据源配置
 */
@Slf4j
@EnableConfigurationProperties(DBProperties.class)
public class DynamicDatasourceAutoConfiguration {

    private static final String LOG_PREFIX = "[自动配置-动态数据源]:";

    private final DBProperties properties;

    private final MybatisPlusProperties mybatisPlusProperties;

    private final ResourceLoader resourceLoader;

    private final List<ConfigurationCustomizer> configurationCustomizers;

    private final Interceptor[] interceptors;

    private final DatabaseIdProvider databaseIdProvider;

    private final TypeHandler[] typeHandlers;

    private final LanguageDriver[] languageDrivers;

    private final ApplicationContext applicationContext;

    private final List<SqlSessionFactoryBeanCustomizer> sqlSessionFactoryBeanCustomizers;

    public DynamicDatasourceAutoConfiguration(DBProperties properties,
                                              MybatisPlusProperties mybatisPlusProperties,
                                              ResourceLoader resourceLoader,
                                              ObjectProvider<List<ConfigurationCustomizer>> configurationCustomizersProvider,
                                              ObjectProvider<Interceptor[]> interceptorsProvider,
                                              ObjectProvider<DatabaseIdProvider> databaseIdProvider,
                                              ObjectProvider<TypeHandler[]> typeHandlersProvider,
                                              ObjectProvider<LanguageDriver[]> languageDriversProvider,
                                              ObjectProvider<List<SqlSessionFactoryBeanCustomizer>> sqlSessionFactoryBeanCustomizers,
                                              ApplicationContext applicationContext
    ) {
        log.info("{}开始自动装配动态数据源", LOG_PREFIX);
        this.properties = properties;
        this.mybatisPlusProperties = mybatisPlusProperties;
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
     * 动态数据源
     *
     * @return 数据源
     */
    @Bean
    @Primary
    public DataSource dataSource(DynamicDataSourceRegister register) {
        log.info("{}初始化动态数据源", LOG_PREFIX);
        var dataSource = new DynamicDataSource();
        dataSource.setDefaultTargetDataSource(register.defaultDataSource);
        dataSource.setTargetDataSources(register.customDataSources);
        return dataSource;
    }

    /**
     * 载入动态数据源的过滤器设置数据源
     *
     * @return 动态数据源过滤器
     */
    @Bean
    public Filter dynamicDatasourceFilter() {
        log.info("{}载入动态数据源", LOG_PREFIX);
        return new DynamicDatasourceFilter();
    }

    @Bean
    public DynamicDataSourceRegister dataSourceRegister(){
        return new DynamicDataSourceRegister();
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        // 使用 MybatisSqlSessionFactoryBean 而不是 SqlSessionFactoryBean
        log.info("{}开始初始化SqlSessionFactory", LOG_PREFIX);
        MybatisSqlSessionFactoryBean factory = new MybatisSqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        factory.setVfs(SpringBootVFS.class);
        if (StringUtils.hasText(mybatisPlusProperties.getConfigLocation())) {
            factory.setConfigLocation(resourceLoader.getResource(mybatisPlusProperties.getConfigLocation()));
        }
        applyConfiguration(factory);
        if (mybatisPlusProperties.getConfigurationProperties() != null) {
            factory.setConfigurationProperties(mybatisPlusProperties.getConfigurationProperties());
        }
        if (!ObjectUtils.isEmpty(this.interceptors)) {
            factory.setPlugins(this.interceptors);
        }
        if (this.databaseIdProvider != null) {
            factory.setDatabaseIdProvider(this.databaseIdProvider);
        }
        if (StringUtils.hasLength(mybatisPlusProperties.getTypeAliasesPackage())) {
            factory.setTypeAliasesPackage(mybatisPlusProperties.getTypeAliasesPackage());
        }
        if (mybatisPlusProperties.getTypeAliasesSuperType() != null) {
            factory.setTypeAliasesSuperType(mybatisPlusProperties.getTypeAliasesSuperType());
        }
        if (StringUtils.hasLength(mybatisPlusProperties.getTypeHandlersPackage())) {
            factory.setTypeHandlersPackage(mybatisPlusProperties.getTypeHandlersPackage());
        }
        if (!ObjectUtils.isEmpty(this.typeHandlers)) {
            factory.setTypeHandlers(this.typeHandlers);
        }
        if (!ObjectUtils.isEmpty(mybatisPlusProperties.resolveMapperLocations())) {
            factory.setMapperLocations(mybatisPlusProperties.resolveMapperLocations());
        }
        //  修改源码支持定义 TransactionFactory
        this.getBeanThen(TransactionFactory.class, factory::setTransactionFactory);

        //  对源码做了一定的修改(因为源码适配了老旧的mybatis版本,但我们不需要适配)
        Class<? extends LanguageDriver> defaultLanguageDriver = mybatisPlusProperties.getDefaultScriptingLanguageDriver();
        if (!ObjectUtils.isEmpty(this.languageDrivers)) {
            factory.setScriptingLanguageDrivers(this.languageDrivers);
        }
        Optional.ofNullable(defaultLanguageDriver).ifPresent(factory::setDefaultScriptingLanguageDriver);

        applySqlSessionFactoryBeanCustomizers(factory);

        //  此处必为非 NULL
        GlobalConfig globalConfig = mybatisPlusProperties.getGlobalConfig();
        //  注入填充器
        this.getBeanThen(MetaObjectHandler.class, globalConfig::setMetaObjectHandler);
        //  注入参与器
        this.getBeanThen(PostInitTableInfoHandler.class, globalConfig::setPostInitTableInfoHandler);
        //  注入主键生成器
        this.getBeansThen(IKeyGenerator.class, i -> globalConfig.getDbConfig().setKeyGenerators(i));
        //  注入sql注入器
        this.getBeanThen(ISqlInjector.class, globalConfig::setSqlInjector);
        //  注入ID生成器
        this.getBeanThen(IdentifierGenerator.class, globalConfig::setIdentifierGenerator);
        //  设置 GlobalConfig 到 MybatisSqlSessionFactoryBean
        factory.setGlobalConfig(globalConfig);
        return factory.getObject();
    }

    /**
     * 入参使用 MybatisSqlSessionFactoryBean
     *
     * @param factory 工厂类
     */
    private void applyConfiguration(MybatisSqlSessionFactoryBean factory) {
        // 使用 MybatisConfiguration
        MybatisConfiguration configuration = this.mybatisPlusProperties.getConfiguration();
        if (configuration == null && !StringUtils.hasText(this.mybatisPlusProperties.getConfigLocation())) {
            configuration = new MybatisConfiguration();
        }
        if (configuration != null && !CollectionUtils.isEmpty(this.configurationCustomizers)) {
            for (ConfigurationCustomizer customizer : this.configurationCustomizers) {
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
     */
    private <T> void getBeansThen(Class<T> clazz, Consumer<List<T>> consumer) {
        if (this.applicationContext.getBeanNamesForType(clazz, false, false).length > 0) {
            final Map<String, T> beansOfType = this.applicationContext.getBeansOfType(clazz);
            List<T> clazzList = new ArrayList<>();
            beansOfType.forEach((k, v) -> clazzList.add(v));
            consumer.accept(clazzList);
        }
    }

    private void applySqlSessionFactoryBeanCustomizers(MybatisSqlSessionFactoryBean factory) {
        if (!CollectionUtils.isEmpty(this.sqlSessionFactoryBeanCustomizers)) {
            for (SqlSessionFactoryBeanCustomizer customizer : this.sqlSessionFactoryBeanCustomizers) {
                customizer.customize(factory);
            }
        }
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        ExecutorType executorType = mybatisPlusProperties.getExecutorType();
        if (executorType != null) {
            return new SqlSessionTemplate(sqlSessionFactory, executorType);
        } else {
            return new SqlSessionTemplate(sqlSessionFactory);
        }
    }


}
