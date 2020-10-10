package com.joh.esms.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.joh.esms.validator.ProductValidation;

@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })

@Entity
@Table(name = "PRE_PRODUCTS")
public class PreProduct {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "I_PRE_PRODUCT")
	private int id;

	@Column(name = "PRODUCT_CODE")
	private String code;

	@NotBlank(groups = { ProductValidation.Insert.class }, message = "product name is null")
	@Column(name = "PRODUCT_NAME")
	private String name;

	@Column(name = "WEIGHT")
	private String weight;

	@Column(name = "PACKET_QUANTITY")
	private Integer packetQuantity;

	@Column(name = "PACKET_SIZE")
	private Integer packetSize;

	@Column(name = "QUANTITY")
	private Integer quantity;

	@Column(name = "NOTE")
	private String note;

	@JsonProperty(access = Access.READ_ONLY)
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "I_ATTACHED_FILE")
	private AttachedFile attachedFile;

	@Column(name = "PRICE")
	private Double price;

	@Column(name = "SECOND_PRICE")
	private Double secondPrice;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public Integer getPacketQuantity() {
		return packetQuantity;
	}

	public void setPacketQuantity(Integer packetQuantity) {
		this.packetQuantity = packetQuantity;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public AttachedFile getAttachedFile() {
		return attachedFile;
	}

	public void setAttachedFile(AttachedFile attachedFile) {
		this.attachedFile = attachedFile;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getSecondPrice() {
		return secondPrice;
	}

	public void setSecondPrice(Double secondPrice) {
		this.secondPrice = secondPrice;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Integer getPacketSize() {
		return packetSize;
	}

	public void setPacketSize(Integer packetSize) {
		this.packetSize = packetSize;
	}

	@Override
	public String toString() {
		return "PreProduct [id=" + id + ", code=" + code + ", name=" + name + ", weight=" + weight + ", packetQuantity="
				+ packetQuantity + ", packetSize=" + packetSize + ", quantity=" + quantity + ", note=" + note
				+ ", attachedFile=" + attachedFile + ", price=" + price + ", secondPrice=" + secondPrice + "]";
	}

}
