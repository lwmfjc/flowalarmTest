package com.ly.service.task;

import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class TaskDeleteListener implements TaskListener {
    private Logger logger = LoggerFactory.getLogger(TaskDeleteListener.class);

    @Override
    public void notify(DelegateTask delegateTask) {

        logger.info("任务[id:{},name:{}]Delete\n",delegateTask.getId(),delegateTask.getName());
    }
}