package com.joh.esms.domain.model;

import java.sql.Timestamp;

public class ProductTransactionD {

	private int productId;
	private double amount;
	private Timestamp time;
	private int reference;
	private String type;

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public int getReference() {
		return reference;
	}

	public void setReference(int reference) {
		this.reference = reference;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "ProductTransactionD [productId=" + productId + ", amount=" + amount + ", time=" + time + ", reference="
				+ reference + ", type=" + type + "]";
	}

}
