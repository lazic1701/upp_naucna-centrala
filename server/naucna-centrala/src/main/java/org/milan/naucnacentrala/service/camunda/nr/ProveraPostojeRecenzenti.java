package org.milan.naucnacentrala.service.camunda.nr;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.milan.naucnacentrala.model.User;
import org.milan.naucnacentrala.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProveraPostojeRecenzenti implements JavaDelegate {

    @Autowired
    UserService _userService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("[ProveraPostojeRecenzenti]: start");
        int nrId = (int) delegateExecution.getVariable("nrId");

        List<User> recenzenti = _userService.getRecenzentiFilter(nrId);
        delegateExecution.setVariable("postoje2recenzenta", recenzenti.size() >= 2);
        delegateExecution.setVariable("recenzentiFilter", recenzenti);

    }
}
