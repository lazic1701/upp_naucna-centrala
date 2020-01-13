package org.milan.naucnacentrala.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class NaucnaOblast {

    @Id
    private String id;

    @Column
    private String naziv;

    @ManyToMany(mappedBy = "naucneOblastiAutor")
    private Set<User> autori = new HashSet<>();

    public NaucnaOblast() {
    }

    public NaucnaOblast(String id, String naziv, Set<User> autori) {
        this.id = id;
        this.naziv = naziv;
        this.autori = autori;
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

    public Set<User> getAutori() {
        return autori;
    }

    public void setAutori(Set<User> autori) {
        this.autori = autori;
    }
}
