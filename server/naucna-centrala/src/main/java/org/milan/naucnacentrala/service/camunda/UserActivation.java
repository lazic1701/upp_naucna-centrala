package org.milan.naucnacentrala.service.camunda;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.milan.naucnacentrala.model.User;
import org.milan.naucnacentrala.repository.IUserRepository;
import org.milan.naucnacentrala.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserActivation implements JavaDelegate {

    @Autowired
    private UserService _userService;

    @Autowired
    private IUserRepository _userRepo;



    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        String username = (String) delegateExecution.getVariable("usernameForValidation");
        _userService.createCamundaUserAndSetActive(username);
    }
}
