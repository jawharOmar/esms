package com.joh.esms.model;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Immutable
@Subselect("SELECT p.I_PRODUCT,sum(p.amount) as amount,ps.MINIMUM_STOCK_LEVEL,ps.PRODUCT_NAME,ps.PRODUCT_CODE from PRODUCT_STOCKS p \n"
		+ "JOIN PRODUCTS ps on (p.i_product=ps.i_product)\n"
		+ "group by p.I_PRODUCT having(sum(p.AMOUNT)<ps.MINIMUM_STOCK_LEVEL)")
public class MinimumProducts {

	@Id
	@Column(name = "I_PRODUCT")
	private int id;

	@Column(name = "AMOUNT")
	private double amount;

	@Column(name = "MINIMUM_STOCK_LEVEL")
	private double min;

	@Column(name = "PRODUCT_NAME")
	private String name;

	@Column(name = "PRODUCT_CODE")
	private String code;

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

	public double getMin() {
		return min;
	}

	public void setMin(double min) {
		this.min = min;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
