package org.milan.naucnacentrala.service.camunda.nr;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.milan.naucnacentrala.model.NaucniRad;
import org.milan.naucnacentrala.model.User;
import org.milan.naucnacentrala.repository.INaucniRadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailAutoruLoseFormatiran implements JavaDelegate {


    @Autowired
    private Environment environment;

    @Autowired
    private INaucniRadRepository _nrRepo;


    @Autowired
    private JavaMailSender jmSender;


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        System.out.println("[EmailAutoruLoseFormatiran]: start");

        int nrId = (int) delegateExecution.getVariable("nrId");

        NaucniRad nr = _nrRepo.findById(nrId).get();

        String komentarUrednika = (String) delegateExecution.getVariable("komentarUrednika");

        sendEmail(nr.getAutor(),
                "Potrebno je da popravite prijavljeni naučni rad. Komentar urednika je sledeći: \"" + komentarUrednika + "\".");

    }

    private void sendEmail(User u, String messageBody) {

        SimpleMailMessage mail = new SimpleMailMessage();

        mail.setTo(u.getEmail());

        mail.setFrom(environment.getProperty("spring.mail.username"));
        mail.setSubject("NC Obaveštenje: Potrebno je ispraviti naučni rad");


        mail.setText("Poštovani,"+
                "\n" + messageBody);

        jmSender.send(mail);
    }

}
