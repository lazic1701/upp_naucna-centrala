package org.milan.naucnacentrala.search_es.dto;

import org.milan.naucnacentrala.model.dto.NaucniRadDTO;

public class ResultInstanceDTO {

    private NaucniRadDTO naucniRad;
    private String highlighter;
    private double score;

    public ResultInstanceDTO() {
    }

    public ResultInstanceDTO(NaucniRadDTO naucniRad, String highlighter, double score) {
        this.naucniRad = naucniRad;
        this.highlighter = highlighter;
        this.score = score;
    }


    public NaucniRadDTO getNaucniRad() {
        return naucniRad;
    }

    public void setNaucniRad(NaucniRadDTO naucniRad) {
        this.naucniRad = naucniRad;
    }

    public String getHighlighter() {
        return highlighter;
    }

    public void setHighlighter(String highlighter) {
        this.highlighter = highlighter;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
