package com.joh.esms.model;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Immutable
@Subselect("select v.I_VENDOR as I_VENDOR,'empty' as note,'Order' as TYPE,v.I_ORDER_PRODUCT_STEPUP AS ACTION_ID,if(v.REFERENCE_INVOICE_ID is null,'empty',v.REFERENCE_INVOICE_ID)as Invoice,v.ORDER_TIME as Time,if(v.DISCOUNT is null,0,if(v.DISCOUNT = 0,0,v.DISCOUNT*-1)) as Discount,(v.TOTAL_PRICE-if(v.DISCOUNT is null,0,v.DISCOUNT)) as Credit, if(v.TOTAL_PAYMENT is null,0,v.TOTAL_PAYMENT) as Debit from ORDER_PRODUCT_STEPUPS v\n" +
		"UNION ALL \n" +
		"select vp.I_VENDOR as I_VENDOR,if(vp.NOTE is null,'empty',vp.NOTE) as note,'Payment' as TYPE,vp.I_VENDOR_PAYMENT AS ACTION_ID,vp.I_VENDOR_PAYMENT as Invoice,vp.PAYMENT_TIME as Time,if(vp.DISCOUNT is null,0,vp.DISCOUNT) AS Discount,0 as Credit,(vp.TOTAL_PAYMENT+if(vp.DISCOUNT is null,0,vp.DISCOUNT)) as Debit from VENDOR_PAYMENTS vp\n" +
		"UNION ALL\n" +
		"select vr.I_VENDOR as I_VENDOR,'empty' as note,'Return' as TYPE,vr.I_RETURN AS ACTION_ID,vr.I_RETURN as Invoice,vr.Time as Time,0 as Discount,0 as Credit,vr.AMOUNT as Debit from VENDOR_RETURNS vr")
public class vendor_reports {


	@Column(name = "I_VENDOR")
	private int id ;

	@Column(name = "TYPE")
	private String type;

	@Column(name = "ACTION_ID")
	private int actionId;


	@Column(name = "note")
	private String note;

	@Column(name = "invoice" ,nullable = false)
	private String invoice;

	@Id
	@Column(name = "time")
	private Date time;

	@Column(name = "Discount")
	private double discount ;

	@Column(name = "debit",nullable = false)
	private double debit ;

	@Column(name = "credit",nullable = false)
	private double credit ;




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
	public String getInvoice() {
		return invoice;
	}

	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
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

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public int getActionId() {
		return actionId;
	}

	public void setActionId(int actionId) {
		this.actionId = actionId;
	}
}
