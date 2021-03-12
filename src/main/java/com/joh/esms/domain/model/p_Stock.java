package com.joh.esms.domain.model;

public class p_Stock {

	private String name;

	private String Productname;

	private String ProductCode;

	private double productamount;

	private double packetSize;



	public String getStockName() {
		return name;
	}

	public void setStockName(String stockName) {
		name = stockName;
	}

	public String getProductname() {
		return Productname;
	}

	public void setProductname(String productname) {
		Productname = productname;
	}

	public double getProductamount() {
		return productamount;
	}

	public void setProductamount(double productamount) {
		this.productamount = productamount;
	}

	public String getProductCode() {
		return ProductCode;
	}

	public void setProductCode(String productCode) {
		ProductCode = productCode;
	}



	public double getPacketSize() {
		return packetSize;
	}

	public void setPacketSize(double packetSize) {
		this.packetSize = packetSize;
	}



}
