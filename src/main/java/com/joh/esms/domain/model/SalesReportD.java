package com.joh.esms.domain.model;

public class SalesReportD {

	private double order;
	private double payment;
	private double loan;
	private double orderReturn;

	public double getOrder() {
		return order;
	}

	public void setOrder(double order) {
		this.order = order;
	}

	public double getPayment() {
		return payment;
	}

	public void setPayment(double payment) {
		this.payment = payment;
	}

	public double getLoan() {
		return loan;
	}

	public void setLoan(double loan) {
		this.loan = loan;
	}

	public double getOrderReturn() {
		return orderReturn;
	}

	public void setOrderReturn(double orderReturn) {
		this.orderReturn = orderReturn;
	}

	@Override
	public String toString() {
		return "SalesReportD [order=" + order + ", payment=" + payment + ", loan=" + loan + ", orderReturn="
				+ orderReturn + "]";
	}

}
