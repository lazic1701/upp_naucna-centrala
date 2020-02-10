package org.milan.naucnacentrala.service;

import net.bytebuddy.asm.Advice;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.milan.naucnacentrala.model.*;
import org.milan.naucnacentrala.model.dto.FormSubmissionDTO;
import org.milan.naucnacentrala.model.enums.Enums;
import org.milan.naucnacentrala.repository.ICasopisRepository;
import org.milan.naucnacentrala.repository.INaucnaOblastRepository;
import org.milan.naucnacentrala.repository.INaucniRadRepository;
import org.milan.naucnacentrala.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    @Autowired
    ServletContext context;


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
        nr.setStatus(Enums.NaucniRadStatus.PRIJAVLJEN);

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

    public void saveKoautor(int nrId, List<FormSubmissionDTO> nrForm) {

        NaucniRad nr = _nrRepo.findById(nrId).get();

        Koautor ka = new Koautor();

        for (FormSubmissionDTO fs : nrForm) {


            if (fs.getFieldId().equals("imeKA")) {
                ka.setIme(fs.getFieldValue());
            } else if (fs.getFieldId().equals("emailKA")) {
                ka.setEmail(fs.getFieldValue());
            } else if (fs.getFieldId().equals("gradKA")) {
                ka.setGrad(fs.getFieldValue());
            } else if (fs.getFieldId().equals("drzavaKA")) {
                ka.setDrzava(fs.getFieldValue());
            }

        }

        ka.setNaucniRad(nr);
        nr.getKoautori().add(ka);
        _nrRepo.save(nr);


    }


    public void savePDF(MultipartFile file, String pid) throws IOException {
        int id = (int) runtimeService.getVariable(pid, "nrId");
        NaucniRad nr = _nrRepo.findById(id).get();
        String path = "src/main/resources/uploads";

        File f = new File(path + "/" + nr.getId() + "_" + nr.getNaslov() + ".pdf");
        f.createNewFile();
        String absolutePath = f.getAbsolutePath();

        Path filepath = Paths.get(absolutePath);
        System.out.println("FILE PATH: " + filepath.toString());

        try (OutputStream os = Files.newOutputStream(filepath)) {
            os.write(file.getBytes());
            nr.setFilePath(filepath.toString());
            _nrRepo.save(nr);
        } catch (IOException ex) {
            ex.printStackTrace();

        }

    }

    public File getPDF(String pid) throws FileNotFoundException {
        int id = (int) runtimeService.getVariable(pid, "nrId");
        NaucniRad nr = _nrRepo.findById(id).get();

        return ResourceUtils.getFile(
                nr.getFilePath());
    }

    public void sacuvajRecenzije(List<FormSubmissionDTO> form, int nrId, String recUsername) {

        NaucniRad nr = _nrRepo.findById(nrId).get();
        User r = _userRepo.findByUsername(recUsername).get();

        Recenzija rec = new Recenzija();

        rec.setNaucniRad(nr);
        rec.setRecenzent(r);


        for (FormSubmissionDTO fs : form) {
            if (fs.getFieldId().equals("odluka")) {
                if (fs.getFieldValue().equals("o1")) {
                    rec.setOdluka(Enums.OdlukaRecenzenta.PRIHVATITI);
                } else if (fs.getFieldValue().equals("o2")) {
                    rec.setOdluka(Enums.OdlukaRecenzenta.ODBITI);
                } else if (fs.getFieldValue().equals("o3")) {
                    rec.setOdluka(Enums.OdlukaRecenzenta.PRIHVATITI_UZ_MANJE_ISPRAVKE);
                } else if (fs.getFieldValue().equals("o4")) {
                    rec.setOdluka(Enums.OdlukaRecenzenta.USLOVNO_PRIHVATITI_UZ_VECE_ISPRAVKE);
                }
            } else if (fs.getFieldId().equals("komentarRec")) {
                rec.setTekst(fs.getFieldValue());
            }
        }

        nr.getRecenzije().add(rec);
        _nrRepo.save(nr);
    }
}

