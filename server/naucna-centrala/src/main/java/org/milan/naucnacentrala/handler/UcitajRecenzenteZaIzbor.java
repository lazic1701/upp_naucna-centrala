package org.milan.naucnacentrala.handler;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.impl.form.type.EnumFormType;
import org.milan.naucnacentrala.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UcitajRecenzenteZaIzbor implements TaskListener {

    @Autowired
    FormService formService;


    @Override
    public void notify(DelegateTask delegateTask) {
        List<User> recenzenti = (ArrayList) delegateTask.getVariable("recenzentiFilter");

        List<FormField> fields = formService.getTaskFormData(delegateTask.getId()).getFormFields();

        for(FormField field : fields){
            if(field.getId().equals("recenzentiZaIzbor_multiselect")){
                EnumFormType enumFormType = (EnumFormType) field.getType();
                for(User r: recenzenti){
                    enumFormType.getValues().put(r.getUsername(),
                            r.getFirstname() + " " + r.getLastname());
                }
            }
        }


    }
}
