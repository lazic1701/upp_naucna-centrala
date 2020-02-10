package org.milan.naucnacentrala.model;


import org.milan.naucnacentrala.model.enums.Enums;

import javax.persistence.*;

@Entity
@Table(name = "_recenzija")
public class Recenzija {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String tekst;

    @Column
    @Enumerated(EnumType.STRING)
    private Enums.OdlukaRecenzenta odluka;

    @ManyToOne
    @JoinColumn(name="naucni_rad_id")
    private NaucniRad naucniRad;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User recenzent;

    public Recenzija() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTekst() {
        return tekst;
    }

    public void setTekst(String tekst) {
        this.tekst = tekst;
    }

    public Enums.OdlukaRecenzenta getOdluka() {
        return odluka;
    }

    public void setOdluka(Enums.OdlukaRecenzenta odluka) {
        this.odluka = odluka;
    }

    public NaucniRad getNaucniRad() {
        return naucniRad;
    }

    public void setNaucniRad(NaucniRad naucniRad) {
        this.naucniRad = naucniRad;
    }

    public User getRecenzent() {
        return recenzent;
    }

    public void setRecenzent(User recenzent) {
        this.recenzent = recenzent;
    }
}
