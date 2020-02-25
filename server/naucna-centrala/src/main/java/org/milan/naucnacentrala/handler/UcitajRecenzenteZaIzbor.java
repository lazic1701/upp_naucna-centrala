package org.milan.naucnacentrala.handler;

import com.google.maps.errors.ApiException;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.impl.form.type.EnumFormType;
import org.milan.naucnacentrala.model.User;
import org.milan.naucnacentrala.model_es.UserES;
import org.milan.naucnacentrala.repository.IUserRepository;
import org.milan.naucnacentrala.repository_es.UserRepositoryES;
import org.milan.naucnacentrala.service.UserService;
import org.milan.naucnacentrala.service_es.UserServiceES;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UcitajRecenzenteZaIzbor implements TaskListener {

    @Autowired
    FormService formService;

    @Autowired
    UserService _userService;


    @Autowired
    UserServiceES _userServiceES;

    @Autowired
    IUserRepository userRepository;


    @Override
    public void notify(DelegateTask delegateTask) {

        List<FormField> fields = formService.getTaskFormData(delegateTask.getId()).getFormFields();
        int nrId = (int) delegateTask.getVariable("nrId");

        Iterable<UserES> recenzentiES = null;
        try {
            recenzentiES = _userServiceES.filterRecenzenti(nrId);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<User> recenzenti = convertES(recenzentiES);

        for(FormField field : fields){
            if(field.getId().equals("recenzentiZaIzbor_multiselect")){
                EnumFormType enumFormType = (EnumFormType) field.getType();
                for(User r: recenzenti){
                    enumFormType.getValues().put(r.getUsername(),
                            r.getFirstname() + " " + r.getLastname() + ", " + r.getCity());
                }
            }
        }


    }

    private List<User> convertES(Iterable<UserES> recenzentiES) {
        List<User> retVal = new ArrayList<>();
        for (UserES uES: recenzentiES) {

            retVal.add(userRepository.findById(uES.getId()).get());
        }

        return retVal;
    }
}
