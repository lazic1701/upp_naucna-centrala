package org.milan.naucnacentrala.service.camunda.nr;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.milan.naucnacentrala.model.NaucniRad;
import org.milan.naucnacentrala.model.User;
import org.milan.naucnacentrala.repository.INaucniRadRepository;
import org.milan.naucnacentrala.service.NaucniRadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class EmailAutoruIGlavnogUredniku implements JavaDelegate {

    @Autowired
    INaucniRadRepository _naucniRadRepo;


    @Autowired
    private Environment environment;

    @Autowired
    private JavaMailSender jmSender;


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("[EmailAutoruIGlavnogUredniku]: start");

        int nrId = (int) delegateExecution.getVariable("nrId");

        NaucniRad nr = _naucniRadRepo.findById(nrId).get();

        sendEmail(nr.getAutor(), "Uspešno ste prijavili naučni rad.");
        sendEmail(nr.getCasopis().getGlavniUrednik(), "Prijavljen je novi naučni rad po nazivom \"" + nr.getNaslov()
                + "\".");

        delegateExecution.setVariable("assignedUser_GL_UREDNIK", nr.getCasopis().getGlavniUrednik().getUsername());


    }

    private void sendEmail(User u, String messageBody) {

        SimpleMailMessage mail = new SimpleMailMessage();

        mail.setTo(u.getEmail());

        mail.setFrom(environment.getProperty("spring.mail.username"));
        mail.setSubject("NC Obaveštenje: Prijavljen je novi naučni rad");


        mail.setText("Poštovani,"+
                "\n" + messageBody);

        jmSender.send(mail);
    }
}
