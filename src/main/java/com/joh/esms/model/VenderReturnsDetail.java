package com.joh.esms.model;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.NotBlank;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "VenderReturnsDetail")
public class VenderReturnsDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "I_RETURN_DETAIL")
	private int id;


	@NotNull(message = "Amount is blank")
	@Column(name = "amount")
	private double amount ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "I_RETURN")
    private VenderReturns venderReturns;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "I_PRODUCT", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn (name = "I_STOCK")
    private Stock stock;

    @NotNull(message = "Amount is blank")
    @Column (name = "QTY")
    private double QTY;

    @Column(name = "Time", nullable = false, updatable = false)
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public VenderReturns getVenderReturns() {
        return venderReturns;
    }

    public void setVenderReturns(VenderReturns venderReturns) {
        this.venderReturns = venderReturns;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public double getQTY() {
        return QTY;
    }

    public void setQTY(double QTY) {
        this.QTY = QTY;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }
}
