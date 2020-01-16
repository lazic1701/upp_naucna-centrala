package org.milan.naucnacentrala.service.camunda;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.milan.naucnacentrala.model.User;
import org.milan.naucnacentrala.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class SendVerificationEmail implements JavaDelegate {

    @Autowired
    private IUserRepository _userRepo;

    @Autowired
    private Environment environment;

    @Autowired
    private JavaMailSender jmSender;

    @Autowired
    RuntimeService runtimeService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        String username = (String) delegateExecution.getVariable("usernameForValidation");
        User u = _userRepo.findByUsername(username).get();

        sendEmail(u, delegateExecution.getProcessInstanceId());

        runtimeService.setVariable(delegateExecution.getProcessInstanceId(), "activated", false);

    }

    private void sendEmail(User u, String processInstanceId) {

        SimpleMailMessage mail = new SimpleMailMessage();

        mail.setTo(u.getEmail());
        mail.setFrom(environment.getProperty("spring.mail.username"));
        mail.setSubject("Verifikujte Vaš nalog na Naučnoj centrali!");

        String username_b64 = Base64.getEncoder().encodeToString(u.getUsername().getBytes());
        String processId_b64 = Base64.getEncoder().encodeToString(processInstanceId.getBytes());

        String endpointUrl = "http://localhost:4200/verification/" + processId_b64 + "/" + username_b64;

        mail.setText("Poštovanje " + u.getFirstname() + "!," +
                "\n" +
                "\n Kako biste verifikovali Vaš nalog i uspešno završili registraciju na sistem Naučne centrale, " +
                "kliknite na sledeći link: " + endpointUrl);

        jmSender.send(mail);
    }
}
