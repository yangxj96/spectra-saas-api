package com.yangxj96.spectra.starter.db.autoconfigure;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.yangxj96.spectra.starter.db.configure.jdbc.MetaObjectHandlerImpl;
import com.yangxj96.spectra.starter.db.props.DBProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * mybatis的自动配置类
 */
@Slf4j
@ConditionalOnProperty(name = "yangxj96.db.jdbc-enable", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(DBProperties.class)
public class MybatisPlusAutoConfiguration {

    private static final String PREFIX = "[MybatisPlus]:";


    @Bean
    MetaObjectHandler metaObjectHandler() {
        log.debug("{}元对象字段填充控制器", PREFIX);
        return new MetaObjectHandlerImpl();
    }

    /**
     * MyBatis Plus插件
     * <p>
     * 一缓和二缓遵循mybatis的规则
     *
     * @return [MybatisPlusInterceptor] MyBatis Plus拦截器
     */
    @Bean
    MybatisPlusInterceptor mybatisPlusInterceptor() {
        log.debug(PREFIX + "载入MybatisPlusInterceptor");
        // 分页插件
        var pageInterceptor = new PaginationInnerInterceptor();
        pageInterceptor.setOverflow(true);
        pageInterceptor.setMaxLimit(500L);
        pageInterceptor.setDbType(DbType.POSTGRE_SQL);
        var interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(pageInterceptor);
        // 针对 update 和 delete 语句 作用: 阻止恶意的全表更新删除
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        return interceptor;
    }


}
