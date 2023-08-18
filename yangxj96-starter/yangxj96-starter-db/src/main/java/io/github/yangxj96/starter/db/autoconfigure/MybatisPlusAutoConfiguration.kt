package io.github.yangxj96.starter.db.autoconfigure

import com.baomidou.mybatisplus.annotation.DbType
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor
import io.github.yangxj96.starter.db.configure.jdbc.MetaObjectHandlerImpl
import io.github.yangxj96.starter.db.props.DBProperties
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean

/**
 * mybatis的自动配置类
 */
@ConditionalOnProperty(name = ["yangxj96.db.jdbc-enable"], havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(DBProperties::class)
class MybatisPlusAutoConfiguration {

    companion object {
        private const val LOG_PREFIX = "[自动配置-mybatis plus]:"

        private val log = LoggerFactory.getLogger(this::class.java)
    }

    @Bean
    fun metaObjectHandler(): MetaObjectHandler {
        log.debug("{}元对象字段填充控制器", LOG_PREFIX)
        return MetaObjectHandlerImpl()
    }

    /**
     * MyBatis Plus插件
     *
     * 一缓和二缓遵循mybatis的规则
     *
     * @return [MybatisPlusInterceptor] MyBatis Plus拦截器
     */
    @Bean
    fun mybatisPlusInterceptor(): MybatisPlusInterceptor {
        log.debug(LOG_PREFIX + "载入MybatisPlusInterceptor")
        // 分页插件
        val pageInterceptor = PaginationInnerInterceptor()
        pageInterceptor.isOverflow = true
        pageInterceptor.maxLimit = 500L
        pageInterceptor.dbType = DbType.POSTGRE_SQL
        val interceptor = MybatisPlusInterceptor()
        interceptor.addInnerInterceptor(pageInterceptor)
        // 针对 update 和 delete 语句 作用: 阻止恶意的全表更新删除
        interceptor.addInnerInterceptor(BlockAttackInnerInterceptor())
        return interceptor
    }


}
