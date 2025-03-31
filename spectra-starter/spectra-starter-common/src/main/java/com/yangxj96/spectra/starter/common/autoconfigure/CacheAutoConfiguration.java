package com.yangxj96.spectra.starter.common.autoconfigure;

import java.lang.reflect.Method;
import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ConditionalOnClass(value = { EnableCaching.class })
public class CacheAutoConfiguration {

    private static final String PREFIX = "[缓存配置]:";

    /**
     * 缓存生成规则
     */
    @Bean
    public KeyGenerator generatorCacheKey() {
        log.info("{} 配置缓存key生成规则", PREFIX);
        return (Object target, Method method, Object... params) -> {
            var result = "";
            if (params.length > 0) {
                var str = new StringBuilder();
                for (Object param : params) {
                    if (param.getClass().isArray()) {
                        var arrStr = new StringBuilder("[");
                        if (param instanceof List<?> p) {
                            p.forEach(item -> arrStr.append(item).append(","));
                        }
                        str.append(arrStr.substring(0, arrStr.length() - 1)).append("]&");
                    } else {
                        str.append(param).append("&");
                    }
                }
                result = String.format("%s{%s}", method.getName(), str.substring(0, str.length() - 1));
            }
            return result;
        };

    }

}