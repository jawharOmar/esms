package com.joh.esms.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.joh.esms.dao.OrderPreProductDAO;
import com.joh.esms.model.AttachedFile;
import com.joh.esms.model.OrderPreProduct;
import com.joh.esms.model.PreProduct;

@Service
public class OrderPreProductServiceImpl implements OrderPreProductService {

	@Autowired
	private OrderPreProductDAO orderPreProductDAO;

	@Autowired
	private AttachedFileService attachedFileService;

	@Override
	public OrderPreProduct findOne(int id) {
		return orderPreProductDAO.findOne(id);
	}

	@Override
	public List<OrderPreProduct> findAllByOrderTimeBetween(Date from, Date to) {
		return orderPreProductDAO.findAllByOrderTimeBetween(from, to);
	}

	@Override
	@Transactional
	public OrderPreProduct save(OrderPreProduct orderPreProduct, MultipartFile[] files) throws IOException {
		for (int i = 0; i < files.length; i++) {
			MultipartFile file = files[i];
			if (!file.isEmpty())
				// Check the index is exist
				if (orderPreProduct.getPreProducts().size() >= i) {

					AttachedFile attachedFile = attachedFileService.save(file);
					orderPreProduct.getPreProducts().get(i).setAttachedFile(attachedFile);
				}
		}
		return orderPreProductDAO.save(orderPreProduct);
	}

	@Override
	@Transactional
	public OrderPreProduct update(OrderPreProduct orderPreProduct, MultipartFile[] files) throws IOException {

		OrderPreProduct old = orderPreProductDAO.findOne(orderPreProduct.getId());

		orderPreProduct.getPreProducts().forEach(e -> {
			if (e.getId() != 0) {
				Optional<PreProduct> optional = old.getPreProducts().stream().filter(p -> e.getId() == e.getId())
						.findFirst();
				if (optional.isPresent()) {
					e.setAttachedFile(optional.get().getAttachedFile());
					optional.get().setAttachedFile(null);// Do not let remove attached file
					e.setId(0);// Mark as detached for new insert
				}

			}
		});

		old.getPreProducts().forEach(e -> {
			if (e.getAttachedFile() != null) {
				try {
					attachedFileService.delete(e.getAttachedFile());
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		});
		orderPreProductDAO.delete(old.getId());
		orderPreProduct.setId(null);
		return save(orderPreProduct, files);
	}

	@Override
	@Transactional
	public void delete(int id) {
		OrderPreProduct orderPreProduct = orderPreProductDAO.findOne(id);

		orderPreProduct.getPreProducts().forEach(e -> {
			if (e.getAttachedFile() != null) {
				try {
					attachedFileService.delete(e.getAttachedFile());
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		});

		orderPreProductDAO.delete(id);
	}
}
