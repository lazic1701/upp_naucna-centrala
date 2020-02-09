package org.milan.naucnacentrala.model;

import org.milan.naucnacentrala.model.enums.Enums;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "_clanarina")
public class Clanarina {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "star_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column
    @Enumerated(EnumType.STRING)
    private Enums.PaymentType paymentType;

    @ManyToOne(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    private User user;

    @ManyToOne(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    private Casopis casopis;


    public Clanarina() {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Casopis getCasopis() {
        return casopis;
    }

    public void setCasopis(Casopis casopis) {
        this.casopis = casopis;
    }
}
