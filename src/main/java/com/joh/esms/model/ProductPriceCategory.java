package com.joh.esms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(name = "PRODUCT_PRICE_CATEGORIES")
@Entity
public class ProductPriceCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "I_PRODUCT_PRICE_CATEGORY")
	private int id;

	@ManyToOne()
	@JoinColumn(name = "I_PRICE_CATEGORY")
	private PriceCategory priceCategory;

	@Column(name = "PRICE")
	private Double price;

	@Column(name = "I_PRODUCT", updatable = false, insertable = false)
	private int porductId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public PriceCategory getPriceCategory() {
		return priceCategory;
	}

	public void setPriceCategory(PriceCategory priceCategory) {
		this.priceCategory = priceCategory;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public int getPorductId() {
		return porductId;
	}

	public void setPorductId(int porductId) {
		this.porductId = porductId;
	}

	@Override
	public String toString() {
		return "ProductPriceCategory [id=" + id + ", priceCategory=" + priceCategory + ", price=" + price
				+ ", porductId=" + porductId + "]";
	}

}
