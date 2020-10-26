package com.joh.esms.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import com.joh.esms.domain.model.*;
import com.joh.esms.model.PriceCategory;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.joh.esms.controller.ProductController;
import com.joh.esms.dao.ProductDAO;
import com.joh.esms.exception.CusDataIntegrityViolationException;
import com.joh.esms.exception.ItemExistsException;
import com.joh.esms.model.Product;
import com.joh.esms.model.ProductPriceCategory;

@Service
public class ProductServiceImpl implements ProductService {

	private static final Logger logger = Logger.getLogger(ProductServiceImpl.class);

	@Autowired
	private ProductDAO productDAO;

	@Autowired
	private AttachedFileService attachedFileService;

	@Autowired
	private ProductPriceCategoryService productPriceCategoryService;

	@Autowired
	private PriceCategoryService priceCategoryService;

	@Autowired
	private MessageSource messageSource;

	@Override
	public Product findOne(int id) {
		return productDAO.findOne(id);
	}

	@Override
	public Iterable<Product> findAll() {
		return productDAO.findAll();
	}

	@Override
	@Transactional
	public Product save(Product product) {

		if (product.getProductUnitType() != null && product.getProductUnitType().getId() == 1
				&& (product.getPacketSize() == null || product.getPacketSize() <= 0)) {
			throw new CusDataIntegrityViolationException("Packet size is not set");
		}

		if (product.getProductUnitType() == null || product.getProductUnitType().getId() != 1) {
			product.setPacketSize(null);
		}

		try {
			product.getProductPriceCategories().removeIf(e -> e.getPrice() == null || e.getPrice() == 0);
			return productDAO.save(product);
		} catch (DataIntegrityViolationException e) {
			throw new ItemExistsException(e.getMessage());
		}
	}

	@Override
	public List<ProductD> findStock() {
		List<ProductD> productDs = productDAO.findStock();

		List<Integer> ids = productDs.stream().map(e -> e.getProductId()).collect(Collectors.toList());

		if (ids.size() > 0) {
			List<ProductPriceCategory> productPriceCategories = productPriceCategoryService.findAllByProductIds(ids);

			productDs.forEach(e -> {
				List<ProductPriceCategory> productProductPriceCategory = productPriceCategories.stream()
						.filter(i -> i.getPorductId() == e.getProductId()).collect(Collectors.toList());
				e.setProductPriceCategories(productProductPriceCategory);
			});

		}

		return productDs;
	}

	@Override
	public List<ProductD> findStockByStockId(int stockId) {
		List<ProductD> productDs = productDAO.findStockByStockId(stockId);

		List<Integer> ids = productDs.stream().map(e -> e.getProductId()).collect(Collectors.toList());

		List<ProductPriceCategory> productPriceCategories = productPriceCategoryService.findAllByProductIds(ids);

		productDs.forEach(e -> {
			List<ProductPriceCategory> productProductPriceCategory = productPriceCategories.stream()
					.filter(i -> i.getPorductId() == e.getProductId()).collect(Collectors.toList());
			e.setProductPriceCategories(productProductPriceCategory);
		});

		return productDs;
	}

	@Override
	public List<ProductD> findStockByProductCategoryId(int id) {
		List<ProductD> productDs = productDAO.findStockByProductCategoryId(id);

		List<Integer> ids = productDs.stream().map(e -> e.getProductId()).collect(Collectors.toList());

		List<ProductPriceCategory> productPriceCategories = productPriceCategoryService.findAllByProductIds(ids);

		productDs.forEach(e -> {
			List<ProductPriceCategory> productProductPriceCategory = productPriceCategories.stream()
					.filter(i -> i.getPorductId() == e.getProductId()).collect(Collectors.toList());
			e.setProductPriceCategories(productProductPriceCategory);
		});

		return productDs;
	}

	@Override
	@Transactional
	public void delete(int id) throws IOException {
		Product product = productDAO.findOne(id);
		if (product.getAttachedFile() != null)
			attachedFileService.delete(product.getAttachedFile());
		productDAO.delete(id);
	}

	@Override
	@Transactional
	public Product update(Product product) {
		productDAO.findOne(product.getId());
		// pack id=1
		if (product.getProductUnitType() != null && product.getProductUnitType().getId() == 1
				&& (product.getPacketSize() == null || product.getPacketSize() <= 0)) {
			throw new CusDataIntegrityViolationException("Packet size is not set");
		}

		if (product.getProductUnitType() == null || product.getProductUnitType().getId() != 1) {
			product.setPacketSize(null);
		}

		return productDAO.save(product);
	}

	@Override
	public ProductD findProductByCode(String code) {
		try {
			ProductD findProductByCode = productDAO.findProductByCode(code);
			Product findOne = productDAO.findOne(findProductByCode.getProductId());
			findProductByCode.setProductPriceCategories(findOne.getProductPriceCategories());

			if (findOne.getAttachedFile() != null)
				findProductByCode.setAttachedFile(findOne.getAttachedFile());
			return findProductByCode;
		} catch (EmptyResultDataAccessException e) {
			throw new EntityNotFoundException();
		}

	}

	@Override
	public ProductD findProductByProductStepUpId(int productStepUpId) {
		try {
			return productDAO.findProductByProductStepUpId(productStepUpId);
		} catch (EmptyResultDataAccessException e) {
			throw new EntityNotFoundException();
		}

	}

	@Override
	public List<ProductTransactionD> findProductTransactionDetailByStockId(int productId, int stockId) {
		return productDAO.findProductTransactionDetailByStockId(productId, stockId);
	}

	@Override
	public Double findCostByProductCode(String code) {
		return productDAO.findCostByProductCode(code);
	}

	@Override
	public Integer findTotalPageStocks(Integer showPerPage) {
		return productDAO.findTotalPageStocks(showPerPage);
	}

	@Override
	public List<ProductD> findStockPaginate(Integer offset, Integer showPerPage) {
		List<ProductD> productDs = productDAO.findStockPaginate(offset, showPerPage);

		List<Integer> ids = productDs.stream().map(e -> e.getProductId()).collect(Collectors.toList());

		if (ids.size() > 0) {
			List<ProductPriceCategory> productPriceCategories = productPriceCategoryService.findAllByProductIds(ids);

			productDs.forEach(e -> {
				List<ProductPriceCategory> productProductPriceCategory = productPriceCategories.stream()
						.filter(i -> i.getPorductId() == e.getProductId()).collect(Collectors.toList());
				e.setProductPriceCategories(productProductPriceCategory);
			});
		}

		return productDs;
	}

	@Override
	public List<ProductD> findStockPaginateByStockId(Integer stockId, Integer offset, Integer showPerPage) {
		logger.info("findStockPaginateByStockId->fired");
		List<ProductD> productDs = productDAO.findStockPaginateByStockId(stockId, offset, showPerPage);

		List<Integer> ids = productDs.stream().map(e -> e.getProductId()).collect(Collectors.toList());

		List<ProductPriceCategory> productPriceCategories = productPriceCategoryService.findAllByProductIds(ids);

		productDs.forEach(e -> {
			List<ProductPriceCategory> productProductPriceCategory = productPriceCategories.stream()
					.filter(i -> i.getPorductId() == e.getProductId()).collect(Collectors.toList());
			e.setProductPriceCategories(productProductPriceCategory);
		});

		return productDs;
	}

	// ----------------------
	// Index For Excel File
	private static int index = -1;

	private static int index() {
		index++;
		return index;
	}

	@Override
	public ByteArrayInputStream stockToExcel(List<ProductD> productDs, Locale local, boolean cost_role,
			List<StockColumnD> selectedColumn) throws IOException {
		String[] columnsFixed = { "stock.code", "stock.name", "stock.category", "stock.unitType", "stock.stockLevel",
				"stock.stockPacketLevel", "stock.lastPrice", "stock.cost", "stock.totalCost", "stock.packetSize",
				"stock.minimumStockLevel", "stock.price" };

		// Export Column if True
		boolean code, name, category, unitType, stockLevel, stockPacketLevel, lastPrice, cost, totalCost, packetSize,
				minimumStockLevel, price;
		code = name = category = unitType = stockLevel = stockPacketLevel = lastPrice = cost = totalCost = packetSize = minimumStockLevel = price = true;

		ArrayList<String> arrayOfColumn = new ArrayList<>();
		ArrayList<String> existCol = new ArrayList<>();

		if (!cost_role) {
			totalCost = lastPrice = cost = false;
			existCol.add(messageSource.getMessage(columnsFixed[6], null, local));
			existCol.add(messageSource.getMessage(columnsFixed[7], null, local));
			existCol.add(messageSource.getMessage(columnsFixed[8], null, local));
		}

		for (String columnName : columnsFixed) {
			String cname = messageSource.getMessage(columnName, null, local);
			for (StockColumnD tableColumn : selectedColumn) {
				boolean exist = false;
				if (code && columnName.equals(columnsFixed[0]) && cname.equals(tableColumn.getName())) {
					code = false;
					exist = true;
				} else if (name && columnName.equals(columnsFixed[1]) && cname.equals(tableColumn.getName())) {
					name = false;
					exist = true;
				} else if (category && columnName.equals(columnsFixed[2]) && cname.equals(tableColumn.getName())) {
					category = false;
					exist = true;
				} else if (unitType && columnName.equals(columnsFixed[3]) && cname.equals(tableColumn.getName())) {
					unitType = false;
					exist = true;
				} else if (stockLevel && columnName.equals(columnsFixed[4]) && cname.equals(tableColumn.getName())) {
					stockLevel = false;
					exist = true;
				} else if (stockPacketLevel && columnName.equals(columnsFixed[5])
						&& cname.equals(tableColumn.getName())) {
					stockPacketLevel = false;
					exist = true;
				} else if (lastPrice && columnName.equals(columnsFixed[6]) && cname.equals(tableColumn.getName())) {
					lastPrice = false;
					exist = true;
				} else if (cost && columnName.equals(columnsFixed[7]) && cname.equals(tableColumn.getName())) {
					cost = false;
					exist = true;
				} else if (totalCost && columnName.equals(columnsFixed[8]) && cname.equals(tableColumn.getName())) {
					totalCost = false;
					exist = true;
				} else if (packetSize && columnName.equals(columnsFixed[9]) && cname.equals(tableColumn.getName())) {
					packetSize = false;
					exist = true;
				} else if (minimumStockLevel && columnName.equals(columnsFixed[10])
						&& cname.equals(tableColumn.getName())) {
					minimumStockLevel = false;
					exist = true;
				} else if (price && columnName.equals(columnsFixed[11]) && cname.equals(tableColumn.getName())) {
					price = false;
					exist = true;
				}
				if (exist) {
					existCol.add(tableColumn.getName());
				}
			}

			if (!existCol.contains(cname)) {
				arrayOfColumn.add(cname);
			}

		}
		List<PriceCategory> priceCategories = priceCategoryService.findAll();
		for (PriceCategory priceCategory : priceCategories) {
			boolean exist = false;
			for (StockColumnD tableColumn : selectedColumn) {
				if (priceCategory.getName().equals(tableColumn.getName())) {
					exist = true;
					break;
				}
			}
			if (!exist) {
				arrayOfColumn.add(priceCategory.getName());
			}
		}

		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			CreationHelper createHelper = workbook.getCreationHelper();

			Sheet sheet = workbook.createSheet("Stock");

			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			headerFont.setColor(IndexedColors.BLUE.getIndex());

			CellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFont(headerFont);

			CellRangeAddress mergedRegion = new CellRangeAddress(0, 0, 0, arrayOfColumn.size() - 1);
			sheet.addMergedRegion(mergedRegion);
			CellRangeAddress mergedRegion2 = new CellRangeAddress(1, 1, 0, arrayOfColumn.size() - 1);
			sheet.addMergedRegion(mergedRegion2);

			Row titlePage = sheet.createRow(0);
			Cell cell1 = titlePage.createCell(0);
			cell1.setCellValue("Stock");
			// set horizontal alignment center
			CellUtil.setCellStyleProperty(cell1, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);

			Row title = sheet.createRow(1);
			Cell cell2 = title.createCell(0);

			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-M-dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();

			cell2.setCellValue(dtf.format(now));
			// set horizontal alignment center
			CellUtil.setCellStyleProperty(cell2, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);

			// Row for Header
			Row headerRow = sheet.createRow(2);
			// Header
			for (int col = 0; col < arrayOfColumn.size(); col++) {
				Cell cell = headerRow.createCell(col);
				cell.setCellValue(arrayOfColumn.get(col));
				cell.setCellStyle(headerCellStyle);
			}
			// CellStyle for Age
			CellStyle ageCellStyle = workbook.createCellStyle();
			ageCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("#"));

			int rowIdx = 3;
			double sumTotalCost = 0.0;
			// Total Cost Index For Summation Result Must Be Under Total Cost
			// Default Value (1)
			int totalCostIndex = 1;
			for (ProductD productD : productDs) {

				index = -1;
				Row row = sheet.createRow(rowIdx++);
				if (code) {
					row.createCell(index()).setCellValue(productD.getCode());
				}
				if (name) {
					row.createCell(index()).setCellValue(productD.getName());
				}
				if (category) {
					row.createCell(index()).setCellValue(productD.getCategory());
				}
				if (unitType) {
					row.createCell(index()).setCellValue(productD.getUnitType());
				}

				if (stockLevel) {
					row.createCell(index()).setCellValue((round(productD.getStockLevel(), 3) + " "
							+ (productD.getUnit() != null ? productD.getUnit() : "")));
				}
				if (stockPacketLevel) {
					row.createCell(index())
							.setCellValue((productD.getPacketSize() != null
									? round((productD.getStockLevel() / productD.getPacketSize()), 3)
									: 0));
				}

				if (cost_role) {
					if (lastPrice) {
						row.createCell(index()).setCellValue(
								(productD.getLastPrice() != null ? round(productD.getLastPrice(), 3) : 0));
					}

					if (cost) {
						row.createCell(index())
								.setCellValue((productD.getCost() != null ? round(productD.getCost(), 3) : 0));
					}

					if (totalCost) {
						int index = index();
						totalCostIndex = index;
						sumTotalCost += productD.getCost() != null
								? round((productD.getStockLevel() * productD.getCost()), 3)
								: 0;
						row.createCell(index)
								.setCellValue((productD.getCost() != null
										? round((productD.getStockLevel() * productD.getCost()), 3)
										: 0));
					}
				}

				if (packetSize) {
					row.createCell(index())
							.setCellValue((productD.getPacketSize() != null ? productD.getPacketSize() : 0));
				}
				if (minimumStockLevel) {
					row.createCell(index()).setCellValue(
							(productD.getMinimumStockLevel() != null ? productD.getMinimumStockLevel() : 0));
				}
				if (price) {
					row.createCell(index())
							.setCellValue((productD.getPrice() != null ? round(productD.getPrice(), 3) : 0));
				}

				for (PriceCategory priceCategory : priceCategories) {

					boolean exist = false;
					for (StockColumnD tableColumn : selectedColumn) {
						if (priceCategory.getName().equals(tableColumn.getName())) {
							exist = true;
							break;
						}
					}

					if (!exist) {
						if (priceCategory.getRatio() != null) {
							row.createCell(index())
									.setCellValue((productD.getCost() != null
											? round(productD.getCost()
													+ productD.getCost() * priceCategory.getRatio().doubleValue(), 3)
											: 0));
						} else {
							Double productPrice = null;
							for (ProductPriceCategory productPriceCategory : productD.getProductPriceCategories()) {
								if (productPriceCategory.getPriceCategory().getId() == priceCategory.getId()) {
									if (productPriceCategory.getPrice() != null) {
										productPrice = productPriceCategory.getPrice();
									} else {
										productPrice = 0.0;
									}
								}
							}
							if (productPrice != null) {
								row.createCell(index()).setCellValue(productPrice);
							}

							if (productD.getProductPriceCategories().size() == 0) {
								row.createCell(index()).setCellValue(0);
							}
						}
					}
				}
			}

			if (cost_role) {
				Row row = sheet.createRow(rowIdx++);
				Cell cell3 = row.createCell(0);
				cell3.setCellValue(messageSource.getMessage("stock.totalCost", null, local));

				row.createCell(totalCostIndex).setCellValue(new DecimalFormat(".###").format(round(sumTotalCost, 3)));
			}

			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		}

	}

	@Override
	public List<SearchInStockD> findByProductCode(String keyword) {
		return productDAO.findByProductCode(keyword);
	}

	@Override
	public List<SearchInStockD> findByProductName(String name) {
		return productDAO.findByProductName(name);
	}

	@Override
	public List<SearchInStockD> findByProductCategory(String category) {
		return productDAO.findByProductCategory(category);
	}

	@Override
	public List<SearchD> findByProductNameOrCode(String keyword) {
		return productDAO.findByProductNameOrCode(keyword);
	}

	@Override
	public ProductD findByProductIdAndStockId(Integer productId, Integer stockId) {
		ProductD productD = productDAO.findByProductIdAndStockId(productId, stockId);
		List<ProductPriceCategory> productPriceCategories = productPriceCategoryService
				.findAllByPorductId(productD.getProductId());

		List<ProductPriceCategory> productProductPriceCategory = productPriceCategories.stream()
				.filter(i -> i.getPorductId() == productD.getProductId()).collect(Collectors.toList());
		productD.setProductPriceCategories(productProductPriceCategory);

		return productD;
	}

	@Override
	public ProductD findByProductId(Integer productId) {
		ProductD productD = productDAO.findByProductId(productId);

		List<ProductPriceCategory> productPriceCategories = productPriceCategoryService
				.findAllByPorductId(productD.getProductId());

		List<ProductPriceCategory> productProductPriceCategory = productPriceCategories.stream()
				.filter(i -> i.getPorductId() == productD.getProductId()).collect(Collectors.toList());
		productD.setProductPriceCategories(productProductPriceCategory);

		return productD;
	}

	@Override
	public List<ProductD> findByProductCategoryAndStockId(Integer categoryId, Integer stockId) {
		return productDAO.findByProductCategoryAndStockId(categoryId, stockId);
	}

	@Override
	public List<ProductD> findByProductCategoryId(Integer categoryId) {
		List<ProductD> productDS = productDAO.findByProductCategoryId(categoryId);

		List<Integer> ids = productDS.stream().map(e -> e.getProductId()).collect(Collectors.toList());

		List<ProductPriceCategory> productPriceCategories = productPriceCategoryService.findAllByProductIds(ids);

		productDS.forEach(e -> {
			List<ProductPriceCategory> productProductPriceCategory = productPriceCategories.stream()
					.filter(i -> i.getPorductId() == e.getProductId()).collect(Collectors.toList());
			e.setProductPriceCategories(productProductPriceCategory);
		});

		return productDS;
	}

	@Override
	public List<ProductD> findStockByKeyword(String keyword) {

		List<ProductD> productDs = productDAO.findStockByKeyword(keyword);

		List<Integer> ids = productDs.stream().map(e -> e.getProductId()).collect(Collectors.toList());

		List<ProductPriceCategory> productPriceCategories = productPriceCategoryService.findAllByProductIds(ids);

		productDs.forEach(e -> {
			List<ProductPriceCategory> productProductPriceCategory = productPriceCategories.stream()
					.filter(i -> i.getPorductId() == e.getProductId()).collect(Collectors.toList());
			e.setProductPriceCategories(productProductPriceCategory);
		});

		return productDs;
	}

	@Override
	public List<ProductD> findStockByStockIdAndKeyword(Integer id, String keyword) {

		List<ProductD> productDs = productDAO.findStockByStockIdAndKeyword(id, keyword);

		List<Integer> ids = productDs.stream().map(e -> e.getProductId()).collect(Collectors.toList());

		List<ProductPriceCategory> productPriceCategories = productPriceCategoryService.findAllByProductIds(ids);

		productDs.forEach(e -> {
			List<ProductPriceCategory> productProductPriceCategory = productPriceCategories.stream()
					.filter(i -> i.getPorductId() == e.getProductId()).collect(Collectors.toList());
			e.setProductPriceCategories(productProductPriceCategory);
		});

		return productDs;
	}

	private static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

}
