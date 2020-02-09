package org.milan.naucnacentrala.service;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.milan.naucnacentrala.exception.BusinessException;
import org.milan.naucnacentrala.model.Casopis;
import org.milan.naucnacentrala.model.NaucnaOblast;
import org.milan.naucnacentrala.model.User;
import org.milan.naucnacentrala.model.dto.CasopisDTO;
import org.milan.naucnacentrala.model.dto.FormSubmissionDTO;
import org.milan.naucnacentrala.model.enums.Enums;
import org.milan.naucnacentrala.repository.ICasopisRepository;
import org.milan.naucnacentrala.repository.INaucnaOblastRepository;
import org.milan.naucnacentrala.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CasopisService {

    private final String KRCAS_PROCESS_INSTANCE_ID = "Process_Kreiranje_Casopisa";

    @Autowired
    ICasopisRepository _casopisRepo;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    INaucnaOblastRepository _noRepo;

    @Autowired
    IUserRepository _userRepo;


    public List<CasopisDTO> getAllCasopisi() {
        return _casopisRepo.findAll().stream().map(c -> CasopisDTO.formDto(c)).collect(Collectors.toList());
    }

    public void initProcessKreiranjeCasopisa() {
        ProcessInstance pi = runtimeService.startProcessInstanceByKey(this.KRCAS_PROCESS_INSTANCE_ID);
    }

    public int createCasopis(List<FormSubmissionDTO> casopisForm, String username) {

        Casopis c = new Casopis();

        c = parseForm(casopisForm, c);


        c.setActive(false);
        c.setGlavniUrednik(_userRepo.findByUsername(username).get());
        return _casopisRepo.save(c).getId();
    }

    private Casopis parseForm(List<FormSubmissionDTO> casopisForm, Casopis c) {
        for (FormSubmissionDTO fs : casopisForm) {

            if (fs.getFieldId().equals("naziv")) {
                c.setNaziv(fs.getFieldValue());
            } else if (fs.getFieldId().equals("issn")) {
                c.setIssn(fs.getFieldValue());
            } else if (fs.getFieldId().equals("nacinNaplate")) {
                if (fs.getFieldValue().equals("naplacivanje_autoru")) {
                    c.setNacinNaplate(Enums.PaymentType.NAPLACIVANJE_AUTORU);
                } else if (fs.getFieldValue().equals("naplacivanje_citaocu")) {
                    c.setNacinNaplate(Enums.PaymentType.NAPLACIVANJE_CITAOCU);
                }
            } else if (fs.getFieldId().equals("naucneOblasti_multiselect")) {
                for (NaucnaOblast no : _noRepo.findAll()) {
                    if (no.getId().equals(fs.getFieldValue())) {
                        c.getNaucneOblasti().add(no);
                    }
                }
            }
        }

        return c;
    }

    public void saveGroupMembers(List<FormSubmissionDTO> casopisForm, int casopisId, String groupId) {


        Casopis c = _casopisRepo.findById(casopisId).get();
        User u = null;

        for (FormSubmissionDTO fs : casopisForm) {

            u = _userRepo.findByUsername(fs.getFieldValue()).get();

            if (groupId.equals("urednici")) {
                c.getUrednici().add(u);
            } else if (groupId.equals("recenzenti")) {
                c.getRecenzenti().add(u);
            }
        }

        _casopisRepo.save(c);
    }


    public void saveCasopisChanges(List<FormSubmissionDTO> casopisForm, int casopisId) {
        Casopis c = _casopisRepo.findById(casopisId).get();
        c = parseForm(casopisForm, c);
        _casopisRepo.save(c);
    }

    public void activateCasopis(int casopisId) {
        Casopis c = _casopisRepo.findById(casopisId).get();
        c.setActive(true);
        _casopisRepo.save(c);
    }

    public void isOpenAccess(List<FormSubmissionDTO> casopisForm, String pid) {

        for (FormSubmissionDTO fs : casopisForm) {
            int casopisId = Integer.valueOf(fs.getFieldValue());
            Casopis c = _casopisRepo.findById(casopisId).get();

            runtimeService.setVariable(pid, "casopisId", c.getId());
            runtimeService.setVariable(pid, "openAccess", c.getNacinNaplate() == Enums.PaymentType.NAPLACIVANJE_AUTORU);
        }

    }
}
