package com.joh.esms.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.joh.esms.domain.model.BoxAccountingD;

@Component
public class BoxAccountingDAO {
	@PersistenceContext
	private EntityManager em;

	@Autowired
	private MessageSource messageSource;

	/**
	 * 1-vendorOrders (typeId 1) 2-vendorPayment (typeId 2) 3-vendorReturn (typeId
	 * 3) 4-customerOrders (typeId 4) 5-customerPayment (typeId 5) 6-customerReturn
	 * (typeId 6) 7-expenses (typeId 7) 8-incomes (typeId 8) 9-withdraws (typeId 9)
	 * 10-loan (typeId 10)
	 *
	 * @param locale
	 */

	public List<BoxAccountingD> findBalance(Locale locale) {
		Query query = em.createNativeQuery(
				"select vs.FULL_NAME as FULL_NAME,?1 as TYPE,v.I_ORDER_PRODUCT_STEPUP AS ACTION_ID,v.DISCOUNT,'empty' as NOTE,if(v.REFERENCE_INVOICE_ID is null,'empty',v.REFERENCE_INVOICE_ID)as Invoice,v.ORDER_TIME as Time,0 as Income, if(v.TOTAL_PAYMENT is null,0,v.TOTAL_PAYMENT) as Expense,1 AS TYPE_ID from ORDER_PRODUCT_STEPUPS v INNER JOIN VENDORS vs ON v.I_VENDOR=vs.I_VENDOR where (if(v.TOTAL_PAYMENT is null,0,v.TOTAL_PAYMENT)>0 or v.DISCOUNT>0 )\n"
						+ "UNION ALL\n"
						+ "select vs.FULL_NAME as FULL_NAME,?2 as TYPE,vp.I_VENDOR_PAYMENT AS ACTION_ID,IF(vp.DISCOUNT IS NULL,0,vp.DISCOUNT) AS DISCOUNT,if(vp.NOTE is null,'empty',vp.NOTE) as NOTE,vp.I_VENDOR_PAYMENT as Invoice,vp.PAYMENT_TIME as Time,0 as Income,vp.TOTAL_PAYMENT as Expense,2 AS TYPE_ID from VENDOR_PAYMENTS vp INNER JOIN VENDORS vs ON vp.I_VENDOR=vs.I_VENDOR\n"
						+ "UNION ALL\n"
						+ "select vs.FULL_NAME as FULL_NAME,?3 as TYPE,vr.I_RETURN AS ACTION_ID,0 AS DISCOUNT,'empty' as NOTE,vr.I_RETURN as Invoice,vr.Time as Time,vr.AMOUNT as Income,0 as Expense,3 AS TYPE_ID from VENDOR_RETURNS vr INNER JOIN VENDORS vs ON vr.I_VENDOR=vs.I_VENDOR\n"
						+ "UNION ALL\n"
						+ "select c.FULL_NAME as FULL_NAME,?4 as TYPE,o.I_CUSTOMER_ORDER AS ACTION_ID,IF(o.DISCOUNT IS NULL,0,o.DISCOUNT) AS DISCOUNT,if(o.NOTE is null,'empty',o.NOTE) as NOTE,o.INVOICE_ID as Invoice,o.ORDER_TIME as Time,if(o.TOTAL_PAYMENT is null,0,o.TOTAL_PAYMENT) as Income, 0 as Expense,4 AS TYPE_ID from CUSTOMER_ORDERS o INNER JOIN CUSTOMERS c ON o.I_CUSTOMER=c.I_CUSTOMER where (if(o.TOTAL_PAYMENT is null,0,o.TOTAL_PAYMENT)>0 or o.DISCOUNT>0)\n"
						+ "UNION ALL\n"
						+ "select c.FULL_NAME as FULL_NAME,?5 as TYPE,p.I_CUSTOMER_PAYMENT as ACTION_ID ,IF(DISCOUNT IS NULL,0,DISCOUNT) AS DISCOUNT,if(p.NOTE is null,'empty',p.NOTE) as NOTE,p.I_CUSTOMER_PAYMENT as Invoice,p.PAYMENT_TIME as Time,p.TOTAL_PAYMENT as Income,0 as Expense,5 AS TYPE_ID from CUSTOMER_PAYMENTS p INNER JOIN CUSTOMERS c ON p.I_CUSTOMER=c.I_CUSTOMER\n"
						+ "UNION ALL\n"
						+ "select c.FULL_NAME as FULL_NAME,?6 as TYPE,r.I_CUSTOMER_ORDER_RETURN AS ACTION_ID,0 AS DISCOUNT,'empty' as NOTE,if(r.REFERENCE_INVOICE_ID is null,0,r.REFERENCE_INVOICE_ID) as Invoice,r.CUSTOMER_ORDER_RETURN_TIME as Time,0 as Income,r.TOTAL_PAID as Expense,6 AS TYPE_ID from CUSTOMER_ORDER_RETURNS r INNER JOIN CUSTOMERS c ON r.I_CUSTOMER=c.I_CUSTOMER\n"
						+ "UNION ALL\n"
						+ "select e.EXPENSE_NAME as FULL_NAME, IF(ec.EXPENSE_CATEGORY_NAME IS NULL,?7,CONCAT(?7,'/',ec.EXPENSE_CATEGORY_NAME)) as TYPE,0 as ACTION_ID,0 AS DISCOUNT,if(e.NOTE is null OR e.NOTE='','empty',e.NOTE) as NOTE,'empty' as Invoice,e.EXPENSE_TIME as Time,0 as Income, e.AMOUNT as Expense,7 AS TYPE_ID from EXPENSES e LEFT JOIN EXPENSE_CATEGORIES ec ON e.expenseCategory_I_EXPENSE_CATEGORY=ec.I_EXPENSE_CATEGORY\n"
						+ "UNION ALL\n"
						+ "select i.INCOME_NAME as FULL_NAME, IF(ic.INCOME_CATEGORY_NAME IS NULL,?8,CONCAT(?8,'/',ic.INCOME_CATEGORY_NAME))  as TYPE, 0 AS ACTION_ID,0 AS DISCOUNT ,if(i.NOTE is null OR i.NOTE='','empty',i.NOTE) as NOTE,'empty' as Invoice, i.EXPENSE_TIME as Time, i.AMOUNT as Income, 0 as Expense,8 AS TYPE_ID from INCOMES i LEFT JOIN INCOME_CATEGORIES ic ON i.incomeCategory_I_INCOME_CATEGORY=ic.I_INCOME_CATEGORY\n"
						+ "UNION ALL\n"
						+ "SELECT w.WITHDRAW_NAME AS FULL_NAME,IF(wc.WITHDRAW_CATEGORY_NAME IS NULL,?9,CONCAT(?9,'/',wc.WITHDRAW_CATEGORY_NAME)) AS TYPE,0 AS ACTION_ID,0 AS DISCOUNT,IF(w.NOTE IS NULL OR w.NOTE='','empty',w.NOTE) AS NOTE,'empty' AS Invoice,w.WITHDRAW_TIME AS Time, 0 AS Income, w.AMOUNT AS Expense,9 AS TYPE_ID FROM WITHDRAW w INNER JOIN WITHDRAW_CATEGORIES wc ON w.I_WITHDRAW_CATEGORY=wc.I_WITHDRAW_CATEGORY order by Time;");

		query.setParameter(1, messageSource.getMessage("boxAccounting.vendorOrders", null, locale));
		query.setParameter(2, messageSource.getMessage("boxAccounting.vendorPayment", null, locale));
		query.setParameter(3, messageSource.getMessage("boxAccounting.vendorReturn", null, locale));
		query.setParameter(4, messageSource.getMessage("boxAccounting.customerOrders", null, locale));
		query.setParameter(5, messageSource.getMessage("boxAccounting.customerPayment", null, locale));
		query.setParameter(6, messageSource.getMessage("boxAccounting.customerReturn", null, locale));
		query.setParameter(7, messageSource.getMessage("boxAccounting.expenses", null, locale));
		query.setParameter(8, messageSource.getMessage("boxAccounting.incomes", null, locale));
		query.setParameter(9, messageSource.getMessage("boxAccounting.withdraws", null, locale));

		List<BoxAccountingD> boxAccountingDS = new ArrayList<>();

		List<Object[]> resultList = query.getResultList();

		for (Object[] row : resultList) {
			BoxAccountingD boxAccountingD = new BoxAccountingD();
			boxAccountingD.setFullName(row[0].toString());
			boxAccountingD.setType(row[1].toString());
			boxAccountingD.setActionId(Integer.parseInt(row[2] + ""));
			boxAccountingD.setDiscount(Double.parseDouble(row[3] + ""));
			boxAccountingD.setNote(row[4].toString());
			boxAccountingD.setInvoice(row[5].toString());
			boxAccountingD.setTime((Date) row[6]);
			boxAccountingD.setIncome(Double.parseDouble(row[7] + ""));
			boxAccountingD.setExpense(Double.parseDouble(row[8] + ""));
			boxAccountingD.setTypeId(Integer.parseInt(row[9] + ""));

			boxAccountingDS.add(boxAccountingD);
		}

		return boxAccountingDS;
	}

	public List<BoxAccountingD> findBalanceByDate(Date from, Date to, Locale locale) {
		Query query = em.createNativeQuery(
				"select * from(select vs.FULL_NAME as FULL_NAME,?3 as TYPE,v.I_ORDER_PRODUCT_STEPUP AS ACTION_ID,v.DISCOUNT,'empty' as NOTE,if(v.REFERENCE_INVOICE_ID is null,'empty',v.REFERENCE_INVOICE_ID)as Invoice,v.ORDER_TIME as Time,0 as Income, if(v.TOTAL_PAYMENT is null,0,v.TOTAL_PAYMENT) as Expense,1 AS TYPE_ID  from ORDER_PRODUCT_STEPUPS v INNER JOIN VENDORS vs ON v.I_VENDOR=vs.I_VENDOR where (if(v.TOTAL_PAYMENT is null,0,v.TOTAL_PAYMENT)>0 or v.DISCOUNT>0 )\n"
						+ "UNION ALL\n"
						+ "select vs.FULL_NAME as FULL_NAME,?4 as TYPE,vp.I_VENDOR_PAYMENT AS ACTION_ID,IF(vp.DISCOUNT IS NULL,0,vp.DISCOUNT) AS DISCOUNT,if(vp.NOTE is null,'empty',vp.NOTE) as NOTE,vp.I_VENDOR_PAYMENT as Invoice,vp.PAYMENT_TIME as Time,0 as Income,vp.TOTAL_PAYMENT as Expense,2 AS TYPE_ID  from VENDOR_PAYMENTS vp INNER JOIN VENDORS vs ON vp.I_VENDOR=vs.I_VENDOR\n"
						+ "UNION ALL\n"
						+ "select vs.FULL_NAME as FULL_NAME,?5 as TYPE,vr.I_RETURN AS ACTION_ID,0 AS DISCOUNT,'empty' as NOTE,vr.I_RETURN as Invoice,vr.Time as Time,vr.AMOUNT as Income,0 as Expense,3 AS TYPE_ID  from VENDOR_RETURNS vr INNER JOIN VENDORS vs ON vr.I_VENDOR=vs.I_VENDOR\n"
						+ "UNION ALL\n"
						+ "select c.FULL_NAME as FULL_NAME,?6 as TYPE,o.I_CUSTOMER_ORDER AS ACTION_ID,IF(o.DISCOUNT IS NULL,0,o.DISCOUNT) AS DISCOUNT,if(o.NOTE is null,'empty',o.NOTE) as NOTE,o.INVOICE_ID as Invoice,o.ORDER_TIME as Time,if(o.TOTAL_PAYMENT is null,0,o.TOTAL_PAYMENT) as Income, 0 as Expense,4 AS TYPE_ID  from CUSTOMER_ORDERS o INNER JOIN CUSTOMERS c ON o.I_CUSTOMER=c.I_CUSTOMER where (if(o.TOTAL_PAYMENT is null,0,o.TOTAL_PAYMENT)>0 or o.DISCOUNT>0)\n"
						+ "UNION ALL\n"
						+ "select c.FULL_NAME as FULL_NAME,?7 as TYPE,p.I_CUSTOMER_PAYMENT as ACTION_ID ,IF(DISCOUNT IS NULL,0,DISCOUNT) AS DISCOUNT,if(p.NOTE is null,'empty',p.NOTE) as NOTE,p.I_CUSTOMER_PAYMENT as Invoice,p.PAYMENT_TIME as Time,p.TOTAL_PAYMENT as Income,0 as Expense,5 AS TYPE_ID  from CUSTOMER_PAYMENTS p INNER JOIN CUSTOMERS c ON p.I_CUSTOMER=c.I_CUSTOMER\n"
						+ "UNION ALL\n"
						+ "select c.FULL_NAME as FULL_NAME,?8 as TYPE,r.I_CUSTOMER_ORDER_RETURN AS ACTION_ID,0 AS DISCOUNT,'empty' as NOTE,if(r.REFERENCE_INVOICE_ID is null,0,r.REFERENCE_INVOICE_ID) as Invoice,r.CUSTOMER_ORDER_RETURN_TIME as Time,0 as Income,r.TOTAL_PAID as Expense,6 AS TYPE_ID  from CUSTOMER_ORDER_RETURNS r INNER JOIN CUSTOMERS c ON r.I_CUSTOMER=c.I_CUSTOMER\n"
						+ "UNION ALL\n"
						+ "select e.EXPENSE_NAME as FULL_NAME, IF(ec.EXPENSE_CATEGORY_NAME IS NULL,?9,CONCAT(?9,'/',ec.EXPENSE_CATEGORY_NAME)) as TYPE,0 as ACTION_ID,0 AS DISCOUNT,if(e.NOTE is null OR e.NOTE='','empty',e.NOTE) as NOTE,'empty' as Invoice,e.EXPENSE_TIME as Time,0 as Income, e.AMOUNT as Expense,7 AS TYPE_ID  from EXPENSES e LEFT JOIN EXPENSE_CATEGORIES ec ON e.expenseCategory_I_EXPENSE_CATEGORY=ec.I_EXPENSE_CATEGORY\n"
						+ "UNION ALL\n"
						+ "select i.INCOME_NAME as FULL_NAME, IF(ic.INCOME_CATEGORY_NAME IS NULL,?10,CONCAT(?10,'/',ic.INCOME_CATEGORY_NAME))  as TYPE, 0 AS ACTION_ID,0 AS DISCOUNT ,if(i.NOTE is null OR i.NOTE='','empty',i.NOTE) as NOTE,'empty' as Invoice, i.EXPENSE_TIME as Time, i.AMOUNT as Income, 0 as Expense,8 AS TYPE_ID  from INCOMES i LEFT JOIN INCOME_CATEGORIES ic ON i.incomeCategory_I_INCOME_CATEGORY=ic.I_INCOME_CATEGORY\n"
						+ "UNION ALL\n"
						+ "SELECT w.WITHDRAW_NAME AS FULL_NAME,IF(wc.WITHDRAW_CATEGORY_NAME IS NULL,?11,CONCAT(?11,'/',wc.WITHDRAW_CATEGORY_NAME)) AS TYPE,0 AS ACTION_ID,0 AS DISCOUNT,IF(w.NOTE IS NULL OR w.NOTE='','empty',w.NOTE) AS NOTE,'empty' AS Invoice,w.WITHDRAW_TIME AS Time, 0 AS Income, w.AMOUNT AS Expense,9 AS TYPE_ID  FROM WITHDRAW w INNER JOIN WITHDRAW_CATEGORIES wc ON w.I_WITHDRAW_CATEGORY=wc.I_WITHDRAW_CATEGORY\n"
						+ ") As A WHERE Time between ?1 and ?2 order by Time;");

		query.setParameter(1, from);
		query.setParameter(2, to);

		query.setParameter(3, messageSource.getMessage("boxAccounting.vendorOrders", null, locale));
		query.setParameter(4, messageSource.getMessage("boxAccounting.vendorPayment", null, locale));
		query.setParameter(5, messageSource.getMessage("boxAccounting.vendorReturn", null, locale));
		query.setParameter(6, messageSource.getMessage("boxAccounting.customerOrders", null, locale));
		query.setParameter(7, messageSource.getMessage("boxAccounting.customerPayment", null, locale));
		query.setParameter(8, messageSource.getMessage("boxAccounting.customerReturn", null, locale));
		query.setParameter(9, messageSource.getMessage("boxAccounting.expenses", null, locale));
		query.setParameter(10, messageSource.getMessage("boxAccounting.incomes", null, locale));
		query.setParameter(11, messageSource.getMessage("boxAccounting.withdraws", null, locale));

		List<BoxAccountingD> boxAccountingDS = new ArrayList<>();

		List<Object[]> resultList = query.getResultList();

		for (Object[] row : resultList) {
			BoxAccountingD boxAccountingD = new BoxAccountingD();
			boxAccountingD.setFullName(row[0].toString());
			boxAccountingD.setType(row[1].toString());
			boxAccountingD.setActionId(Integer.parseInt(row[2] + ""));
			boxAccountingD.setDiscount(Double.parseDouble(row[3] + ""));
			boxAccountingD.setNote(row[4].toString());
			boxAccountingD.setInvoice(row[5].toString());
			boxAccountingD.setTime((Date) row[6]);
			boxAccountingD.setIncome(Double.parseDouble(row[7] + ""));
			boxAccountingD.setExpense(Double.parseDouble(row[8] + ""));
			boxAccountingD.setTypeId(Integer.parseInt(row[9] + ""));

			boxAccountingDS.add(boxAccountingD);
		}
		return boxAccountingDS;
	}

	public HashMap<String, Double> findIncomesAndExpensesByDate(Date date) {
		Query query = em.createNativeQuery(
				"select ROUND(SUM(Income),3) As Incomes, ROUND(SUM(Expense),3) AS Expenses from(select v.ORDER_TIME as Time,0 as Income, if(v.TOTAL_PAYMENT is null,0,v.TOTAL_PAYMENT) as Expense from ORDER_PRODUCT_STEPUPS v INNER JOIN VENDORS vs ON v.I_VENDOR=vs.I_VENDOR where (if(v.TOTAL_PAYMENT is null,0,v.TOTAL_PAYMENT)>0 or v.DISCOUNT>0 )\n"
						+ "UNION ALL\n"
						+ "select vp.PAYMENT_TIME as Time,0 as Income,vp.TOTAL_PAYMENT as Expense from VENDOR_PAYMENTS vp INNER JOIN VENDORS vs ON vp.I_VENDOR=vs.I_VENDOR\n"
						+ "UNION ALL\n"
						+ "select vr.Time as Time,vr.AMOUNT as Income,0 as Expense from VENDOR_RETURNS vr INNER JOIN VENDORS vs ON vr.I_VENDOR=vs.I_VENDOR\n"
						+ "UNION ALL\n"
						+ "select o.ORDER_TIME as Time,if(o.TOTAL_PAYMENT is null,0,o.TOTAL_PAYMENT) as Income, 0 as Expense from CUSTOMER_ORDERS o INNER JOIN CUSTOMERS c ON o.I_CUSTOMER=c.I_CUSTOMER where (if(o.TOTAL_PAYMENT is null,0,o.TOTAL_PAYMENT)>0 or o.DISCOUNT>0)\n"
						+ "UNION ALL\n"
						+ "select p.PAYMENT_TIME as Time,p.TOTAL_PAYMENT as Income,0 as Expense from CUSTOMER_PAYMENTS p INNER JOIN CUSTOMERS c ON p.I_CUSTOMER=c.I_CUSTOMER\n"
						+ "UNION ALL\n"
						+ "select r.CUSTOMER_ORDER_RETURN_TIME as Time,0 as Income,r.TOTAL_PAID as Expense from CUSTOMER_ORDER_RETURNS r INNER JOIN CUSTOMERS c ON r.I_CUSTOMER=c.I_CUSTOMER\n"
						+ "UNION ALL\n"
						+ "select e.EXPENSE_TIME as Time,0 as Income, e.AMOUNT as Expense from EXPENSES e LEFT JOIN EXPENSE_CATEGORIES ec ON e.expenseCategory_I_EXPENSE_CATEGORY=ec.I_EXPENSE_CATEGORY\n"
						+ "UNION ALL\n"
						+ "select i.EXPENSE_TIME as Time, i.AMOUNT as Income, 0 as Expense from INCOMES i LEFT JOIN INCOME_CATEGORIES ic ON i.incomeCategory_I_INCOME_CATEGORY=ic.I_INCOME_CATEGORY\n"
						+ "UNION ALL\n"
						+ "SELECT w.WITHDRAW_TIME AS Time, 0 AS Income, w.AMOUNT AS Expense FROM WITHDRAW w INNER JOIN WITHDRAW_CATEGORIES wc ON w.I_WITHDRAW_CATEGORY=wc.I_WITHDRAW_CATEGORY) As A WHERE Time <?1");

		query.setParameter(1, date);

		HashMap<String, Double> incExpValues = new HashMap<String, Double>();

		List<Object[]> resultList = query.getResultList();

		for (Object[] row : resultList) {
			if (row[0] != null) {
				incExpValues.put("incomes", Double.parseDouble(row[0].toString()));
			} else {
				incExpValues.put("incomes", 0.0);
			}
			if (row[1] != null) {
				incExpValues.put("expenses", Double.parseDouble(row[1].toString()));
			} else {
				incExpValues.put("expenses", 0.0);
			}
		}

		return incExpValues;
	}

	public List<BoxAccountingD> findLoan(Locale locale) {
		List<BoxAccountingD> boxAccountingDS = new ArrayList<>();

		Query loan = em.createNativeQuery(
				"SELECT ?1 as FULL_NAME,?3 as Type, 0 AS ACTION_ID, ROUND(IFNULL((SELECT SUM(IFNULL((DISCOUNT),0)) AS DISCOUNT FROM CUSTOMER_ORDERS),0)+ IFNULL((SELECT SUM(IFNULL((DISCOUNT),0)) AS DISCOUNT FROM CUSTOMER_PAYMENTS),0),3) AS DISCOUNT,'empty' as NOTE,'empty' as Invoice,NOW() as Time,ROUND(IFNULL((SELECT SUM(IFNULL(TOTAL_PRICE,0)-IFNULL(DISCOUNT,0)-IFNULL(TOTAL_PAYMENT,0)) FROM CUSTOMER_ORDERS),0)-IFNULL((SELECT SUM(IFNULL(TOTAL_PRICE-TOTAL_PAID,0)) FROM CUSTOMER_ORDER_RETURNS ),0)-IFNULL((SELECT SUM(TOTAL_PAYMENT) + SUM(if(DISCOUNT is null,0,DISCOUNT)) FROM CUSTOMER_PAYMENTS),0),3) as Income,0  as Expense,10 AS TYPE_ID \n"
						+ "UNION ALL\n"
						+ "SELECT ?2 as FULL_NAME, ?3 as Type, 0 AS ACTION_ID,ROUND(IFNULL((SELECT SUM(IFNULL((DISCOUNT),0)) AS DISCOUNT FROM ORDER_PRODUCT_STEPUPS ),0)+ IFNULL((SELECT SUM(IFNULL((DISCOUNT),0)) AS DISCOUNT FROM VENDOR_PAYMENTS ),0),3) AS DISCOUNT ,'empty' as NOTE,'empty' as Invoice,NOW() as Time,0 as Income, ROUND(IFNULL((SELECT SUM(IFNULL(TOTAL_PRICE,0)-IFNULL(TOTAL_PAYMENT,0) -IFNULL(O.DISCOUNT,0) ) FROM ORDER_PRODUCT_STEPUPS O),0)-IFNULL((SELECT SUM(TOTAL_PAYMENT) + SUM(IF(vp.DISCOUNT IS NULL,0,vp.DISCOUNT))\n"
						+ " FROM VENDOR_PAYMENTS vp ),0)-IFNULL((SELECT SUM(AMOUNT) FROM VENDOR_RETURNS ),0) ,3) as Expense,10 AS TYPE_ID ");

		loan.setParameter(1, messageSource.getMessage("boxAccounting.customer", null, locale));
		loan.setParameter(2, messageSource.getMessage("boxAccounting.vendor", null, locale));
		loan.setParameter(3, messageSource.getMessage("boxAccounting.loan", null, locale));

		List<Object[]> loanResultList = loan.getResultList();

		for (Object[] row : loanResultList) {
			BoxAccountingD boxAccountingD = new BoxAccountingD();
			boxAccountingD.setFullName(row[0].toString());
			boxAccountingD.setType(row[1].toString());
			boxAccountingD.setActionId(Integer.parseInt(row[2] + ""));
			boxAccountingD.setDiscount(Double.parseDouble(row[3] + ""));
			boxAccountingD.setNote(row[4].toString());
			boxAccountingD.setInvoice(row[5].toString());
			boxAccountingD.setTime((Date) row[6]);
			boxAccountingD.setIncome(Double.parseDouble(row[7] + ""));
			boxAccountingD.setExpense(Double.parseDouble(row[8] + ""));
			boxAccountingD.setTypeId(Integer.parseInt(row[9] + ""));

			boxAccountingDS.add(boxAccountingD);
		}
		return boxAccountingDS;
	}

	public List<BoxAccountingD> findLoanByDate(Date to, Locale locale) {
		List<BoxAccountingD> boxAccountingDS = new ArrayList<>();

		Query loan = em.createNativeQuery(
				"  SELECT ?2 as FULL_NAME, ?4 as Type, 0 AS ACTION_ID,ROUND(IFNULL((SELECT SUM(IFNULL((DISCOUNT),0)) AS DISCOUNT FROM CUSTOMER_ORDERS WHERE ORDER_TIME < ?1),0)+ IFNULL((SELECT SUM(IFNULL((DISCOUNT),0)) AS DISCOUNT FROM CUSTOMER_PAYMENTS WHERE PAYMENT_TIME < ?1),0),3) AS DISCOUNT ,'empty' as NOTE,'empty' as Invoice,str_to_date(concat(?1,' ',TIME_FORMAT(current_time(),'%T')),'%Y-%m-%d %h:%i:%s') as Time,ROUND(IFNULL((SELECT SUM(IFNULL(TOTAL_PRICE,0)-IFNULL(DISCOUNT,0)-IFNULL(TOTAL_PAYMENT,0)) FROM CUSTOMER_ORDERS WHERE ORDER_TIME  < ?1),0)-IFNULL((SELECT SUM(IFNULL(TOTAL_PRICE-TOTAL_PAID,0)) FROM CUSTOMER_ORDER_RETURNS WHERE CUSTOMER_ORDER_RETURN_TIME  < ?1),0)-IFNULL((SELECT SUM(TOTAL_PAYMENT) + SUM(if(DISCOUNT is null,0,DISCOUNT)) FROM CUSTOMER_PAYMENTS WHERE PAYMENT_TIME <?1),0),3) as Income, 0 as Expense,10 AS TYPE_ID \n"
						+ "UNION ALL\n"
						+ "SELECT ?3 as FULL_NAME, ?4 as Type, 0 AS ACTION_ID,ROUND(IFNULL((SELECT SUM(IFNULL((DISCOUNT),0)) AS DISCOUNT FROM ORDER_PRODUCT_STEPUPS WHERE ORDER_TIME < ?1),0)+ IFNULL((SELECT SUM(IFNULL((DISCOUNT),0)) AS DISCOUNT FROM VENDOR_PAYMENTS WHERE PAYMENT_TIME < ?1),0),3) AS DISCOUNT ,'empty' as NOTE,'empty' as Invoice,\n"
						+ "str_to_date(concat(?1,' ',TIME_FORMAT(current_time(),'%T')),'%Y-%m-%d %h:%i:%s')as Time,0 as Income,  ROUND(IFNULL((SELECT SUM(IFNULL(TOTAL_PRICE,0)-IFNULL(TOTAL_PAYMENT,0) -IFNULL(O.DISCOUNT,0) ) FROM ORDER_PRODUCT_STEPUPS O WHERE ORDER_TIME < ?1),0)-IFNULL((SELECT SUM(TOTAL_PAYMENT) + SUM(IF(vp.DISCOUNT IS NULL,0,vp.DISCOUNT)) FROM VENDOR_PAYMENTS vp WHERE PAYMENT_TIME < ?1 ),0)\n"
						+ "-IFNULL((SELECT SUM(AMOUNT) FROM VENDOR_RETURNS WHERE Time  < ?1),0) ,3) as Expense,10 AS TYPE_ID");
		loan.setParameter(1, to);

		loan.setParameter(2, messageSource.getMessage("boxAccounting.customer", null, locale));
		loan.setParameter(3, messageSource.getMessage("boxAccounting.vendor", null, locale));
		loan.setParameter(4, messageSource.getMessage("boxAccounting.loan", null, locale));

		List<Object[]> loanResultList = loan.getResultList();

		for (Object[] row : loanResultList) {
			BoxAccountingD boxAccountingD = new BoxAccountingD();
			boxAccountingD.setFullName(row[0].toString());
			boxAccountingD.setType(row[1].toString());
			boxAccountingD.setActionId(Integer.parseInt(row[2] + ""));
			boxAccountingD.setDiscount(Double.parseDouble(row[3] + ""));
			boxAccountingD.setNote(row[4].toString());
			boxAccountingD.setInvoice(row[5].toString());
			boxAccountingD.setTime((Date) row[6]);
			boxAccountingD.setIncome(Double.parseDouble(row[7] + ""));
			boxAccountingD.setExpense(Double.parseDouble(row[8] + ""));
			boxAccountingD.setTypeId(Integer.parseInt(row[9] + ""));

			boxAccountingDS.add(boxAccountingD);
		}
		return boxAccountingDS;
	}

	public List<BoxAccountingD> findCustomerPayment(Date from, Date to, Locale locale) {

		Query query = em.createNativeQuery(
				"select c.FULL_NAME as FULL_NAME,?3 as TYPE,p.I_CUSTOMER_PAYMENT as ACTION_ID ,IF(DISCOUNT IS NULL,0,DISCOUNT) AS DISCOUNT,if(p.NOTE is null,'empty',p.NOTE) as NOTE,p.I_CUSTOMER_PAYMENT as Invoice,p.PAYMENT_TIME as Time,p.TOTAL_PAYMENT as Income,0 as Expense,5 AS TYPE_ID from CUSTOMER_PAYMENTS p INNER JOIN CUSTOMERS c ON p.I_CUSTOMER=c.I_CUSTOMER where p.PAYMENT_TIME between ?1 and ?2 order by p.PAYMENT_TIME;");
		query.setParameter(1, from);
		query.setParameter(2, to);
		query.setParameter(3, messageSource.getMessage("boxAccounting.customerPayment", null, locale));

		List<BoxAccountingD> boxAccountingDS = new ArrayList<>();

		List<Object[]> resultList = query.getResultList();

		for (Object[] row : resultList) {
			BoxAccountingD boxAccountingD = new BoxAccountingD();
			boxAccountingD.setFullName(row[0].toString());
			boxAccountingD.setType(row[1].toString());
			boxAccountingD.setActionId(Integer.parseInt(row[2] + ""));
			boxAccountingD.setDiscount(Double.parseDouble(row[3] + ""));
			boxAccountingD.setNote(row[4].toString());
			boxAccountingD.setInvoice(row[5].toString());
			boxAccountingD.setTime((Date) row[6]);
			boxAccountingD.setIncome(Double.parseDouble(row[7] + ""));
			boxAccountingD.setExpense(Double.parseDouble(row[8] + ""));
			boxAccountingD.setTypeId(Integer.parseInt(row[9] + ""));

			boxAccountingDS.add(boxAccountingD);
		}
		return boxAccountingDS;

	}

	public HashMap<String, Double> findCustomerPaymentIncomesByDate(Date date) {
		HashMap<String, Double> incExpValues = new HashMap<String, Double>();

		Query query = em.createNativeQuery(
				"select ROUND(SUM(TOTAL_PAYMENT),3) as Income from CUSTOMER_PAYMENTS where PAYMENT_TIME < ?1");
		query.setParameter(1, date);

		if (query.getSingleResult() != null) {
			incExpValues.put("incomes", Double.parseDouble(query.getSingleResult().toString()));
		} else {
			incExpValues.put("incomes", 0.0);
		}
		incExpValues.put("expenses", 0.0);
		return incExpValues;
	}

	public List<BoxAccountingD> findCustomerOrder(Date from, Date to, Locale locale) {
		Query query = em.createNativeQuery(
				"select c.FULL_NAME as FULL_NAME,?3 as TYPE,o.I_CUSTOMER_ORDER AS ACTION_ID,IF(DISCOUNT IS NULL,0,DISCOUNT) AS DISCOUNT,if(o.NOTE is null,'empty',o.NOTE) as NOTE,o.INVOICE_ID as Invoice,o.ORDER_TIME as Time,if(o.TOTAL_PAYMENT is null,0,o.TOTAL_PAYMENT) as Income, (o.TOTAL_PRICE-if(o.DISCOUNT is null,0,o.DISCOUNT)) as Expense,4 AS TYPE_ID from CUSTOMER_ORDERS o INNER JOIN CUSTOMERS c ON o.I_CUSTOMER=c.I_CUSTOMER where (if(o.TOTAL_PAYMENT is null,0,o.TOTAL_PAYMENT)>0 or o.DISCOUNT>0) and o.ORDER_TIME between ?1 and ?2 order by o.ORDER_TIME;");
		query.setParameter(1, from);
		query.setParameter(2, to);
		query.setParameter(3, messageSource.getMessage("boxAccounting.customerOrders", null, locale));

		List<BoxAccountingD> boxAccountingDS = new ArrayList<>();

		List<Object[]> resultList = query.getResultList();

		for (Object[] row : resultList) {
			BoxAccountingD boxAccountingD = new BoxAccountingD();
			boxAccountingD.setFullName(row[0].toString());
			boxAccountingD.setType(row[1].toString());
			boxAccountingD.setActionId(Integer.parseInt(row[2] + ""));
			boxAccountingD.setDiscount(Double.parseDouble(row[3] + ""));
			boxAccountingD.setNote(row[4].toString());
			boxAccountingD.setInvoice(row[5].toString());
			boxAccountingD.setTime((Date) row[6]);
			boxAccountingD.setIncome(Double.parseDouble(row[7] + ""));
			boxAccountingD.setExpense(Double.parseDouble(row[8] + ""));
			boxAccountingD.setTypeId(Integer.parseInt(row[9] + ""));

			boxAccountingDS.add(boxAccountingD);
		}

		return boxAccountingDS;
	}

	public HashMap<String, Double> findCustomerOrderIncomesByDate(Date date) {
		HashMap<String, Double> incExpValues = new HashMap<String, Double>();

		Query query = em.createNativeQuery(
				"select ROUND(SUM(if(TOTAL_PAYMENT is null,0,TOTAL_PAYMENT)),3) as Income from CUSTOMER_ORDERS where ORDER_TIME<?1");
		query.setParameter(1, date);

		if (query.getSingleResult() != null) {
			incExpValues.put("incomes", Double.parseDouble(query.getSingleResult().toString()));
		} else {
			incExpValues.put("incomes", 0.0);
		}
		incExpValues.put("expenses", 0.0);
		return incExpValues;
	}

	public List<BoxAccountingD> findCustomerReturn(Date from, Date to, Locale locale) {
		Query query = em.createNativeQuery(
				"select c.FULL_NAME as FULL_NAME,?3 as TYPE,r.I_CUSTOMER_ORDER_RETURN AS ACTION_ID,0 AS DISCOUNT,'empty' as NOTE,if(r.REFERENCE_INVOICE_ID is null,0,r.REFERENCE_INVOICE_ID) as Invoice,r.CUSTOMER_ORDER_RETURN_TIME as Time,0 as Income,r.TOTAL_PAID as Expense,6 AS TYPE_ID from CUSTOMER_ORDER_RETURNS r INNER JOIN CUSTOMERS c ON r.I_CUSTOMER=c.I_CUSTOMER where r.CUSTOMER_ORDER_RETURN_TIME between ?1 and ?2 order by r.CUSTOMER_ORDER_RETURN_TIME;");
		query.setParameter(1, from);
		query.setParameter(2, to);
		query.setParameter(3, messageSource.getMessage("boxAccounting.customerReturn", null, locale));

		List<BoxAccountingD> boxAccountingDS = new ArrayList<>();

		List<Object[]> resultList = query.getResultList();

		for (Object[] row : resultList) {
			BoxAccountingD boxAccountingD = new BoxAccountingD();
			boxAccountingD.setFullName(row[0].toString());
			boxAccountingD.setType(row[1].toString());
			boxAccountingD.setActionId(Integer.parseInt(row[2] + ""));
			boxAccountingD.setDiscount(Double.parseDouble(row[3] + ""));
			boxAccountingD.setNote(row[4].toString());
			boxAccountingD.setInvoice(row[5].toString());
			boxAccountingD.setTime((Date) row[6]);
			boxAccountingD.setIncome(Double.parseDouble(row[7] + ""));
			boxAccountingD.setExpense(Double.parseDouble(row[8] + ""));
			boxAccountingD.setTypeId(Integer.parseInt(row[9] + ""));

			boxAccountingDS.add(boxAccountingD);
		}

		return boxAccountingDS;
	}

	public HashMap<String, Double> findCustomerReturnIncomesByDate(Date date) {
		HashMap<String, Double> incExpValues = new HashMap<String, Double>();

		Query query = em.createNativeQuery(
				"select ROUND(SUM(TOTAL_PAID),3) as Expense from CUSTOMER_ORDER_RETURNS  where CUSTOMER_ORDER_RETURN_TIME < ?1");
		query.setParameter(1, date);

		if (query.getSingleResult() != null) {
			incExpValues.put("expenses", Double.parseDouble(query.getSingleResult().toString()));
		} else {
			incExpValues.put("expenses", 0.0);
		}
		incExpValues.put("incomes", 0.0);
		return incExpValues;
	}

	public List<BoxAccountingD> findVendorPayment(Date from, Date to, Locale locale) {
		Query query = em.createNativeQuery(
				"select vs.FULL_NAME as FULL_NAME,?3 as TYPE,vp.I_VENDOR_PAYMENT AS ACTION_ID,IF(vp.DISCOUNT IS NULL,0,vp.DISCOUNT) AS DISCOUNT,if(vp.NOTE is null,'empty',vp.NOTE) as NOTE,vp.I_VENDOR_PAYMENT as Invoice,vp.PAYMENT_TIME as Time,0 as Income,vp.TOTAL_PAYMENT as Expense,2 AS TYPE_ID from VENDOR_PAYMENTS vp INNER JOIN VENDORS vs ON vp.I_VENDOR=vs.I_VENDOR where vp.PAYMENT_TIME between ?1 and ?2 order by vp.PAYMENT_TIME;");
		query.setParameter(1, from);
		query.setParameter(2, to);
		query.setParameter(3, messageSource.getMessage("boxAccounting.vendorPayment", null, locale));

		List<BoxAccountingD> boxAccountingDS = new ArrayList<>();

		List<Object[]> resultList = query.getResultList();

		for (Object[] row : resultList) {
			BoxAccountingD boxAccountingD = new BoxAccountingD();
			boxAccountingD.setFullName(row[0].toString());
			boxAccountingD.setType(row[1].toString());
			boxAccountingD.setActionId(Integer.parseInt(row[2] + ""));
			boxAccountingD.setDiscount(Double.parseDouble(row[3] + ""));
			boxAccountingD.setNote(row[4].toString());
			boxAccountingD.setInvoice(row[5].toString());
			boxAccountingD.setTime((Date) row[6]);
			boxAccountingD.setIncome(Double.parseDouble(row[7] + ""));
			boxAccountingD.setExpense(Double.parseDouble(row[8] + ""));
			boxAccountingD.setTypeId(Integer.parseInt(row[9] + ""));

			boxAccountingDS.add(boxAccountingD);
		}
		return boxAccountingDS;
	}

	public HashMap<String, Double> findVendorPaymentExpenseByDate(Date date) {
		HashMap<String, Double> incExpValues = new HashMap<String, Double>();

		Query query = em.createNativeQuery(
				"select ROUND(SUM(TOTAL_PAYMENT),3) as Expense from VENDOR_PAYMENTS where PAYMENT_TIME < ?1");
		query.setParameter(1, date);

		if (query.getSingleResult() != null) {
			incExpValues.put("expenses", Double.parseDouble(query.getSingleResult().toString()));
		} else {
			incExpValues.put("expenses", 0.0);
		}
		incExpValues.put("incomes", 0.0);
		return incExpValues;
	}

	public List<BoxAccountingD> findVendorOrder(Date from, Date to, Locale locale) {
		Query query = em.createNativeQuery(
				"select vs.FULL_NAME as FULL_NAME,?3 as TYPE,v.I_ORDER_PRODUCT_STEPUP AS ACTION_ID,v.DISCOUNT,'empty' as NOTE,if(v.REFERENCE_INVOICE_ID is null,'empty',v.REFERENCE_INVOICE_ID)as Invoice,v.ORDER_TIME as Time,0 as Income, if(v.TOTAL_PAYMENT is null,0,v.TOTAL_PAYMENT) as Expense,1 AS TYPE_ID from ORDER_PRODUCT_STEPUPS v INNER JOIN VENDORS vs ON v.I_VENDOR=vs.I_VENDOR where (if(v.TOTAL_PAYMENT is null,0,v.TOTAL_PAYMENT)>0 or v.DISCOUNT>0 ) and  v.ORDER_TIME between ?1 and ?2 order by v.ORDER_TIME;");
		query.setParameter(1, from);
		query.setParameter(2, to);
		query.setParameter(3, messageSource.getMessage("boxAccounting.vendorOrders", null, locale));

		List<BoxAccountingD> boxAccountingDS = new ArrayList<>();

		List<Object[]> resultList = query.getResultList();

		for (Object[] row : resultList) {
			BoxAccountingD boxAccountingD = new BoxAccountingD();
			boxAccountingD.setFullName(row[0].toString());
			boxAccountingD.setType(row[1].toString());
			boxAccountingD.setActionId(Integer.parseInt(row[2] + ""));
			boxAccountingD.setDiscount(Double.parseDouble(row[3] + ""));
			boxAccountingD.setNote(row[4].toString());
			boxAccountingD.setInvoice(row[5].toString());
			boxAccountingD.setTime((Date) row[6]);
			boxAccountingD.setIncome(Double.parseDouble(row[7] + ""));
			boxAccountingD.setExpense(Double.parseDouble(row[8] + ""));
			boxAccountingD.setTypeId(Integer.parseInt(row[9] + ""));

			boxAccountingDS.add(boxAccountingD);
		}
		return boxAccountingDS;
	}

	public HashMap<String, Double> findVendorOrderExpenseByDate(Date date) {
		HashMap<String, Double> incExpValues = new HashMap<String, Double>();

		Query query = em.createNativeQuery(
				"select ROUND(SUM(if(TOTAL_PAYMENT is null,0,TOTAL_PAYMENT)),3)  as Expense from ORDER_PRODUCT_STEPUPS where ORDER_TIME < ?1");
		query.setParameter(1, date);

		if (query.getSingleResult() != null) {
			incExpValues.put("expenses", Double.parseDouble(query.getSingleResult().toString()));
		} else {
			incExpValues.put("expenses", 0.0);
		}
		incExpValues.put("incomes", 0.0);

		return incExpValues;
	}

	public List<BoxAccountingD> findVendorReturn(Date from, Date to, Locale locale) {
		Query query = em.createNativeQuery(
				"select vs.FULL_NAME as FULL_NAME,?3 as TYPE,vr.I_RETURN AS ACTION_ID,0 AS DISCOUNT,'empty' as NOTE,vr.I_RETURN as Invoice,vr.Time as Time,vr.AMOUNT as Income,0 as Expense,3 AS TYPE_ID from VENDOR_RETURNS vr INNER JOIN VENDORS vs ON vr.I_VENDOR=vs.I_VENDOR where vr.Time between ?1 and ?2 order by vr.Time;");
		query.setParameter(1, from);
		query.setParameter(2, to);
		query.setParameter(3, messageSource.getMessage("boxAccounting.vendorReturn", null, locale));

		List<BoxAccountingD> boxAccountingDS = new ArrayList<>();

		List<Object[]> resultList = query.getResultList();

		for (Object[] row : resultList) {
			BoxAccountingD boxAccountingD = new BoxAccountingD();
			boxAccountingD.setFullName(row[0].toString());
			boxAccountingD.setType(row[1].toString());
			boxAccountingD.setActionId(Integer.parseInt(row[2] + ""));
			boxAccountingD.setDiscount(Double.parseDouble(row[3] + ""));
			boxAccountingD.setNote(row[4].toString());
			boxAccountingD.setInvoice(row[5].toString());
			boxAccountingD.setTime((Date) row[6]);
			boxAccountingD.setIncome(Double.parseDouble(row[7] + ""));
			boxAccountingD.setExpense(Double.parseDouble(row[8] + ""));
			boxAccountingD.setTypeId(Integer.parseInt(row[9] + ""));

			boxAccountingDS.add(boxAccountingD);
		}

		return boxAccountingDS;
	}

	public HashMap<String, Double> findVendorReturnExpenseByDate(Date date) {
		HashMap<String, Double> incExpValues = new HashMap<String, Double>();

		Query query = em
				.createNativeQuery("select ROUND(SUM(AMOUNT),3) as Income from VENDOR_RETURNS vr where vr.Time < ?1");
		query.setParameter(1, date);

		if (query.getSingleResult() != null) {
			incExpValues.put("incomes", Double.parseDouble(query.getSingleResult().toString()));
		} else {
			incExpValues.put("incomes", 0.0);
		}
		incExpValues.put("expenses", 0.0);
		return incExpValues;
	}

	public List<BoxAccountingD> findIncomes(Date from, Date to, Locale locale) {
		Query query = em.createNativeQuery(
				"select i.INCOME_NAME as FULL_NAME, IF(ic.INCOME_CATEGORY_NAME IS NULL,?3,CONCAT(?3,'/',ic.INCOME_CATEGORY_NAME))  as TYPE, 0 AS ACTION_ID,0 AS DISCOUNT ,if(i.NOTE is null OR i.NOTE='','empty',i.NOTE) as NOTE,'empty' as Invoice, i.EXPENSE_TIME as Time, i.AMOUNT as Income, 0 as Expense,8 AS TYPE_ID from INCOMES i LEFT JOIN INCOME_CATEGORIES ic ON i.incomeCategory_I_INCOME_CATEGORY=ic.I_INCOME_CATEGORY where i.EXPENSE_TIME between ?1 and ?2 order by i.EXPENSE_TIME;");
		query.setParameter(1, from);
		query.setParameter(2, to);
		query.setParameter(3, messageSource.getMessage("boxAccounting.incomes", null, locale));

		List<BoxAccountingD> boxAccountingDS = new ArrayList<>();

		List<Object[]> resultList = query.getResultList();

		for (Object[] row : resultList) {
			BoxAccountingD boxAccountingD = new BoxAccountingD();
			boxAccountingD.setFullName(row[0].toString());
			boxAccountingD.setType(row[1].toString());
			boxAccountingD.setActionId(Integer.parseInt(row[2] + ""));
			boxAccountingD.setDiscount(Double.parseDouble(row[3] + ""));
			boxAccountingD.setNote(row[4].toString());
			boxAccountingD.setInvoice(row[5].toString());
			boxAccountingD.setTime((Date) row[6]);
			boxAccountingD.setIncome(Double.parseDouble(row[7] + ""));
			boxAccountingD.setExpense(Double.parseDouble(row[8] + ""));
			boxAccountingD.setTypeId(Integer.parseInt(row[9] + ""));

			boxAccountingDS.add(boxAccountingD);
		}

		return boxAccountingDS;
	}

	public HashMap<String, Double> findOldIncomes(Date date) {
		HashMap<String, Double> incExpValues = new HashMap<String, Double>();

		Query query = em.createNativeQuery("select ROUND(SUM(AMOUNT),3) as Income from INCOMES where EXPENSE_TIME <?1");
		query.setParameter(1, date);

		if (query.getSingleResult() != null) {
			incExpValues.put("incomes", Double.parseDouble(query.getSingleResult().toString()));
		} else {
			incExpValues.put("incomes", 0.0);
		}
		incExpValues.put("expenses", 0.0);
		return incExpValues;
	}

	public List<BoxAccountingD> findExpenses(Date from, Date to, Locale locale) {

		Query query = em.createNativeQuery(
				"select e.EXPENSE_NAME as FULL_NAME, IF(ec.EXPENSE_CATEGORY_NAME IS NULL,?3,CONCAT(?3,'/',ec.EXPENSE_CATEGORY_NAME)) as TYPE,0 as ACTION_ID,0 AS DISCOUNT,if(e.NOTE is null OR e.NOTE='','empty',e.NOTE) as NOTE,'empty' as Invoice,e.EXPENSE_TIME as Time,0 as Income, e.AMOUNT as Expense,9 AS TYPE_ID from EXPENSES e LEFT JOIN EXPENSE_CATEGORIES ec ON e.expenseCategory_I_EXPENSE_CATEGORY=ec.I_EXPENSE_CATEGORY where e.EXPENSE_TIME between ?1 and ?2 order by e.EXPENSE_TIME;");
		query.setParameter(1, from);
		query.setParameter(2, to);
		query.setParameter(3, messageSource.getMessage("boxAccounting.expenses", null, locale));

		List<BoxAccountingD> boxAccountingDS = new ArrayList<>();

		List<Object[]> resultList = query.getResultList();

		for (Object[] row : resultList) {
			BoxAccountingD boxAccountingD = new BoxAccountingD();
			boxAccountingD.setFullName(row[0].toString());
			boxAccountingD.setType(row[1].toString());
			boxAccountingD.setActionId(Integer.parseInt(row[2] + ""));
			boxAccountingD.setDiscount(Double.parseDouble(row[3] + ""));
			boxAccountingD.setNote(row[4].toString());
			boxAccountingD.setInvoice(row[5].toString());
			boxAccountingD.setTime((Date) row[6]);
			boxAccountingD.setIncome(Double.parseDouble(row[7] + ""));
			boxAccountingD.setExpense(Double.parseDouble(row[8] + ""));
			boxAccountingD.setTypeId(Integer.parseInt(row[9] + ""));

			boxAccountingDS.add(boxAccountingD);
		}

		return boxAccountingDS;
	}

	public HashMap<String, Double> findOldExpenses(Date date) {
		HashMap<String, Double> incExpValues = new HashMap<String, Double>();

		Query query = em
				.createNativeQuery("select ROUND(SUM(AMOUNT),3) as Expense from EXPENSES where EXPENSE_TIME <?1");
		query.setParameter(1, date);

		if (query.getSingleResult() != null) {
			incExpValues.put("expenses", Double.parseDouble(query.getSingleResult().toString()));
		} else {
			incExpValues.put("expenses", 0.0);
		}
		incExpValues.put("incomes", 0.0);
		return incExpValues;
	}

	public List<BoxAccountingD> findWithdraws(Date from, Date to, Locale locale) {

		Query query = em.createNativeQuery(
				"SELECT w.WITHDRAW_NAME AS FULL_NAME,IF(wc.WITHDRAW_CATEGORY_NAME IS NULL,?3,CONCAT(?3,'/',wc.WITHDRAW_CATEGORY_NAME)) AS TYPE,0 AS ACTION_ID,0 AS DISCOUNT,IF(w.NOTE IS NULL OR w.NOTE='','empty',w.NOTE) AS NOTE,'empty' AS Invoice,w.WITHDRAW_TIME AS Time, 0 AS Income, w.AMOUNT AS Expense,10 AS TYPE_ID FROM WITHDRAW w INNER JOIN WITHDRAW_CATEGORIES wc ON w.I_WITHDRAW_CATEGORY=wc.I_WITHDRAW_CATEGORY WHERE w.WITHDRAW_TIME BETWEEN ?1 AND ?2 ORDER BY w.WITHDRAW_TIME;");
		query.setParameter(1, from);
		query.setParameter(2, to);
		query.setParameter(3, messageSource.getMessage("boxAccounting.withdraws", null, locale));

		List<BoxAccountingD> boxAccountingDS = new ArrayList<>();

		List<Object[]> resultList = query.getResultList();

		for (Object[] row : resultList) {
			BoxAccountingD boxAccountingD = new BoxAccountingD();
			boxAccountingD.setFullName(row[0].toString());
			boxAccountingD.setType(row[1].toString());
			boxAccountingD.setActionId(Integer.parseInt(row[2] + ""));
			boxAccountingD.setDiscount(Double.parseDouble(row[3] + ""));
			boxAccountingD.setNote(row[4].toString());
			boxAccountingD.setInvoice(row[5].toString());
			boxAccountingD.setTime((Date) row[6]);
			boxAccountingD.setIncome(Double.parseDouble(row[7] + ""));
			boxAccountingD.setExpense(Double.parseDouble(row[8] + ""));
			boxAccountingD.setTypeId(Integer.parseInt(row[9] + ""));

			boxAccountingDS.add(boxAccountingD);
		}

		return boxAccountingDS;
	}

	public HashMap<String, Double> findOldWithdraws(Date date) {
		HashMap<String, Double> incExpValues = new HashMap<String, Double>();

		Query query = em
				.createNativeQuery("SELECT ROUND(SUM(AMOUNT),3) AS Expense FROM WITHDRAW WHERE WITHDRAW_TIME <?1");
		query.setParameter(1, date);

		if (query.getSingleResult() != null) {
			incExpValues.put("expenses", Double.parseDouble(query.getSingleResult().toString()));
		} else {
			incExpValues.put("expenses", 0.0);
		}
		incExpValues.put("incomes", 0.0);
		return incExpValues;
	}

	public Double findWastedCost() {

		Query query = em.createNativeQuery("SELECT ROUND(SUM(TOTAL_PRICE),3) FROM CUSTOMER_RETURN_WASTED_PRODUCT;");
		if (query.getSingleResult() != null) {
			return (Double.parseDouble(query.getSingleResult() + ""));
		}
		return null;
	}

}
