package com.joh.esms.service;

import com.joh.esms.dao.CustomerOrderDAO;
import com.joh.esms.dao.ProductDAO;
import com.joh.esms.dao.ProductStepUpDAO;
import com.joh.esms.dao.VendorReturnDAO;
import com.joh.esms.domain.model.AccountTransactionType;
import com.joh.esms.domain.model.ProductD;
import com.joh.esms.exception.ItemNotAvaiableException;
import com.joh.esms.model.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class VenderReturnServiceImpl implements VenderReturnService {

	private static final Logger logger = Logger.getLogger(VenderReturnServiceImpl.class);

	@Autowired
	private VendorReturnDAO vendorReturnDAO;

	@Autowired
	private ProductStepUpDAO productStepUpDAO;

	@Autowired
	private ProductDAO productDAO;

	@Autowired
	private AccountTransactionService accountTransactionService;

	@Autowired
	private ProductStockService productStockService;

	@Override
	@Transactional
	public VenderReturns save(VenderReturns venderReturns) {
	    double totalPrice=0;
		for (VenderReturnsDetail venderReturnsDetail : venderReturns.getVenderReturnsDetail()) {
			Product product = null;

			Product productD = venderReturnsDetail.getProduct();


			int i = 0;
			for (i = 0; i < venderReturnsDetail.getQTY(); i++) {
				int amount = 1;
				final ProductStepUp itemForStockDown = productStepUpDAO
						.findProductStepUpForStockDown(venderReturnsDetail.getProduct().getCode(), amount);

				if (itemForStockDown == null) {

					String message = String.format("This product (%s) is not avaiable enough in the stock",
							venderReturnsDetail.getProduct().getCode());

					throw new ItemNotAvaiableException(message);
				}

				if (i == 0 && itemForStockDown != null) {
					product = itemForStockDown.getProduct();

				}


				productStepUpDAO.stockDown(itemForStockDown.getId(), amount);
			}



		}

		totalPrice = venderReturns.getAmount();


		venderReturns  = vendorReturnDAO.save(venderReturns);

		accountTransactionService.makeTransaction(AccountTransactionType.VENDOR_RETURN, venderReturns.getId(),
				venderReturns.getAmount());

		venderReturns.getVenderReturnsDetail().forEach(e -> {
			productStockService.stepDown(e.getStock().getId(), e.getProduct().getId(), e.getQTY());
		});

		return venderReturns;
	}

    @Override
   public List<VenderReturns> findAllByTimeBetween(Date from, Date to){
	    return vendorReturnDAO.findAllByTimeBetween(from,to);
   }

   @Override
   public VenderReturns findone(int id){
	    return vendorReturnDAO.findOne(id);
   }

    @Override
    @Transactional
    public void delete(int id) {
        AccountTransaction accountTransaction = accountTransactionService.findAccountTransaction(id,
                AccountTransactionType.VENDOR_RETURN);
        if (accountTransaction != null)
            accountTransactionService.delete(accountTransaction.getId());

        VenderReturns venderReturns = vendorReturnDAO.findOne(id);

        venderReturns.getVenderReturnsDetail().forEach(e -> {
            productStockService.stepUp(e.getStock().getId(), e.getProduct().getId(), e.getQTY());

        });

        vendorReturnDAO.delete(id);
    }

}
