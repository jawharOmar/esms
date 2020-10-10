package com.joh.esms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "INCOME_CATEGORIES")
public class IncomeCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "I_INCOME_CATEGORY")
	private int id;

	@NotBlank(message = "name is blank")
	@Column(name = "INCOME_CATEGORY_NAME")
	private String name;

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

	@Override
	public String toString() {
		return "ExpenseCategory [id=" + id + ", name=" + name + "]";
	}

}
