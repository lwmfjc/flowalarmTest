package com.ly.service;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.ExecutionListener;
import org.flowable.engine.impl.el.FixedValue;
import org.springframework.stereotype.Service;
//start
@Service
public class A1 implements ExecutionListener {
    private FixedValue D1=new FixedValue("D1---value");
    private FixedValue F1=new FixedValue("F1---value");

    public FixedValue getD1() {
        return D1;
    }

    public void setD1(FixedValue d1) {
        D1 = d1;
    }

    public FixedValue getF1() {
        return F1;
    }

    public void setF1(FixedValue f1) {
        F1 = f1;
    }

    @Override
    public void notify(DelegateExecution delegateExecution) {
        System.out.println("A1");
        //System.out.println("A1"+delegateExecution.getVariable("D1"));
       // System.out.println("A1"+delegateExecution.getVariable("F1"));
        //System.out.println("A1"+delegateExecution.getVariable("D2"));

    }
}
