package com.joh.esms.model;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Immutable
@Subselect("select o.I_CUSTOMER,c.FULL_NAME,n.I_CUSTOMER_ORDER,sum(n.COST*n.QUANTITY)  as cost,o.TOTAL_PRICE as price,if(o.discount is null,0,o.discount) as discount ,o.ORDER_TIME as time,(sum(n.price*n.QUANTITY)-sum(n.cost*n.QUANTITY))-if(o.discount is null,0,o.discount) as profit from CUSTOMER_ORDER_DETAILS n \n" +
		"JOIN CUSTOMER_ORDERS o on(n.I_CUSTOMER_ORDER=o.I_CUSTOMER_ORDER) join CUSTOMERS c on(o.I_CUSTOMER=c.I_CUSTOMER) group by I_CUSTOMER_ORDER \n")
public class profit {


	@Id
	@Column(name = "I_CUSTOMER_ORDER")
	private int id ;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "FULL_NAME")
	private String name;

	@Column(name = "I_CUSTOMER")
	private int customer;

	@Column(name = "cost")
	private double cost;

	@Column(name = "price")
	private double price;

	@Column(name = "discount")
	private double discount ;

	@Column(name = "time")
	private Date time ;

	@Column(name = "profit")
	private double profit ;



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getI_customer() {
		return customer;
	}

	public void setI_customer(int customer) {
		this.customer = customer;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public double getProfit() {
		return profit;
	}

	public void setProfit(double profit) {
		this.profit = profit;
	}



}
