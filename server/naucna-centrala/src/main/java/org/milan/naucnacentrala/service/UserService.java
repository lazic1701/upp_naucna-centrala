package org.milan.naucnacentrala.service;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.milan.naucnacentrala.exception.BusinessException;
import org.milan.naucnacentrala.model.User;
import org.milan.naucnacentrala.model.dto.FormFieldsDTO;
import org.milan.naucnacentrala.model.dto.FormSubmissionDTO;
import org.milan.naucnacentrala.model.dto.Mapper;
import org.milan.naucnacentrala.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;

@Service
public class UserService {


    @Autowired
    TaskService taskService;

    @Autowired
    FormService formService;

    @Autowired
    IdentityService identityService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    IUserRepository _userRepo;

    private final String REG_PROCESS_INSTANCE_ID = "Process_registracija_korisnika";

    public FormFieldsDTO getRegistrationForm() {
        ProcessInstance pi = runtimeService.startProcessInstanceByKey(this.REG_PROCESS_INSTANCE_ID);

        Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).list().get(0);

        TaskFormData tfd = formService.getTaskFormData(task.getId());
        List<FormField> properties = tfd.getFormFields();

        return new FormFieldsDTO(task.getId(), pi.getId(), properties);
    }

    public void registerUser(List<FormSubmissionDTO> dto, String taskId) {

        HashMap<String, Object> map = Mapper.mapListToDto(dto);

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        runtimeService.setVariable(processInstanceId, "registrationForm", dto);
        formService.submitTaskForm(taskId, map);
    }

    public int createUser(User u) {
        return _userRepo.save(u).getId();
    }

    public void verify(String processInstanceId_b64, String username_b64) {

        String username = new String(Base64.getDecoder().decode(username_b64));
        String processInstanceId = new String(Base64.getDecoder().decode(processInstanceId_b64));

        User u = _userRepo.findByUsername(username).get();

        runtimeService.setVariable(processInstanceId, "active", true);

        u.setActive(true);
        _userRepo.save(u);
    }


    public void validateForm(List<FormSubmissionDTO> dto) throws BusinessException {
        for (FormSubmissionDTO f : dto) {
            if(f.getFieldId().equals("email")) {
                if(_userRepo.existsByEmail(f.getFieldValue())) {
                    throw new BusinessException("Već postoji registrovan korisnik sa unetom email adresom.");
                }
            } else if (f.getFieldId().equals("username")) {
                if(_userRepo.existsByUsername(f.getFieldValue())) {
                    throw new BusinessException("Već postoji registrovan korisnik sa unetim korisničkim imenom.");
                }
            }
        }
    }
}
