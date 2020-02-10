package org.milan.naucnacentrala.service.camunda.nr;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.milan.naucnacentrala.model.NaucniRad;
import org.milan.naucnacentrala.model.User;
import org.milan.naucnacentrala.repository.INaucniRadRepository;
import org.milan.naucnacentrala.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailBiranjeNovogRecenzenta implements JavaDelegate {


    @Autowired
    INaucniRadRepository _naucniRadRepo;


    @Autowired
    IUserRepository _userRepo;

    @Autowired
    private Environment environment;

    @Autowired
    private JavaMailSender jmSender;


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        System.out.println("[EmailBiranjeNovogRecenzenta]: start");

        String assignedUser_UREDNIK_NO = (String) delegateExecution.getVariable("assignedUser_UREDNIK_NO");

        User urednikNo = _userRepo.findByUsername(assignedUser_UREDNIK_NO).get();

        sendEmail(urednikNo, "Izaberite novog recenzenta.");

    }


    private void sendEmail(User u, String messageBody) {

        SimpleMailMessage mail = new SimpleMailMessage();

        mail.setTo(u.getEmail());
        System.out.println("Sending mail to: " + u.getEmail());

        mail.setFrom(environment.getProperty("spring.mail.username"));
        mail.setSubject("NC Obaveštenje: Potrebno je izabrati novog recenzenta");


        mail.setText("Poštovani,"+
                "\n" + messageBody);

        jmSender.send(mail);
    }
}
