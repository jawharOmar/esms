package com.joh.esms.model;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Immutable
@Subselect("select ps.I_PRODUCT,ps.EXPIRATION_DATE,p.PRODUCT_NAME,p.PRODUCT_CODE from PRODUCT_STEPUPS ps \n" +
        "JOIN PRODUCTS p on(ps.I_PRODUCT=p.I_PRODUCT) where date(EXPIRATION_DATE)<date(curdate())")
public class ExpiredProducts {


	@Id
	@Column(name = "i_product")
	private int id ;

	@Column(name = "EXPIRATION_DATE")
	private String expire;


	@Column(name = "PRODUCT_NAME")
	private String name;

	@Column(name = "PRODUCT_CODE")
	private String code;


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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getExpire() {
        return expire;
    }

    public void setExpire(String expire) {
        this.expire = expire;
    }
}
