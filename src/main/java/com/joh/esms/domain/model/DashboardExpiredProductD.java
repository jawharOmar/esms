package com.joh.esms.domain.model;

public class DashboardExpiredProductD {
	private int productId;
	private String productCode;
	private String productName;
	private double amount;

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "DashboardExpiredProductD [productId=" + productId + ", productCode=" + productCode + ", productName="
				+ productName + ", amount=" + amount + "]";
	}

}
