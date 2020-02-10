package org.milan.naucnacentrala.handler;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.impl.form.type.EnumFormType;
import org.milan.naucnacentrala.model.NaucniRad;
import org.milan.naucnacentrala.model.Recenzija;
import org.milan.naucnacentrala.repository.INaucniRadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnalizaRecenzija implements TaskListener {

    @Autowired
    INaucniRadRepository _nrRepo;

    @Autowired
    FormService formService;

    @Override
    public void notify(DelegateTask delegateTask) {

        int nrId = (int) delegateTask.getExecution().getVariable("nrId");

        NaucniRad nr = _nrRepo.findById(nrId).get();

        List<FormField> fields = formService.getTaskFormData(delegateTask.getId()).getFormFields();

        int cnt = 0;

        for(FormField field : fields){

            if(field.getId().equals("fp_odluke")){
                for(Recenzija rec: nr.getRecenzije()){
                    EnumFormType enumFormType = (EnumFormType) field.getType();
                    enumFormType.getValues().put(String.valueOf(cnt),
                            rec.getOdluka().toString());

                    cnt++;
                }

            } else if (field.getId().equals("fp_komentari")) {
                for(Recenzija rec: nr.getRecenzije()){
                    EnumFormType enumFormType = (EnumFormType) field.getType();
                    enumFormType.getValues().put(String.valueOf(cnt),
                            rec.getTekst());

                    cnt++;
                }
            }


        }


    }
}
