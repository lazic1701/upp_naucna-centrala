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
import org.milan.naucnacentrala.model.dto.UserDTO;
import org.milan.naucnacentrala.security.auth.JwtAuthenticationRequest;
import org.milan.naucnacentrala.security.auth.UserTokenState;
import org.milan.naucnacentrala.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<?> login(@RequestBody JwtAuthenticationRequest authenticationRequest, Device device) {

        UserTokenState uts = _userService.login(authenticationRequest, device);
        return ResponseEntity.ok(uts);
    }

    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public ResponseEntity<?> logOut(HttpServletRequest request, HttpServletResponse response){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null)
            new SecurityContextLogoutHandler().logout(request, response, authentication);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/me", method = RequestMethod.GET)
    public ResponseEntity<UserDTO> getLoggedUser(HttpServletRequest request) {
        return new ResponseEntity<>(_userService.getLoggedUser(request), HttpStatus.OK);
    }



}
