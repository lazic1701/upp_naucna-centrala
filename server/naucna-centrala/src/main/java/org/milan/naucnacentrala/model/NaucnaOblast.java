package org.milan.naucnacentrala.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "_naucna_oblast")
public class NaucnaOblast {

    @Id
    private String id;

    @Column
    private String naziv;

    @ManyToMany(mappedBy = "naucneOblastiUser")
    private Set<User> korisnici = new HashSet<>();

    @ManyToMany(mappedBy = "naucneOblasti")
    private Set<Casopis> casopisi = new HashSet<>();

    @OneToMany(mappedBy = "naucnaOblast", fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private Set<NaucniRad> naucniRadovi = new HashSet<>();


    public NaucnaOblast() {
    }


    public NaucnaOblast(String id, String naziv, Set<User> korisnici, Set<Casopis> casopisi) {
        this.id = id;
        this.naziv = naziv;
        this.korisnici = korisnici;
        this.casopisi = casopisi;
    }

    public Set<Casopis> getCasopisi() {
        return casopisi;
    }

    public void setCasopisi(Set<Casopis> casopisi) {
        this.casopisi = casopisi;
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

    public Set<User> getKorisnici() {
        return korisnici;
    }

    public void setKorisnici(Set<User> korisnici) {
        this.korisnici = korisnici;
    }

    public Set<NaucniRad> getNaucniRadovi() {
        return naucniRadovi;
    }

    public void setNaucniRadovi(Set<NaucniRad> naucniRadovi) {
        this.naucniRadovi = naucniRadovi;
    }
}
