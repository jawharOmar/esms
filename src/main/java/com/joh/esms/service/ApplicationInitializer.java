package com.joh.esms.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.joh.esms.model.Account;
import com.joh.esms.model.ProductCategory;
import com.joh.esms.model.ProductUnitType;
import com.joh.esms.model.Role;
import com.joh.esms.model.User;

@Component
public class ApplicationInitializer {

	private static final Logger logger = Logger.getLogger(ApplicationInitializer.class);

	@Autowired
	private ProductUnitTypeService productUnitTypeService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private ProductCategorySevice productCategorySevice;

	@Autowired
	private StockService stockService;

	@EventListener(ContextRefreshedEvent.class)
	public void contextRefreshedEvent() {
		logger.info("Insert component into database");

		if (((Collection<ProductUnitType>) productUnitTypeService.findAll()).size() == 0) {

			ProductCategory productCategory = new ProductCategory();
			// pack id=1
			ProductUnitType productUnitType1 = new ProductUnitType();
			productUnitType1.setName("کارتۆن-package");

			ProductUnitType productUnitType2 = new ProductUnitType();
			productUnitType2.setName("دانە-item");

			productUnitTypeService.save(productUnitType1);
			productUnitType2 = productUnitTypeService.save(productUnitType2);

		}

		if (((Collection<Account>) accountService.findAll()).size() == 0) {

			Account account = new Account();
			account.setName("Main");
			account.setBalance(0d);

			accountService.save(account);
		}

		if (((Collection<Role>) roleService.findAll()).size() == 0) {

			String[] roleNames = { "ROLE_ADMIN", "ROLE_CUSTOMER_PAYMENT_EDIT", "ROLE_CUSTOMER_PAYMENT_WRITE",
					"ROLE_CUSTOMER_PAYMENT_READ", "ROLE_CUSTOMER_ORDER_RETURN_EDIT", "ROLE_CUSTOMER_ORDER_RETURN_WRITE",
					"ROLE_CUSTOMER_ORDER_RETURN_READ", "ROLE_CUSTOMER_ORDER_EDIT", "ROLE_CUSTOMER_ORDER_WRITE",
					"ROLE_CUSTOMER_ORDER_READ", "ROLE_CUSTOMER_EDIT", "ROLE_CUSTOMER_WRITE", "ROLE_CUSTOMER_READ",
					"ROLE_PRODUCT_CATEGORY_EDIT", "ROLE_PRODUCT_CATEGORY_WRITE", "ROLE_PRODUCT_CATEGORY_READ",
					"ROLE_STOCK_EDIT", "ROLE_STOCK_WRITE", "ROLE_STOCK_READ", "ROLE_STOCK_TRANSFER_EDIT",
					"ROLE_STOCK_TRANSFER_WRITE", "ROLE_STOCK_TRANSFER_READ", "ROLE_PRODUCT_EDIT", "ROLE_PRODUCT_WRITE",
					"ROLE_PRODUCT_READ", "ROLE_ORDER_PRODUCT_STEPUP_EDIT", "ROLE_ORDER_PRODUCT_STEPUP_WRITE",
					"ROLE_ORDER_PRODUCT_STEPUP_READ", "ROLE_VENDOR_EDIT", "ROLE_VENDOR_WRITE", "ROLE_VENDOR_READ",
					"ROLE_VENDOR_PAYMENT_EDIT", "ROLE_VENDOR_PAYMENT_WRITE", "ROLE_VENDOR_PAYMENT_READ",
					"ROLE_PRICE_CATEGORY_EDIT", "ROLE_PRICE_CATEGORY_WRITE", "ROLE_PRICE_CATEGORY_READ",
					"ROLE_EXPENSE_CATEGORY_EDIT", "ROLE_EXPENSE_CATEGORY_WRITE", "ROLE_EXPENSE_CATEGORY_READ",
					"ROLE_EXPENSE_EDIT", "ROLE_EXPENSE_WRITE", "ROLE_EXPENSE_READ", "ROLE_INCOME_CATEGORY_EDIT",
					"ROLE_INCOME_CATEGORY_WRITE", "ROLE_INCOME_CATEGORY_READ", "ROLE_INCOME_EDIT", "ROLE_INCOME_WRITE",
					"ROLE_INCOME_READ", "ROLE_ORDER_PRE_PRODUCT_EDIT", "ROLE_ORDER_PRE_PRODUCT_WRITE",
					"ROLE_ORDER_PRE_PRODUCT_READ", "ROLE_ACCOUNT_READ", "ROLE_ACCOUNT_WRITE", "ROLE_ACCOUNT_EDIT",
					"ROLE_CUSTOMER_WASTED_PRODUCT_WRITE", "ROLE_CUSTOMER_WASTED_PRODUCT_EDIT",
					"ROLE_CUSTOMER_WASTED_PRODUCT_READ", "ROLE_BOX_ACCOUNTING_READ", "ROLE_SETTING",
					"ROLE_VENDOR_RETURN_WRITE", "ROLE_VENDOR_RETURN_READ", "ROLE_VENDOR_RETURN_DELETE",
					"ROLE_ACCOUNT_STATEMENT_READ", "ROLE_PROFIT", "ROLE_PRODUCT_RECORD_READ",
					"ROLE_WITHDRAW_CATEGORY_EDIT", "ROLE_WITHDRAW_CATEGORY_WRITE", "ROLE_WITHDRAW_CATEGORY_READ",
					"ROLE_WITHDRAW_EDIT", "ROLE_WITHDRAW_WRITE", "ROLE_WITHDRAW_READ" };

			for (String roleName : roleNames) {
				Role role = new Role();
				role.setName(roleName);
				roleService.save(role);
			}

		}

		if (((Collection<User>) userService.findAll()).size() == 0) {
			logger.info("Insert admin user into database");
			User user = new User();
			user.setUserName("admin");
			user.setEnabled(true);
			user.setPassword("admin");

			Role role = roleService.findByName("ROLE_ADMIN");
			List<Role> roles = new ArrayList<>();
			roles.add(role);
			user.setRoles(roles);
			userService.save(user);
		}

	}
}