package io.github.yangxj96.starter.security.task;

import io.github.yangxj96.starter.security.store.TokenStore;
import io.github.yangxj96.starter.security.store.impl.RedisTokenStore;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.scheduling.config.Task;
import org.springframework.scheduling.support.ScheduledMethodRunnable;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Set;

/**
 * 自动清理过期token
 * <br/> 暂时主要是针对jdbc的,redis会自己过期
 */
@Slf4j
@Component
@EnableScheduling
public class CleanTask {

    @Resource
    private TokenStore tokenStore;

    @Resource
    private ScheduledAnnotationBeanPostProcessor postProcessor;

    @Scheduled(fixedRate = (60 * 1000))
    public void tokenAutoClean() {
        if (tokenStore instanceof RedisTokenStore) {
            log.debug("当前为Redis为存储介质,取消当前自动执行任务");
            Set<ScheduledTask> tasks = postProcessor.getScheduledTasks();
            tasks.forEach(task -> {
                Task t = task.getTask();
                ScheduledMethodRunnable runnable = (ScheduledMethodRunnable) t.getRunnable();
                if (Objects.equals(runnable.getMethod().getName(), "tokenAutoClean")) {
                    postProcessor.postProcessBeforeDestruction(runnable.getTarget(), "tokenAutoClean");
                }
            });
        }
        try {
            tokenStore.autoClean();
        } catch (Exception e) {
            log.error("自动清理token出现异常,请检查");
        }
    }


}
