package org.milan.naucnacentrala.controller;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.milan.naucnacentrala.exception.BusinessException;
import org.milan.naucnacentrala.model.dto.FormFieldsDTO;
import org.milan.naucnacentrala.model.dto.FormSubmissionDTO;
import org.milan.naucnacentrala.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;

import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {


    @Autowired
    private UserService _userService;

    @GetMapping(path = "/registration")
    public @ResponseBody
    FormFieldsDTO getRegistrationForm() {
        return _userService.getRegistrationForm();
    }

    @PostMapping(path = "/registration/{taskId}")
    public @ResponseBody
    ResponseEntity registerUser(@RequestBody List<FormSubmissionDTO> dto, @PathVariable String taskId) {

        _userService.registerUser(dto, taskId);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/verification/{processId}/{username}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity verifyMail(@PathVariable String processId,
                                                 @PathVariable String username) {

        _userService.verify(processId, username);

        return new ResponseEntity(HttpStatus.OK);
    }



}
