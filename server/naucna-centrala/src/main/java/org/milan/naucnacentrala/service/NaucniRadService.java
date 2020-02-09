package org.milan.naucnacentrala.service;

import net.bytebuddy.asm.Advice;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.milan.naucnacentrala.model.Casopis;
import org.milan.naucnacentrala.model.NaucnaOblast;
import org.milan.naucnacentrala.model.NaucniRad;
import org.milan.naucnacentrala.model.User;
import org.milan.naucnacentrala.model.dto.FormSubmissionDTO;
import org.milan.naucnacentrala.model.enums.Enums;
import org.milan.naucnacentrala.repository.ICasopisRepository;
import org.milan.naucnacentrala.repository.INaucnaOblastRepository;
import org.milan.naucnacentrala.repository.INaucniRadRepository;
import org.milan.naucnacentrala.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class NaucniRadService {

    @Autowired
    INaucniRadRepository _nrRepo;

    @Autowired
    ICasopisRepository _casopisRepo;

    @Autowired
    INaucnaOblastRepository _noRepo;

    @Autowired
    IUserRepository _userRepo;

    @Autowired
    UserService _userService;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TasksService _tasksService;

    @Autowired
    TaskService taskService;


    private final String OBJNR_PROCESS_INSTANCE_ID = "Process_Obrade_PT";

    public void initObjavaNR(HttpServletRequest request) {
        ProcessInstance pi = runtimeService.startProcessInstanceByKey(this.OBJNR_PROCESS_INSTANCE_ID);
        Task t = taskService.createTaskQuery().processInstanceId(pi.getId()).list().get(0);
        _tasksService.claimTask(t.getId(), request, "assignedUser_ROLE_AUTOR");
    }


    public int saveNaucniRad(List<FormSubmissionDTO> nrForm, int casopisId, String username) {

        Casopis c = _casopisRepo.findById(casopisId).get();
        User u = _userRepo.findByUsername(username).get();

        NaucniRad nr = new NaucniRad();
        nr = parseForm(nrForm, nr);
        nr.setCasopis(c);
        nr.setAutor(u);

        nr = _nrRepo.save(nr);

        return nr.getId();
    }

    private NaucniRad parseForm(List<FormSubmissionDTO> nrForm, NaucniRad nr) {
        for (FormSubmissionDTO fs : nrForm) {

            if (fs.getFieldId().equals("naslov")) {
                nr.setNaslov(fs.getFieldValue());
            } else if (fs.getFieldId().equals("apstrakt")) {
                nr.setApstrakt(fs.getFieldValue());
            } else if (fs.getFieldId().equals("kljucniPojmovi")) {
                nr.setKljucniPojmovi(fs.getFieldValue());
            } else if (fs.getFieldId().equals("f_naucnaOblast")) {
                NaucnaOblast no = _noRepo.findById(fs.getFieldValue()).get();
                nr.setNaucnaOblast(no);
            }
        }

        return nr;
    }
}
