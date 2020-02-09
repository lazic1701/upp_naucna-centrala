package org.milan.naucnacentrala.service.camunda.nr;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.milan.naucnacentrala.service.CasopisService;
import org.milan.naucnacentrala.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProveraClanarine implements JavaDelegate {

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    @Autowired
    UserService _userService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("[ProveraClanarine]: start");

        String pid = delegateExecution.getProcessInstanceId();

        int casopisId = (int) runtimeService.getVariable(pid, "casopisId");
        String username = (String) runtimeService.getVariable(pid, "assignedUser_ROLE_AUTOR");

        _userService.proveraClanarine(username, casopisId, pid);


    }
}
