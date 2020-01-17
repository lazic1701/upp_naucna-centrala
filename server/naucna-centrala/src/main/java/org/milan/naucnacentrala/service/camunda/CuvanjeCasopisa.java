package org.milan.naucnacentrala.service.camunda;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.task.Task;
import org.milan.naucnacentrala.model.dto.FormSubmissionDTO;
import org.milan.naucnacentrala.repository.ICasopisRepository;
import org.milan.naucnacentrala.repository.IUserRepository;
import org.milan.naucnacentrala.service.CasopisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CuvanjeCasopisa implements JavaDelegate {

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    @Autowired
    CasopisService _casopisService;



    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("[CuvanjeCasopisa]: start");


        String pid = delegateExecution.getProcessInstanceId();
        String username = (String) runtimeService.getVariable(pid, "assignedUser_ROLE_UREDNIK");

        List<FormSubmissionDTO> casopisForm =
                (List<FormSubmissionDTO>)delegateExecution.getVariable("submittedForm");

        int id = _casopisService.createCasopis(casopisForm, username);

        runtimeService.setVariable(pid, "casopisId", id);

    }
}
