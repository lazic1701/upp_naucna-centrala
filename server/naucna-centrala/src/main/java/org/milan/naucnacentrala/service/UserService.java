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
import org.milan.naucnacentrala.model.Casopis;
import org.milan.naucnacentrala.model.Clanarina;
import org.milan.naucnacentrala.model.User;
import org.milan.naucnacentrala.model.dto.FormFieldsDTO;
import org.milan.naucnacentrala.model.dto.FormSubmissionDTO;
import org.milan.naucnacentrala.model.dto.Mapper;
import org.milan.naucnacentrala.model.dto.UserDTO;
import org.milan.naucnacentrala.model.enums.Enums;
import org.milan.naucnacentrala.repository.IAuthorityRepository;
import org.milan.naucnacentrala.repository.ICasopisRepository;
import org.milan.naucnacentrala.repository.IClanarinaRepository;
import org.milan.naucnacentrala.repository.IUserRepository;
import org.milan.naucnacentrala.security.TokenUtils;
import org.milan.naucnacentrala.security.auth.JwtAuthenticationRequest;
import org.milan.naucnacentrala.security.auth.UserTokenState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

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

    @Autowired
    ICasopisRepository _casopisRepo;


    @Autowired
    IClanarinaRepository _clanarinaRepo;

    @Autowired
    TokenUtils tokenUtils;

    @Autowired
    IAuthorityRepository _authorityRepository;

    @Autowired
    private AuthenticationManager manager;

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

    public UserTokenState login(JwtAuthenticationRequest authenticationRequest, Device device) {

        final Authentication authentication = manager
                .authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()));


        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = (User) authentication.getPrincipal();

        if(user == null) {
            throw new UsernameNotFoundException("Bad credentials.");
        }

        String jwt = tokenUtils.generateToken(user.getUsername(), device);
        return new UserTokenState(user.getId(), user.getAuthorities().get(0).getName(), jwt, tokenUtils.EXPIRES_IN);
    }


    public int createUser(User u) {
        return _userRepo.save(u).getId();
    }

    public void createCamundaUserAndSetActive(String username) {
        User u = _userRepo.findByUsername(username).get();

        org.camunda.bpm.engine.identity.User camundaUser = identityService.newUser(u.getUsername());
        camundaUser.setEmail(u.getEmail());
        camundaUser.setFirstName(u.getFirstname());
        camundaUser.setLastName(u.getLastname());
        camundaUser.setPassword(u.getPassword());

        identityService.saveUser(camundaUser);
        identityService.createMembership(u.getUsername(), "autori");

        u.setActive(true);
        _userRepo.save(u);
    }

    public void verify(String processInstanceId_b64, String username_b64) {

        String username = new String(Base64.getDecoder().decode(username_b64));
        String processInstanceId = new String(Base64.getDecoder().decode(processInstanceId_b64));

        User u = _userRepo.findByUsername(username).get();

        runtimeService.setVariable(processInstanceId, "activated", true);
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

    public void assignRecenzentRole(String username) {
        org.camunda.bpm.engine.identity.User userCamunda =
                identityService.createUserQuery().userId(username).singleResult();
        if (userCamunda == null) {
            throw new BusinessException("Error! Cannot find user in camunda DB");
        }

        identityService.deleteMembership(username, "autori");
        identityService.createMembership(username, "recenzenti");

        User u = _userRepo.findByUsername(username).get();

        u.getAuthorities().clear();
        u.getAuthorities().add(_authorityRepository.findOneByName(Enums.UserRole.ROLE_RECENZENT.toString()));

        _userRepo.save(u);

    }

    public UserDTO getLoggedUser(HttpServletRequest request) {

        String username = getUsernameFromRequest(request);

        return UserDTO.formDto(_userRepo.findByUsername(username).get());
    }

    public String getUsernameFromRequest(HttpServletRequest request) {
        return tokenUtils.getUsernameFromToken(tokenUtils.getToken(request));
    }

    public void proveraClanarine(String username, int casopisId, String pid) {

        User u = _userRepo.findByUsername(username).get();

        List<Clanarina> clanarine = _clanarinaRepo.findAllByUserIdAndCasopisId(u.getId(), casopisId);

        Date now = new Date(System.currentTimeMillis());

        for (Clanarina c: clanarine) {

            if (c.getPaymentType() != Enums.PaymentType.NAPLACIVANJE_AUTORU) {
                continue;
            }

            if (c.getStartDate().compareTo(now) < 0 && c.getEndDate().compareTo(now) > 0) {
                runtimeService.setVariable(pid, "clanarinaPostoji", true);
                return;
            }
        }

        runtimeService.setVariable(pid, "clanarinaPostoji", false);
    }
}
