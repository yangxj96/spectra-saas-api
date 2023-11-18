/*
 * Copyright (c) 2018 - 2023
 * 作者：杨新杰(Jack Young)
 * 邮箱：yangxj96@gmail.com
 * 博客：www.yangxj96.com
 * 日期：2023-09-13 00:04:11
 * Copyright (c) 2018 - 2023
 */

package com.yangxj96.saas.starter.db.autoconfigure

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties
import com.baomidou.mybatisplus.autoconfigure.SpringBootVFS
import com.baomidou.mybatisplus.autoconfigure.SqlSessionFactoryBeanCustomizer
import com.baomidou.mybatisplus.core.MybatisConfiguration
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler
import com.baomidou.mybatisplus.core.handlers.PostInitTableInfoHandler
import com.baomidou.mybatisplus.core.incrementer.IKeyGenerator
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator
import com.baomidou.mybatisplus.core.injector.ISqlInjector
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean
import com.yangxj96.saas.starter.db.configure.dynamic.DynamicDataSourceRegister
import com.yangxj96.saas.starter.db.configure.jdbc.DynamicDataSource
import com.yangxj96.saas.starter.db.filters.DynamicDatasourceFilter
import com.yangxj96.saas.starter.db.holder.DynamicDataSourceContextHolder
import com.yangxj96.saas.starter.db.props.DBProperties
import jakarta.servlet.Filter
import org.apache.ibatis.mapping.DatabaseIdProvider
import org.apache.ibatis.plugin.Interceptor
import org.apache.ibatis.scripting.LanguageDriver
import org.apache.ibatis.session.SqlSessionFactory
import org.apache.ibatis.transaction.TransactionFactory
import org.apache.ibatis.type.TypeHandler
import org.mybatis.spring.SqlSessionTemplate
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.ObjectProvider
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.core.annotation.Order
import org.springframework.core.io.ResourceLoader
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.springframework.util.CollectionUtils
import org.springframework.util.ObjectUtils
import org.springframework.util.StringUtils
import java.util.*
import java.util.function.Consumer
import javax.sql.DataSource

/**
 * 动态数据源配置
 */
@EnableTransactionManagement
@EnableConfigurationProperties(DBProperties::class)
class DynamicDatasourceAutoConfiguration(
    properties: MybatisPlusProperties,
    resourceLoader: ResourceLoader,
    configurationCustomizersProvider: ObjectProvider<List<ConfigurationCustomizer?>>,
    interceptorsProvider: ObjectProvider<Array<Interceptor?>>,
    databaseIdProvider: ObjectProvider<DatabaseIdProvider?>,
    typeHandlersProvider: ObjectProvider<Array<TypeHandler<*>?>>,
    languageDriversProvider: ObjectProvider<Array<LanguageDriver?>>,
    sqlSessionFactoryBeanCustomizers: ObjectProvider<List<SqlSessionFactoryBeanCustomizer?>>,
    applicationContext: ApplicationContext
) {

    companion object {
        private const val PREFIX = "[自动配置-动态数据源]:"

        private val log = LoggerFactory.getLogger(this::class.java)
    }

    private val properties: MybatisPlusProperties
    private val resourceLoader: ResourceLoader
    private val configurationCustomizers: List<ConfigurationCustomizer?>
    private val interceptors: Array<Interceptor?>
    private val databaseIdProvider: DatabaseIdProvider?
    private val typeHandlers: Array<TypeHandler<*>?>
    private val languageDrivers: Array<LanguageDriver?>
    private val applicationContext: ApplicationContext
    private val sqlSessionFactoryBeanCustomizers: List<SqlSessionFactoryBeanCustomizer?>

    init {
        log.info("{}开始自动装配动态数据源", PREFIX)
        this.properties = properties
        this.resourceLoader = resourceLoader
        configurationCustomizers = configurationCustomizersProvider.getIfAvailable()!!
        interceptors = interceptorsProvider.getIfAvailable()!!
        this.databaseIdProvider = databaseIdProvider.getIfAvailable()
        typeHandlers = typeHandlersProvider.getIfAvailable()!!
        languageDrivers = languageDriversProvider.getIfAvailable()!!
        this.sqlSessionFactoryBeanCustomizers = sqlSessionFactoryBeanCustomizers.getIfAvailable()!!
        this.applicationContext = applicationContext
    }

    /**
     * 数据源注册<br></br>
     * 需要在前面进行注册,不然后面启动的bean会循环查找
     *
     * @return 注册后的数据源
     */
    @Bean
    @Order(Int.MIN_VALUE)
    fun dataSourceRegister(): DynamicDataSourceRegister {
        return DynamicDataSourceRegister()
    }

    /**
     * 注册数据源<br></br>
     * 需要在这里进行注册并指定是主数据源
     * @param register 数据源信息
     * @return 数据源
     */
    @Bean
    @Primary
    fun dataSource(register: DynamicDataSourceRegister): DataSource {
        val source = DynamicDataSource()
        val targetDataSources = HashMap<Any, Any?>()
        // 将主数据源添加到更多数据源中
        targetDataSources["default"] = register.defaultDataSource
        DynamicDataSourceContextHolder.dataSourceIds.add("default")
        // 添加更多数据源
        targetDataSources.putAll(register.customDataSources)
        for (key in register.customDataSources.keys) {
            DynamicDataSourceContextHolder.dataSourceIds.add(key.toString())
        }
        source.setTargetDataSources(targetDataSources)
        source.setDefaultTargetDataSource(register.defaultDataSource!!)
        return source
    }

    /**
     * 载入动态数据源的拦截器设置数据源
     *
     * @return 动态数据源过滤器
     */
    @Bean
    fun dynamicDatasourceInterceptor(): Filter {
        log.info("{}载入动态数据源", PREFIX)
        return DynamicDatasourceFilter()
    }

    @Bean
    @Throws(Exception::class)
    fun sqlSessionFactory(dataSource: DataSource?): SqlSessionFactory? {
        // 使用 MybatisSqlSessionFactoryBean 而不是 SqlSessionFactoryBean
        log.info("{}开始初始化SqlSessionFactory", PREFIX)
        val factory = MybatisSqlSessionFactoryBean()
        factory.setDataSource(dataSource)
        factory.vfs = SpringBootVFS::class.java
        if (StringUtils.hasText(properties.configLocation)) {
            factory.setConfigLocation(resourceLoader.getResource(properties.configLocation))
        }
        applyConfiguration(factory)
        if (properties.configurationProperties != null) {
            factory.setConfigurationProperties(properties.configurationProperties)
        }
        if (!ObjectUtils.isEmpty(interceptors)) {
            factory.setPlugins(*interceptors)
        }
        if (databaseIdProvider != null) {
            factory.databaseIdProvider = databaseIdProvider
        }
        if (StringUtils.hasLength(properties.typeAliasesPackage)) {
            factory.setTypeAliasesPackage(properties.typeAliasesPackage)
        }
        if (properties.typeAliasesSuperType != null) {
            factory.setTypeAliasesSuperType(properties.typeAliasesSuperType)
        }
        if (StringUtils.hasLength(properties.typeHandlersPackage)) {
            factory.setTypeHandlersPackage(properties.typeHandlersPackage)
        }
        if (!ObjectUtils.isEmpty(typeHandlers)) {
            factory.setTypeHandlers(*typeHandlers)
        }
        if (!ObjectUtils.isEmpty(properties.resolveMapperLocations())) {
            factory.setMapperLocations(*properties.resolveMapperLocations())
        }
        //  修改源码支持定义 TransactionFactory
        getBeanThen(TransactionFactory::class.java) { transactionFactory: TransactionFactory? ->
            factory.setTransactionFactory(
                transactionFactory
            )
        }

        //  对源码做了一定的修改(因为源码适配了老旧的mybatis版本,但我们不需要适配)
        val defaultLanguageDriver = properties.defaultScriptingLanguageDriver
        if (!ObjectUtils.isEmpty(languageDrivers)) {
            factory.setScriptingLanguageDrivers(*languageDrivers)
        }

        Optional.ofNullable(defaultLanguageDriver).ifPresent {
            factory.setDefaultScriptingLanguageDriver(it)
        }

        applySqlSessionFactoryBeanCustomizers(factory)

        //  此处必为非 NULL
        val globalConfig = properties.globalConfig
        //  注入填充器
        getBeanThen(MetaObjectHandler::class.java) { globalConfig.setMetaObjectHandler(it) }
        //  注入参与器
        getBeanThen(PostInitTableInfoHandler::class.java) { globalConfig.setPostInitTableInfoHandler(it) }
        //  注入主键生成器
        getBeansThen(IKeyGenerator::class.java) { globalConfig.dbConfig.setKeyGenerators(it) }
        //  注入sql注入器
        getBeanThen(ISqlInjector::class.java) { globalConfig.setSqlInjector(it) }
        //  注入ID生成器
        getBeanThen(IdentifierGenerator::class.java) { globalConfig.setIdentifierGenerator(it) }
        //  设置 GlobalConfig 到 MybatisSqlSessionFactoryBean
        factory.setGlobalConfig(globalConfig)
        return factory.getObject()
    }

    /**
     * 入参使用 MybatisSqlSessionFactoryBean
     *
     * @param factory 工厂类
     */
    private fun applyConfiguration(factory: MybatisSqlSessionFactoryBean) {
        // 使用 MybatisConfiguration
        //var configuration = properties.configuration
        //if (configuration == null && !StringUtils.hasText(properties.configLocation)) {
        //    configuration = MybatisPlusProperties()
        //}
        //if (configuration != null && !CollectionUtils.isEmpty(configurationCustomizers)) {
        //    for (customizer in configurationCustomizers) {
        //        customizer!!.customize(configuration)
        //    }
        //}
        //factory.configuration = configuration
        val coreConfiguration = properties.configuration
        var configuration: MybatisConfiguration? = null
        if (coreConfiguration != null || !StringUtils.hasText(properties.configLocation)) {
            configuration = MybatisConfiguration()
        }
        if (configuration != null && coreConfiguration != null) {
            coreConfiguration.applyTo(configuration)
        }
        if (configuration != null && !CollectionUtils.isEmpty(configurationCustomizers)) {
            for (customizer in configurationCustomizers) {
                customizer!!.customize(configuration)
            }
        }
        factory.configuration = configuration
    }

    /**
     * 检查spring容器里是否有对应的bean,有则进行消费
     *
     * @param clazz    class
     * @param consumer 消费
     * @param <T>      泛型
    </T> */
    private fun <T> getBeanThen(clazz: Class<T>, consumer: Consumer<T>) {
        if (applicationContext.getBeanNamesForType(clazz, false, false).isNotEmpty()) {
            consumer.accept(applicationContext.getBean(clazz))
        }
    }

    /**
     * 检查spring容器里是否有对应的bean,有则进行消费
     *
     * @param clazz    class
     * @param consumer 消费
     * @param <T>      泛型
    </T> */
    private fun <T> getBeansThen(clazz: Class<T>, consumer: Consumer<List<T>>) {
        if (applicationContext.getBeanNamesForType(clazz, false, false).isNotEmpty()) {
            val beansOfType = applicationContext.getBeansOfType(clazz)
            val clazzList: MutableList<T> = ArrayList()
            beansOfType.forEach { (_, v) -> clazzList.add(v) }
            consumer.accept(clazzList)
        }
    }

    private fun applySqlSessionFactoryBeanCustomizers(factory: MybatisSqlSessionFactoryBean) {
        if (!CollectionUtils.isEmpty(sqlSessionFactoryBeanCustomizers)) {
            for (customizer in sqlSessionFactoryBeanCustomizers) {
                customizer!!.customize(factory)
            }
        }
    }

    @Bean
    fun sqlSessionTemplate(sqlSessionFactory: SqlSessionFactory?): SqlSessionTemplate {
        val executorType = properties.executorType
        return executorType?.let { SqlSessionTemplate(sqlSessionFactory, it) } ?: SqlSessionTemplate(sqlSessionFactory)
    }


}
