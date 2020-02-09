package org.milan.naucnacentrala.model.dto;

import org.milan.naucnacentrala.model.Casopis;
import org.milan.naucnacentrala.model.enums.Enums;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class CasopisDTO {

    private int id;
    private String naziv;
    private String issn;
    private Enums.PaymentType nacinNaplate;
    private boolean active;

    public CasopisDTO(int id, String naziv, String issn, Enums.PaymentType nacinNaplate, boolean active) {
        this.id = id;
        this.naziv = naziv;
        this.issn = issn;
        this.nacinNaplate = nacinNaplate;
        this.active = active;
    }

    public CasopisDTO() {
    }

    public static CasopisDTO formDto(Casopis c) {
        if (c == null) {
            return new CasopisDTO();
        } else {
            CasopisDTO cDTO = new CasopisDTO(c.getId(), c.getNaziv(), c.getIssn(), c.getNacinNaplate(), c.isActive());
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
}
