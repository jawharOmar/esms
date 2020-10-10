package com.joh.esms.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.joh.esms.domain.model.p_Stock;
import com.joh.esms.model.IncomeCategory;
import com.joh.esms.model.reports;
import org.springframework.stereotype.Component;

import com.joh.esms.domain.model.NotificationD;

@Component
public class ReportDAO {
	@PersistenceContext
	private EntityManager em;
	
	
//
//	public List<DoctorCustomerOrderD> findDoctorCustomerOrder(int doctorId, Date from, Date to) {
//
//		Query query = em.createNativeQuery(
//				"SELECT I_CUSTOMER_ORDER,CUSTOMER_NAME,ORDER_TIME,TOTAL_PRICE,TOTAL_PRICE-TOTAL_PRICE*IFNULL(DISCOUNT_AMOUNT,0) AS TOTAL_PAYMENT,DISCOUNT_AMOUNT,DISCOUNT_TYPE\n"
//						+ ",\n" + "(\n" + "CASE \n"
//						+ "WHEN I_DISCOUNT_TYPE=3 THEN -TOTAL_PRICE*IFNULL(DISCOUNT_AMOUNT,0) \n"
//						+ "ELSE (TOTAL_PRICE-TOTAL_PRICE*IFNULL(DISCOUNT_AMOUNT,0))*PROFIT \n" + "END\n" + ") INCOME\n"
//						+ "FROM DOCTORS D\n" + "INNER JOIN \n" + "CUSTOMER_ORDERS C USING(I_DOCTOR)\n"
//						+ "LEFT OUTER JOIN DISCOUNT_TYPES DT USING(I_DISCOUNT_TYPE)\n" + "WHERE I_DOCTOR=:I_DOCTOR \n"
//						+ "AND ORDER_TIME BETWEEN :from AND :to");
//
//		query.setParameter("I_DOCTOR", doctorId);
//		query.setParameter("from", from, TemporalType.TIMESTAMP);
//		query.setParameter("to", to, TemporalType.TIMESTAMP);
//
//		List<Object[]> rows = query.getResultList();
//
//		List<DoctorCustomerOrderD> doctorCustomerOrderDs = new ArrayList<>();
//		for (Object[] columns : rows) {
//			DoctorCustomerOrderD doctorCustomerOrderD = new DoctorCustomerOrderD();
//
//			doctorCustomerOrderD.setCustomerOrderId(Integer.parseInt("" + columns[0]));
//			doctorCustomerOrderD.setCustomerName((String) columns[1]);
//			doctorCustomerOrderD.setOrderTime((Date) columns[2]);
//			doctorCustomerOrderD.setTotalPrice((Double) columns[3]);
//			doctorCustomerOrderD.setTotalPayment((Double) columns[4]);
//			doctorCustomerOrderD.setDiscountAmount((BigDecimal) columns[5]);
//			doctorCustomerOrderD.setDiscountType((String) columns[6]);
//			doctorCustomerOrderD.setIncome((Double) columns[7]);
//
//			doctorCustomerOrderDs.add(doctorCustomerOrderD);
//		}
//		return doctorCustomerOrderDs;
//
//	}

	public NotificationD findAdminNotifications() {


		// // Notification-1
		//
		Query query = em.createNativeQuery("select count(I_CUSTOMER_ORDER) as qty, (if(sum(TOTAL_PRICE) is null,0,sum(TOTAL_PRICE))-if(sum(DISCOUNT) is null,0,sum(DISCOUNT))) as price,if(sum(total_payment) is null,0,sum(total_payment)) as payment,((if(sum(TOTAL_PRICE) is null,0,sum(TOTAL_PRICE))-if(sum(DISCOUNT) is null,0,sum(DISCOUNT)))-if(sum(total_payment) is null,0,sum(total_payment))) as loan from  CUSTOMER_ORDERS where date(ORDER_TIME)=current_date();");

		NotificationD zh=new NotificationD();

		List<Object[]> dashboard = query.getResultList();

		for (Object[] row : dashboard) {
			zh.setQty(Integer.valueOf(row[0]+""));
			zh.setPrice(Double.valueOf(row[1]+""));
			zh.setPayment(Double.valueOf(row[2]+""));
			zh.setLoan(Double.valueOf(row[3]+""));

		}


		 query = em.createNativeQuery("select if(sum(amount) is null,0,sum(amount)) from EXPENSES where date(EXPENSE_TIME)=curdate()");
		Object ex=query.getSingleResult();
		zh.setExpense(Double.valueOf(ex+""));

		query = em.createNativeQuery("select if(sum(TOTAL_PAYMENT) is null,0,sum(TOTAL_PAYMENT) ) from CUSTOMER_PAYMENTS where date(PAYMENT_TIME)=curdate()");

		zh.setPaid(Double.valueOf(query.getSingleResult()+""));

		//
		// not1.setNotificationType(NotificationType.DANGER);
		//
		// notificationDs.add(not1);
		//
		// // Notification-2
		//
		// query = em.createNativeQuery(
		// "SELECT ROUND(IFNULL(SUM(TOTAL_PRICE),0),3) FROM CUSTOMER_ORDERS WHERE
		// DATE(ORDER_TIME)=CURDATE();");
		//
		// Object totalTodayCustomerPriceResult = query.getSingleResult();
		//
		// double totalTodayCustomerPrice = 0;
		//
		// if (totalTodayCustomerPriceResult != null)
		// totalTodayCustomerPrice = Double.parseDouble("" +
		// totalTodayCustomerPriceResult);
		//
		// //
		// NotificationD not2 = new NotificationD();
		// not2.setTitle("Today Total Customer Price");
		// not2.setEtc("" + totalTodayCustomerPrice);
		// not2.setMessage("Total customer price without discount");
		//
		// not2.setNotificationType(NotificationType.INFO);
		//
		// notificationDs.add(not2);
		//
		// // Notification-3
		//
		// query = em.createNativeQuery(
		// "SELECT ROUND(SUM(TOTAL_PRICE)-IFNULL(SUM(TOTAL_PRICE*DISCOUNT_AMOUNT),0),3)
		// FROM CUSTOMER_ORDERS WHERE DATE(ORDER_TIME)=CURDATE();");
		//
		// Object totalTodayCustomerPriceResultWithDiscount = query.getSingleResult();
		//
		// double totalTodayCustomerPriceWithDiscount = 0;
		//
		// if (totalTodayCustomerPriceResultWithDiscount != null)
		// totalTodayCustomerPriceWithDiscount = Double.parseDouble("" +
		// totalTodayCustomerPriceResultWithDiscount);
		//
		// NotificationD not3 = new NotificationD();
		// not3.setTitle("Today Total Customer Order Income ");
		// not3.setEtc("" + totalTodayCustomerPriceWithDiscount);
		// not3.setMessage("Total customer price after make discount");
		//
		// not3.setNotificationType(NotificationType.INFO);
		//
		// notificationDs.add(not3);
		//
		// // Notification-4
		//
		// query = em.createNativeQuery(
		// "SELECT ROUND(SUM(TOTAL_PRICE*DISCOUNT_AMOUNT),3) FROM CUSTOMER_ORDERS WHERE
		// DATE(ORDER_TIME)=CURDATE();");
		//
		// Object totalTodayCustomerDiscountResult = query.getSingleResult();
		//
		// double totalTodayCustomerDiscount = 0;
		//
		// if (totalTodayCustomerDiscountResult != null)
		// totalTodayCustomerDiscount = Double.parseDouble("" +
		// totalTodayCustomerDiscountResult);
		//
		// NotificationD not4 = new NotificationD();
		// not4.setTitle("Today Total Customer Discount ");
		// not4.setEtc("" + totalTodayCustomerDiscount);
		// not4.setMessage("Total discount made to customer");
		//
		// not4.setNotificationType(NotificationType.INFO);
		//
		// notificationDs.add(not4);
		//
		// // Notification-5
		//
		// query = em.createNativeQuery(
		// "SELECT ROUND(IFNULL(SUM(TOTAL_PAYMENT_AMOUNT),0)) FROM
		// phms.ORDER_PRODUCT_STEPUPS WHERE DATE(ORDER_TIME)=CURDATE()");
		//
		// Object totalProductStepUpPaymentamountResult = query.getSingleResult();
		//
		// double totalProductStepUpPaymentamount = 0;
		//
		// if (totalProductStepUpPaymentamountResult != null)
		// totalProductStepUpPaymentamount = Double.parseDouble("" +
		// totalProductStepUpPaymentamountResult);
		//
		// NotificationD not5 = new NotificationD();
		// not5.setTitle("Today total Stockup Payment Amount");
		// not5.setEtc("" + totalProductStepUpPaymentamount);
		// not5.setMessage("The total today order amount payment");
		//
		// not5.setNotificationType(NotificationType.INFO);
		//
		// notificationDs.add(not5);

		return zh;

	}

	public p_Stock findStockP(int I_Product,int I_STOCK) {

		Query query = em.createNativeQuery("select s.STOCK_NAME,p.PRODUCT_NAME,p.PRODUCT_CODE,ps.AMOUNT,if(p.PACKET_SIZE is null ,0,p.PACKET_SIZE)\n" +
				"from PRODUCTS p\n" +
				"JOIN PRODUCT_STOCKS ps on(p.i_product=ps.I_PRODUCT)\n" +
				"JOIN STOCKS s on(ps.I_STOCK=s.I_STOCK)\n" +
				"where p.I_PRODUCT=:I_PRODUCT " +
				"and s.I_STOCK=:I_STOCK");
				query.setParameter("I_PRODUCT",I_Product);
				query.setParameter("I_STOCK",I_STOCK);



		List<Object[]> dashboard = query.getResultList();

		p_Stock Stock_info=new p_Stock();


		for (Object[] row : dashboard) {
			Stock_info.setStockName(row[0].toString()+"");
			Stock_info.setProductname(row[1].toString()+"");
			Stock_info.setProductCode(row[2].toString()+"");
			Stock_info.setProductamount(Double.valueOf(row[3]+""));
			Stock_info.setPacketSize(Double.valueOf(row[4]+""));

		}




		return Stock_info;
	}


	public Object sumproduct(int I_Product) {

		int sum=-1;
		Query query = em.createNativeQuery("select sum(amount) from PRODUCT_STOCKS where I_PRODUCT=:I_PRODUCT");
		query.setParameter("I_PRODUCT",I_Product);

		return query.getSingleResult();
	}

	}
