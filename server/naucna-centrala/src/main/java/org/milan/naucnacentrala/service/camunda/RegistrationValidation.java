package org.milan.naucnacentrala.service.camunda;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.impl.bpmn.delegate.JavaDelegateInvocation;
import org.milan.naucnacentrala.model.NaucnaOblast;
import org.milan.naucnacentrala.model.User;
import org.milan.naucnacentrala.model.dto.FormSubmissionDTO;
import org.milan.naucnacentrala.model.enums.Enums;
import org.milan.naucnacentrala.repository.IAuthorityRepository;
import org.milan.naucnacentrala.repository.INaucnaOblastRepository;
import org.milan.naucnacentrala.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistrationValidation implements JavaDelegate {

    @Autowired
    UserService _userService;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    INaucnaOblastRepository _noRepo;

    @Autowired
    IAuthorityRepository authorityRepository;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("[RegistrationValidation]: start");
        List<FormSubmissionDTO> registrationForm =
                (List<FormSubmissionDTO>)delegateExecution.getVariable("registrationForm");

        try {
            _userService.validateForm(registrationForm);
            runtimeService.setVariable(delegateExecution.getProcessInstanceId(), "validForm", true);

        } catch (Exception e) {
            runtimeService.setVariable(delegateExecution.getProcessInstanceId(), "validForm", false);
        }

        User u = new User();

        for (FormSubmissionDTO fs : registrationForm) {

            if (fs.getFieldId().equals("username")) {
                u.setUsername(fs.getFieldValue());
                runtimeService.setVariable(delegateExecution.getProcessInstanceId(), "usernameForValidation", fs.getFieldValue());
            } else if (fs.getFieldId().equals("password")) {
                u.setPassword(hashPass(fs.getFieldValue()));
            } else if (fs.getFieldId().equals("firstname")) {
                u.setFirstname((fs.getFieldValue()));
            } else if (fs.getFieldId().equals("lastname")) {
                u.setLastname((fs.getFieldValue()));
            } else if (fs.getFieldId().equals("city")) {
                u.setCity((fs.getFieldValue()));
            } else if (fs.getFieldId().equals("country")) {
                u.setCountry((fs.getFieldValue()));
            } else if (fs.getFieldId().equals("title")) {
                u.setTitle((fs.getFieldValue()));
            } else if (fs.getFieldId().equals("email")) {
                u.setEmail((fs.getFieldValue()));
            } else if (fs.getFieldId().equals("naucneOblasti")) {
                for (NaucnaOblast no : _noRepo.findAll()) {
                    if (no.getId().equals(fs.getFieldValue())) {
                        u.getNaucneOblastiAutor().add(no);
                    }
                }
            }
        }

        u.getAuthorities().add(authorityRepository.findOneByName(Enums.UserRole.AUTOR.toString()));
        u.setActive(false);

        _userService.createUser(u);

    }
    private String hashPass(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }


}
