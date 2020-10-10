package com.joh.esms.model;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.NotBlank;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "VenderReturns")
public class VenderReturns {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "I_RETURN")
	private int id;

	@NotNull(message = "Amount is blank")
	@Column(name = "AMOUNT")
	private double amount ;


    @Column(name = "Time", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @ColumnDefault("CURRENT_TIMESTAMP")
    private Date time;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "I_RETURN")
    private List<VenderReturnsDetail> venderReturnsDetail = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name="I_VENDOR")
    private Vendor vendor;


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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public List<VenderReturnsDetail> getVenderReturnsDetail() {
        return venderReturnsDetail;
    }

    public void setVenderReturnsDetail(List<VenderReturnsDetail> venderReturnsDetail) {
        this.venderReturnsDetail = venderReturnsDetail;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }
}
