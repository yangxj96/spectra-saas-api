/*
 *  Copyright (c) 2021 - 2023
 *  作者：杨新杰(Jack Young)
 *  邮箱：yangxj96@gmail.com
 *  日期：2023-04-12 13:54:26
 *  Copyright (c) 2021 - 2023
 */

package io.github.yangxj96.commonsaas.yangxj96serve.flow;

import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.*;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
     * <br/>
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
     * <br/>
     * 流程实例ID：8c987ed8-daea-11ed-8fe8-088e90e01c12
     * <br/>
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

    /**
     * 查询任务且完成节点
     */
    @Test
    void flow() {
        List<Task> tasks = taskService.createTaskQuery()
                .processInstanceId("9009c2e3-df2b-11ed-8899-e45e373be23c")
                .list();
        for (Task task : tasks) {
            log.info("任务信息:{}", task.getId());
            log.info("任务名称:{}", task.getName());
            taskService.complete(task.getId());
        }
    }

    /**
     * 查询历史
     */
    @Test
    void history() {

        List<HistoricProcessInstance> history = historyService.createHistoricProcessInstanceQuery()
                .processInstanceId("9009c2e3-df2b-11ed-8899-e45e373be23c")
                .list();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");


        for (HistoricProcessInstance detail : history) {
            log.info("--------------begin--------------");
            log.info("ID:{}", detail.getId());
            log.info("任务名称:{}", detail.getName());
            log.info("步骤名称:{}", detail.getProcessDefinitionName());
            log.info("开始时间:{}", detail.getStartTime() != null ? format.format(detail.getStartTime()) : "");
            log.info("完成时间:{}", detail.getEndTime() != null ? format.format(detail.getEndTime()) : "");
            log.info("流程耗时:{}", detail.getDurationInMillis());
            log.info("-------------- end --------------");
        }


    }

    private static final String PROCESS_ID = "code_deploy";
    private static final String PROCESS_NAME = "代码创建的流程";

    /**
     * 代码创建流程,而不是bpmn20.xml
     * <p>
     * 流程ID:68d21eab-df28-11ed-9645-e45e373be23c
     * <br/>
     * 流程名称:代码创建的流程
     */
    @Test
    void codeDeploy() {
        var process = new Process();
        var bpmnModel = new BpmnModel();
        bpmnModel.addProcess(process);

        // 流程信息
        process.setId(PROCESS_ID);
        process.setName(PROCESS_NAME);

        // 开始事件
        var start = new StartEvent();
        start.setId("start");
        start.setName("开始");
        process.addFlowElement(start);

        // 用户处理事件
        var task = new UserTask();
        task.setId("audit");
        task.setName("审核版本");
        task.setAssignee("王总");
        task.setCandidateUsers(new ArrayList<>());
        task.setCandidateGroups(new ArrayList<>());
        process.addFlowElement(task);

        // 结束事件
        var end = new EndEvent();
        end.setId("end");
        end.setName("结束");
        process.addFlowElement(end);

        // 连接线
        var seq1 = new SequenceFlow();
        seq1.setSourceRef(start.getId());
        seq1.setTargetRef(task.getId());
        process.addFlowElement(seq1);

        var seq2 = new SequenceFlow();
        seq2.setSourceRef(task.getId());
        seq2.setTargetRef(end.getId());
        process.addFlowElement(seq2);


        Deployment deploy = repositoryService
                .createDeployment()
                .addBpmnModel(PROCESS_ID + ".bpmn", bpmnModel)
                .name(PROCESS_NAME)
                .deploy();

        Assertions.assertNotNull(deploy);

        log.info("流程ID:{}", deploy.getId());
        log.info("流程名称:{}", deploy.getName());
    }

    /**
     * 启动流程
     * <br/>
     * 流程实例ID:9009c2e3-df2b-11ed-8899-e45e373be23c
     * <br/>
     * 流程实例名称:第一次的实例
     */
    @Test
    void codeStartProcess() {
        var instance = runtimeService
                .createProcessInstanceBuilder()
                .name("第一次的实例")
                .processDefinitionKey(PROCESS_ID)
                .start();

//                .startProcessInstanceByKey(PROCESS_ID);

        Assertions.assertNotNull(instance);

        log.info("流程实例ID:{}", instance.getId());
        log.info("流程实例名称:{}", instance.getName());
    }

    /**
     * 转换成bpmn2格式的xml
     * @throws IOException io
     */
    @Test
    void convertBpmnXml() throws IOException {
        var resource = repositoryService.getResourceAsStream("68d21eab-df28-11ed-9645-e45e373be23c", PROCESS_ID + ".bpmn");
        Assertions.assertNotNull(resource);
        FileUtils.copyInputStreamToFile(resource, new File("src/main/resources/bpmn/" + PROCESS_ID + ".bpmn"));
    }

    /**
     * 转换成svg图片
     * @throws IOException io
     */
    @Test
    void convertSvg() throws IOException {
        var instance = runtimeService
                .createProcessInstanceQuery()
                .processDefinitionKey(PROCESS_ID)
                .singleResult();
        Assertions.assertNotNull(instance);
        var bpmnModel = repositoryService.getBpmnModel(instance.getProcessDefinitionId());
        if (bpmnModel != null && bpmnModel.getLocationMap().size() > 0) {
            DefaultProcessDiagramGenerator ge = new DefaultProcessDiagramGenerator();

            InputStream inputStream = ge.generateDiagram(bpmnModel, runtimeService.getActiveActivityIds(instance.getId()), new ArrayList<>(), "宋体", "宋体", null, false);

            FileUtils.copyInputStreamToFile(inputStream, new File("src/main/resources/bpmn/" + PROCESS_ID + ".svg"));
        } else {
            System.out.println("bpmnModel 为空！");
        }
    }

}
