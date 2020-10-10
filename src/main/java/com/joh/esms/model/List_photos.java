package com.joh.esms.model;

import javax.persistence.*;

@Entity
@Table(name="List_Photo")
public class List_photos {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="ID")
    private int id;

    @Column(name="path",nullable = false,unique = true)
    private String path;

    @ManyToOne
    @JoinColumn(name="I_LIST")
    private store_list list;

    public int getMain() {
        return main;
    }

    public void setMain(int main) {
        this.main = main;
    }

    @Column(name="main")
    private int main;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public store_list getList() {
        return list;
    }

    public void setList(store_list list) {
        this.list = list;
    }


}
