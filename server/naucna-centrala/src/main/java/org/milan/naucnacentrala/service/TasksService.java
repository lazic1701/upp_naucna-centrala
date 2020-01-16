package org.milan.naucnacentrala.service;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.task.Task;
import org.milan.naucnacentrala.model.dto.FormFieldsDTO;
import org.milan.naucnacentrala.model.dto.FormSubmissionDTO;
import org.milan.naucnacentrala.model.dto.Mapper;
import org.milan.naucnacentrala.model.dto.TaskDTO;
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

    public List<TaskDTO> getGroupTasks(String groupId) {

        List<TaskDTO> groupTasks = new ArrayList<>();

        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup(groupId).list();
        groupTasks.addAll(tasks
                .stream()
                .map(t -> new TaskDTO(t.getId(), t.getName(), t.getAssignee()))
                .collect(Collectors.toList()));

        return groupTasks;
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

    // TODO maybe implement some kind of authorization
    public FormFieldsDTO getTaskForm(String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        TaskFormData tfd = formService.getTaskFormData(taskId);
        List<FormField> properties = tfd.getFormFields();
        return new FormFieldsDTO(task.getId(), task.getProcessInstanceId(), properties);
    }

    public void submitTaskForm(List<FormSubmissionDTO> dto, String taskId, String identifier) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        runtimeService.setVariable(processInstanceId, identifier, dto);
        formService.submitTaskForm(taskId, Mapper.mapListToDto(dto));
    }


}
