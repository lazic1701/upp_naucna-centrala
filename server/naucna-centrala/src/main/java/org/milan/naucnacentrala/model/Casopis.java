package org.milan.naucnacentrala.model;

import org.milan.naucnacentrala.model.enums.Enums;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "_casopis")
public class Casopis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String naziv;

    @Column(nullable = false)
    private String issn;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Enums.PaymentType nacinNaplate;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean active;

    @ManyToOne(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    private User glavniUrednik;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "_casopis_urednik",
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            joinColumns = @JoinColumn(name = "casopis_id", referencedColumnName = "id"))
    private List<User> urednici = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "_casopis_recenzent",
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            joinColumns = @JoinColumn(name = "casopis_id", referencedColumnName = "id"))
    private List<User> recenzenti = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "_casopis_naucnaoblast",
            inverseJoinColumns = @JoinColumn(name = "naucnaoblast_id", referencedColumnName = "id"),
            joinColumns = @JoinColumn(name = "casopis_id", referencedColumnName = "id"))
    private List<NaucnaOblast> naucneOblasti = new ArrayList<>();

    public Casopis() {
    }

    public Casopis(String naziv, String issn, Enums.PaymentType nacinNaplate, boolean active, User glavniUrednik, List<User> urednici, List<User> recenzenti, List<NaucnaOblast> naucneOblasti) {
        this.naziv = naziv;
        this.issn = issn;
        this.nacinNaplate = nacinNaplate;
        this.active = active;
        this.glavniUrednik = glavniUrednik;
        this.urednici = urednici;
        this.recenzenti = recenzenti;
        this.naucneOblasti = naucneOblasti;
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

    public User getGlavniUrednik() {
        return glavniUrednik;
    }

    public void setGlavniUrednik(User glavniUrednik) {
        this.glavniUrednik = glavniUrednik;
    }

    public List<User> getUrednici() {
        return urednici;
    }

    public void setUrednici(List<User> urednici) {
        this.urednici = urednici;
    }

    public List<User> getRecenzenti() {
        return recenzenti;
    }

    public void setRecenzenti(List<User> recenzenti) {
        this.recenzenti = recenzenti;
    }

    public List<NaucnaOblast> getNaucneOblasti() {
        return naucneOblasti;
    }

    public void setNaucneOblasti(List<NaucnaOblast> naucneOblasti) {
        this.naucneOblasti = naucneOblasti;
    }
}
