package com.joh.esms.model;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import javax.persistence.*;
import java.util.Date;

@Entity
@Immutable
@Subselect("select o.I_CUSTOMER as I_CUSTOMER,'Order' as TYPE,o.I_CUSTOMER_ORDER AS ACTION_ID,if(o.NOTE is null,'empty',o.NOTE) as note,o.INVOICE_ID as Invoice,o.ORDER_TIME as Time,if(o.DISCOUNT is null,0,if(o.DISCOUNT=0,0,o.DISCOUNT*-1) ) as Discount,(o.TOTAL_PRICE-if(o.DISCOUNT is null,0,o.DISCOUNT)) as Debit,if(o.TOTAL_PAYMENT is null,0,o.TOTAL_PAYMENT) as Credit from CUSTOMER_ORDERS o\n" +
        "UNION ALL \n" +
        "select p.I_CUSTOMER as I_CUSTOMER,'Payment' as TYPE,p.I_CUSTOMER_PAYMENT as ACTION_ID ,if(p.NOTE is null,'empty',p.NOTE) as note,p.I_CUSTOMER_PAYMENT as Invoice,p.PAYMENT_TIME as Time,if(p.DISCOUNT is null,0,p.DISCOUNT) as Discount,0 as Debit,(p.TOTAL_PAYMENT+if(p.DISCOUNT is null,0,p.DISCOUNT)) as Credit from CUSTOMER_PAYMENTS p\n" +
        "UNION ALL\n" +
        "select r.I_CUSTOMER as I_CUSTOMER,'Return' as TYPE,r.I_CUSTOMER_ORDER_RETURN AS ACTION_ID,'empty' as note,if(r.REFERENCE_INVOICE_ID is null,0,r.REFERENCE_INVOICE_ID) as Invoice,r.CUSTOMER_ORDER_RETURN_TIME as Time,0 as Discount,if(r.TOTAL_PAID is null,0,r.TOTAL_PAID) as Debit,r.TOTAL_PRICE as Credit from CUSTOMER_ORDER_RETURNS r\n" +
        "UNION ALL\n" +
        "select wp.I_CUSTOMER as I_CUSTOMER,'Wasted Product' as TYPE,wp.I_RETURN_WASTED_PRODUCT AS ACTION_ID,'empty' as note,if(wp.REFERENCE is null,0,wp.REFERENCE) as Invoice,wp.RETURN_DATE as Time,0 as Discount,0 as Debit,0 as Credit  from CUSTOMER_RETURN_WASTED_PRODUCT wp order by Time")
public class reports {


    @Column(name = "I_CUSTOMER")
    private int id;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "ACTION_ID")
    private int actionId;

    @Column(name = "invoice")
    private int invoice;

    @Id
    @Column(name = "time")
    private Date time;

    @Column(name = "Discount")
    private double discount;

    @Column(name = "debit")
    private double debit;

    @Column(name = "credit")
    private double credit;

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    private String note;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getActionId() {
        return actionId;
    }

    public void setActionId(int actionId) {
        this.actionId = actionId;
    }

    public int getInvoice() {
        return invoice;
    }

    public void setInvoice(int invoice) {
        this.invoice = invoice;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public double getDebit() {
        return debit;
    }

    public void setDebit(int debit) {
        this.debit = debit;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
