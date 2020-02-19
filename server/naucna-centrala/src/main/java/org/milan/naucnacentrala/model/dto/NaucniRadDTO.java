package org.milan.naucnacentrala.model.dto;

import org.milan.naucnacentrala.model.Casopis;
import org.milan.naucnacentrala.model.NaucnaOblast;
import org.milan.naucnacentrala.model.NaucniRad;
import org.milan.naucnacentrala.model.enums.Enums;

import javax.persistence.*;

public class NaucniRadDTO {

    private int id;
    private String naslov;
    private String apstrakt;
    private String kljucniPojmovi;
    private String filePath;
    private Enums.NaucniRadStatus status;
    private NaucnaOblastDTO naucnaOblast;
    private CasopisDTO casopis;
    private UserDTO autor;
    private double cena;


    public NaucniRadDTO() {
    }


    public NaucniRadDTO(int id, String naslov, String apstrakt, String kljucniPojmovi, String filePath, Enums.NaucniRadStatus status, NaucnaOblastDTO naucnaOblast, CasopisDTO casopis, UserDTO autor, double cena) {
        this.id = id;
        this.naslov = naslov;
        this.apstrakt = apstrakt;
        this.kljucniPojmovi = kljucniPojmovi;
        this.filePath = filePath;
        this.status = status;
        this.naucnaOblast = naucnaOblast;
        this.casopis = casopis;
        this.autor = autor;
        this.cena = cena;
    }

    public static NaucniRadDTO formDto(NaucniRad nr) {
        if (nr == null) {
            return new NaucniRadDTO();
        } else {
            return new NaucniRadDTO(nr.getId(), nr.getNaslov(), nr.getApstrakt(), nr.getKljucniPojmovi(),
                    nr.getFilePath(), nr.getStatus(),
                    NaucnaOblastDTO.formDto(nr.getNaucnaOblast()), CasopisDTO.formDto(nr.getCasopis()),
                    UserDTO.formDto(nr.getAutor()), nr.getCena());
        }
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaslov() {
        return naslov;
    }

    public void setNaslov(String naslov) {
        this.naslov = naslov;
    }

    public String getApstrakt() {
        return apstrakt;
    }

    public void setApstrakt(String apstrakt) {
        this.apstrakt = apstrakt;
    }

    public String getKljucniPojmovi() {
        return kljucniPojmovi;
    }

    public void setKljucniPojmovi(String kljucniPojmovi) {
        this.kljucniPojmovi = kljucniPojmovi;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Enums.NaucniRadStatus getStatus() {
        return status;
    }

    public void setStatus(Enums.NaucniRadStatus status) {
        this.status = status;
    }

    public NaucnaOblastDTO getNaucnaOblast() {
        return naucnaOblast;
    }

    public void setNaucnaOblast(NaucnaOblastDTO naucnaOblast) {
        this.naucnaOblast = naucnaOblast;
    }

    public CasopisDTO getCasopis() {
        return casopis;
    }

    public void setCasopis(CasopisDTO casopis) {
        this.casopis = casopis;
    }

    public UserDTO getAutor() {
        return autor;
    }

    public void setAutor(UserDTO autor) {
        this.autor = autor;
    }
}
