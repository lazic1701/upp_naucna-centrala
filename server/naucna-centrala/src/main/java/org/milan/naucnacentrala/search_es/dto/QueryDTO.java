package org.milan.naucnacentrala.search_es.dto;

public class QueryDTO {

    private String fieldName;
    private String fieldValue;

    public QueryDTO() {
    }

    public QueryDTO(String fieldName, String fieldValue) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }
}
