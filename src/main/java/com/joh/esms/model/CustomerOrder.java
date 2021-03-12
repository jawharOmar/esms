package com.joh.esms.model;

import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.PostPersist;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "CUSTOMER_ORDERS")
public class CustomerOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "I_CUSTOMER_ORDER")
	private int id;

	@Column(name = "INVOICE_ID", unique = true)
	private Integer invoiceId;

	@Column(name = "ORDER_TIME", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@ColumnDefault("CURRENT_TIMESTAMP")
	private Date orderTime;

	@Column(name = "TOTAL_PRICE")
	private Double totalPrice;

	@Column(name = "TOTAL_PAYMENT")
	private Double totalPayment;

	@Column(name = "DISCOUNT")

	private Double discount;

	@Column(name = "NOTE")
	private String note;

	@Column(name = "RECEIVED_BY")
	private String receivedBy;

	@ManyToOne()
	@JoinColumn(name = "I_CUSTOMER")
	private Customer customer;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "I_CUSTOMER_ORDER")
	private List<CustomerOrderDetail> customerOrderDetails = new ArrayList<>();

	@ManyToOne()
	@JoinColumn(name = "I_CUSTOMER_PROJECT")
	private CustomerProject customerProject;

	@PrePersist
	private void prePersisi() {
		if (this.orderTime == null) {
			this.orderTime = new Date();
		}
	}

	@PostPersist
	private void postPersist() {
		if (invoiceId == null)
			invoiceId = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<CustomerOrderDetail> getCustomerOrderDetails() {
		return customerOrderDetails;
	}

	public void setCustomerOrderDetails(List<CustomerOrderDetail> customerOrderDetails) {
		this.customerOrderDetails = customerOrderDetails;
	}

	public Double getTotalPayment() {
		return totalPayment;
	}

	public void setTotalPayment(Double totalPayment) {
		this.totalPayment = totalPayment;
	}

	public CustomerProject getCustomerProject() {
		return customerProject;
	}

	public void setCustomerProject(CustomerProject customerProject) {
		this.customerProject = customerProject;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getReceivedBy() {
		return receivedBy;
	}

	public Integer getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Integer invoiceId) {
		this.invoiceId = invoiceId;
	}

	public void setReceivedBy(String receivedBy) {
		this.receivedBy = receivedBy;
	}

	@Override
	public String toString() {
		return "CustomerOrder [id=" + id + ", invoiceId=" + invoiceId + ", orderTime=" + orderTime + ", totalPrice="
				+ totalPrice + ", totalPayment=" + totalPayment + ", customer=" + customer + ", customerOrderDetails="
				+ customerOrderDetails + ", customerProject=" + customerProject + ", discount=" + discount + ", note="
				+ note + ", receivedBy=" + receivedBy + "]";
	}

}
