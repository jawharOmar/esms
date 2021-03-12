package com.joh.esms.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import com.joh.esms.domain.model.AccountTransactionType;

@Entity
@Table(name = "ACCOUNT_TRANSACTIONS", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "I_ACCOUNT", "REFERENCE", "ACCOUNT_TRANSACTION_TYPE" }) })
public class AccountTransaction {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "I_ACCOUNT_TRANSACTION")
	private int id;

	@ManyToOne
	@JoinColumn(name = "I_ACCOUNT", nullable = false)
	private Account account;

	@Column(name = "ACCOUNT_TRANSACTION_TYPE", nullable = false)
	@Enumerated(EnumType.ORDINAL)
	private AccountTransactionType accountTransactionType;

	@Column(name = "REFERENCE", nullable = false)
	private Integer reference;

	@Column(name = "AMOUNT", nullable = false)
	private Double amount;

	@Column(name = "TRANSACTION_TIME", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	@ColumnDefault("CURRENT_TIMESTAMP")
	private Date time;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public AccountTransactionType getAccountTransactionType() {
		return accountTransactionType;
	}

	public void setAccountTransactionType(AccountTransactionType accountTransactionType) {
		this.accountTransactionType = accountTransactionType;
	}

	public Integer getReference() {
		return reference;
	}

	public void setReference(Integer reference) {
		this.reference = reference;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "AccountTransaction [id=" + id + ", account=" + account + ", accountTransactionType="
				+ accountTransactionType + ", reference=" + reference + ", amount=" + amount + ", time=" + time + "]";
	}

}
