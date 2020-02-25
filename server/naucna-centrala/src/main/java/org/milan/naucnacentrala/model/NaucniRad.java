package org.milan.naucnacentrala.model;

import org.milan.naucnacentrala.model.enums.Enums;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "_naucni_rad")
public class NaucniRad implements Serializable {


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

    @Column
    private double cena;

    @Column
    @Enumerated(EnumType.STRING)
    private Enums.NaucniRadStatus status;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch=FetchType.EAGER)
    private NaucnaOblast naucnaOblast;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch=FetchType.EAGER)
    private Casopis casopis;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch=FetchType.EAGER)
    private User autor;

    @OneToMany(mappedBy = "naucniRad", cascade = CascadeType.ALL)
    private Set<Koautor> koautori = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "_naucni_rad_recenzent",
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            joinColumns = @JoinColumn(name = "naucni_rad_id", referencedColumnName = "id"))
    private List<User> recenzenti = new ArrayList<>();

    @OneToMany(mappedBy = "naucniRad", cascade = CascadeType.ALL)
    private Set<Recenzija> recenzije = new HashSet<>();


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

    public Enums.NaucniRadStatus getStatus() {
        return status;
    }

    public void setStatus(Enums.NaucniRadStatus status) {
        this.status = status;
    }

    public Set<Recenzija> getRecenzije() {
        return recenzije;
    }

    public void setRecenzije(Set<Recenzija> recenzije) {
        this.recenzije = recenzije;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public List<User> getRecenzenti() {
        return recenzenti;
    }

    public void setRecenzenti(List<User> recenzenti) {
        this.recenzenti = recenzenti;
    }
}
