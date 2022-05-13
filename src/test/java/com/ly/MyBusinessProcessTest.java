package com.ly;

import com.ly.service.execution.ExecutionEndListener;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.engine.test.Deployment;
import org.flowable.spring.impl.test.FlowableSpringExtension;
import org.flowable.task.api.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@ExtendWith(FlowableSpringExtension.class)
@ExtendWith({SpringExtension.class})
//@ExtendWith( JUnit4ClassRunner.class )
//@ContextConfiguration(classes = {SpringJunitJupiterTest.TestConfiguration.class })
@SpringBootTest(classes = MyApplication.class)
public class MyBusinessProcessTest {

    //角色map
    private Map<String, List<String>> roles =new HashMap<>();
    //所有人
    private List<String> emps=new ArrayList<>();
    @BeforeEach
    public void testBefore(){
        System.out.println("开始测试");
        emps.add("a1");
        emps.add("a2");
        emps.add("b1");
        emps.add("b2");
        emps.add("c1");
        emps.add("c2");
        List<String> roleA=new ArrayList<>();
        roleA.add("a1");
        roleA.add("a2");
        roles.put("roleA",roleA);

        List<String> roleB=new ArrayList<>();
        roleB.add("b1");
        roleB.add("b2");
        roles.put("roleB",roleB);

        List<String> roleC=new ArrayList<>();
        roleC.add("c1");
        roleC.add("c2");
        roles.put("roleC",roleC);
    }

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private ExecutionEndListener executionEndListener;

    @Test
    @Deployment(resources = {"ly/simpleProcessName.bpmn20.xml"})
    void simpleProcessTest() {
        //启动流程1
        ProcessInstance processInstance1 = runtimeService.startProcessInstanceByKey("simpleProcessKey");
        //启动流程2
        ProcessInstance processInstance2 = runtimeService.startProcessInstanceByKey("simpleProcessKey");
        //获取流程1的当前任务
        Task task1 = taskService.createTaskQuery().processInstanceId(processInstance1.getId()).singleResult();
        assertEquals("My Task", task1.getName());
        log.info("流程{}的当前任务{}\n",processInstance1.getId(),task1.getId());

        //获取流程2的当前任务
        Task task2 = taskService.createTaskQuery().processInstanceId(processInstance2.getId()).singleResult();
        assertEquals("My Task", task2.getName());
        log.info("流程{}的当前任务{}\n",processInstance2.getId(),task2.getId());

        assertEquals(2, runtimeService.createProcessInstanceQuery().count());
        taskService.complete(task2.getId());
        log.info("流程{}任务{}完成\n",processInstance2.getId(),task2.getId());
        assertEquals(1, runtimeService.createProcessInstanceQuery().count());
        String curRunProcessId = runtimeService.createProcessInstanceQuery().singleResult().getId();
        log.info("正在运行的流程{}\n",curRunProcessId);
        log.info("该流程目前的任务{}",taskService.createTaskQuery().processInstanceId(curRunProcessId)
        .singleResult().getId());

    }


    @Test
    @Deployment(resources = {"ly/holiday_name.bpmn20.xml"})
    void holidayProcessTest(){
        //executionEndListener.notify(null );
        //启动一个请假流程
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("holiday_key");
        //查看当前流程每个人的任务
    }

    @Test
    public void methodTest(){
        String role = getEmpRole("b2");
        System.out.println(role);

    }

    /**
     * 获取该员工的角色
     * @param empSearch
     * @return
     */
    private String getEmpRole(String empSearch){
        Set<String> keySet = roles.keySet();
        for (String role:keySet){
            List<String> emps = roles.get(role);
            for(String emp:emps){
                if(emp.equals(empSearch)){
                    return role;
                }
            }
        }
        return null;
    }

    /**
     * 获取所有员工的任务
     */
    private void showAllTasksAllEmp(){
        log.info("获取所有员工的任务--start");
         for(String empId:emps){
             String empRole = getEmpRole(empId);

             List<Task> listRole = taskService.createTaskQuery().taskCandidateGroup(empRole).list();
             List<Task> listEmp = taskService.createTaskQuery().taskAssignee(empId).list();
         }
        log.info("获取所有员工的任务--end");
    }

    private void showTasksByProcessId(String processId,String empId,String roleId){

    }
}
