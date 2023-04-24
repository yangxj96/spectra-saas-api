package io.github.yangxj96.commonsaas.yangxj96serve.flow;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.List;

@Slf4j
@SpringBootTest
class ApplicationTests {

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private RepositoryService repositoryService;

    @Resource
    private HistoryService historyService;


    @Resource
    private TaskService taskService;


    /**
     * 流程ID :80d3f3db-daea-11ed-8a24-088e90e01c12
     * 流程名称:测试流程
     */
    @Test
    void deploy() {
        Deployment deploy = repositoryService
                .createDeployment()
                .addClasspathResource("bpmn/Demo.bpmn20.xml")
                .name("测试流程")
                .deploy();

        log.info("流程ID :" + deploy.getId());
        log.info("流程名称:" + deploy.getName());
    }

    /**
     * 流程实例名称：null
     * 流程实例ID：8c987ed8-daea-11ed-8fe8-088e90e01c12
     * 流程定义ID：Demo:1:80e0c51d-daea-11ed-8a24-088e90e01c12
     */
    @Test
    void start() {
        ProcessInstance instance = runtimeService.createProcessInstanceBuilder()
                .processDefinitionKey("Demo")
                .start();

        log.info("流程实例名称：" + instance.getName());
        log.info("流程实例ID：" + instance.getId());
        log.info("流程定义ID：" + instance.getProcessDefinitionId());

    }

    @Test
    void flow() {
        List<Task> tasks = taskService.createTaskQuery()
                .processInstanceId("8c987ed8-daea-11ed-8fe8-088e90e01c12")
                .list();
        for (Task task : tasks) {
            log.info("任务信息:{}", task.getId());
            log.info("任务名称:{}", task.getName());
            taskService.complete(task.getId());
        }
    }

    @Test
    void history() {

        List<HistoricProcessInstance> history = historyService.createHistoricProcessInstanceQuery()
                .processInstanceId("8c987ed8-daea-11ed-8fe8-088e90e01c12")
                .list();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");


        for (HistoricProcessInstance detail : history) {
            log.info("--------------begin--------------");
            log.info("ID :{}", detail.getId());
            log.info("步骤名称:{}", detail.getProcessDefinitionName());
            log.info("开始时间:{}", format.format(detail.getStartTime()));
            log.info("完成时间:{}", format.format(detail.getEndTime()));
            log.info("流程耗时:{}", detail.getDurationInMillis());
            log.info("-------------- end --------------");
        }


    }

}
