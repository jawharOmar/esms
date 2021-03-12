package com.joh.esms.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity(name = "CUSTOMER_ORDER_DETAILS")
public class CustomerOrderDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "I_CUSTOMER_ORDER_DETAIL")
	private int id;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "I_CUSTOMER_ORDER")
	private CustomerOrder customerOrder;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "I_CUSTOMER_ORDER_DETAIL")
	public List<CustomerOrderDetailProductStepUp> customerOrderDetailProductStepUps = new ArrayList<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "I_PRODUCT", nullable = false)
	private Product product;

	@Column(name = "PRODUCT_NAME")
	private String productName;

	@Column(name = "PRODUCT_CODE")
	private String productCode;

	@Column(name = "QUANTITY")
	private Double quantity;

	@Column(name = "PRICE")
	private Double price;

	@Column(name = "COST")
	private Double cost;

	@ManyToOne
	@JoinColumn(name = "I_STOCK")
	private Stock stock;

	@Transient()
	private Integer productStepUpId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public CustomerOrder getCustomerOrder() {
		return customerOrder;
	}

	public void setCustomerOrder(CustomerOrder customerOrder) {
		this.customerOrder = customerOrder;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public List<CustomerOrderDetailProductStepUp> getCustomerOrderDetailProductStepUps() {
		return customerOrderDetailProductStepUps;
	}

	public void setCustomerOrderDetailProductStepUps(
			List<CustomerOrderDetailProductStepUp> customerOrderDetailProductStepUps) {
		this.customerOrderDetailProductStepUps = customerOrderDetailProductStepUps;
	}

	public Integer getProductStepUpId() {
		return productStepUpId;
	}

	public void setProductStepUpId(Integer productStepUpId) {
		this.productStepUpId = productStepUpId;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	@Override
	public String toString() {
		return "CustomerOrderDetail [id=" + id + ", productName=" + productName + ", productCode=" + productCode
				+ ", stock=" + stock + ", quantity=" + quantity + ", price=" + price + ", productStepUpId="
				+ productStepUpId + ", cost=" + cost + "]";
	}

}
