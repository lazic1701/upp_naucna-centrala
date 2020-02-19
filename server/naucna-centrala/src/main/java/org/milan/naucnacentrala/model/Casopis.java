package org.milan.naucnacentrala.model;

import org.milan.naucnacentrala.model.enums.Enums;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "_casopis")
public class Casopis implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String naziv;

    @Column(nullable = false)
    private String issn;

    @Column
    @Nullable
    private Integer sellerId;

    @Column
    private boolean isRegistered = false;

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

    @OneToMany(mappedBy = "casopis", fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private Set<Clanarina> clanarine = new HashSet<>();

    @OneToMany(mappedBy = "casopis", fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private Set<NaucniRad> naucniRadovi = new HashSet<>();


    public Casopis() {
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

    public Set<Clanarina> getClanarine() {
        return clanarine;
    }

    public void setClanarine(Set<Clanarina> clanarine) {
        this.clanarine = clanarine;
    }

    public Set<NaucniRad> getNaucniRadovi() {
        return naucniRadovi;
    }

    public void setNaucniRadovi(Set<NaucniRad> naucniRadovi) {
        this.naucniRadovi = naucniRadovi;
    }

    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    public boolean isRegistered() {
        return isRegistered;
    }

    public void setRegistered(boolean registered) {
        isRegistered = registered;
    }
}
