package com.joh.esms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "EXPENSE_CATEGORIES")
public class ExpenseCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "I_EXPENSE_CATEGORY")
	private int id;

	@NotBlank(message = "name is blank")
	@Column(name = "EXPENSE_CATEGORY_NAME")
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
