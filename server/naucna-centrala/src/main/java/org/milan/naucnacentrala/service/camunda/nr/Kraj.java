package org.milan.naucnacentrala.service.camunda.nr;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.milan.naucnacentrala.model.NaucniRad;
import org.milan.naucnacentrala.model.dto.FormSubmissionDTO;
import org.milan.naucnacentrala.model.enums.Enums;
import org.milan.naucnacentrala.model_es.NaucniRadES;
import org.milan.naucnacentrala.repository.INaucniRadRepository;
import org.milan.naucnacentrala.service.NaucniRadService;
import org.milan.naucnacentrala.service_es.NaucniRadServiceES;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Kraj implements JavaDelegate {

    @Autowired
    INaucniRadRepository _nrRepo;


    @Autowired
    NaucniRadService naucniRadService;

    @Autowired
    NaucniRadServiceES naucniRadServiceES;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        int nrId = (int) delegateExecution.getVariable("nrId");

        NaucniRad nr = _nrRepo.findById(nrId).get();

        List<FormSubmissionDTO> form =
                (List<FormSubmissionDTO>)delegateExecution.getVariable("submittedForm");

        nr.setStatus(Enums.NaucniRadStatus.ODOBREN);
        nr = _nrRepo.save(nr);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        NaucniRadES nrES = naucniRadServiceES.save(nr, naucniRadService.getPDF(nr.getId()));
//
//        System.out.println("----------------------------------------------------------------------");
//        System.out.println("Indeksiran: ");
//        System.out.println(gson.toJson(nrES));
//        System.out.println("----------------------------------------------------------------------");

    }
}
