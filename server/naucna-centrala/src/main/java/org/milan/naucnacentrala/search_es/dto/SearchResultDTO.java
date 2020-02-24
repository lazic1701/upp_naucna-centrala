package org.milan.naucnacentrala.search_es.dto;

import java.util.ArrayList;
import java.util.List;

public class SearchResultDTO {

    private List<ResultInstanceDTO> results = new ArrayList<>();
    private int hits;
    private double maxScore;
    private int resultsSize;

    public SearchResultDTO() {
    }

    public SearchResultDTO(List<ResultInstanceDTO> results, int hits, double maxScore) {
        this.results = results;
        this.hits = hits;
        this.maxScore = maxScore;
        this.resultsSize = results != null ? results.size() : 0;
    }

    public List<ResultInstanceDTO> getResults() {
        return results;
    }

    public void setResults(List<ResultInstanceDTO> results) {
        this.results = results;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public double getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(double maxScore) {
        this.maxScore = maxScore;
    }

    public int getResultsSize() {
        return resultsSize;
    }

    public void setResultsSize(int resultsSize) {
        this.resultsSize = resultsSize;
    }
}
