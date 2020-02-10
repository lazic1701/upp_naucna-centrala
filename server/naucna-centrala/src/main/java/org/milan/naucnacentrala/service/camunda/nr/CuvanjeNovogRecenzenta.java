package org.milan.naucnacentrala.service.camunda.nr;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.milan.naucnacentrala.model.User;
import org.milan.naucnacentrala.model.dto.FormSubmissionDTO;
import org.milan.naucnacentrala.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CuvanjeNovogRecenzenta implements JavaDelegate {

    @Autowired
    IUserRepository _userRepo;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        List<FormSubmissionDTO> form =
                (List<FormSubmissionDTO>)delegateExecution.getVariable("submittedForm");

        for (FormSubmissionDTO fs : form) {
            if (fs.getFieldId().equals("noviRecenzent")) {
                String username = fs.getFieldValue();
                System.out.println("Izabran novi za recenziju: " + username);
                delegateExecution.setVariable("rec", username);
            }
        }
    }
}
