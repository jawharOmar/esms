package com.joh.esms.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joh.esms.dao.UserDAO;
import com.joh.esms.domain.model.StockColumnD;
import com.joh.esms.model.*;
import com.joh.esms.service.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.joh.esms.domain.model.ProductD;
import com.joh.esms.domain.model.ProductTransactionD;
import com.joh.esms.validator.ProductValidation;

@Controller()
@RequestMapping(path = "/products")
public class ProductController {

	private static final Logger logger = Logger.getLogger(ProductController.class);

	@Autowired
	private ProductService productService;

	@Autowired
	private UserDAO userDAO;

	@Autowired
	AppUserDetailService appUserDetailService;

	@Autowired
	private ProductCategorySevice productCategorySevice;

	@Autowired
	private PriceCategoryService priceCategoryService;

	@Autowired
	private ProductUnitTypeService productUnitTypeService;

	@Autowired
	private CustomerOrderDetailService customerOrderDetailService;

	@Autowired
	private ProductStepUpService productStepUpService;

	@Autowired
	private AttachedFileService attachedFileService;

	@Autowired
	private ProductStockService productStockService;

	@Autowired
	private StockService stockService;

	@GetMapping(path = "/add")
	private String getAddingProduct(Model model) {
		logger.info("getAddingProduct->fired");

		Product product = new Product();

		Iterable<ProductCategory> productCategories = productCategorySevice.findAll();

		logger.info("productCategories=" + productCategories);

		Iterable<ProductUnitType> productUnitTypes = productUnitTypeService.findAll();
		logger.info("productUnitTypes=" + productUnitTypes);

		product.setProductPriceCategories(priceCategoryService.findAll().stream().map(e -> {
			ProductPriceCategory p = new ProductPriceCategory();
			p.setPriceCategory(e);
			return p;
		}).collect(Collectors.toList()));

		model.addAttribute("productCategories", productCategories);
		model.addAttribute("productUnitTypes", productUnitTypes);

		logger.info("product=" + product);

		model.addAttribute("product", product);

		return "product/addProduct";
	}

	@PostMapping(path = "/add")
	public String addProduct(@RequestParam MultipartFile file,
			@Validated(ProductValidation.Insert.class) Product product, BindingResult result, Model model)
			throws IOException {
		logger.info("addProduct->fired");

		logger.info("product=" + product);

		logger.info("error=" + result.getAllErrors());
		if (result.hasErrors()) {

			Iterable<ProductCategory> productCategories = productCategorySevice.findAll();

			logger.info("productCategories=" + productCategories);

			Iterable<ProductUnitType> productUnitTypes = productUnitTypeService.findAll();
			logger.info("productUnitTypes=" + productUnitTypes);

			model.addAttribute("productCategories", productCategories);

			model.addAttribute("productUnitTypes", productUnitTypes);

			model.addAttribute("product", product);

			return "product/addProduct";
		} else {

			if (!file.isEmpty()) {
				AttachedFile attachedFile = attachedFileService.save(file);
				logger.info("attachedFile=" + attachedFile);
				product.setAttachedFile(attachedFile);
			}

			productService.save(product);
			return "success";
		}
	}

	@PostMapping(path = "/delete/{id}")
	@ResponseBody
	public String deleteProduct(@PathVariable int id) throws IOException {
		logger.info("deleteProduct->fired");
		logger.info("id=" + id);
		productService.delete(id);
		return "success";
	}

	@GetMapping(path = "/edit/{id}")
	public String getEditingProduct(@PathVariable int id, Model model) {
		logger.info("getEditingProduct->fired");
		logger.info("id=" + id);
		Iterable<ProductCategory> productCategories = productCategorySevice.findAll();

		logger.info("productCategories=" + productCategories);

		model.addAttribute("productCategories", productCategories);

		Iterable<ProductUnitType> productUnitTypes = productUnitTypeService.findAll();
		logger.info("productUnitTypes=" + productUnitTypes);
		model.addAttribute("productUnitTypes", productUnitTypes);

		Product product = productService.findOne(id);

		product.setProductPriceCategories(priceCategoryService.findAll().stream().map(e -> {
			ProductPriceCategory p;
			p = product.getProductPriceCategories().stream().filter(i -> i.getPriceCategory().getId() == e.getId())
					.findAny().orElse(null);
			if (p == null)
				p = new ProductPriceCategory();
			p.setPriceCategory(e);
			return p;
		}).collect(Collectors.toList()));

		logger.info("product=" + product);

		model.addAttribute("product", product);

		return "product/editProduct";
	}

	@PostMapping(path = "/update")
	public String updateProduct(@RequestParam MultipartFile file,
			@Validated(ProductValidation.Update.class) Product product, BindingResult result, Model model)
			throws IOException {
		logger.info("updateProduct->fired");
		logger.info("product=" + product);

		logger.info("error=" + result.getAllErrors());
		if (result.hasErrors()) {

			Iterable<ProductCategory> productCategories = productCategorySevice.findAll();

			logger.info("productCategories=" + productCategories);

			model.addAttribute("productCategories", productCategories);
			Iterable<ProductUnitType> productUnitTypes = productUnitTypeService.findAll();
			logger.info("productUnitTypes=" + productUnitTypes);
			model.addAttribute("productUnitTypes", productUnitTypes);

			model.addAttribute("product", product);
			return "product/editProduct";
		} else {

			AttachedFile oldAttachedFile = productService.findOne(product.getId()).getAttachedFile();

			logger.info("oldAttachedFile=" + oldAttachedFile);

			boolean newAttachedFileExists = false;
			if (!file.isEmpty()) {
				newAttachedFileExists = true;
				logger.info("attache new image to student");
				AttachedFile attachedFile = attachedFileService.save(file);
				logger.info("attachedFile=" + attachedFile);
				product.setAttachedFile(attachedFile);
			} else {
				product.setAttachedFile(productService.findOne(product.getId()).getAttachedFile());
			}

			productService.update(product);

			// Remove Old Image
			if (oldAttachedFile != null && newAttachedFileExists) {
				attachedFileService.delete(oldAttachedFile);
			}

			return "success";
		}

	}

	@GetMapping(path = "/find/code/{code}")
	@ResponseBody
	private ProductD getProductByCode(@PathVariable String code, @RequestParam(required = false) Integer customerId,
			@RequestParam(required = false) Integer stockId) {
		logger.info("getProductByCode->fired");

		logger.info("code=" + code);

		ProductD productD = productService.findProductByCode(code);
		if (customerId != null) {
			CustomerOrderDetail customerOrderDetail = customerOrderDetailService.findLastSoldToCustomer(customerId,
					productD.getProductId());
			if (customerOrderDetail != null)
				productD.setLastPrice(customerOrderDetail.getPrice());
		} else {
			productD.setLastPrice(productStepUpService.findLastPrice(productD.getProductId()));
		}

		if (stockId != null) {
			ProductStock productStock = productStockService.findOne(stockId, productD.getProductId());
			if (productStock != null)
				productD.setStockLevel(productStock.getAmount());
			else
				productD.setStockLevel(0.0);
		}

		return productD;
	}

	@GetMapping(path = "/find/productStepUpId/{productStepUpId}")
	@ResponseBody
	private ProductD getProductByProductStepUpId(@PathVariable int productStepUpId) {
		logger.info("getProductByProductStepUpId->fired");

		logger.info("productStepUpId=" + productStepUpId);

		ProductD productD = productService.findProductByProductStepUpId(productStepUpId);

		return productD;
	}

	@GetMapping(path = "{id}/customerOrderDetails")
	public String getCustomerOrderDetail(@PathVariable int id, Model model) {
		List<CustomerOrderDetail> customerOrderDetails = customerOrderDetailService.findAllByProductId(id);
		logger.info("customerOrderDetails=" + customerOrderDetails);
		model.addAttribute("customerOrderDetails", customerOrderDetails);
		return "product/customerOrderDetails";
	}

	@GetMapping(path = "/stock")
	private String getAdminStock(Model model, @RequestParam(name = "page", required = false) Integer page,
			@RequestParam(name = "show", required = false) Integer showPerPage,
			@RequestParam(name = "stockId", required = false) Integer stockId, Principal principal)
			throws JsonProcessingException {
		logger.info("getAdminStock->fired");
		List<ProductD> productDs;

		if (page == null) {
			page = 1;
		}
		if (showPerPage == null) {
			showPerPage = 10;
		}

		Integer totalPage = productService.findTotalPageStocks(showPerPage);

		if (stockId == null) {
			productDs = productService.findStockPaginate(showPerPage * (page - 1), showPerPage);
		} else {
			productDs = productService.findStockPaginateByStockId(stockId, showPerPage * (page - 1), showPerPage);
			Stock stock = stockService.findOne(stockId);
			model.addAttribute("stock", stock);
		}

		List<PriceCategory> priceCategories = priceCategoryService.findAll();
		Iterable<Stock> stocks = stockService.findAll();

		User user = userDAO.findByUserName(principal.getName());
		boolean cost_role = false;
		for (Role role : user.getRoles()) {
			if (role.getName().contains("ROLE_ADMIN") || role.getName().contains("ROLE_PROFIT")) {
				cost_role = true;
				break;
			}
		}

		ObjectMapper objectMapper = new ObjectMapper();

		model.addAttribute("cost_role", cost_role);
		model.addAttribute("productDs", objectMapper.writeValueAsString(productDs));

		model.addAttribute("priceCategories", objectMapper.writeValueAsString(priceCategories));
		model.addAttribute("stocks", stocks);

		model.addAttribute("currentPage", page);
		model.addAttribute("showPerPage", showPerPage);
		model.addAttribute("totalPage", totalPage);

		if (stockId == null) {
			productDs = productService.findStock();
		} else {
			productDs = productService.findStockByStockId(stockId);
		}

		double sumTotalCost = productDs.stream()
				.mapToDouble(
						e -> (e.getCost() != null && e.getStockLevel() != null ? e.getCost() * e.getStockLevel() : 0.0))
				.sum();

		model.addAttribute("sumTotalCost", sumTotalCost);

		return "adminStock";
	}

	@PostMapping(value = "/stock/download")
	public ResponseEntity<InputStreamResource> downloadExcelStock(
			@RequestParam(name = "stockId", required = false) Integer stockId, Locale local,
			@RequestBody List<StockColumnD> selectedColumn, Principal principal) throws IOException {
		List<ProductD> productDs;

		if (stockId != null) {
			productDs = productService.findStockByStockId(stockId);
		} else {
			productDs = productService.findStock();
		}

		User user = userDAO.findByUserName(principal.getName());
		boolean cost_role = false;
		for (Role role : user.getRoles()) {
			if (role.getName().contains("ROLE_ADMIN") || role.getName().contains("ROLE_PROFIT")) {
				cost_role = true;
				break;
			}
		}
		ByteArrayInputStream in = productService.stockToExcel(productDs, local, cost_role, selectedColumn);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=Stock.xlsx");

		return ResponseEntity.ok().headers(headers).body(new InputStreamResource(in));
	}

	@GetMapping(value = "/search/code", produces = "application/json; charset=utf-8")
	@ResponseBody
	private String searchByProductCode(@RequestParam("productCode") String productCode) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(productService.findByProductCode(productCode));
	}

	@GetMapping(value = "/search/name", produces = "application/json; charset=utf-8")
	@ResponseBody
	private String searchByProductName(@RequestParam("productName") String productName) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(productService.findByProductName(productName));
	}

	@GetMapping(value = "/search/category", produces = "application/json; charset=utf-8")
	@ResponseBody
	private String searchByProductCategory(@RequestParam("category") String category) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(productService.findByProductCategory(category));
	}

	@GetMapping(value = "/search/nameOrCode", produces = "application/json; charset=utf-8")
	@ResponseBody
	private String searchByProductNameOrCode(@RequestParam("keyword") String keyword) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(productService.findByProductNameOrCode(keyword));
	}

	@GetMapping(value = "/search/product", produces = "application/json; charset=utf-8")
	@ResponseBody
	private Object findByProductID(@RequestParam("productId") Integer productId,
			@RequestParam(name = "stockId", required = false) Integer stockId) {
		logger.info("findByProductID->fired");

		ProductD productD;
		if (stockId != null) {
			productD = productService.findByProductIdAndStockId(productId, stockId);
		} else {
			productD = productService.findByProductId(productId);
		}

		logger.info("productD=" + productD);

		return productD;
	}

	@GetMapping(value = "/search/productCategory", produces = "application/json; charset=utf-8")
	@ResponseBody
	private Object findByProductCategoryId(@RequestParam("categoryId") Integer categoryId,
			@RequestParam(name = "stockId", required = false) Integer stockId) {
		logger.info("findByProductCategoryId->fired");
		List<ProductD> productDS;

		if (stockId != null) {
			productDS = productService.findByProductCategoryAndStockId(categoryId, stockId);
		} else {
			productDS = productService.findByProductCategoryId(categoryId);
		}

		logger.info("productDS=" + productDS);
		return productDS;
	}

	@GetMapping(value = "/search/general", produces = "application/json; charset=utf-8")
	@ResponseBody
	private Object generalSearch(@RequestParam("keyword") String keyword,
			@RequestParam(name = "stockId", required = false) Integer stockId) {
		logger.info("generalSearch->fired");
		List<ProductD> productDS;

		if (stockId != null) {
			logger.info("" + stockId);
			productDS = productService.findStockByStockIdAndKeyword(stockId, keyword);
		} else {
			logger.info("findStockByKeyword->fired");
			productDS = productService.findStockByKeyword(keyword);
		}

		logger.info("productDS=" + productDS);
		return productDS;
	}

	@GetMapping(path = "/stock_info")
	private String getAdminStock_info(Model model, @RequestParam(required = false) Integer stockId, Principal principal)
			throws JsonProcessingException {

		List<ProductD> productDs = null;

		if (stockId == null) {
			productDs = productService.findStock();
		} else {
			productDs = productService.findStockByStockId(stockId);
			Stock stock = stockService.findOne(stockId);
			model.addAttribute("stock", stock);
		}

		List<PriceCategory> priceCategories = priceCategoryService.findAll();
		Iterable<Stock> stocks = stockService.findAll();
		User user = userDAO.findByUserName(principal.getName());
		boolean cost_role = false;
		for (Role role : user.getRoles()) {
			if (role.getName().contains("ROLE_ADMIN") || role.getName().contains("ROLE_PROFIT")) {
				cost_role = true;
				break;
			}
		}

		ObjectMapper objectMapper = new ObjectMapper();

		model.addAttribute("products", objectMapper.writeValueAsString(productDs));

		model.addAttribute("cost_role", cost_role);
		model.addAttribute("productDs", productDs);
		model.addAttribute("priceCategories", priceCategories);
		model.addAttribute("stocks", stocks);

		return "admin/stock_load";
	}

	@GetMapping(path = "/stock/{productCategoryId}")
	private String getAdminStockByProductCategory(@PathVariable int productCategoryId, Model model) {
		logger.info("getAdminStockByProductCategory->fired");

		List<ProductD> productDs = productService.findStockByProductCategoryId(productCategoryId);

		List<PriceCategory> priceCategories = priceCategoryService.findAll();

		logger.info("productDs=" + productDs);

		model.addAttribute("productDs", productDs);
		model.addAttribute("priceCategories", priceCategories);

		return "adminStock";
	}

	@GetMapping(path = "/printBarcode/{id}")
	public String printBarcode(@PathVariable int id, Model model) {
		logger.info("printBarcode->fired");

		Product product = productService.findOne(id);

		logger.info("product=" + product);

		model.addAttribute("product", product);

		return "product/printProductBarcode";
	}

	@GetMapping(path = "/transactionDetail")
	public String getTransactionDetail(@RequestParam int stockId, @RequestParam int productId, Model model) {
		logger.info("getTransactionDetail->fired");

		List<ProductTransactionD> productTransactionDs = productService.findProductTransactionDetailByStockId(productId,
				stockId);

		return "productTransactionDs";
	}

	@GetMapping(path = "/find/cost/{code}")
	@ResponseBody
	public Double findCostByProductCode(@PathVariable("code") String code) {
		if (code != null) {
			return productService.findCostByProductCode(code);
		}
		return 0.0;
	}

}
