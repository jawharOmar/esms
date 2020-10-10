package com.joh.esms.dao;


import com.joh.esms.domain.model.WastedProductsD;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Component
public class WastedProductsDAO {

    @PersistenceContext
    private EntityManager em;

    public List<WastedProductsD> findAllWastedProducts() {
        Query query = em.createNativeQuery("SELECT P.PRODUCT_CODE AS PRODUCT_CODE,P.PRODUCT_NAME AS PRODUCT_NAME,SUM(QUANTITY) AS QUANTITY,PC.CATEGORY_NAME AS CATEGORY_NAME FROM PRODUCTS P  \n" +
                "INNER JOIN CUSTOMER_WASTED_PRODUCT WP ON P.I_PRODUCT = WP.I_PRODUCT\n" +
                "INNER JOIN PRODUCT_CATEGORIES PC ON P.I_PRODUCT_CATEGORY = PC.I_PRODUCT_CATEGORY GROUP BY  P.I_PRODUCT;");

        List<WastedProductsD> wastedProductsDS = new ArrayList<>();


        List<Object[]> resultList = query.getResultList();

        for (Object[] row : resultList) {
            WastedProductsD wastedProductsD = new WastedProductsD();
            wastedProductsD.setCode((String) row[0]);
            wastedProductsD.setName((String) row[1]);
            wastedProductsD.setQuantity(Integer.parseInt(row[2]+"") );
            wastedProductsD.setCategory((String) row[3]);

            wastedProductsDS.add(wastedProductsD);
        }

        return wastedProductsDS;
    }
}
