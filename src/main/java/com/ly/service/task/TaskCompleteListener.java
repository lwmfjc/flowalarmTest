package com.ly.service.task;

import com.ly.service.busi.BusiServiceA;
import com.ly.service.busi.BusiServiceB;
import com.ly.service.busi.BusiServiceC;
import com.ly.service.busi.IBusiService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.delegate.TaskListener;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.task.service.delegate.DelegateTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskCompleteListener implements TaskListener {
    private Logger logger= LoggerFactory.getLogger(TaskCompleteListener.class);

    @Autowired
    private RepositoryService repositoryService;

    private IBusiService iBusiService;

    @Override
    public void notify(DelegateTask delegateTask) {
        String processDefinitionId = delegateTask.getProcessDefinitionId();
        ProcessDefinition definition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId)
                .singleResult();
        System.out.println(definition.getDescription());
        distribute(definition.getDescription());
        if(null != iBusiService){
            //执行具体业务
            iBusiService.execute(delegateTask);
        }
        logger.info("任务处理人:{}--\n",delegateTask.getAssignee());
        logger.info("任务[id:{},name:{}]Complete\n",delegateTask.getId(),delegateTask.getName());
    }
    private void distribute(String mark){
        switch (mark){
            case "gpA":
                iBusiService=new BusiServiceA();
                break;
            case "gpB":
                iBusiService=new BusiServiceB();
                break;
            case "gpC":
                iBusiService=new BusiServiceC();
                break;
            default:
                iBusiService=new BusiServiceA();
        }

    }
}