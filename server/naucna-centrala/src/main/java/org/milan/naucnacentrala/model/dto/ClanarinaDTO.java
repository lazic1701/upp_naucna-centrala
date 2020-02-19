package org.milan.naucnacentrala.model.dto;

import org.milan.naucnacentrala.model.Casopis;
import org.milan.naucnacentrala.model.Clanarina;
import org.milan.naucnacentrala.model.enums.Enums;

import javax.persistence.*;
import java.util.Date;

public class ClanarinaDTO {

    private int id;
    private Date startDate;
    private Date endDate;
    private Enums.PaymentType paymentType;
    private UserDTO user;
    private CasopisDTO casopis;

    public ClanarinaDTO() {
    }

    public ClanarinaDTO(int id, Date startDate, Date endDate, Enums.PaymentType paymentType, UserDTO user, CasopisDTO casopis) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.paymentType = paymentType;
        this.user = user;
        this.casopis = casopis;
    }

    public static ClanarinaDTO formDto(Clanarina c) {
        if (c == null) {
            return new ClanarinaDTO();
        } else {
            return new ClanarinaDTO(c.getId(), c.getStartDate(), c.getEndDate(), c.getPaymentType(),
                    UserDTO.formDto(c.getUser()), CasopisDTO.formDto(c.getCasopis()));
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Enums.PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Enums.PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public CasopisDTO getCasopis() {
        return casopis;
    }

    public void setCasopis(CasopisDTO casopis) {
        this.casopis = casopis;
    }
}
