package com.joh.esms.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name = "ORDER_PRE_PRODUCT")
public class OrderPreProduct {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "I_ORDER_PRE_PRODUCT")
	private Integer id;

	@JsonProperty(access = Access.READ_ONLY)
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	@Column(name = "ORDER_TIME", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	@ColumnDefault("CURRENT_TIMESTAMP")
	private Date orderTime;

	@Column(name = "TOTAL_PRICE", nullable = false)
	private Double totalPrice;

	@Column(name = "TOTAL_SECOND_PRICE", nullable = false)
	private Double totalSecondPrice;

	@Column(name = "TOTAL_SECOND_PAYMENT", nullable = false)
	private Double totalSecondPayment;

	@Size(min = 0)
	@OneToMany(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "I_ORDER_PRE_PRODUCT")
	private List<PreProduct> preProducts = new ArrayList<>();

	@Column(name = "COMPANY_NAME")
	private String companyName;

	@Column(name = "COMPANY_ADDRESS")
	private String companyAddress;

	@Column(name = "REFERENCE")
	private String reference;

	@Column(name = "PHONE")
	private String phone;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Double getTotalSecondPrice() {
		return totalSecondPrice;
	}

	public void setTotalSecondPrice(Double totalSecondPrice) {
		this.totalSecondPrice = totalSecondPrice;
	}

	public List<PreProduct> getPreProducts() {
		return preProducts;
	}

	public void setPreProducts(List<PreProduct> preProducts) {
		this.preProducts = preProducts;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Double getTotalSecondPayment() {
		return totalSecondPayment;
	}

	public void setTotalSecondPayment(Double totalSecondPayment) {
		this.totalSecondPayment = totalSecondPayment;
	}

	@Override
	public String toString() {
		return "OrderPreProduct [id=" + id + ", orderTime=" + orderTime + ", totalPrice=" + totalPrice
				+ ", totalSecondPrice=" + totalSecondPrice + ", totalSecondPayment=" + totalSecondPayment
				+ ", preProducts=" + preProducts + ", companyName=" + companyName + ", companyAddress=" + companyAddress
				+ ", reference=" + reference + ", phone=" + phone + "]";
	}

}
