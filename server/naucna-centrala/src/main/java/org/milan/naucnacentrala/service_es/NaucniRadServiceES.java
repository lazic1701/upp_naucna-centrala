package org.milan.naucnacentrala.service_es;

import org.milan.naucnacentrala.model.NaucniRad;
import org.milan.naucnacentrala.model_es.CasopisES;
import org.milan.naucnacentrala.model_es.NaucniRadES;
import org.milan.naucnacentrala.model_es.UserES;
import org.milan.naucnacentrala.repository_es.NaucniRadRepositoryES;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.stream.Collectors;

@Service
public class NaucniRadServiceES {

    @Autowired
    NaucniRadRepositoryES naucniRadRepositoryES;

    @Autowired
    NaucniRadServiceES naucniRadServiceES;

    @Autowired
    UserServiceES userServiceES;


    public NaucniRadES getNaucniRadES(int id) {
        return naucniRadRepositoryES.findById(id).get();
    }

    public NaucniRadES save(NaucniRad nr, File pdf) {
        return naucniRadRepositoryES.save(mapNaucniRadtoNaucniRadES(nr));
    }

    public NaucniRadES mapNaucniRadtoNaucniRadES(NaucniRad nr) {

        NaucniRadES nrES = new NaucniRadES();

        nrES.setId(nr.getId());
        nrES.setNaslov(nr.getNaslov());
        nrES.setKljucniPojmovi(nr.getKljucniPojmovi());
        nrES.setCasopis(new CasopisES(nr.getCasopis().getId(), nr.getCasopis().getNaziv()));
        nrES.setAutor(userServiceES.getUser(nr.getAutor()));

        return  nrES;
    }
}
