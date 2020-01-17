package org.milan.naucnacentrala.controller;

import org.camunda.bpm.engine.task.Task;
import org.milan.naucnacentrala.model.dto.FormFieldsDTO;
import org.milan.naucnacentrala.model.dto.FormSubmissionDTO;
import org.milan.naucnacentrala.model.dto.TaskDTO;
import org.milan.naucnacentrala.service.TasksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/api/tasks")
public class TaskController {

    @Autowired
    TasksService _taskService;

    @GetMapping(path = "/{id}")
    public @ResponseBody
    ResponseEntity<TaskDTO> getTask(@PathVariable String id, HttpServletRequest request) {
        return new ResponseEntity(_taskService.getTask(id, request), HttpStatus.OK);
    }

    @GetMapping(path = "/groups/{groupId}")
    public @ResponseBody
    ResponseEntity<List<TaskDTO>> getGroupTasks(@PathVariable String groupId) {
        return new ResponseEntity(_taskService.getGroupTasks(groupId), HttpStatus.OK);
    }

    @GetMapping(path = "/my")
    public @ResponseBody
    ResponseEntity<List<TaskDTO>> getUserTasks(HttpServletRequest request) {
        return new ResponseEntity(_taskService.getUserTasks(request), HttpStatus.OK);
    }

    @PostMapping(path = "/claim/{taskId}")
    public @ResponseBody
    ResponseEntity claim(@PathVariable String taskId, HttpServletRequest request) {
        _taskService.claimTask(taskId, request);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(path = "/claim/{taskId}/{variableName}")
    public @ResponseBody
    ResponseEntity claimSetUsernameVariable(@PathVariable String taskId,
                                            @PathVariable String variableName, HttpServletRequest request) {
        _taskService.claimTask(taskId, request, variableName);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(path = "/form/{taskId}")
    public @ResponseBody
    ResponseEntity<FormFieldsDTO> getForm(@PathVariable String taskId) {
        return new ResponseEntity(_taskService.getTaskForm(taskId), HttpStatus.OK);
    }

    @PostMapping(path = "/form/{taskId}/{identifier}")
    public @ResponseBody
    ResponseEntity submit(@RequestBody List<FormSubmissionDTO> dto, @PathVariable String taskId,
                          @PathVariable String identifier) {
        _taskService.submitTaskForm(dto, taskId, identifier);
        return new ResponseEntity(HttpStatus.OK);
    }
}
