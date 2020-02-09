package org.milan.naucnacentrala.service.camunda.nr;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.milan.naucnacentrala.model.dto.FormSubmissionDTO;
import org.milan.naucnacentrala.service.CasopisService;
import org.milan.naucnacentrala.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProveraTipaCasopisa implements JavaDelegate {

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    @Autowired
    CasopisService _casopisService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("[ProveraTipaCasopisa]: start");

        String pid = delegateExecution.getProcessInstanceId();

        List<FormSubmissionDTO> casopisForm =
                (List<FormSubmissionDTO>)delegateExecution.getVariable("submittedForm");

        _casopisService.isOpenAccess(casopisForm, pid);

    }
}
