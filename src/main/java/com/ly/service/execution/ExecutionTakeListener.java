package com.ly.service.execution;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.ExecutionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class ExecutionTakeListener implements ExecutionListener {
    private Logger logger= LoggerFactory.getLogger(ExecutionTakeListener.class);
    @Override
    public void notify(DelegateExecution delegateExecution) {
        logger.info("流程[{}]某任务take\n",delegateExecution.getProcessInstanceId());

    }
}
