package com.joh.esms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "STOCK_TRANSFER_DETAILS")
public class StockTransferDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "I_STOCK_TRANSFER_DETAIL")
	private int id;

	@ManyToOne
	@JoinColumn(name = "I_PRODUCT")
	private Product product;

	@Column(name = "AMOUNT")
	private double amount;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "StockTransferDetail [id=" + id + ", product=" + product + ", amount=" + amount + "]";
	}

}
