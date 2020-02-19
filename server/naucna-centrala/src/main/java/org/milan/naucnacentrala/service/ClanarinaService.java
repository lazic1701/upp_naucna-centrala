package org.milan.naucnacentrala.service;

import org.milan.naucnacentrala.model.Casopis;
import org.milan.naucnacentrala.model.Clanarina;
import org.milan.naucnacentrala.model.User;
import org.milan.naucnacentrala.model.enums.Enums;
import org.milan.naucnacentrala.repository.ICasopisRepository;
import org.milan.naucnacentrala.repository.IClanarinaRepository;
import org.milan.naucnacentrala.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class ClanarinaService {

    @Autowired
    IClanarinaRepository _clanarinaRepo;

    @Autowired
    IUserRepository _userRepo;

    @Autowired
    ICasopisRepository _casopisRepo;

    public void uplataClanarineMock(int casopisId, String username) {
        Casopis c = _casopisRepo.findById(casopisId).get();
        User u = _userRepo.findByUsername(username).get();

        Clanarina clanarina = new Clanarina();
        clanarina.setCasopis(c);
        clanarina.setUser(u);
        clanarina.setPaymentType(Enums.PaymentType.NAPLACIVANJE_AUTORU);
        clanarina.setStartDate(new Date(System.currentTimeMillis()));

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(System.currentTimeMillis()));
        calendar.add(Calendar.DATE, 30);
        clanarina.setEndDate(calendar.getTime());

        _clanarinaRepo.save(clanarina);

    }

    public Clanarina formClanarinaForCasopis(Casopis casopis, User u, Enums.PaymentType paymentType) {
        Clanarina clanarina = new Clanarina();
        clanarina.setPaymentType(paymentType);
        clanarina.setCasopis(casopis);
        clanarina.setUser(u);
        return _clanarinaRepo.save(clanarina);
    }

}
