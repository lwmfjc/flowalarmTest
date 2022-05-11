package com.ly.service.busi;

import org.flowable.task.service.delegate.DelegateTask;

public interface IBusiService {
    void execute(DelegateTask delegateTask);
}
