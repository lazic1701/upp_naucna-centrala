package org.milan.naucnacentrala.service;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.impl.form.type.EnumFormType;
import org.camunda.bpm.engine.task.Task;
import org.milan.naucnacentrala.model.Casopis;
import org.milan.naucnacentrala.model.NaucnaOblast;
import org.milan.naucnacentrala.model.User;
import org.milan.naucnacentrala.model.dto.FormFieldsDTO;
import org.milan.naucnacentrala.model.dto.FormSubmissionDTO;
import org.milan.naucnacentrala.model.dto.Mapper;
import org.milan.naucnacentrala.model.dto.TaskDTO;
import org.milan.naucnacentrala.repository.ICasopisRepository;
import org.milan.naucnacentrala.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TasksService {

    @Autowired
    TaskService taskService;

    @Autowired
    FormService formService;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    UserService _userService;

    @Autowired
    IUserRepository _userRepo;

    @Autowired
    ICasopisRepository _casopisRepo;



    public List<TaskDTO> getGroupTasks(String groupId) {

        List<TaskDTO> groupTasks = new ArrayList<>();

        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup(groupId).list();
        groupTasks.addAll(tasks
                .stream()
                .map(t -> new TaskDTO(t.getId(), t.getName(), t.getAssignee()))
                .collect(Collectors.toList()));

        return groupTasks;
    }

    // TODO authorization
    public TaskDTO getTask(String id, HttpServletRequest request) {
        return TaskDTO.formDto(taskService.createTaskQuery().taskId(id).singleResult());
    }

    public List<TaskDTO> getUserTasks(HttpServletRequest request) {

        List<TaskDTO> userTasks = new ArrayList<>();

        List<Task> tasks = taskService.createTaskQuery().taskAssignee(_userService.getUsernameFromRequest(request)).list();

        userTasks.addAll(tasks
                .stream()
                .map(t -> new TaskDTO(t.getId(), t.getName(), t.getAssignee()))
                .collect(Collectors.toList()));

        return userTasks;
    }

    public void claimTask(String taskId, HttpServletRequest request) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String username = _userService.getUsernameFromRequest(request);
        taskService.claim(taskId, username);
    }

    // assign variable with specified name to the username from request
    public void claimTask(String taskId, HttpServletRequest request, String variableName) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        String username = _userService.getUsernameFromRequest(request);
        taskService.claim(taskId, username);
        runtimeService.setVariable(processInstanceId, variableName, username);
    }


    // TODO maybe implement some kind of authorization
    public FormFieldsDTO getTaskForm(String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String username = task.getAssignee();
        TaskFormData tfd = formService.getTaskFormData(taskId);
        List<FormField> properties = tfd.getFormFields();
        extendPropertiesIfNeeded(properties, username, task);
        return new FormFieldsDTO(task.getId(), task.getProcessInstanceId(), properties);
    }

    private void extendPropertiesIfNeeded(List<FormField> properties, String username, Task task) {
        for (FormField ff: properties) {
            if (ff.getId().startsWith("f_")) {
                EnumFormType enumType = (EnumFormType) ff.getType();
                if (ff.getId().contains("urednici")) {
                    formEnumValues(enumType, 2, username);
                } else if (ff.getId().contains("recenzenti")) {
                    formEnumValuesFilterNO(enumType, 3, username, task.getProcessInstanceId());
                }
            } else if (ff.getId().startsWith("fp_")) {
                EnumFormType enumType = (EnumFormType) ff.getType();
                if (ff.getId().contains("urednici")) {
                    formReviewEnumValues(enumType, 2, username, task.getProcessInstanceId());
                } else if (ff.getId().contains("recenzenti")) {
                    formReviewEnumValues(enumType, 3, username, task.getProcessInstanceId());
                } else if (ff.getId().contains("naucneOblasti")) {
                    formReviewEnumValuesNO(enumType, task.getProcessInstanceId());
                }
            }
        }
    }

    private void formReviewEnumValuesNO(EnumFormType enumType, String pid) {
        String username = (String) runtimeService.getVariable(pid,"usernameForValidation");
        User u = _userRepo.findByUsername(username).get();

        for (NaucnaOblast no: u.getNaucneOblastiUser()) {
            enumType.getValues().put(no.getId(),
                    no.getNaziv());
        }

    }

    private void formReviewEnumValues(EnumFormType enumType, int role, String username, String pid) {
        int casopisId = (int) runtimeService.getVariable(pid, "casopisId");
        Casopis c = _casopisRepo.findById(casopisId).get();
        List<User> users = new ArrayList<>();

        if (role == 2) {
            users = c.getUrednici();
        } else if (role == 3) {
            users = c.getRecenzenti();
        }

        for (User u: users) {
            enumType.getValues().put(u.getUsername(),
                    u.getFirstname() + " " + u.getLastname());
        }

    }

    public void submitTaskForm(List<FormSubmissionDTO> dto, String taskId, String identifier) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        runtimeService.setVariable(processInstanceId, identifier, dto);
        formService.submitTaskForm(taskId, Mapper.mapListToDto(dto));
    }

    private void formEnumValues(EnumFormType enumType, int role, String skipUsername) {
        for (User u: _userRepo.findAllByAuthorityId(role)) {
            if (!u.getUsername().equals(skipUsername)) {
                System.out.println("DODAT: " + u.getUsername());
                enumType.getValues().put(u.getUsername(),
                        u.getFirstname() + " " + u.getLastname());
            }
        }
    }

    private void formEnumValuesFilterNO(EnumFormType enumType, int role, String skipUsername, String pid) {
        int casopisId = (int) runtimeService.getVariable(pid, "casopisId");

        Casopis c = _casopisRepo.findById(casopisId).get();
        List<User> users = _userRepo.findAllByAuthorityId(role);
        for (User u: users) {
            if (!u.getUsername().equals(skipUsername)) {
                for (NaucnaOblast noUser: u.getNaucneOblastiUser()) {
                    for (NaucnaOblast no: c.getNaucneOblasti()) {
                        if (noUser.getId().equals(no.getId())) {

                            System.out.println("DODAT: " + u.getUsername());
                            enumType.getValues().put(u.getUsername(),
                                    u.getFirstname() + " " + u.getLastname());
                        }
                    }
                }

            }
        }
    }



}
