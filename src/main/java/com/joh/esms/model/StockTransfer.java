package com.joh.esms.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "STOCK_TRANSFERS")
public class StockTransfer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "I_STOCK_TRANSFER")
	private int id;

	@ManyToOne
	@JoinColumn(name = "I_STOCK_FROM", nullable = false)
	private Stock from;

	@ManyToOne
	@JoinColumn(name = "I_STOCK_TO", nullable = false)
	private Stock to;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "I_STOCK_TRANSFER", nullable = false)
	private List<StockTransferDetail> stockTransferDetails = new ArrayList<>();

	@Column(name = "TRASFER_TIME", nullable = false)
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

	public Stock getFrom() {
		return from;
	}

	public void setFrom(Stock from) {
		this.from = from;
	}

	public Stock getTo() {
		return to;
	}

	public void setTo(Stock to) {
		this.to = to;
	}

	public List<StockTransferDetail> getStockTransferDetails() {
		return stockTransferDetails;
	}

	public void setStockTransferDetails(List<StockTransferDetail> stockTransferDetails) {
		this.stockTransferDetails = stockTransferDetails;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "StockTransfer [id=" + id + ", from=" + from + ", to=" + to + ", stockTransferDetails="
				+ stockTransferDetails + ", time=" + time + "]";
	}

}
