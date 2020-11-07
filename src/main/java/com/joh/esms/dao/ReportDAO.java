package com.joh.esms.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Component;

import com.joh.esms.domain.model.CustomerOrderD;
import com.joh.esms.domain.model.DashboardBalanceD;
import com.joh.esms.domain.model.DashboardExpiredProductD;
import com.joh.esms.domain.model.MinimumProductStockLevelD;
import com.joh.esms.domain.model.NotificationD;
import com.joh.esms.domain.model.SalesReportD;
import com.joh.esms.domain.model.p_Stock;

@Component
public class ReportDAO {
	@PersistenceContext
	private EntityManager em;

	public NotificationD findAdminNotifications() {

		Query query = em.createNativeQuery(
				"select count(I_CUSTOMER_ORDER) as qty, (if(sum(TOTAL_PRICE) is null,0,sum(TOTAL_PRICE))-if(sum(DISCOUNT) is null,0,sum(DISCOUNT))) as price,if(sum(total_payment) is null,0,sum(total_payment)) as payment,((if(sum(TOTAL_PRICE) is null,0,sum(TOTAL_PRICE))-if(sum(DISCOUNT) is null,0,sum(DISCOUNT)))-if(sum(total_payment) is null,0,sum(total_payment))) as loan from  CUSTOMER_ORDERS where date(ORDER_TIME)=current_date();");

		NotificationD zh = new NotificationD();

		List<Object[]> dashboard = query.getResultList();

		for (Object[] row : dashboard) {
			zh.setQty(Integer.valueOf(row[0] + ""));
			zh.setPrice(Double.valueOf(row[1] + ""));
			zh.setPayment(Double.valueOf(row[2] + ""));
			zh.setLoan(Double.valueOf(row[3] + ""));

		}

		query = em.createNativeQuery(
				"select if(sum(amount) is null,0,sum(amount)) from EXPENSES where date(EXPENSE_TIME)=curdate()");
		Object ex = query.getSingleResult();
		zh.setExpense(Double.valueOf(ex + ""));

		query = em.createNativeQuery(
				"select if(sum(TOTAL_PAYMENT) is null,0,sum(TOTAL_PAYMENT) ) from CUSTOMER_PAYMENTS where date(PAYMENT_TIME)=curdate()");

		zh.setPaid(Double.valueOf(query.getSingleResult() + ""));

		return zh;

	}

	public p_Stock findStockP(int I_Product, int I_STOCK) {

		Query query = em.createNativeQuery(
				"select s.STOCK_NAME,p.PRODUCT_NAME,p.PRODUCT_CODE,ps.AMOUNT,if(p.PACKET_SIZE is null ,0,p.PACKET_SIZE)\n"
						+ "from PRODUCTS p\n" + "JOIN PRODUCT_STOCKS ps on(p.i_product=ps.I_PRODUCT)\n"
						+ "JOIN STOCKS s on(ps.I_STOCK=s.I_STOCK)\n" + "where p.I_PRODUCT=:I_PRODUCT "
						+ "and s.I_STOCK=:I_STOCK");
		query.setParameter("I_PRODUCT", I_Product);
		query.setParameter("I_STOCK", I_STOCK);

		List<Object[]> dashboard = query.getResultList();

		p_Stock Stock_info = new p_Stock();

		for (Object[] row : dashboard) {
			Stock_info.setStockName(row[0].toString() + "");
			Stock_info.setProductname(row[1].toString() + "");
			Stock_info.setProductCode(row[2].toString() + "");
			Stock_info.setProductamount(Double.valueOf(row[3] + ""));
			Stock_info.setPacketSize(Double.valueOf(row[4] + ""));

		}

		return Stock_info;
	}

	public Object sumproduct(int I_Product) {

		int sum = -1;
		Query query = em.createNativeQuery("select sum(amount) from PRODUCT_STOCKS where I_PRODUCT=:I_PRODUCT");
		query.setParameter("I_PRODUCT", I_Product);

		return query.getSingleResult();
	}

	public List<CustomerOrderD> customerOrders() {

		Query query = em.createNativeQuery(
				"SELECT TOTAL_PRICE-DISCOUNT AS AMOUNT,DATE(ORDER_TIME) FROM CUSTOMER_ORDERS WHERE ORDER_TIME BETWEEN CURDATE()-INTERVAL 20 DAY AND CURTIME()");

		List<Object[]> results = query.getResultList();

		List<CustomerOrderD> customerOrderDs = new ArrayList<>();

		for (Object[] row : results) {

			CustomerOrderD customerOrderD = new CustomerOrderD();
			customerOrderD.setAmount(Double.valueOf(row[0] + ""));
			customerOrderD.setDate(row[1].toString());
			customerOrderDs.add(customerOrderD);

		}
		return customerOrderDs;
	}

	public SalesReportD salesReport() {

		Query query = em.createNativeQuery("SELECT TOTAL_SALE ,TOTAL_PAYMENT ,TOTAL_SALE-TOTAL_PAYMENT ,ORDER_RETURN\n"
				+ "FROM (\n"
				+ "SELECT (SELECT IFNULL(SUM(TOTAL_PRICE-DISCOUNT),0) FROM CUSTOMER_ORDERS) AS TOTAL_SALE,\n"
				+ "(SELECT IFNULL(SUM(TOTAL_PAYMENT-DISCOUNT),0) FROM CUSTOMER_PAYMENTS) TOTAL_PAYMENT,(SELECT IFNULL(SUM(TOTAL_PRICE - TOTAL_PAID), 0) FROM CUSTOMER_ORDER_RETURNS) ORDER_RETURN\n"
				+ ") SP");

		SalesReportD salesReportD = new SalesReportD();

		Object[] row = (Object[]) query.getSingleResult();

		salesReportD.setOrder(Double.parseDouble("" + row[0]));
		salesReportD.setPayment(Double.parseDouble("" + row[1]));
		salesReportD.setLoan(Double.parseDouble("" + row[2]));
		salesReportD.setOrderReturn(Double.parseDouble("" + row[3]));

		return salesReportD;
	}

	public DashboardBalanceD getBalance() {
		Query query = em.createNativeQuery("SELECT IFNUll(BALANCE,0) FROM ACCOUNTS LIMIT 1");

		DashboardBalanceD dashboardBalanceD = new DashboardBalanceD();

		double account = (double) query.getSingleResult();
		dashboardBalanceD.setAccount(account);

		query = em.createNativeQuery(
				"SELECT IFNULL(SUM(AMOUNT),0)  FROM EXPENSES WHERE EXPENSE_TIME BETWEEN MONTH(CURDATE()-INTERVAL 1 MONTH) AND MONTH(CURDATE())");

		double expense = (double) query.getSingleResult();
		dashboardBalanceD.setExpense(expense);

		query = em.createNativeQuery("SELECT ORD-PAID-RE LOAN_VENDOR\n" + "FROM (\n"
				+ "SELECT IFNULL((SELECT SUM(TOTAL_PRICE-TOTAL_PAYMENT-DISCOUNT) FROM ORDER_PRODUCT_STEPUPS),0) ORD,\n"
				+ "IFNULL((SELECT SUM(TOTAL_PAYMENT-DISCOUNT) FROM VENDOR_PAYMENTS),0) PAID,IFNULL((SELECT SUM(AMOUNT) FROM VENDOR_RETURNS),0)RE) T");

		double vendorLoan = (double) query.getSingleResult();
		dashboardBalanceD.setVendorLoan(vendorLoan);

		query = em.createNativeQuery("SELECT ORD-PAID-RE LOAN_VENDOR\n" + "FROM (\n"
				+ "SELECT IFNULL((SELECT SUM(TOTAL_PRICE-TOTAL_PAYMENT-DISCOUNT) FROM CUSTOMER_ORDERS),0) ORD,\n"
				+ "IFNULL((SELECT SUM(TOTAL_PAYMENT-DISCOUNT) FROM CUSTOMER_PAYMENTS),0) PAID,IFNULL((SELECT SUM(TOTAL_PRICE) FROM CUSTOMER_ORDER_RETURNS),0)RE) T");

		double customerLoan = (double) query.getSingleResult();
		dashboardBalanceD.setCustomerLoan(customerLoan);

		return dashboardBalanceD;
	}

	public List<MinimumProductStockLevelD> getMinimumStockLevelProducts() {

		Query query = em.createNativeQuery(
				"SELECT I_PRODUCT,PRODUCT_CODE,PRODUCT_NAME,MINIMUM_STOCK_LEVEL,IFNULL(CURRENT_LEVEL,0)  FROM PRODUCTS\n"
						+ "LEFT OUTER JOIN (\n"
						+ "SELECT QUANTITY-SOLD_QUANTITY CURRENT_LEVEL,I_PRODUCT FROM PRODUCT_STEPUPS WHERE EXPIRATION_DATE<CURDATE()) D USING(I_PRODUCT)\n"
						+ "WHERE IFNULL(CURRENT_LEVEL,0)<=MINIMUM_STOCK_LEVEL");

		List<Object[]> results = query.getResultList();

		List<MinimumProductStockLevelD> minimumProductStockLevelDs = new ArrayList<>();

		for (Object[] row : results) {

			MinimumProductStockLevelD minimumProductStockLevelD = new MinimumProductStockLevelD();
			minimumProductStockLevelD.setProductId(Integer.valueOf(row[0] + ""));
			minimumProductStockLevelD.setProductCode(row[1].toString());
			minimumProductStockLevelD.setProductName(row[2].toString());
			minimumProductStockLevelD.setMinimumStockLevel(Double.valueOf(row[3] + ""));
			minimumProductStockLevelD.setCurrentStockLevel(Double.valueOf(row[4] + ""));
			minimumProductStockLevelDs.add(minimumProductStockLevelD);
		}
		return minimumProductStockLevelDs;
	}

	public List<DashboardExpiredProductD> getExpiredProducts() {

		Query query = em.createNativeQuery(
				"SELECT I_PRODUCT,PRODUCT_CODE,PRODUCT_NAME,CAST(SUM(QUANTITY-SOLD_QUANTITY) AS SIGNED) AMOUNT FROM PRODUCTS\n" + 
				"INNER JOIN  PRODUCT_STEPUPS USING(I_PRODUCT)\n" + 
				"WHERE EXPIRATION_DATE>=CURDATE() AND QUANTITY-SOLD_QUANTITY>0\n" + 
				"GROUP BY I_PRODUCT");

		List<Object[]> results = query.getResultList();

		List<DashboardExpiredProductD> dashboardExpiredProductDs = new ArrayList<>();

		for (Object[] row : results) {

			DashboardExpiredProductD dashboardExpiredProductD = new DashboardExpiredProductD();
			dashboardExpiredProductD.setProductId(Integer.valueOf(row[0] + ""));
			dashboardExpiredProductD.setProductCode(row[1].toString());
			dashboardExpiredProductD.setProductName(row[2].toString());
			dashboardExpiredProductD.setAmount(Integer.valueOf(row[3] + ""));
			dashboardExpiredProductDs.add(dashboardExpiredProductD);
		}
		return dashboardExpiredProductDs;
	}

}
