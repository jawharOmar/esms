package com.joh.esms.model;


import javax.persistence.*;

@Entity
@Table(name = "SETTINGS")
public class Setting {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;


    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;


    @Column(name = "address")
    private String address;

    @Column(name = "header_image")
    private String header_image;

    @Column(name = "footer_image")
    private String footer_image;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "email")
    private String email;


    @Column(name = "logo")
    private String logo;

    @Column(name = "description")
    private String description;

    @Column(name = "base_cur")
    private String base;

    @Column(name = "to_cur")
    private String to;

    @Column(name = "Cur_Rate")
    private Double rate;

    @Column(name = "Cur_Reverse_Rate")
    private Double reverseRate;

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

    public String getPhone() {
        return phone;
    }

    public String getHeader_image() {
        return header_image;
    }

    public String getFooter_image() {
        return footer_image;
    }


    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setFooter_image(String footer_image) {
        this.footer_image = footer_image;
    }

    public void setHeader_image(String header_image) {
        this.header_image = header_image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Double getReverseRate() {
        return reverseRate;
    }

    public void setReverseRate(Double reverseRate) {
        this.reverseRate = reverseRate;
    }
}
