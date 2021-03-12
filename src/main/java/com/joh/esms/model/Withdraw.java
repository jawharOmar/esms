package com.joh.esms.model;


import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "WITHDRAW")
public class Withdraw {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "I_WITHDRAW")
    private int id;

    @NotBlank(message = "name is blank")
    @Column(name = "WITHDRAW_NAME")
    private String name;

    @NotNull(message = "amount cannot be null")
    @DecimalMin("1.00")
    @Column(name = "AMOUNT",nullable = false)
    private Double amount;

    @Column(name = "NOTE")
    private String note;

    @Column(name = "WITHDRAW_TIME", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @ColumnDefault("CURRENT_TIMESTAMP")
    private Date time;

    @ManyToOne(optional = false)
    @JoinColumn(name = "I_WITHDRAW_CATEGORY")
    private WithdrawCategory withdrawCategory;

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

    public WithdrawCategory getWithdrawCategory() {
        return withdrawCategory;
    }

    public void setWithdrawCategory(WithdrawCategory withdrawCategory) {
        this.withdrawCategory = withdrawCategory;
    }

    @Override
    public String toString() {
        return "Withdraw [" +
                "id=" + id +
                ", amount=" + amount +
                ", note='" + note + '\'' +
                ", time=" + time +
                ", withdrawCategory=" + withdrawCategory +
                ']';
    }
}
