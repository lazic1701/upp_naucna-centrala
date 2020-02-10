package org.milan.naucnacentrala.handler;


import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.impl.form.type.EnumFormType;
import org.milan.naucnacentrala.model.User;
import org.milan.naucnacentrala.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UcitajPreostaleRecenzenteZaIzbor implements TaskListener {

    @Autowired
    FormService formService;

    @Autowired
    UserService _userService;


    @Override
    public void notify(DelegateTask delegateTask) {

        int nrId = (int) delegateTask.getExecution().getVariable("nrId");

        List<User> recenzentiFilter = _userService.getRecenzentiFilter(nrId);
        List<String> recenzentiPrethodnoOdabrani = (ArrayList) delegateTask.getVariable("recenzentiZaRecenziju");

        for (User r: new ArrayList<>(recenzentiFilter)) {
            for (String rUsername: recenzentiPrethodnoOdabrani) {
                if (r.getUsername().equals(rUsername)) {
                    recenzentiFilter.remove(r);
                }
            }
        }

        List<FormField> fields = formService.getTaskFormData(delegateTask.getId()).getFormFields();

        for(FormField field : fields){
            if(field.getId().equals("noviRecenzent")){
                EnumFormType enumFormType = (EnumFormType) field.getType();
                for(User r: recenzentiFilter){
                    enumFormType.getValues().put(r.getUsername(),
                            r.getFirstname() + " " + r.getLastname());
                }
            }
        }


    }
}