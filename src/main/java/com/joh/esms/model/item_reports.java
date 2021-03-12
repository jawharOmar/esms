package com.joh.esms.model;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Immutable
@Subselect("select v.I_VENDOR as I_VENDOR,'empty' as note,'Order' as TYPE,if(v.REFERENCE_INVOICE_ID is null,'empty',v.REFERENCE_INVOICE_ID)as Invoice,v.ORDER_TIME as Time,v.TOTAL_PRICE as Credit, if(v.TOTAL_PAYMENT is null,0,v.TOTAL_PAYMENT) as Debit from ORDER_PRODUCT_STEPUPS v\n" +
		"UNION ALL \n" +
		"select vp.I_VENDOR as I_VENDOR,if(vp.NOTE is null,'empty',vp.NOTE) as note,'Payment' as TYPE,vp.I_VENDOR_PAYMENT as Invoice,vp.PAYMENT_TIME as Time,0 as Credit,vp.TOTAL_PAYMENT as Debit from VENDOR_PAYMENTS vp\n")
public class item_reports {


	@Column(name = "I_VENDOR")
	private int id ;

	@Column(name = "TYPE")
	private String type;

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Column(name = "note")
	private String note;

	@Column(name = "invoice" ,nullable = false)
	private String invoice;

	@Id
	@Column(name = "time")
	private Date time;

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


}
