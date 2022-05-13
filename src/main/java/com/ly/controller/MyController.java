package com.ly.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.flowable.common.engine.impl.identity.Authentication;
import org.flowable.editor.language.json.converter.BpmnJsonConverter;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("flowable")
public class MyController {

    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private TaskService taskService;
    @Autowired
    protected ObjectMapper objectMapper;

    @GetMapping("/hello")
    public String hello() {

          BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();
          //return_initiator:6:e32d895b-d194-11ec-992f-28d0ea3a9c2a
        //ObjectNode editorJsonNode = (ObjectNode) objectMapper.readTree(model.getModelEditorJson());
         //bpmnJsonConverter.convertToBpmnModel(editorJsonNode );
        return "hello world!";
    }

    //流程图定义上传
    @PostMapping("/defineProcess")
    public int defineProcess(MultipartFile file) {
        try {
            byte[] bytes = file.getBytes();
            Deployment deployment = repositoryService.createDeployment().addBytes(
                    file.getOriginalFilename(), bytes
            ).deploy();
            System.out.printf("部署的流程id为--%s", deployment.getId());
            //查询引擎是否已经知道流程定义
            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                    .deploymentId(deployment.getId())
                    .singleResult();
            System.out.println("Found process definition : " + processDefinition.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
        //repositoryService.createDeployment().add
        return -1;
    }


    //查找所有的流程图定义
    @RequestMapping("findAllDefineProcess")
    public List<String> findAllDefineProcess() {
        ArrayList<String> arrayList = new ArrayList<>();
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery()
                .list();
        for (ProcessDefinition definition : list) {
            arrayList.add(definition.getName() + "==" + definition.getId());
            //repositoryService.getProcessDiagram()
            //repositoryService.getProcessDiagram(definition.getName())
        }

        return arrayList;
    }
    //查找所有的运行中的流程
    @RequestMapping("findAllProcessRun")
    public List<String> findAllProcessRun() {
        ArrayList<String> arrayList = new ArrayList<>();
        List<ProcessInstance> list = runtimeService.createProcessInstanceQuery()
                .list() ;
        for (ProcessInstance processInstance : list) {
            arrayList.add(processInstance.getName() + "==" + processInstance.getId());
        }

        return arrayList;
    }

    @RequestMapping("getProcessDefine")
    public String getProcessDefine(@RequestParam("definedId") String definedId, HttpServletResponse response) {
        // repositoryService.createDeploymentQuery().processDefinitionKey(definedId).singleResult();
        InputStream processDiagram = repositoryService.getProcessDiagram(definedId);

        try {
            ServletOutputStream outputStream = response.getOutputStream();


            response.reset();
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
            response.setContentType("application/octet-stream");
            response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode("fileName.png", "UTF-8"));//设置下载的文件名称
            response.addHeader("Access-Control-Expose-Headers", "content-disposition");

            byte[] bytes = new byte[1024];
            int byteLength = 0;
            while ((byteLength = processDiagram.read(bytes)) != -1) {
                outputStream.write(bytes, 0, byteLength);
            }
            processDiagram.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    //开启一个流程
    @RequestMapping("startProcess")
    public String startProcess(@RequestParam("deployId") String deployId) {
        String authStart=UUID.randomUUID().toString().substring(3,7);
        Authentication.setAuthenticatedUserId(authStart);
        System.out.printf("发起时的发起人为%s\n",authStart);
        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("INITIATOR",authStart);
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(deployId,hashMap);
        System.out.println("启动的流程id" + processInstance.getId());

        Authentication.setAuthenticatedUserId(null);
        return processInstance.getId();
    }

    //查询流程走到哪个任务了
    @RequestMapping("queryTasks")
    public List<String> queryTasks(@RequestParam("processId") String processId) {
        /*ProcessInstance processInstance = runtimeService.create
        System.out.println("启动的流程id" + processInstance.getId());*/
        List<Task> list = taskService.createTaskQuery().processInstanceId(processId).list();
        ArrayList<String> arrayList=new ArrayList<>();
        for (Task task:list){
            String s="任务名"+task.getName()+"---"+task.getId();
            arrayList.add(s);
            System.out.println(s);
        }
        return arrayList;
    }

    //执行某一个任务
    @RequestMapping("completeTask")
    public int completeTask(@RequestParam("taskId")String taskId){
        //完成任务
        taskService.complete(taskId);
        return -1;
    }
    //执行某一个任务
    @RequestMapping("agreeTask")
    public int agreeTask(@RequestParam("taskId")String taskId){
        HashMap<String,Object> map=new HashMap<>();
        map.put("agree","1");
        //同意并完成任务
        taskService.complete(taskId,map);
        return -1;
    }
    //执行某一个任务
    @RequestMapping("rejectTask")
    public int rejectTask(@RequestParam("taskId")String taskId){
        HashMap<String,Object> map=new HashMap<>();
        map.put("agree","0");
        //拒绝并完成任务
        taskService.complete(taskId,map);
        return -1;
    }


}
