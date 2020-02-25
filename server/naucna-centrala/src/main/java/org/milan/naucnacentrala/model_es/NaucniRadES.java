package org.milan.naucnacentrala.model_es;


import org.milan.naucnacentrala.model.User;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Score;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Document(indexName = "naucni_rad", type = "naucni_rad", shards = 1, replicas = 0)
public class NaucniRadES {

    @Id
    @Field(type = FieldType.Integer, store = true)
    private int id;

    @Field(type = FieldType.Text, store = true, searchAnalyzer = "serbian", analyzer = "serbian")
    private String naslov;

    @Field(type = FieldType.Nested, store = true)
    private CasopisES casopis;

    @Field(type = FieldType.Nested, store = true)
    private List<UserES> autori = new ArrayList<>();

    @Field(type = FieldType.Nested, store = true)
    private List<UserES> recenzenti = new ArrayList<>();

    @Field(type = FieldType.Text, store = true, searchAnalyzer = "serbian", analyzer = "serbian")
    private String kljucniPojmovi;

    @Field(type = FieldType.Text, searchAnalyzer = "serbian", analyzer = "serbian")
    private String tekst;



    public NaucniRadES() {
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

    public CasopisES getCasopis() {
        return casopis;
    }

    public void setCasopis(CasopisES casopis) {
        this.casopis = casopis;
    }

    public List<UserES> getAutori() {
        return autori;
    }

    public void setAutori(List<UserES> autori) {
        this.autori = autori;
    }

    public String getKljucniPojmovi() {
        return kljucniPojmovi;
    }

    public void setKljucniPojmovi(String kljucniPojmovi) {
        this.kljucniPojmovi = kljucniPojmovi;
    }

    public String getTekst() {
        return tekst;
    }

    public void setTekst(String tekst) {
        this.tekst = tekst;
    }

    public List<UserES> getRecenzenti() {
        return recenzenti;
    }

    public void setRecenzenti(List<UserES> recenzenti) {
        this.recenzenti = recenzenti;
    }
}
