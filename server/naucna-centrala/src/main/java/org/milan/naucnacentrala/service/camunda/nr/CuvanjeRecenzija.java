package org.milan.naucnacentrala.service.camunda.nr;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.milan.naucnacentrala.model.dto.FormSubmissionDTO;
import org.milan.naucnacentrala.service.NaucniRadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CuvanjeRecenzija implements JavaDelegate {

    @Autowired
    NaucniRadService _nrService;


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("[CuvanjeRecenzija]: start");

        int nrId = (int) delegateExecution.getVariable("nrId");

        String recUsername = (String) delegateExecution.getVariable("rec");

        List<FormSubmissionDTO> form =
                (List<FormSubmissionDTO>)delegateExecution.getVariable("submittedForm");

        _nrService.sacuvajRecenzije(form, nrId, recUsername);
    }
}
