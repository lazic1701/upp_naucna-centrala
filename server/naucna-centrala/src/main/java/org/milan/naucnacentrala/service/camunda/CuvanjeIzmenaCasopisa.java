package org.milan.naucnacentrala.service.camunda;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.milan.naucnacentrala.model.dto.FormSubmissionDTO;
import org.milan.naucnacentrala.service.CasopisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CuvanjeIzmenaCasopisa implements JavaDelegate {

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    @Autowired
    CasopisService _casopisService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("[CuvanjeIzmenaCasopisa]: start");

        String pid = (String) delegateExecution.getProcessInstanceId();

        List<FormSubmissionDTO> casopisForm =
                (List<FormSubmissionDTO>)delegateExecution.getVariable("submittedForm");

        int casopisId = (int) runtimeService.getVariable(pid, "casopisId");

        _casopisService.saveCasopisChanges(casopisForm, casopisId);
    }
}
