package org.milan.naucnacentrala.service.camunda.nr;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.milan.naucnacentrala.model.NaucniRad;
import org.milan.naucnacentrala.model.dto.FormSubmissionDTO;
import org.milan.naucnacentrala.model.enums.Enums;
import org.milan.naucnacentrala.repository.INaucniRadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Kraj implements JavaDelegate {

    @Autowired
    INaucniRadRepository _nrRepo;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        int nrId = (int) delegateExecution.getVariable("nrId");

        NaucniRad nr = _nrRepo.findById(nrId).get();

        List<FormSubmissionDTO> form =
                (List<FormSubmissionDTO>)delegateExecution.getVariable("submittedForm");

        for (FormSubmissionDTO fs : form) {
            if (fs.getFieldId().equals("konacnaOdluka")) {
                if (fs.getFieldValue().equals("prihvatiti")) {
                    nr.setStatus(Enums.NaucniRadStatus.ODOBREN);
                } else if (fs.getFieldValue().equals("odbiti")) {
                    nr.setStatus(Enums.NaucniRadStatus.ODBIJEN);
                }
            }

        }

        _nrRepo.save(nr);

    }
}
