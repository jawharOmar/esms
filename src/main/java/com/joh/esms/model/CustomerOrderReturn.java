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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import com.joh.esms.validator.OrderProductStepUpValidator;

@Entity
@Table(name = "CUSTOMER_ORDER_RETURNS")

public class CustomerOrderReturn {



	@NotNull(message = "Id id null", groups = { OrderProductStepUpValidator.Edit.class })
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "I_CUSTOMER_ORDER_RETURN")
	private Integer id;

	@NotNull(message = "Cusotmer is null")
	@ManyToOne()
	@JoinColumn(name = "I_CUSTOMER", nullable = false, updatable = false)
	private Customer customer;

	@Column(name = "CUSTOMER_ORDER_RETURN_TIME", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	@ColumnDefault("CURRENT_TIMESTAMP")
	private Date time;

	@Column(name = "REFERENCE_INVOICE_ID")
	private String referecneInvoiceId;

	@Column(name = "TOTAL_PRICE", nullable = false)
	private Double totalPrice;

	public Double getTotalpaid() {
		return totalpaid;
	}

	public void setTotalpaid(Double totalpaid) {
		this.totalpaid = totalpaid;
	}

	@Column(name = "TOTAL_PAID", nullable = false)
	private Double totalpaid;

	@Size(min = 0)
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "I_CUSTOMER_ORDER_RETURN")
	private List<ProductStepUp> productStepUps = new ArrayList<>();


	@ManyToOne()
	@JoinColumn(name = "I_CUSTOMER_PROJECT")
	private CustomerProject customerProject;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getReferecneInvoiceId() {
		return referecneInvoiceId;
	}

	public void setReferecneInvoiceId(String referecneInvoiceId) {
		this.referecneInvoiceId = referecneInvoiceId;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public List<ProductStepUp> getProductStepUps() {
		return productStepUps;
	}

	public void setProductStepUps(List<ProductStepUp> productStepUps) {
		this.productStepUps = productStepUps;
	}


	public CustomerProject getCustomerProject() {
		return customerProject;
	}

	public void setCustomerProject(CustomerProject customerProject) {
		this.customerProject = customerProject;
	}

	@Override
	public String toString() {
		return "CustomerOrderReturn [id=" + id + ", customer=" + customer + ", time=" + time + ", referecneInvoiceId="
				+ referecneInvoiceId  + ", customerProject=" + customerProject + ", totalPrice=" + totalPrice + ", productStepUps=" + productStepUps + "]";
	}

}
