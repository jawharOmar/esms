package com.joh.esms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "SETTINGS")
public class Setting {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;

	@NotEmpty()
	@Column(name = "name")
	private String name;

	@NotEmpty()
	@Column(name = "phone")
	private String phone;

	@NotEmpty()
	@Column(name = "address")
	private String address;

	@NotEmpty()
	@Column(name = "headerImage")
	private String headerImage;

	@Column(name = "footerImage")
	private String footerImage;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "email")
	private String email;

	@Column(name = "logo")
	private String logo;

	@Column(name = "description")
	private String description;

	@Column(name = "base_cur")
	private String base;

	@Column(name = "to_cur")
	private String to;

	@Column(name = "Cur_Rate")
	private Double rate;

	@Column(name = "Cur_Reverse_Rate")
	private Double reverseRate;

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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getHeaderImage() {
		return headerImage;
	}

	public void setHeaderImage(String headerImage) {
		this.headerImage = headerImage;
	}

	public String getFooterImage() {
		return footerImage;
	}

	public void setFooterImage(String footerImage) {
		this.footerImage = footerImage;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Double getReverseRate() {
		return reverseRate;
	}

	public void setReverseRate(Double reverseRate) {
		this.reverseRate = reverseRate;
	}

	@Override
	public String toString() {
		return "Setting [id=" + id + ", name=" + name + ", phone=" + phone + ", address=" + address + ", headerImage="
				+ headerImage + ", footerImage=" + footerImage + ", email=" + email + ", logo=" + logo
				+ ", description=" + description + ", base=" + base + ", to=" + to + ", rate=" + rate + ", reverseRate="
				+ reverseRate + "]";
	}

}
