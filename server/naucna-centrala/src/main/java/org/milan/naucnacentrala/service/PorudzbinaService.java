package org.milan.naucnacentrala.service;

import org.milan.naucnacentrala.client.OrderClient;
import org.milan.naucnacentrala.model.*;
import org.milan.naucnacentrala.model.dto.*;
import org.milan.naucnacentrala.model.enums.Enums;
import org.milan.naucnacentrala.repository.ICasopisRepository;
import org.milan.naucnacentrala.repository.INaucniRadRepository;
import org.milan.naucnacentrala.repository.IPorudzbinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PorudzbinaService {

    @Autowired
    IPorudzbinaRepository _porudzbinaRepo;

    @Autowired
    ICasopisRepository _casopisRepo;

    @Autowired
    INaucniRadRepository _naucniRadRepo;

    @Autowired
    ClanarinaService _clanarinaService;

    @Autowired
    UserService _userService;

    @Autowired
    OrderClient orderClient;



    public List<PorudzbinaDTO> getAllPorudzbine() {
        return _porudzbinaRepo.findAll().stream().map(p -> PorudzbinaDTO.formDto(p)).collect(Collectors.toList());
    }

    public Object getAllPorudzbineByUser(HttpServletRequest request) {
        User u = _userService.getUserFromRequest(request);
        return _porudzbinaRepo.findAllByKupacId(u.getId()).stream().map(p -> PorudzbinaDTO.formDto(p)).collect(Collectors.toList());
    }


    public InitOrderResponseDTO kupiCasopis(CasopisDTO cDTO, HttpServletRequest request) {
        Casopis c = _casopisRepo.findById(cDTO.getId()).get();

        Porudzbina p = new Porudzbina();
        p.setCasopis(c);
        p = formPorudzbina(p, request, Enums.OrderType.ORDER_CASOPIS);
        p = _porudzbinaRepo.save(p);
        long sellerId = p.getCasopis().getSellerId();

        return orderClient.initOrder(p, sellerId);
    }

    public InitOrderResponseDTO kupiNaucniRad(NaucniRadDTO nrDTO, HttpServletRequest request) {
        NaucniRad nr = _naucniRadRepo.findById(nrDTO.getId()).get();

        Porudzbina p = new Porudzbina();
        p.setNaucniRad(nr);
        p = formPorudzbina(p, request, Enums.OrderType.ORDER_RAD);
        p = _porudzbinaRepo.save(p);
        long sellerId = p.getNaucniRad().getCasopis().getSellerId();

        return orderClient.initOrder(p, sellerId);
    }

    public InitOrderResponseDTO kupiClanarinu(CasopisDTO cDTO, Enums.PaymentType paymentType,
                                              HttpServletRequest request) {
        Casopis c = _casopisRepo.findById(cDTO.getId()).get();
        Clanarina clanarina = _clanarinaService.formClanarinaForCasopis(c, _userService.getUserFromRequest(request),
                paymentType);

        Porudzbina p = new Porudzbina();
        p.setClanarina(clanarina);
        p = formPorudzbina(p, request, Enums.OrderType.ORDER_SUBSCRIPTION);
        p = _porudzbinaRepo.save(p);

        long sellerId = c.getSellerId();
        return orderClient.initOrder(p, sellerId);
    }


    public Porudzbina formPorudzbina(Porudzbina p, HttpServletRequest request, Enums.OrderType orderType) {

        p.setKupac(_userService.getUserFromRequest(request));

        if (orderType == Enums.OrderType.ORDER_RAD) {
            p.setCena(p.getNaucniRad().getCena());
        } else if (orderType == Enums.OrderType.ORDER_CASOPIS) {
            p.setCena(izracunajCenu(p.getCasopis()));
        } else if (orderType == Enums.OrderType.ORDER_SUBSCRIPTION) {
            p.setCena(izracunajCenu(p.getClanarina().getCasopis()));
        }

        p.setStatusPorudzbine(Enums.OrderStatus.PENDING);
        p.setTipPorudzbine(orderType);
        p.setDatum(new Date(System.currentTimeMillis()));

        return p;
    }

    private double izracunajCenu(Casopis casopis) {
        return 40;

//        double amount = 0;
//        for (NaucniRad nr: casopis.getNaucniRadovi()) {
//            amount = amount + nr.getCena();
//        }
//        amount = amount * 0.95;
//        return Math.round(amount * 100.0) / 100.0;
    }

    public void finalizeOrder(FinalizeOrderDTO foDTO) {
        Porudzbina p = _porudzbinaRepo.findById(foDTO.getNcOrderId()).get();
        p.setStatusPorudzbine(foDTO.getOrderStatus());

        if (p.getTipPorudzbine() == Enums.OrderType.ORDER_SUBSCRIPTION) {
            p.getClanarina().setStartDate(new Date(System.currentTimeMillis()));
            p.getClanarina().setEndDate(foDTO.getFinalDate());

        }
        _porudzbinaRepo.save(p);

    }


}
