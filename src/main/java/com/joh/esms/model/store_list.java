package com.joh.esms.model;


import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "STORE_LIST")
public class store_list {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="I_LIST")
    private int id;

    @OneToOne
    @JoinColumn(name = "I_PRODUCT", nullable = false,unique = true)
    private Product product;

    @Column(name="TITLE",nullable = false)
    private String title;

    @Column(name="BRAND",nullable = false)
    private String brand;

    @Column(name="DESCRIPTION",nullable = false)
    private String Description;


    @Column(name="DETAIL",nullable = false,columnDefinition = "mediumtext")
    private String Details;

    public List<List_photos> getPhotos() {
        return photos;
    }

    public void setPhotos(List<List_photos> photos) {
        this.photos = photos;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "list", cascade = CascadeType.ALL)
    private List<List_photos> photos;

    @Column(name="Time",nullable = false, columnDefinition="TIMESTAMP default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date Time;

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    @Column(name="Active")
    private int active;

    public Date getTime() {
        return Time;
    }

    public void setTime(Date time) {
        Time = time;
    }

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getDetails() {
        return Details;
    }

    public void setDetails(String details) {
        Details = details;
    }




}
