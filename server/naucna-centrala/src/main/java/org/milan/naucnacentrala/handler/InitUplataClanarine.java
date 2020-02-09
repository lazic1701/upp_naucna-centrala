package org.milan.naucnacentrala.handler;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.milan.naucnacentrala.service.TasksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class InitUplataClanarine  implements ExecutionListener {

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TasksService _tasksService;

    @Autowired
    TaskService taskService;

    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {
        ProcessInstance pi = (ProcessInstance) delegateExecution.getProcessInstance();
        Map<String, Object> variables = delegateExecution.getVariables();
       // runtimeService.setVariable(pi.getId(), "assignedUser_ROLE_AUTOR", username);
        System.out.println("bl");
    }
}
