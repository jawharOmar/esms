package com.joh.esms.domain.model;

import java.util.Date;

public class CustomerInvoiceD {
	private int reference;

	private double amount;
	private Date time;

	public static enum INVOICE_TYPE {
		PAYMENT, ORDER, RETURN
	}

	private INVOICE_TYPE invoiceType;

	public int getReference() {
		return reference;
	}

	public void setReference(int reference) {
		this.reference = reference;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public INVOICE_TYPE getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(INVOICE_TYPE invoiceType) {
		this.invoiceType = invoiceType;
	}

	@Override
	public String toString() {
		return "CustomerInvoiceD [reference=" + reference + ", amount=" + amount + ", time=" + time + ", invoiceType="
				+ invoiceType + "]";
	}

}
