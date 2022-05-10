package com.ly.service;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.ExecutionListener;
import org.flowable.engine.impl.el.FixedValue;
import org.springframework.stereotype.Service;
//end
@Service
public class A3 implements ExecutionListener {


    @Override
    public void notify(DelegateExecution delegateExecution) {
        System.out.println("A3");

    }
}
