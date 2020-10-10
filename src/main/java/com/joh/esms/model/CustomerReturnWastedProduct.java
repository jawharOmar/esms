package com.joh.esms.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "CUSTOMER_RETURN_WASTED_PRODUCT")
public class CustomerReturnWastedProduct {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "I_RETURN_WASTED_PRODUCT")
    private Integer id;

    @Column(name = "REFERENCE")
    private String reference;

    @Column(name = "TOTAL_PRICE", nullable = false)
    private Double totalPrice;

    @Size(min = 0)
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "I_CUSTOMER_WASTED_PRODUCT")
    private List<CustomerWastedProduct> customerWastedProducts = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "I_CUSTOMER")
    private Customer customer;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @Column(name = "RETURN_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @ColumnDefault("CURRENT_TIMESTAMP")
    private Date returnDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<CustomerWastedProduct> getCustomerWastedProducts() {
        return customerWastedProducts;
    }

    public void setCustomerWastedProducts(List<CustomerWastedProduct> customerWastedProducts) {
        this.customerWastedProducts = customerWastedProducts;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public String toString() {
        return "CustomerReturnWastedProduct{" +
                "id=" + id +
                ", reference='" + reference + '\'' +
                ", totalPrice=" + totalPrice +
                ", customerWastedProducts=" + customerWastedProducts +
                ", customer=" + customer +
                ", returnDate=" + returnDate +
                '}';
    }
}
