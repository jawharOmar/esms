package com.joh.esms.model;


import javax.persistence.*;

@Entity
@Table(name = "CUSTOMER_WASTED_PRODUCT")
public class CustomerWastedProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "I_WASTED_PRODUCT")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "I_PRODUCT", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "I_STOCK")
    private Stock stock;


    @Column(name = "QUANTITY")
    private Integer quantity;

    @Column(name = "PRICE")
    private Double price;

    @Column(name = "NOTE")
    private String note;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "CustomerWastedProduct{" +
                "id=" + id +
                ", product=" + product +
                ", stock=" + stock +
                ", quantity=" + quantity +
                ", price=" + price +
                ", note='" + note + '\'' +
                '}';
    }
}
