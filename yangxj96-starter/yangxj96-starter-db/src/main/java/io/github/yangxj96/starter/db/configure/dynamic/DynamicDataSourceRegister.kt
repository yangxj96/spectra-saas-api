package io.github.yangxj96.starter.db.configure.dynamic

import lombok.Getter
import org.slf4j.LoggerFactory
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.EnvironmentAware
import org.springframework.core.env.Environment
import javax.sql.DataSource

/**
 * 初始化注册数据源信息
 */
class DynamicDataSourceRegister : EnvironmentAware {

    companion object {
        /**
         * 指定默认数据源(springboot2.0默认数据源是hikari如何想使用其他数据源可以自己配置)
         */
        private const val DATASOURCE_TYPE_DEFAULT = "com.zaxxer.hikari.HikariDataSource"

        private val log = LoggerFactory.getLogger(this::class.java)
    }

    /**
     * 默认数据源
     */
    @JvmField
    @Getter
    var defaultDataSource: DataSource? = null

    /**
     * 用户自定义数据源
     */
    @JvmField
    @Getter
    var customDataSources: Map<Any, Any> = HashMap()
    override fun setEnvironment(environment: Environment) {
        // 初始化数据源注册
        initDefaultDataSource(environment)
        initCustomDataSources(environment)
    }

    /**
     * 初始化自定义数据源<br></br>
     * 在这里读取远程数据源信息后配置
     *
     * @param env 上下文环境
     */
    private fun initCustomDataSources(env: Environment) {
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
    private fun initDefaultDataSource(env: Environment) {
        // 读取主数据源
        val datum = DataSourceEntity()
        datum.driverClassName = env.getProperty("spring.datasource.driver-class-name")
        datum.url = env.getProperty("spring.datasource.url")
        datum.username = env.getProperty("spring.datasource.username")
        datum.password = env.getProperty("spring.datasource.password")
        defaultDataSource = buildDataSource(datum)
    }

    /**
     * 创建DataSource
     *
     * @param entity 数据源信息
     * @return 创建好的数据源
     */
    fun buildDataSource(entity: DataSourceEntity): DataSource? {
        try {
            var type: String? = entity.type
            if (entity.type == null) {
                type = DATASOURCE_TYPE_DEFAULT
            }

            // 构建datasource
            return DataSourceBuilder.create()
                .driverClassName(entity.driverClassName)
                .url(entity.url)
                .username(entity.username)
                .password(entity.password)
                .type(Class.forName(type) as Class<out DataSource?>)
                .build()
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * 数据源信息实体
     */
    class DataSourceEntity {

        var type: String? = null
        var driverClassName: String? = null
        var url: String? = null
        var username: String? = null
        var password: String? = null

        override fun toString(): String {
            return "DataSourceEntity(type=$type, driverClassName=$driverClassName, url=$url, username=$username, password=$password)"
        }
    }


}
