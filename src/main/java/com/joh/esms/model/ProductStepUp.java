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
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "PRODUCT_STEPUPS")
public class ProductStepUp {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "I_PRODUCT_STEPUP")
	private Integer id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "I_PRODUCT", nullable = false)
	private Product product;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	@Column(name = "EXPIRATION_DATE")
	private Date expirationDate;

	@Column(name = "QUANTITY", nullable = false)
	private Double quantity;

	@Column(name = "PAYMENT_AMOUNT", nullable = false)
	private Double paymentAmount;

	@Column(name = "SOLD_QUANTITY", nullable = false)
	private Double soldQuantity;

	@Column(name = "NOTE")
	private String note;

	@ManyToOne()
	@JoinColumn(name = "I_STOCK", nullable = false)
	private Stock stock;

    public Double getReturnPrice() {
        return returnPrice;
    }

    public void setReturnPrice(Double returnPrice) {
        this.returnPrice = returnPrice;
    }

    @Column(name = "ReturnPrice", nullable = true)
    private Double returnPrice;


	@PrePersist
	public void prePersist() {
		if (soldQuantity == null) {
			soldQuantity = 0d;
		}
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Double getSoldQuantity() {
		return soldQuantity;
	}

	public void setSoldQuantity(Double soldQuantity) {
		this.soldQuantity = soldQuantity;
	}

	public Double getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(Double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	@Override
	public String toString() {
		return "ProductStepUp [id=" + id + ", product=" + product + ", expirationDate=" + expirationDate + ", quantity="
				+ quantity + ", paymentAmount=" + paymentAmount + ", soldQuantity=" + soldQuantity + ", note=" + note
				+ ", stock=" + stock + "]";
	}

}
