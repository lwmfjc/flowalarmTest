package com.ly.service.busi;


import org.flowable.task.service.delegate.DelegateTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BusiServiceC implements IBusiService {

    private Logger logger= LoggerFactory.getLogger(BusiServiceC.class);
    @Override
    public void execute(DelegateTask delegateTask) {
        logger.info("C业务");
    }
}
