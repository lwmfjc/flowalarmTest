package com.ly.service;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.ExecutionListener;
import org.flowable.engine.impl.el.FixedValue;
import org.springframework.stereotype.Service;
//take 这里压根不触发
@Service
public class A2 implements ExecutionListener {

    private FixedValue D2=new FixedValue("D2---value");

    public FixedValue getD2() {
        return D2;
    }

    public void setD2(FixedValue d2) {
        D2 = d2;
    }

    @Override
    public void notify(DelegateExecution delegateExecution) {
        System.out.println("A2");
        //System.out.println("A2"+delegateExecution.getVariable("D1"));
        //System.out.println("A2"+delegateExecution.getVariable("F1"));
        //System.out.println("A2"+delegateExecution.getVariable("D2"));

    }
}
