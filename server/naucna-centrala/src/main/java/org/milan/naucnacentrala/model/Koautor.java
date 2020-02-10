package org.milan.naucnacentrala.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "_koautor")
public class Koautor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String ime;

    @Column
    private String email;

    @Column
    private String grad;

    @Column
    private String drzava;


    @ManyToOne
    @JoinColumn(name="naucni_rad_id")
    private NaucniRad naucniRad;

    public Koautor() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGrad() {
        return grad;
    }

    public void setGrad(String grad) {
        this.grad = grad;
    }

    public String getDrzava() {
        return drzava;
    }

    public void setDrzava(String drzava) {
        this.drzava = drzava;
    }

    public NaucniRad getNaucniRad() {
        return naucniRad;
    }

    public void setNaucniRad(NaucniRad naucniRad) {
        this.naucniRad = naucniRad;
    }
}
