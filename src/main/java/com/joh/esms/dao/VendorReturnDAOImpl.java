package com.joh.esms.dao;


import com.joh.esms.controller.VendorReturnController;
import com.joh.esms.model.VenderReturns;
import com.joh.esms.model.VenderReturnsDetail;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public class VendorReturnDAOImpl implements VendorReturnDAOExt {
    private static final Logger logger = Logger.getLogger(VendorReturnController.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    public void delete(int id) {
        VenderReturns venderReturns = em.find(VenderReturns.class, id);

        if (venderReturns == null)
            throw new EntityNotFoundException("venderReturns not found with id=" + id);


        for (VenderReturnsDetail venderReturnsDetail : venderReturns.getVenderReturnsDetail()) {
            logger.info("venderReturnsDetail=" + venderReturnsDetail);

            for (int i = 0; i < venderReturnsDetail.getQTY(); i++) {
                int quantity = 1;
                Query result = em.createNativeQuery(
                        "SELECT I_PRODUCT_STEPUP FROM PRODUCT_STEPUPS WHERE I_PRODUCT=:i_product AND SOLD_QUANTITY>0 ORDER BY I_PRODUCT_STEPUP DESC LIMIT 1");
                result.setParameter("i_product", venderReturnsDetail.getProduct().getId());


                if(result.getSingleResult().toString()==null){
                    throw new EntityNotFoundException("not found product order to return amount");
                }
                int I_PRODUCT_STEPUP = Integer.parseInt(result.getSingleResult().toString());
                logger.info("I_PRODUCT_STEPUP=" + I_PRODUCT_STEPUP);

                Query query = em.createNativeQuery(
                        "UPDATE PRODUCT_STEPUPS SET SOLD_QUANTITY=SOLD_QUANTITY-:quantity WHERE I_PRODUCT_STEPUP=:id");
                query.setParameter("id", I_PRODUCT_STEPUP);
                query.setParameter("quantity", quantity);
                query.executeUpdate();
            }


            em.remove(venderReturnsDetail);

        }
        em.remove(venderReturns);
        logger.info("customerOrder=" + venderReturns);
    }
}
