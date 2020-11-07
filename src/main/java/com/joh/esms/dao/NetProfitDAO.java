package com.joh.esms.dao;

import com.joh.esms.domain.model.NetProfitD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Component
public class NetProfitDAO {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private MessageSource messageSource;


    public List<NetProfitD> findNetProfit(Locale locale) {
        Query query = em.createNativeQuery("SELECT ?1 AS TYPE ,IFNULL(ROUND(SUM(STOCK_LEVEL * COST),3),0)  AS AMOUNT FROM (SELECT P.I_PRODUCT,IFNULL(SUM(PS.QUANTITY-PS.SOLD_QUANTITY),0) AS STOCK_LEVEL,ROUND(SUM(PAYMENT_AMOUNT)/SUM(QUANTITY),3) as COST\n" +
                "FROM PRODUCTS P LEFT OUTER JOIN PRODUCT_STEPUPS PS ON P.I_PRODUCT=PS.I_PRODUCT\n" +
                "GROUP BY P.I_PRODUCT ORDER BY PRODUCT_CODE) A\n" +
                "UNION ALL\n" +
                "SELECT ?2 AS TYPE, ROUND(IFNULL((SELECT SUM(IFNULL(TOTAL_PRICE,0)-IFNULL(DISCOUNT,0)-IFNULL(TOTAL_PAYMENT,0))\n" +
                "FROM CUSTOMER_ORDERS),0)-IFNULL((SELECT SUM(IFNULL(TOTAL_PRICE-TOTAL_PAID,0))\n" +
                "FROM CUSTOMER_ORDER_RETURNS),0)-IFNULL( (SELECT SUM(TOTAL_PAYMENT) + SUM(if(DISCOUNT is null,0,DISCOUNT)) FROM CUSTOMER_PAYMENTS),0),3) AMOUNT\n" +
                "UNION ALL\n" +
                "SELECT ?3 AS Type,IFNULL((SELECT SUM(AMOUNT) FROM ACCOUNT_TRANSACTIONS),0) + IFNULL((SELECT SUM(AMOUNT) FROM WITHDRAW),0) AS AMOUNT\n" +
                "UNION ALL\n" +
                "SELECT ?4 AS TYPE, (ROUND(IFNULL((SELECT SUM(IFNULL(TOTAL_PRICE,0)-IFNULL(TOTAL_PAYMENT,0) -IFNULL(O.DISCOUNT,0) ) FROM ORDER_PRODUCT_STEPUPS O),0)-IFNULL((SELECT SUM(TOTAL_PAYMENT)+\n" +
                "SUM(IF(vp.DISCOUNT IS NULL,0,vp.DISCOUNT)) FROM VENDOR_PAYMENTS vp),0)\n" +
                "-IFNULL((SELECT SUM(AMOUNT) FROM VENDERRETURNS),0),3)*-1) AMOUNT");

        query.setParameter(1,messageSource.getMessage("netProfit.stock", null, locale));
        query.setParameter(2,messageSource.getMessage("netProfit.customer_loan", null, locale));
        query.setParameter(3,messageSource.getMessage("netProfit.account", null, locale));
        query.setParameter(4,messageSource.getMessage("netProfit.vendor_loan", null, locale));


        List<NetProfitD> netProfitDS = new ArrayList<>();

        List<Object[]> resultList = query.getResultList();

        for (Object[] row : resultList) {
            NetProfitD netProfitD = new NetProfitD();
            netProfitD.setType(row[0].toString());
            netProfitD.setAmount(Double.parseDouble(row[1].toString()));

            netProfitDS.add(netProfitD);
        }
        return netProfitDS;
    }

    public List<NetProfitD> findNetProfitByDate(Date from, Date to, Locale locale) {
        Query query = em.createNativeQuery("SELECT ?3 AS TYPE ,IFNULL(ROUND(SUM(STOCK_LEVEL * COST),3),0)  AS AMOUNT FROM (SELECT P.I_PRODUCT,IFNULL(SUM(PS.QUANTITY-PS.SOLD_QUANTITY),0) AS STOCK_LEVEL,ROUND(SUM(PAYMENT_AMOUNT)/SUM(QUANTITY),3) as COST\n" +
                "FROM PRODUCTS P LEFT OUTER JOIN (SELECT I_PRODUCT,QUANTITY, SOLD_QUANTITY,PAYMENT_AMOUNT FROM PRODUCT_STEPUPS PS LEFT JOIN ORDER_PRODUCT_STEPUPS OS ON PS.I_ORDER_PRODUCT_STEPUP=OS.I_ORDER_PRODUCT_STEPUP \n" +
                "WHERE if(ORDER_TIME is null,?1,ORDER_TIME) < ?2) PS ON P.I_PRODUCT=PS.I_PRODUCT\n" +
                "GROUP BY P.I_PRODUCT ORDER BY PRODUCT_CODE) A\n" +
                "UNION ALL\n" +
                "SELECT ?4 AS TYPE, ROUND(IFNULL((SELECT SUM(IFNULL(TOTAL_PRICE,0)-IFNULL(DISCOUNT,0)-IFNULL(TOTAL_PAYMENT,0))\n" +
                "FROM CUSTOMER_ORDERS WHERE ORDER_TIME BETWEEN ?1 AND ?2),0)-IFNULL((SELECT SUM(IFNULL(TOTAL_PRICE-TOTAL_PAID,0))\n" +
                "FROM CUSTOMER_ORDER_RETURNS WHERE CUSTOMER_ORDER_RETURN_TIME BETWEEN ?1 AND ?2),0)-IFNULL(\n" +
                "(SELECT SUM(TOTAL_PAYMENT) + SUM(if(DISCOUNT is null,0,DISCOUNT)) FROM CUSTOMER_PAYMENTS WHERE PAYMENT_TIME BETWEEN ?1 AND ?2 ),0),3) AS AMOUNT\n" +
                "UNION ALL\n" +
                "SELECT ?5 AS Type,IFNULL((SELECT SUM(AMOUNT) FROM ACCOUNT_TRANSACTIONS WHERE TRANSACTION_TIME BETWEEN ?1 AND ?2),0) + IFNULL((SELECT SUM(AMOUNT) FROM WITHDRAW WHERE WITHDRAW_TIME BETWEEN ?1 AND ?2),0) AS AMOUNT\n" +
                "UNION ALL\n" +
                "SELECT ?6 AS TYPE, (ROUND(IFNULL((SELECT SUM(IFNULL(TOTAL_PRICE,0)-IFNULL(TOTAL_PAYMENT,0) -IFNULL(O.DISCOUNT,0) ) FROM ORDER_PRODUCT_STEPUPS O\n" +
                " WHERE ORDER_TIME BETWEEN ?1 AND ?2),0)-IFNULL((SELECT SUM(TOTAL_PAYMENT) + SUM(IF(vp.DISCOUNT IS NULL,0,vp.DISCOUNT)) FROM VENDOR_PAYMENTS vp WHERE PAYMENT_TIME BETWEEN ?1 AND ?2 ),0)\n" +
                "-IFNULL((SELECT SUM(AMOUNT) FROM VENDOR_RETURNS WHERE Time  BETWEEN ?1 AND ?2),0) ,3)*-1) AMOUNT\n");

        query.setParameter(1,from);
        query.setParameter(2,to);
        query.setParameter(3,messageSource.getMessage("netProfit.stock", null, locale));
        query.setParameter(4,messageSource.getMessage("netProfit.customer_loan", null, locale));
        query.setParameter(5,messageSource.getMessage("netProfit.account", null, locale));
        query.setParameter(6,messageSource.getMessage("netProfit.vendor_loan", null, locale));


        List<NetProfitD> netProfitDS = new ArrayList<>();

        List<Object[]> resultList = query.getResultList();

        for (Object[] row : resultList) {
            NetProfitD netProfitD = new NetProfitD();
            netProfitD.setType(row[0]+"");
            netProfitD.setAmount(Double.parseDouble(row[1]+""));

            netProfitDS.add(netProfitD);
        }
        return netProfitDS;
    }


    public List<NetProfitD> findNetProfitOldBalance(Date from, Locale locale) {
        Query query = em.createNativeQuery("SELECT ?2 AS TYPE, ROUND(IFNULL((SELECT SUM(IFNULL(TOTAL_PRICE,0)-IFNULL(DISCOUNT,0)-IFNULL(TOTAL_PAYMENT,0))\n" +
                "FROM CUSTOMER_ORDERS WHERE ORDER_TIME < ?1),0)-IFNULL((SELECT SUM(IFNULL(TOTAL_PRICE-TOTAL_PAID,0))\n" +
                "FROM CUSTOMER_ORDER_RETURNS WHERE CUSTOMER_ORDER_RETURN_TIME < ?1),0)-IFNULL(\n" +
                "(SELECT SUM(TOTAL_PAYMENT) + SUM(if(DISCOUNT is null,0,DISCOUNT)) FROM CUSTOMER_PAYMENTS WHERE PAYMENT_TIME < ?1 ),0),3) AS AMOUNT\n" +
                "UNION ALL\n" +
                "SELECT ?3 AS Type,IFNULL((SELECT SUM(AMOUNT) FROM ACCOUNT_TRANSACTIONS WHERE TRANSACTION_TIME <?1),0) + IFNULL((SELECT SUM(AMOUNT) FROM WITHDRAW WHERE WITHDRAW_TIME <?1),0) AS AMOUNT \n" +
                "UNION ALL\n" +
                "SELECT ?4 AS TYPE, (ROUND(IFNULL((SELECT SUM(IFNULL(TOTAL_PRICE,0)-IFNULL(TOTAL_PAYMENT,0) -IFNULL(O.DISCOUNT,0) ) FROM ORDER_PRODUCT_STEPUPS O\n" +
                " WHERE ORDER_TIME < ?1),0)-IFNULL((SELECT SUM(TOTAL_PAYMENT) + SUM(IF(vp.DISCOUNT IS NULL,0,vp.DISCOUNT)) FROM VENDOR_PAYMENTS vp WHERE PAYMENT_TIME < ?1 ),0)\n" +
                "-IFNULL((SELECT SUM(AMOUNT) FROM VENDOR_RETURNS WHERE Time < ?1),0) ,3)*-1) AMOUNT\n");

        query.setParameter(1,from);
        query.setParameter(2,messageSource.getMessage("netProfit.customer_loan", null, locale));
        query.setParameter(3,messageSource.getMessage("netProfit.account", null, locale));
        query.setParameter(4,messageSource.getMessage("netProfit.vendor_loan", null, locale));

        List<NetProfitD> netProfitDS = new ArrayList<>();

        List<Object[]> resultList = query.getResultList();

        for (Object[] row : resultList) {
            NetProfitD netProfitD = new NetProfitD();
            netProfitD.setType(row[0]+"");
            netProfitD.setAmount(Double.parseDouble(row[1]+""));

            netProfitDS.add(netProfitD);
        }
        return netProfitDS;
    }
}
