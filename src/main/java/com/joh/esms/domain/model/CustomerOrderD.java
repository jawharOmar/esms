package com.joh.esms.domain.model;

public class CustomerOrderD {

	private String date;
	private double amount;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "CustomerOrderD [date=" + date + ", amount=" + amount + "]";
	}

}
