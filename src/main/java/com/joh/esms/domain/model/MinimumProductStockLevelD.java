package com.joh.esms.domain.model;

public class MinimumProductStockLevelD {
	private int productId;
	private String productCode;
	private String productName;
	private double minimumStockLevel;
	private double currentStockLevel;

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getMinimumStockLevel() {
		return minimumStockLevel;
	}

	public void setMinimumStockLevel(double minimumStockLevel) {
		this.minimumStockLevel = minimumStockLevel;
	}

	public double getCurrentStockLevel() {
		return currentStockLevel;
	}

	public void setCurrentStockLevel(double currentStockLevel) {
		this.currentStockLevel = currentStockLevel;
	}

	@Override
	public String toString() {
		return "MinimumProductStockLevelD [productId=" + productId + ", productCode=" + productCode + ", productName="
				+ productName + ", minimumStockLevel=" + minimumStockLevel + ", currentStockLevel=" + currentStockLevel
				+ "]";
	}

}
