package com.joh.esms.model;


import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;

@Entity
@Table(name = "WITHDRAW_CATEGORIES")
public class WithdrawCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "I_WITHDRAW_CATEGORY")
    private int id;

    @NotBlank(message = "name is blank")
    @Column(name = "WITHDRAW_CATEGORY_NAME")
    private String name;

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

    @Override
    public String toString() {
        return "WithdrawCategory [id=" + id + ", name=" + name + "]";
    }

}
