package com.joh.esms.model;

import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name = "VENDOR_PAYMENTS")
public class VendorPayment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "I_VENDOR_PAYMENT")
	private int id;

	@Column(name = "TOTAL_PAYMENT")
	private Double totalPayment;

	@Column(name = "DISCOUNT")
	private Double discount;

	@Column(name = "PAYMENT_TIME", insertable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp()
	@ColumnDefault("CURRENT_TIMESTAMP")
	private Date time;

	@JsonProperty(access = Access.WRITE_ONLY)
	@ManyToOne()
	@JoinColumn(name = "I_VENDOR", nullable = false)
	private Vendor vendor;



	@Column(name = "NOTE")
	private String note;

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

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
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
		return "VendorPayment [id=" + id + ", totalPayment=" + totalPayment + "discount=" + discount + ", time=" + time +  ", vendor=" + vendor
				+ ", note=" + note + "]";
	}

}
