package org.milan.naucnacentrala.service.camunda;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.milan.naucnacentrala.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssignRecenzent implements JavaDelegate {

    @Autowired
    private UserService _userService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("[AssignRecenzent]: start");
        String username = (String) delegateExecution.getVariable("usernameForValidation");

        _userService.assignRecenzentRole(username);
    }
}
