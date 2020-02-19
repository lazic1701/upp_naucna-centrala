package org.milan.naucnacentrala.model.dto;

import org.milan.naucnacentrala.model.Casopis;
import org.milan.naucnacentrala.model.enums.Enums;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CasopisDTO {

    private int id;
    private String naziv;
    private String issn;
    private int sellerId;
    private boolean isRegistered;
    private Enums.PaymentType nacinNaplate;
    private boolean active;
    private List<NaucnaOblastDTO> naucneOblasti;

    public CasopisDTO(int id, String naziv, String issn, int sellerId, boolean isRegistered, Enums.PaymentType nacinNaplate, boolean active, List<NaucnaOblastDTO> naucneOblasti) {
        this.id = id;
        this.naziv = naziv;
        this.issn = issn;
        this.sellerId = sellerId;
        this.isRegistered = isRegistered;
        this.nacinNaplate = nacinNaplate;
        this.active = active;
        this.naucneOblasti = naucneOblasti;
    }

    public CasopisDTO() {
    }

    public static CasopisDTO formDto(Casopis c) {
        if (c == null) {
            return new CasopisDTO();
        } else {
            List<NaucnaOblastDTO> noDTO = new ArrayList<>();
            noDTO.addAll(c.getNaucneOblasti().stream().map(no -> NaucnaOblastDTO.formDto(no)).collect(Collectors.toList()));
            CasopisDTO cDTO = new CasopisDTO(c.getId(), c.getNaziv(), c.getIssn(), c.getSellerId() != null ?
                    c.getSellerId() : 0,
             c.isRegistered(), c.getNacinNaplate(),
                    c.isActive(), noDTO);
            return cDTO;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getIssn() {
        return issn;
    }

    public void setIssn(String issn) {
        this.issn = issn;
    }

    public Enums.PaymentType getNacinNaplate() {
        return nacinNaplate;
    }

    public void setNacinNaplate(Enums.PaymentType nacinNaplate) {
        this.nacinNaplate = nacinNaplate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public boolean isRegistered() {
        return isRegistered;
    }

    public void setRegistered(boolean registered) {
        isRegistered = registered;
    }
}
