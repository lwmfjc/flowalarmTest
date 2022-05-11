package com.ly.service.execution;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.ExecutionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class ExecutionStartListener implements ExecutionListener {
    private Logger logger= LoggerFactory.getLogger(ExecutionStartListener.class);
    @Override
    public void notify(DelegateExecution delegateExecution) {
        logger.info("流程[{}]某任务开始\n",delegateExecution.getProcessInstanceId());

    }
}
