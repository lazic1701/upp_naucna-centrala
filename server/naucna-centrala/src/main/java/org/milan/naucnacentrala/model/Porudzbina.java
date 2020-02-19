package org.milan.naucnacentrala.model;

import org.milan.naucnacentrala.model.enums.Enums;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "_porudzbina")
public class Porudzbina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    private User kupac;

    @Column
    private double cena;

    @Column
    private Date datum;

    @Column
    @Enumerated(EnumType.STRING)
    private Enums.OrderStatus statusPorudzbine;

    @Column(name = "order_type")
    @Enumerated(EnumType.STRING)
    private Enums.OrderType tipPorudzbine;

    @ManyToOne(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    private Casopis casopis;

    @ManyToOne(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    private NaucniRad naucniRad;

    @ManyToOne(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    private Clanarina clanarina;

    public Porudzbina() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getKupac() {
        return kupac;
    }

    public void setKupac(User kupac) {
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

    public Casopis getCasopis() {
        return casopis;
    }

    public void setCasopis(Casopis casopis) {
        this.casopis = casopis;
    }

    public NaucniRad getNaucniRad() {
        return naucniRad;
    }

    public void setNaucniRad(NaucniRad naucniRad) {
        this.naucniRad = naucniRad;
    }

    public Clanarina getClanarina() {
        return clanarina;
    }

    public void setClanarina(Clanarina clanarina) {
        this.clanarina = clanarina;
    }
}
