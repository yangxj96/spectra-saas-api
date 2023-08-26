package com.yangxj96.saas.starter.security.task

import com.yangxj96.saas.starter.security.store.TokenStore
import com.yangxj96.saas.starter.security.store.impl.RedisTokenStore
import jakarta.annotation.Resource
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor
import org.springframework.scheduling.config.ScheduledTask
import org.springframework.scheduling.support.ScheduledMethodRunnable
import org.springframework.stereotype.Component
import java.util.function.Consumer

/**
 * 自动清理过期token
 * <br></br> 暂时主要是针对jdbc的,redis会自己过期
 */
@Component
@EnableScheduling
class CleanTask {

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    @Resource
    private lateinit var tokenStore: TokenStore

    @Resource
    private lateinit var postProcessor: ScheduledAnnotationBeanPostProcessor

    @Scheduled(fixedRate = 60 * 1000)
    fun tokenAutoClean() {
        if (tokenStore is RedisTokenStore) {
            log.debug("当前为Redis为存储介质,取消当前自动执行任务")
            val tasks = postProcessor.getScheduledTasks()
            tasks.forEach(Consumer { task: ScheduledTask ->
                val t = task.task
                val runnable = t.runnable as ScheduledMethodRunnable
                if (runnable.method.name == "tokenAutoClean") {
                    postProcessor.postProcessBeforeDestruction(runnable.target, "tokenAutoClean")
                }
            })
        }
        try {
            tokenStore.autoClean()
        } catch (e: Exception) {
            log.error("自动清理token出现异常,请检查")
        }
    }
}
