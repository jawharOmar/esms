package com.joh.esms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "PRODUCT_STOCKS", uniqueConstraints = { @UniqueConstraint(columnNames = { "I_STOCK", "I_PRODUCT" }) })
public class ProductStock {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "I_PRODUCT_STOCK")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "I_STOCK", nullable = false)
	private Stock stock;

	@ManyToOne
	@JoinColumn(name = "I_PRODUCT", nullable = false)
	private Product product;

	@Column(name = "AMOUNT", nullable = false)
	private double amount;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
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
		return "ProductStock [id=" + id + ", stock=" + stock + ", product=" + product + ", amount=" + amount + "]";
	}

}
