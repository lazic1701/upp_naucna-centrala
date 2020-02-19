package org.milan.naucnacentrala.model.enums;

public class Enums {

    public static enum OrderType {ORDER_CASOPIS, ORDER_RAD, ORDER_SUBSCRIPTION }
    public static enum OrderStatus { SUCCESS, FAILED, PENDING}
    public static enum UserRole { ROLE_ADMIN, ROLE_UREDNIK, ROLE_RECENZENT, ROLE_AUTOR }
    public static enum PaymentType { NAPLACIVANJE_AUTORU, NAPLACIVANJE_CITAOCU }
    public static enum NaucniRadStatus { PRIJAVLJEN, ODOBREN, ODBIJEN }
    public static enum OdlukaRecenzenta { PRIHVATITI, ODBITI, PRIHVATITI_UZ_MANJE_ISPRAVKE,
        USLOVNO_PRIHVATITI_UZ_VECE_ISPRAVKE }
}
