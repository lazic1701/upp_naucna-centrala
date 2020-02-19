package org.milan.naucnacentrala.model.dto;

import org.milan.naucnacentrala.model.enums.Enums;

public class KupovinaClanarineDTO {

    private CasopisDTO casopis;
    private Enums.PaymentType paymentType;

    public KupovinaClanarineDTO() {
    }

    public KupovinaClanarineDTO(CasopisDTO casopis, Enums.PaymentType paymentType) {
        this.casopis = casopis;
        this.paymentType = paymentType;
    }

    public CasopisDTO getCasopis() {
        return casopis;
    }

    public void setCasopis(CasopisDTO casopis) {
        this.casopis = casopis;
    }

    public Enums.PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Enums.PaymentType paymentType) {
        this.paymentType = paymentType;
    }
}
