package com.joh.esms.model;


import javax.persistence.*;

@Entity(name = "CUSTOMER_ORDER_DETAIL_PRODUCT_STEPUPS")
public class CustomerOrderDetailProductStepUp {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "I_CODPS")
	private int id;

	@ManyToOne()
	@JoinColumn(name = "I_PRODUCT_STEPUP")
	private ProductStepUp productStepUp;

	@Column(name = "AMOUNT", nullable = false)
	private Double amount;

	public ProductStepUp getProductStepUp() {
		return productStepUp;
	}

	public void setProductStepUp(ProductStepUp productStepUp) {
		this.productStepUp = productStepUp;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "CustomerOrderDetailProductStepUp [id=" + id + ", productStepUp=" + productStepUp + ", amount=" + amount
				+ "]";
	}
	
}
