package com.ly.service.task;

import com.ly.service.execution.ExecutionEndListener;
import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class TaskAssignmentListener implements TaskListener {
    private Logger logger= LoggerFactory.getLogger(TaskAssignmentListener.class);
    @Override
    public void notify(DelegateTask delegateTask) {

        logger.info("任务[id:{},name:{}]Assignment\n",delegateTask.getId(),delegateTask.getName());
    }
}
