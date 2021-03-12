package com.joh.esms.model;

import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name = "CUSTOMER_PAYMENTS")
public class    CustomerPayment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "I_CUSTOMER_PAYMENT")
	private int id;

	@Column(name = "TOTAL_PAYMENT")
	private Double totalPayment;

	@Column(name = "DISCOUNT")
	private Double discount;

	@ManyToOne()
	@JoinColumn(name = "I_CUSTOMER_PROJECT")
	private CustomerProject customerProject;

	@Column(name = "PAYMENT_TIME", insertable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp()
	@ColumnDefault("CURRENT_TIMESTAMP")
	private Date time;

	@JsonProperty(access = Access.WRITE_ONLY)
	@ManyToOne()
	@JoinColumn(name = "I_CUSTOMER", nullable = false)
	private Customer customer;


	@Column(name = "NOTE")
	private String note;

	public CustomerProject getCustomerProject() {
		return customerProject;
	}

	public void setCustomerProject(CustomerProject customerProject) {
		this.customerProject = customerProject;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Double getTotalPayment() {
		return totalPayment;
	}

	public void setTotalPayment(Double totalPayment) {
		this.totalPayment = totalPayment;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}


	@Override
	public String toString() {
		return "CustomerPayment [id=" + id + ", totalPayment=" + totalPayment + "discount=" + discount +", customerProject=" + customerProject
				+ ", note=" + note + ", time=" + time + "]";
	}

}
