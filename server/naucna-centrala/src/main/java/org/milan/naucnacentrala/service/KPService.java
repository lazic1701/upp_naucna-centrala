package org.milan.naucnacentrala.service;

import org.milan.naucnacentrala.client.RegistrationClient;
import org.milan.naucnacentrala.model.Casopis;
import org.milan.naucnacentrala.model.dto.CasopisDTO;
import org.milan.naucnacentrala.model.dto.KPRegistrationDTO;
import org.milan.naucnacentrala.repository.ICasopisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KPService {
    
    @Autowired
    ICasopisRepository _casopisRepo;

    @Autowired
    RegistrationClient registrationClient;

    private final String REG_STATUS_CALLBACK_URL = "https://localhost:8602/api/casopisi/registration/status";

    public KPRegistrationDTO initRegistration(CasopisDTO casopisDTO) {

        Casopis c = _casopisRepo.findById(casopisDTO.getId()).get();

        // ako postoji seller id za tu instancu magazina, znaci da vec postoji registracija, uspesna ili neuspesna
        if (c.getSellerId() != null && c.getSellerId() != 0) {
            return reviewRegistration(c);
        }

        KPRegistrationDTO kprDTO = new KPRegistrationDTO();
        kprDTO.setRegistrationStatusCallbackUrl(this.REG_STATUS_CALLBACK_URL);

        kprDTO = registrationClient.initRegistration(kprDTO);

        c.setSellerId(kprDTO.getSellerId());

        _casopisRepo.save(c);

        return kprDTO;
    }

    public void changeRegistrationStatus(KPRegistrationDTO kprDTO) {
        Casopis c = _casopisRepo.findBySellerId(kprDTO.getSellerId()).get();
        // isStatus lmao fucking shit but who cares
        c.setRegistered(kprDTO.isStatus());
        _casopisRepo.save(c);
        System.out.println("Casopis: " + c.getId() + " registration success: " + c.isRegistered());

    }

    public KPRegistrationDTO reviewRegistrationSeller(CasopisDTO casopisDTO) {
        Casopis c = _casopisRepo.findById(casopisDTO.getId()).get();
        return reviewRegistration(c);
    }

    private KPRegistrationDTO reviewRegistration(Casopis c) {
        KPRegistrationDTO kprDTO = new KPRegistrationDTO();
        kprDTO.setRegistrationStatusCallbackUrl(this.REG_STATUS_CALLBACK_URL);
        kprDTO.setSellerId(c.getSellerId());
        kprDTO = registrationClient.reviewRegistration(kprDTO);
        return kprDTO;
    }
}
