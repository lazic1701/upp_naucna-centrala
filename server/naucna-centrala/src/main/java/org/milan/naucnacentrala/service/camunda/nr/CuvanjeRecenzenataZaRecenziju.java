package org.milan.naucnacentrala.service.camunda.nr;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.milan.naucnacentrala.model.User;
import org.milan.naucnacentrala.model.dto.FormSubmissionDTO;
import org.milan.naucnacentrala.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CuvanjeRecenzenataZaRecenziju implements JavaDelegate {

    @Autowired
    IUserRepository _userRepo;


    @Autowired
    private Environment environment;

    @Autowired
    private JavaMailSender jmSender;


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("[CuvanjeRecenzenataZaRecenziju]: start");

        List<String> recenzentiZaRecenziju = new ArrayList<>();


        List<FormSubmissionDTO> form =
                (List<FormSubmissionDTO>)delegateExecution.getVariable("submittedForm");

        for (FormSubmissionDTO fs : form) {
            recenzentiZaRecenziju.add(fs.getFieldValue());
            System.out.println("Dodat: " + fs.getFieldValue());
            User r = _userRepo.findByUsername(fs.getFieldValue()).get();
            sendEmail(r, "Dodeljena Vam je recenzija rada!");

        }

        delegateExecution.setVariable("recenzentiZaRecenziju", recenzentiZaRecenziju);

    }

    private void sendEmail(User u, String messageBody) {

        SimpleMailMessage mail = new SimpleMailMessage();

        mail.setTo(u.getEmail());
        System.out.println("Sending mail to: " + u.getEmail());

        mail.setFrom(environment.getProperty("spring.mail.username"));
        mail.setSubject("NC Obaveštenje: Dodeljena vam je recenzija rada");


        mail.setText("Poštovani,"+
                "\n" + messageBody);

        jmSender.send(mail);
    }
}
