package com.joh.esms.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "INCOMES")
public class Income {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "I_INCOME")
	private int id;

	@NotBlank(message = "name is blank")
	@Column(name = "INCOME_NAME")
	private String name;

	@Column(name = "AMOUNT")
	private Double amount;

	@Column(name = "NOTE")
	private String note;

	@Column(name = "EXPENSE_TIME", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	@ColumnDefault("CURRENT_TIMESTAMP")
	private Date time;

	@ManyToOne
	private IncomeCategory incomeCategory;

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

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public IncomeCategory getIncomeCategory() {
		return incomeCategory;
	}

	public void setIncomeCategory(IncomeCategory incomeCategory) {
		this.incomeCategory = incomeCategory;
	}

	@Override
	public String toString() {
		return "Income [id=" + id + ", name=" + name + ", amount=" + amount + ", note=" + note + ", time=" + time
				+ ", incomeCategory=" + incomeCategory + "]";
	}

}
