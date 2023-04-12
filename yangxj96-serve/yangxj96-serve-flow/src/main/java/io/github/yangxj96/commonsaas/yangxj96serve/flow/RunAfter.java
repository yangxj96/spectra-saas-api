/*
 *  Copyright (c) 2021 - 2023
 *  作者：杨新杰(Jack Young)
 *  邮箱：yangxj96@gmail.com
 *  日期：2023-04-12 14:07:10
 *  Copyright (c) 2021 - 2023
 */

package io.github.yangxj96.commonsaas.yangxj96serve.flow;

import org.activiti.api.task.model.builders.TaskPayloadBuilder;
import org.activiti.api.task.runtime.TaskRuntime;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author yangxj96
 * @version 1.0.0
 * @date 2023/4/12 14:07
 */
@Component
public class RunAfter implements CommandLineRunner {

    @Resource
    private TaskRuntime taskRuntime;

    @Override
    public void run(String... args) throws Exception {
        taskRuntime.create(TaskPayloadBuilder
                .create()
                .withName("First Team Task")
                .withDescription("This is something really important")
                .withCandidateGroup("activitiTeam")
                .withPriority(10)
                .build());


    }


}
