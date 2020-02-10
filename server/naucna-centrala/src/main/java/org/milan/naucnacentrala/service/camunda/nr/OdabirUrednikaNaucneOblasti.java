package org.milan.naucnacentrala.service.camunda.nr;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.milan.naucnacentrala.model.User;
import org.milan.naucnacentrala.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class OdabirUrednikaNaucneOblasti implements JavaDelegate {

    @Autowired
    UserService _userService;

    @Autowired
    private Environment environment;

    @Autowired
    private JavaMailSender jmSender;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("[OdabirUrednikaNaucneOblasti]: start");

        int nrId = (int) delegateExecution.getVariable("nrId");

        User urednikNO = _userService.odabirUrednikaNO(nrId);

        delegateExecution.setVariable("assignedUser_UREDNIK_NO", urednikNO.getUsername());

        System.out.println("AssignedUser_UREDNIK_NO: " + urednikNO.getUsername());

        sendEmail(urednikNO, "Odaberite recenzente.");


    }
    private void sendEmail(User u, String messageBody) {

        SimpleMailMessage mail = new SimpleMailMessage();

        mail.setTo(u.getEmail());

        mail.setFrom(environment.getProperty("spring.mail.username"));
        mail.setSubject("NC Obaveštenje: Dodeljen Vam je zadatak da izaberete recenzente");


        mail.setText("Poštovani,"+
                "\n" + messageBody);

        jmSender.send(mail);
    }

}
