package com.ly.controller;

import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController("flowable")
public class MyController {

    @Autowired
    private RepositoryService repositoryService;

    //流程图定义上传
    @GetMapping("defineProcess")
    public int defineProcess(MultipartFile file) {
        try {
            byte[] bytes = file.getBytes();
            Deployment deployment = repositoryService.createDeployment().addBytes(
                    "ly-process", bytes
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
}
