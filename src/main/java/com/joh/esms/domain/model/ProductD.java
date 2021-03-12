package com.joh.esms.domain.model;

import java.util.ArrayList;
import java.util.List;

import com.joh.esms.model.AttachedFile;
import com.joh.esms.model.ProductPriceCategory;

public class ProductD {

	private Integer productId;
	private String code;
	private String name;
	private String unitType;
	private Double stockLevel;
	private Double cost;
	private Double price;
	private String category;
	private Double packetSize;
	private Integer productStepUpId;
	private Double minimumStockLevel;
	private Double lastPrice;
	private String unit;
    private Double unitsize;
	private Integer attachedFileId;

	private AttachedFile attachedFile;

	private List<ProductPriceCategory> productPriceCategories = new ArrayList<>();

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
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

	public String getUnitType() {
		return unitType;
	}

	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}

	public Double getStockLevel() {
		return stockLevel;
	}

	public void setStockLevel(Double stockLevel) {
		this.stockLevel = stockLevel;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Double getPacketSize() {
		return packetSize;
	}

	public void setPacketSize(Double packetSize) {
		this.packetSize = packetSize;
	}

	public Integer getProductStepUpId() {
		return productStepUpId;
	}

	public void setProductStepUpId(Integer productStepUpId) {
		this.productStepUpId = productStepUpId;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public List<ProductPriceCategory> getProductPriceCategories() {
		return productPriceCategories;
	}

	public void setProductPriceCategories(List<ProductPriceCategory> productPriceCategories) {
		this.productPriceCategories = productPriceCategories;
	}

	public Double getMinimumStockLevel() {
		return minimumStockLevel;
	}

	public void setMinimumStockLevel(Double minimumStockLevel) {
		this.minimumStockLevel = minimumStockLevel;
	}

	public Double getLastPrice() {
		return lastPrice;
	}

	public void setLastPrice(Double lastPrice) {
		this.lastPrice = lastPrice;
	}

	public AttachedFile getAttachedFile() {
		return attachedFile;
	}

	public void setAttachedFile(AttachedFile attachedFile) {
		this.attachedFile = attachedFile;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Integer getAttachedFileId() {
		return attachedFileId;
	}

	public void setAttachedFileId(Integer attachedFileId) {
		this.attachedFileId = attachedFileId;
	}

    public Double getUnitsize() {
        return unitsize;
    }

    public void setUnitsize(Double unitsize) {
        this.unitsize = unitsize;
    }

    @Override
	public String toString() {
		return "ProductD [productId=" + productId + ", code=" + code + ", name=" + name + ", unitType=" + unitType
				+ ", stockLevel=" + stockLevel + ", cost=" + cost + ", price=" + price + ", category=" + category
				+ ", packetSize=" + packetSize + ", productStepUpId=" + productStepUpId + ", minimumStockLevel="
				+ minimumStockLevel + ", lastPrice=" + lastPrice + ", unit=" + unit + ", attachedFileId="
				+ attachedFileId + ", attachedFile=" + attachedFile + ", productPriceCategories="
				+ productPriceCategories + "]";
	}

}
