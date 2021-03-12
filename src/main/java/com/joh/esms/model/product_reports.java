package com.joh.esms.model;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import javax.persistence.*;
import java.util.Date;

@Entity
@Immutable
@Subselect("select concat(o.ORDER_TIME,p.I_product,s.QUANTITY) as id,p.I_product,s.I_STOCK as stock,(select STOCK_NAME from STOCKS where I_STOCK=s.I_STOCK) as stockname,o.I_ORDER_PRODUCT_STEPUP as invoice,'Order' as type,o.ORDER_TIME as Time ,s.QUANTITY,(s.payment_amount/s.quantity) as payment,v.Full_NAME as detail\n" +
        "\t\tFROM PRODUCTS p\n" +
        "\t\tJOIN PRODUCT_STEPUPS s on(p.I_PRODUCT=s.I_PRODUCT)\n" +
        "\t\tJOIN ORDER_PRODUCT_STEPUPS o on(s.I_ORDER_PRODUCT_STEPUP=o.I_ORDER_PRODUCT_STEPUP)\n" +
        "\t\tJOIN VENDORS v on(v.I_VENDOR=o.I_VENDOR)\n" +
        "\t\tUNION ALL\n" +
        "\t\tselect concat(od.ORDER_TIME,po.I_product,po.QUANTITY) as id, po.I_product,po.I_STOCK as stock,(select STOCK_NAME from STOCKS where I_STOCK=po.I_STOCK) as stockname,po.I_CUSTOMER_ORDER as invoice,'sale' as type,\n" +
        "     \tod.ORDER_TIME,po.QUANTITY,po.PRICE as payment,c.FULL_NAME as detail\n" +
        "\t\tfrom CUSTOMER_ORDER_DETAILS po\n" +
        "\t\tJOIN PRODUCTS p  on(p.I_PRODUCT=po.I_PRODUCT)\n" +
        "\t\tJOIN CUSTOMER_ORDERS od on(po.I_CUSTOMER_ORDER=od.I_CUSTOMER_ORDER)\n" +
        "\t\tJOIN CUSTOMERS c on(od.I_CUSTOMER=c.I_CUSTOMER)\n" +
        "\t\tUNION ALL\n" +
        "\t\tselect concat(s.TRASFER_TIME,t.I_product,t.AMOUNT) as id,  t.I_PRODUCT,s.I_STOCK_FROM as stockname,s.I_STOCK_TO as stockname,t.I_STOCK_TRANSFER,'transfer' as type,s.TRASFER_TIME as Time,t.AMOUNT as QUANTITY,0 as payment,CONCAT((select STOCK_NAME from STOCKS where I_STOCK=s.I_STOCK_FROM),'->',(select STOCK_NAME from STOCKS where I_STOCK=s.I_STOCK_TO)) as detail from STOCK_TRANSFER_DETAILS t\n" +
        "\t\tjoin STOCK_TRANSFERS s on(t.I_STOCK_TRANSFER=s.I_STOCK_TRANSFER)\n" +
        "\t    UNION ALL\n" +
        "\t\tselect concat(r.CUSTOMER_ORDER_RETURN_TIME,r.I_CUSTOMER_ORDER_RETURN,rp.I_product) as id,rp.I_product,rp.I_stock,(select STOCK_NAME from STOCKS where I_STOCK=rp.I_STOCK) as stockname,r.I_CUSTOMER_ORDER_RETURN as invoice,'return' as type,r.CUSTOMER_ORDER_RETURN_TIME,rp.QUANTITY,rp.PAYMENT_AMOUNT as payment,+\n" +
        "\t    (select FULL_NAME from CUSTOMERS where I_CUSTOMER=r.I_CUSTOMER) as detail from CUSTOMER_ORDER_RETURNS r \n" +
        "\t\tJOIN PRODUCT_STEPUPS rp on(rp.I_CUSTOMER_ORDER_RETURN=r.I_CUSTOMER_ORDER_RETURN)\n" +
        "        UNION ALL\n" +
        "\t\tselect concat(vr.I_RETURN,vr.Time,vr.AMOUNT) as id,vrr.I_PRODUCT,vrr.I_STOCK,(select STOCK_NAME from STOCKS where I_STOCK=vrr.I_STOCK) as stockname,vr.I_RETURN as invoice,'Vendor Return' as type,vr.Time,vrr.QTY,vrr.amount as payment,\n" +
        "\t    (select FULL_NAME from VENDORS where I_VENDOR=vr.I_VENDOR) as detail from VENDERRETURNS vr \n" +
        "\t\tJOIN VENDERRETURNSDETAIL vrr on(vr.I_RETURN=vrr.I_RETURN)")
public class product_reports   {


	@Column(name = "I_PRODUCT")
	private int product ;

	public int getProduct() {
		return product;
	}

	public void setProduct(int product) {
		this.product = product;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	@Id
	@Column(name = "id")
	private String id ;

	public String getStockname() {
		return stockname;
	}

	public void setStockname(String stockname) {
		this.stockname = stockname;
	}

	@Column(name = "stockname")
	private String stockname ;

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public String getDetail() {
		return detail;
	}

	@Column(name = "stock")
	private int stock ;


	@Column(name = "invoice")
	private int invoice;



	public int getI_PRODUCT() {
		return product;
	}

	public void setproduct(int product) {
		product = product;
	}

	public int getInvoice() {
		return invoice;
	}

	public void setInvoice(int invoice) {
		this.invoice = invoice;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Double getQUANTITY() {
		return QUANTITY;
	}

	public void setQUANTITY(Double QUANTITY) {
		this.QUANTITY = QUANTITY;
	}

	public double getPayment() {
		return payment;
	}

	public void setPayment(double payment) {
		this.payment = payment;
	}

	public String getdetail() {
		return detail;
	}

	public void setDetail(String detail) {
		detail = detail;
	}

	@Column(name = "type")
	private String type;


	@Column(name = "Time")
	private Date time;

	@Column(name = "QUANTITY")
	private Double QUANTITY ;

	@Column(name = "payment")
	private double payment ;

	@Column(name = "detail")
	private String detail ;

}

