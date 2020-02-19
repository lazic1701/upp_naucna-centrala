package org.milan.naucnacentrala.model.dto;

import org.milan.naucnacentrala.model.Porudzbina;
import org.milan.naucnacentrala.model.enums.Enums;

import java.util.Date;

public class PorudzbinaDTO {

    private long id;
    private UserDTO kupac;
    private double cena;
    private Date datum;
    private Enums.OrderStatus statusPorudzbine;
    private Enums.OrderType tipPorudzbine;
    private CasopisDTO casopis;
    private NaucniRadDTO naucniRad;
    private ClanarinaDTO clanarina;

    public PorudzbinaDTO() {
    }

    public PorudzbinaDTO(long id, UserDTO kupac, double cena, Date datum, Enums.OrderStatus statusPorudzbine, Enums.OrderType tipPorudzbine, CasopisDTO casopis, NaucniRadDTO naucniRad, ClanarinaDTO clanarina) {
        this.id = id;
        this.kupac = kupac;
        this.cena = cena;
        this.datum = datum;
        this.statusPorudzbine = statusPorudzbine;
        this.tipPorudzbine = tipPorudzbine;
        this.casopis = casopis;
        this.naucniRad = naucniRad;
        this.clanarina = clanarina;
    }

    public static PorudzbinaDTO formDto(Porudzbina p) {
        if (p == null) {
            return new PorudzbinaDTO();
        } else {
            return new PorudzbinaDTO(p.getId(), UserDTO.formDto(p.getKupac()), p.getCena(), p.getDatum(),
                    p.getStatusPorudzbine(), p.getTipPorudzbine(), CasopisDTO.formDto(p.getCasopis()),
                    NaucniRadDTO.formDto(p.getNaucniRad()), ClanarinaDTO.formDto(p.getClanarina()));
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserDTO getKupac() {
        return kupac;
    }

    public void setKupac(UserDTO kupac) {
        this.kupac = kupac;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public Enums.OrderStatus getStatusPorudzbine() {
        return statusPorudzbine;
    }

    public void setStatusPorudzbine(Enums.OrderStatus statusPorudzbine) {
        this.statusPorudzbine = statusPorudzbine;
    }

    public Enums.OrderType getTipPorudzbine() {
        return tipPorudzbine;
    }

    public void setTipPorudzbine(Enums.OrderType tipPorudzbine) {
        this.tipPorudzbine = tipPorudzbine;
    }

    public CasopisDTO getCasopis() {
        return casopis;
    }

    public void setCasopis(CasopisDTO casopis) {
        this.casopis = casopis;
    }

    public NaucniRadDTO getNaucniRad() {
        return naucniRad;
    }

    public void setNaucniRad(NaucniRadDTO naucniRad) {
        this.naucniRad = naucniRad;
    }

    public ClanarinaDTO getClanarina() {
        return clanarina;
    }

    public void setClanarina(ClanarinaDTO clanarina) {
        this.clanarina = clanarina;
    }
}
