package org.milan.naucnacentrala.model_es;


import org.elasticsearch.common.geo.GeoPoint;
import org.milan.naucnacentrala.model.enums.Enums;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Document(indexName = "user_es", type = "user", shards = 1, replicas = 0)
public class UserES {

    @Id
    @Field(type = FieldType.Integer, store = true)
    private int id;

    @Field(type = FieldType.Text, store = true)
    private String ime;

    @Field(type = FieldType.Text, store = true)
    private String prezime;

    @Field(type = FieldType.Text, store = true)
    private String role;

    @GeoPointField
    private GeoPoint location;

    @Field(type = FieldType.Text, store = true)
    private List<String> naucneOblasti = new ArrayList<>();

    public UserES() {
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

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public GeoPoint getLocation() {
        return location;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }

    public List<String> getNaucneOblasti() {
        return naucneOblasti;
    }

    public void setNaucneOblasti(List<String> naucneOblasti) {
        this.naucneOblasti = naucneOblasti;
    }
}
