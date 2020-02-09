package org.milan.naucnacentrala.service.camunda.nr;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.milan.naucnacentrala.model.dto.FormSubmissionDTO;
import org.milan.naucnacentrala.service.NaucniRadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CuvanjeKoautora  implements JavaDelegate {

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    NaucniRadService _naucniRadService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("[CuvanjeKoautora]: start");


        List<FormSubmissionDTO> nrForm =
                (List<FormSubmissionDTO>)delegateExecution.getVariable("submittedForm");


        System.out.println("[CuvanjeKoautora]: end");
    }
}
