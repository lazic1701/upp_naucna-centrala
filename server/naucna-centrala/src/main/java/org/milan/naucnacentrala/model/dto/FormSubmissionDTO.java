package org.milan.naucnacentrala.model.dto;

import java.io.Serializable;

public class FormSubmissionDTO implements Serializable {

    String fieldId;
    String fieldValue;

    public FormSubmissionDTO(String fieldId, String fieldValue) {
        this.fieldId = fieldId;
        this.fieldValue = fieldValue;
    }

    public FormSubmissionDTO() {
    }

    public String getFieldId() {
        return fieldId;
    }

    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }
}
