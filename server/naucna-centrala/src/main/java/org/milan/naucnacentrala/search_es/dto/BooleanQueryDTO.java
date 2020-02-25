package org.milan.naucnacentrala.search_es.dto;

import java.util.ArrayList;
import java.util.List;

public class BooleanQueryDTO {

    private List<QueryDTO> queries = new ArrayList<>();
    private String operator;

    public BooleanQueryDTO() {
    }

    public BooleanQueryDTO(List<QueryDTO> queries, String operator) {
        this.queries = queries;
        this.operator = operator;
    }

    public List<QueryDTO> getQueries() {
        return queries;
    }

    public void setQueries(List<QueryDTO> queries) {
        this.queries = queries;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
