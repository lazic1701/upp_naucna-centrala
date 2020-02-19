package org.milan.naucnacentrala.model.dto;

import org.milan.naucnacentrala.model.NaucnaOblast;

import javax.persistence.Column;

public class NaucnaOblastDTO {

    private String id;
    private String naziv;

    public NaucnaOblastDTO() {
    }


    public NaucnaOblastDTO(String id, String naziv) {
        this.id = id;
        this.naziv = naziv;
    }

    public static NaucnaOblastDTO formDto(NaucnaOblast no) {
        if (no == null) {
            return new NaucnaOblastDTO();
        } else {
            return new NaucnaOblastDTO(no.getId(), no.getNaziv());
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }
}
