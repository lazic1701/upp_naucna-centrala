package org.milan.naucnacentrala.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "_naucni_rad")
public class NaucniRad {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String naslov;

    @Column
    private String apstrakt;

    @Column
    private String kljucniPojmovi;

    @Column
    private String filePath;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch=FetchType.EAGER)
    private NaucnaOblast naucnaOblast;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch=FetchType.EAGER)
    private Casopis casopis;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch=FetchType.EAGER)
    private User autor;

    @OneToMany(mappedBy = "naucniRad", cascade = CascadeType.ALL)
    private Set<Koautor> koautori = new HashSet<>();


    public NaucniRad() {
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

    public NaucnaOblast getNaucnaOblast() {
        return naucnaOblast;
    }

    public void setNaucnaOblast(NaucnaOblast naucnaOblast) {
        this.naucnaOblast = naucnaOblast;
    }

    public Casopis getCasopis() {
        return casopis;
    }

    public void setCasopis(Casopis casopis) {
        this.casopis = casopis;
    }

    public User getAutor() {
        return autor;
    }

    public void setAutor(User autor) {
        this.autor = autor;
    }

    public Set<Koautor> getKoautori() {
        return koautori;
    }

    public void setKoautori(Set<Koautor> koautori) {
        this.koautori = koautori;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
