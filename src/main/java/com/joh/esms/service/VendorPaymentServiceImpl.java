package com.joh.esms.service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joh.esms.dao.VendorPaymentDAO;
import com.joh.esms.domain.model.AccountTransactionType;
import com.joh.esms.model.AccountTransaction;
import com.joh.esms.model.VendorPayment;

@Service
public class VendorPaymentServiceImpl implements VendorPaymentService {

	@Autowired
	private VendorPaymentDAO vendorPaymentDAO;

	@Autowired
	private AccountTransactionService accountTransactionService;

	@Override
	public List<VendorPayment> findAllByTimeBetween(Date from, Date to) {
		return vendorPaymentDAO.findAllByTimeBetween(from, to);
	}

	@Override
	public List<VendorPayment> findAllByVendorId(int id) {
		return vendorPaymentDAO.findAllByVendorId(id);
	}

	@Override
	@Transactional
	public VendorPayment save(VendorPayment vendorPayment) {

		vendorPayment = vendorPaymentDAO.save(vendorPayment);

		accountTransactionService.makeTransaction(AccountTransactionType.VENDOR_PAYMENT, vendorPayment.getId(),
				vendorPayment.getTotalPayment());

		return vendorPayment;
	}

	@Override
	@Transactional
	public void delete(int id) {

		AccountTransaction accountTransaction = accountTransactionService.findAccountTransaction(id,
				AccountTransactionType.VENDOR_PAYMENT);
		if (accountTransaction != null)
			accountTransactionService.delete(accountTransaction.getId());


		vendorPaymentDAO.delete(id);

	}

	@Override
	public VendorPayment findOne(int id) {
		return vendorPaymentDAO.findOne(id);
	}

	@Override
	public VendorPayment update(VendorPayment vendorPayment) {
		if (vendorPaymentDAO.findOne(vendorPayment.getId()) == null)
			throw new EntityNotFoundException("Vendor Payment not found with id=" + vendorPayment.getId());

		return save(vendorPayment);
	}
}
