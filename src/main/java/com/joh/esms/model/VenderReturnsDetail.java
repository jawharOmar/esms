package com.joh.esms.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "VenderReturnsDetail")
public class VenderReturnsDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "I_RETURN_DETAIL")
	private int id;

	@NotNull(message = "Amount is blank")
	@Column(name = "amount")
	private double amount;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "I_RETURN")
	private VendorReturn vendorReturn;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "I_PRODUCT", nullable = false)
	private Product product;

	@ManyToOne
	@JoinColumn(name = "I_STOCK")
	private Stock stock;

	@NotNull(message = "Amount is blank")
	@Column(name = "QTY")
	private double QTY;

	@Column(name = "Time", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	@ColumnDefault("CURRENT_TIMESTAMP")
	private Date time;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public VendorReturn getVendorReturn() {
		return vendorReturn;
	}

	public void setVendorReturn(VendorReturn vendorReturn) {
		this.vendorReturn = vendorReturn;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public double getQTY() {
		return QTY;
	}

	public void setQTY(double QTY) {
		this.QTY = QTY;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}
}
