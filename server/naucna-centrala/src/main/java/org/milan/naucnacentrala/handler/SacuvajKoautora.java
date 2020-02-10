package org.milan.naucnacentrala.handler;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.milan.naucnacentrala.model.dto.FormSubmissionDTO;
import org.milan.naucnacentrala.service.NaucniRadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SacuvajKoautora implements TaskListener {

    @Autowired
    NaucniRadService _nrService;

    @Autowired
    RuntimeService runtimeService;

    @Override
    public void notify(DelegateTask delegateTask) {

        System.out.println("[SacuvajKoautora]: start");


        List<FormSubmissionDTO> nrForm =
                (List<FormSubmissionDTO>)delegateTask.getExecution().getVariable("submittedForm");

        int nrId = (int) delegateTask.getExecution().getVariable("nrId");

        _nrService.saveKoautor(nrId, nrForm);

    }
}
