package com.joh.esms.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;

@Table(name = "PRICE_CATEGORIES")
@Entity
public class PriceCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "I_PRICE_CATEGORY")
	private int id;

	@NotBlank(message = "{priceCategory.name.blank}")
	@Column(name = "PRICE_CATEGORY_NAME", nullable = false, unique = true)
	private String name;

	@Column(name = "RATIO")
	@Min(value = 0)
	@Max(value = 1)
	private BigDecimal ratio;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getRatio() {
		return ratio;
	}

	public void setRatio(BigDecimal ratio) {
		this.ratio = ratio;
	}

	@Override
	public String toString() {
		return "PriceCategory [id=" + id + ", name=" + name + ", ratio=" + ratio + "]";
	}

}
