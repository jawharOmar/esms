package com.joh.esms.domain.model;

public class DashboardBalanceD {

	private double expense;
	private double account;
	private double vendorLoan;
	private double customerLoan;

	public double getExpense() {
		return expense;
	}

	public void setExpense(double expense) {
		this.expense = expense;
	}

	public double getAccount() {
		return account;
	}

	public void setAccount(double account) {
		this.account = account;
	}

	public double getVendorLoan() {
		return vendorLoan;
	}

	public void setVendorLoan(double vendorLoan) {
		this.vendorLoan = vendorLoan;
	}

	public double getCustomerLoan() {
		return customerLoan;
	}

	public void setCustomerLoan(double customerLoan) {
		this.customerLoan = customerLoan;
	}

	@Override
	public String toString() {
		return "DashboardBalanceD [expense=" + expense + ", account=" + account + ", vendorLoan=" + vendorLoan
				+ ", customerLoan=" + customerLoan + "]";
	}
	
}
