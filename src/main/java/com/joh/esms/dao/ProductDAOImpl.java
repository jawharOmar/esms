package com.joh.esms.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.apache.log4j.Logger;

import com.joh.esms.domain.model.ProductD;
import com.joh.esms.domain.model.ProductTransactionD;
import com.joh.esms.domain.model.SearchD;
import com.joh.esms.domain.model.SearchInStockD;

public class ProductDAOImpl implements ProductDAOExt {

	private static final Logger logger = Logger.getLogger(ProductDAOImpl.class);

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<ProductD> findStock() {
		List<ProductD> productDs = new ArrayList<>();

		Query query = em.createNativeQuery(
				"SELECT A.I_PRODUCT,PRODUCT_CODE,PRODUCT_NAME,UNIT_TYPE_NAME,STOCK_LEVEL,COST,PRICE,CATEGORY,PACKET_SIZE,MINIMUM_STOCK_LEVEL,ROUND(LP.PAYMENT_AMOUNT/LP.QUANTITY,3) AS LAST_PRICE,UNIT,I_ATTACHED_FILE\n"
						+ "\t\t\tFROM ( SELECT P.I_PRODUCT,P.PRODUCT_CODE,P.PRODUCT_NAME,PUT.UNIT_TYPE_NAME,\n"
						+ "\t\t\t\tIFNULL(SUM(PS.QUANTITY-PS.SOLD_QUANTITY),0) AS STOCK_LEVEL,ROUND(SUM(PAYMENT_AMOUNT)/SUM(QUANTITY),3) as COST,P.PRICE,PC.CATEGORY_NAME AS CATEGORY,PACKET_SIZE,MINIMUM_STOCK_LEVEL,MAX(I_PRODUCT_STEPUP) AS LAST_PURCHASE,UNIT,I_ATTACHED_FILE\n"
						+ "\t\t\t\tFROM PRODUCTS P LEFT OUTER JOIN PRODUCT_UNIT_TYPES PUT USING(I_PRODUCT_UNIT_TYPE) LEFT OUTER JOIN PRODUCT_CATEGORIES PC USING(I_PRODUCT_CATEGORY) \n"
						+ "\t\t\t\tLEFT OUTER JOIN PRODUCT_STEPUPS PS ON P.I_PRODUCT=PS.I_PRODUCT\n"
						+ "\t\t\t\tGROUP BY P.I_PRODUCT ORDER BY PRODUCT_CODE) A\n"
						+ "\t\t\t\tLEFT OUTER JOIN PRODUCT_STEPUPS LP ON LP.I_PRODUCT_STEPUP=LAST_PURCHASE;");

		List<Object[]> resultList = query.getResultList();

		for (Object row[] : resultList) {
			ProductD productD = new ProductD();

			productD.setProductId((Integer) row[0]);
			productD.setCode((String) row[1]);
			productD.setName((String) row[2]);
			productD.setUnitType((String) row[3]);
			productD.setStockLevel(Double.parseDouble("" + row[4]));
			productD.setCost((Double) row[5]);
			productD.setPrice((Double) row[6]);
			productD.setCategory((String) row[7]);
			if (row[8] != null)
				productD.setPacketSize((Double) row[8]);

			if (row[9] != null)
				productD.setMinimumStockLevel(Double.parseDouble("" + row[9]));

			if (row[10] != null)
				productD.setLastPrice((Double) row[10]);

			if (row[11] != null)
				productD.setUnit((String) row[11]);

			if (row[12] != null)
				productD.setAttachedFileId((Integer) row[12]);

			productDs.add(productD);
		}
		return productDs;
	}

	@Override
	public List<ProductD> findStockByStockId(int stockId) {
		List<ProductD> productDs = new ArrayList<>();

		Query query = em.createNativeQuery(
				"SELECT A.I_PRODUCT,PRODUCT_CODE,PRODUCT_NAME,UNIT_TYPE_NAME,IFNULL(ROUND(PSTOCK.AMOUNT,3),0) STOCK_LEVEL,COST,PRICE,CATEGORY,PACKET_SIZE,MINIMUM_STOCK_LEVEL,ROUND(LP.PAYMENT_AMOUNT/LP.QUANTITY,3) AS LAST_PRICE,UNIT,I_ATTACHED_FILE \n"
						+ "FROM( SELECT P.I_PRODUCT,P.PRODUCT_CODE,P.PRODUCT_NAME,PUT.UNIT_TYPE_NAME,IFNULL(SUM(QUANTITY-SOLD_QUANTITY),0) AS STOCK_LEVEL,ROUND(SUM(PAYMENT_AMOUNT)/SUM(QUANTITY),3) as COST,P.PRICE,PC.CATEGORY_NAME AS CATEGORY,PACKET_SIZE,MINIMUM_STOCK_LEVEL,MAX(I_PRODUCT_STEPUP) AS LAST_PURCHASE,UNIT,I_ATTACHED_FILE \n"
						+ "FROM PRODUCTS P LEFT OUTER JOIN PRODUCT_UNIT_TYPES PUT USING(I_PRODUCT_UNIT_TYPE)\n"
						+ " LEFT OUTER JOIN PRODUCT_CATEGORIES PC USING(I_PRODUCT_CATEGORY) \n"
						+ " LEFT OUTER JOIN PRODUCT_STEPUPS PS ON P.I_PRODUCT=PS.I_PRODUCT \n"
						+ " GROUP BY P.I_PRODUCT ORDER BY PRODUCT_CODE) A \n"
						+ "LEFT OUTER JOIN PRODUCT_STEPUPS LP ON LP.I_PRODUCT_STEPUP=LAST_PURCHASE\n"
						+ "LEFT OUTER JOIN PRODUCT_STOCKS PSTOCK ON  A.I_PRODUCT=PSTOCK.I_PRODUCT\n"
						+ "AND PSTOCK.I_STOCK=?1 ;");

		query.setParameter(1, stockId);

		List<Object[]> resultList = query.getResultList();

		for (Object row[] : resultList) {
			ProductD productD = new ProductD();

			productD.setProductId((Integer) row[0]);
			productD.setCode((String) row[1]);
			productD.setName((String) row[2]);
			productD.setUnitType((String) row[3]);
			productD.setStockLevel(Double.parseDouble("" + row[4]));
			productD.setCost((Double) row[5]);
			productD.setPrice((Double) row[6]);
			productD.setCategory((String) row[7]);
			if (row[8] != null)
				productD.setPacketSize((Double) row[8]);

			if (row[9] != null)
				productD.setMinimumStockLevel(Double.parseDouble("" + row[9]));

			if (row[10] != null)
				productD.setLastPrice((Double) row[10]);

			if (row[11] != null)
				productD.setUnit((String) row[11]);

			if (row[12] != null)
				productD.setAttachedFileId((Integer) row[12]);

			productDs.add(productD);
		}
		return productDs;
	}

	@Override
	public List<ProductD> findStockByProductCategoryId(int id) {
		List<ProductD> productDs = new ArrayList<>();

		Query query = em.createNativeQuery("SELECT \n"
				+ "A.I_PRODUCT,PRODUCT_CODE,PRODUCT_NAME,UNIT_TYPE_NAME,STOCK_LEVEL,COST,PRICE,CATEGORY,PACKET_SIZE,MINIMUM_STOCK_LEVEL,ROUND(LP.PAYMENT_AMOUNT/LP.QUANTITY,3) AS LAST_PRICE\n"
				+ "FROM ( SELECT P.I_PRODUCT,P.PRODUCT_CODE,P.PRODUCT_NAME,PUT.UNIT_TYPE_NAME,\n"
				+ "IFNULL(SUM(QUANTITY-SOLD_QUANTITY),0) AS STOCK_LEVEL,ROUND(SUM(PAYMENT_AMOUNT)/SUM(QUANTITY),3) as COST,P.PRICE,PC.CATEGORY_NAME AS CATEGORY,PACKET_SIZE,MINIMUM_STOCK_LEVEL,MAX(I_PRODUCT_STEPUP) AS LAST_PURCHASE\n"
				+ "FROM PRODUCTS P LEFT OUTER JOIN PRODUCT_UNIT_TYPES PUT USING(I_PRODUCT_UNIT_TYPE) LEFT OUTER JOIN PRODUCT_CATEGORIES PC USING(I_PRODUCT_CATEGORY) \n"
				+ "LEFT OUTER JOIN PRODUCT_STEPUPS PS ON P.I_PRODUCT=PS.I_PRODUCT AND QUANTITY-SOLD_QUANTITY \n"
				+ "WHERE P.I_PRODUCT_CATEGORY=?1 GROUP BY P.I_PRODUCT ORDER BY PRODUCT_CODE) A\n"
				+ "LEFT OUTER JOIN PRODUCT_STEPUPS LP ON LP.I_PRODUCT_STEPUP=LAST_PURCHASE;");

		query.setParameter(1, id);

		List<Object[]> resultList = query.getResultList();

		for (Object row[] : resultList) {
			ProductD productD = new ProductD();

			productD.setProductId((Integer) row[0]);
			productD.setCode((String) row[1]);
			productD.setName((String) row[2]);
			productD.setUnitType((String) row[3]);
			productD.setStockLevel(Double.parseDouble("" + row[4]));
			productD.setCost((Double) row[5]);
			productD.setPrice((Double) row[6]);
			productD.setCategory((String) row[7]);
			if (row[8] != null)
				productD.setPacketSize((Double) row[8]);

			if (row[9] != null)
				productD.setMinimumStockLevel(Double.parseDouble("" + row[9]));

			if (row[10] != null)
				productD.setLastPrice((Double) row[10]);

			productDs.add(productD);
		}
		return productDs;
	}

	@Override
	public ProductD findProductByCode(String productCode) {
		Query query = em.createNativeQuery(
				"SELECT P.I_PRODUCT,P.PRODUCT_CODE,P.PRODUCT_NAME,PUT.UNIT_TYPE_NAME,IFNULL(SUM(QUANTITY-SOLD_QUANTITY),0) AS STOCK_LEVEL,ROUND(SUM(PAYMENT_AMOUNT)/SUM(QUANTITY),3) as COST,PRICE,PACKET_SIZE,MINIMUM_STOCK_LEVEL \n"
						+ "    FROM\n" + "PRODUCTS  P \n" + "    LEFT OUTER JOIN\n"
						+ "PRODUCT_UNIT_TYPES PUT USING(I_PRODUCT_UNIT_TYPE)   \n" + "    LEFT OUTER JOIN\n"
						+ "PRODUCT_STEPUPS PS ON P.I_PRODUCT=PS.I_PRODUCT AND QUANTITY-SOLD_QUANTITY>0  AND (EXPIRATION_DATE>CURDATE()|EXPIRATION_DATE is null)  \n"
						+ "    WHERE PRODUCT_CODE=?1   GROUP BY\n" + "P.I_PRODUCT;");

		query.setParameter(1, productCode);

		Object[] row = (Object[]) query.getSingleResult();

		ProductD productD = new ProductD();

		productD.setProductId((Integer) row[0]);
		productD.setCode((String) row[1]);
		productD.setName((String) row[2]);
		productD.setUnitType((String) row[3]);
		productD.setStockLevel(Double.parseDouble("" + row[4]));
		productD.setCost((Double) row[5]);
		productD.setPrice((Double) row[6]);
		if (row[7] != null)
			productD.setPacketSize((Double) row[7]);

		if (row[8] != null)
			productD.setMinimumStockLevel(Double.parseDouble("" + row[8]));

		return productD;

	}

	@Override
	public ProductD findProductByProductStepUpId(int productStepUpId) {

		Query query = em.createNativeQuery(
				"SELECT P.I_PRODUCT,P.PRODUCT_CODE,P.PRODUCT_NAME,PUT.UNIT_TYPE_NAME,IFNULL(QUANTITY-SOLD_QUANTITY,0) AS STOCK_LEVEL,ROUND(PAYMENT_AMOUNT/QUANTITY,3) as COST,PACKET_SIZE,I_PRODUCT_STEPUP\n"
						+ "FROM PRODUCTS  P        \n" + "INNER JOIN PRODUCT_STEPUPS PS \n"
						+ "ON P.I_PRODUCT=PS.I_PRODUCT  AND QUANTITY-SOLD_QUANTITY>0 AND PS.I_PRODUCT_STEPUP=?1 \n"
						+ "LEFT OUTER JOIN PRODUCT_UNIT_TYPES PUT USING(I_PRODUCT_UNIT_TYPE) ;");

		query.setParameter(1, productStepUpId);

		Object[] row = (Object[]) query.getSingleResult();

		ProductD productD = new ProductD();

		productD.setProductId((Integer) row[0]);
		productD.setCode((String) row[1]);
		productD.setName((String) row[2]);
		productD.setUnitType((String) row[3]);
		productD.setStockLevel(Double.parseDouble("" + row[4]));
		productD.setCost((Double) row[5]);
		if (row[6] != null)
			productD.setPacketSize((Double) row[6]);

		productD.setProductStepUpId(Integer.parseInt("" + row[7]));

		return productD;
	}

	@Override
	public List<ProductTransactionD> findProductTransactionDetailByStockId(int productId, int stockId) {
		List<ProductTransactionD> productTransactionDs = new ArrayList<>();

		Query query = em.createNativeQuery("SELECT I_PRODUCT,ROUND(AMOUNT,3) AS AMOUNT,TIME,REFERENCE,TYPE FROM (\n"
				+ "SELECT I_PRODUCT,QUANTITY AS AMOUNT,ORDER_TIME AS TIME,I_ORDER_PRODUCT_STEPUP AS REFERENCE,'ORDER_PRODUCT_STEPUP' AS TYPE\n"
				+ "FROM PRODUCT_STEPUPS\n" + "INNER JOIN ORDER_PRODUCT_STEPUPS USING(I_ORDER_PRODUCT_STEPUP)\n"
				+ "WHERE I_PRODUCT=?2 AND I_STOCK=?1\n" + "UNION\n"
				+ "SELECT I_PRODUCT,AMOUNT,TRASFER_TIME AS TIME,I_STOCK_TRANSFER AS REFERENCE,'STOCK_TRANSFER' AS TYPE\n"
				+ "FROM STOCK_TRANSFERS \n" + "INNER JOIN STOCK_TRANSFER_DETAILS USING(I_STOCK_TRANSFER)\n"
				+ "WHERE I_PRODUCT=?2\n" + "AND I_STOCK_TO=?1\n" + "UNION\n"
				+ "SELECT I_PRODUCT,-AMOUNT,TRASFER_TIME,I_STOCK_TRANSFER AS REFERENCE,'STOCK_TRANSFER' AS TYPE\n"
				+ "FROM STOCK_TRANSFERS \n" + "INNER JOIN STOCK_TRANSFER_DETAILS USING(I_STOCK_TRANSFER)\n"
				+ "WHERE I_PRODUCT=?2\n" + "AND I_STOCK_FROM=?1\n" + "UNION\n"
				+ "SELECT  I_PRODUCT,-SUM(AMOUNT) AS AMOUNT,CO.ORDER_TIME AS TIME,I_CUSTOMER_ORDER AS REFERENCE,'CUSTOMER_ORDER' AS TYPE\n"
				+ "FROM CUSTOMER_ORDERS CO\n" + "INNER JOIN CUSTOMER_ORDER_DETAILS USING(I_CUSTOMER_ORDER)\n"
				+ "INNER JOIN CUSTOMER_ORDER_DETAIL_PRODUCT_STEPUPS USING(I_CUSTOMER_ORDER_DETAIL)\n"
				+ "WHERE I_STOCK=?1 AND I_PRODUCT=?2\n" + "GROUP BY I_CUSTOMER_ORDER_DETAIL) A\n" + "ORDER BY TIME;");

		query.setParameter(1, stockId);
		query.setParameter(2, productId);

		List<Object[]> resultList = query.getResultList();

		for (Object row[] : resultList) {
			ProductTransactionD productTransactionD = new ProductTransactionD();

			productTransactionD.setProductId((Integer) row[0]);
			productTransactionD.setAmount(Double.parseDouble("" + row[1]));
			productTransactionD.setTime((Timestamp) row[2]);
			productTransactionD.setReference((Integer) row[3]);
			productTransactionD.setType((String) row[4]);
			productTransactionDs.add(productTransactionD);
		}
		return productTransactionDs;
	}

	// Find Cost For Wasted Product
	@Override
	public Double findCostByProductCode(String code) {
		Query query = em.createNativeQuery("SELECT COST\n"
				+ "FROM ( SELECT P.PRODUCT_CODE,ROUND(SUM(PAYMENT_AMOUNT)/SUM(QUANTITY),3) as COST ,MAX(I_PRODUCT_STEPUP) AS LAST_PURCHASE\n"
				+ "FROM PRODUCTS P LEFT OUTER JOIN PRODUCT_UNIT_TYPES PUT USING(I_PRODUCT_UNIT_TYPE) LEFT OUTER JOIN PRODUCT_CATEGORIES PC USING(I_PRODUCT_CATEGORY) \n"
				+ "LEFT OUTER JOIN PRODUCT_STEPUPS PS ON P.I_PRODUCT=PS.I_PRODUCT\n"
				+ "GROUP BY P.I_PRODUCT ORDER BY PRODUCT_CODE) A\n"
				+ "LEFT OUTER JOIN PRODUCT_STEPUPS LP ON LP.I_PRODUCT_STEPUP=LAST_PURCHASE where PRODUCT_CODE=?1");
		query.setParameter(1, code);
		if (query.getSingleResult() != null) {
			return (Double.parseDouble(query.getSingleResult() + ""));
		}
		return null;
	}

	@Override
	public Integer findTotalPageStocks(Integer showPerPage) {
		Query finPages = em.createNativeQuery("SELECT CEIL(COUNT(I_PRODUCT)/?1) FROM PRODUCTS");
		finPages.setParameter(1, showPerPage);
		if (finPages.getSingleResult() != null) {
			return (Integer.parseInt(finPages.getSingleResult().toString()));
		}
		return null;
	}

	@Override
	public List<ProductD> findStockPaginate(Integer offset, Integer showPerPage) {
		List<ProductD> productDs = new ArrayList<>();

		String sql = "SELECT A.I_PRODUCT,PRODUCT_CODE,PRODUCT_NAME,UNIT_TYPE_NAME,STOCK_LEVEL,COST,PRICE,CATEGORY,PACKET_SIZE,MINIMUM_STOCK_LEVEL,ROUND(LP.PAYMENT_AMOUNT/LP.QUANTITY,3) AS LAST_PRICE,UNIT,I_ATTACHED_FILE\n"
				+ "FROM ( SELECT P.I_PRODUCT,P.PRODUCT_CODE,P.PRODUCT_NAME,PUT.UNIT_TYPE_NAME,\n"
				+ "IFNULL(SUM(PS.QUANTITY-PS.SOLD_QUANTITY),0) AS STOCK_LEVEL,ROUND(SUM(PAYMENT_AMOUNT)/SUM(QUANTITY),3) as COST,P.PRICE,PC.CATEGORY_NAME AS CATEGORY,PACKET_SIZE,MINIMUM_STOCK_LEVEL,MAX(I_PRODUCT_STEPUP) AS LAST_PURCHASE,UNIT,I_ATTACHED_FILE\n"
				+ "FROM PRODUCTS P LEFT OUTER JOIN PRODUCT_UNIT_TYPES PUT USING(I_PRODUCT_UNIT_TYPE) LEFT OUTER JOIN PRODUCT_CATEGORIES PC USING(I_PRODUCT_CATEGORY) \n"
				+ "LEFT OUTER JOIN PRODUCT_STEPUPS PS ON P.I_PRODUCT=PS.I_PRODUCT\n"
				+ "GROUP BY P.I_PRODUCT ORDER BY PRODUCT_CODE) A\n"
				+ "LEFT OUTER JOIN PRODUCT_STEPUPS LP ON LP.I_PRODUCT_STEPUP=LAST_PURCHASE LIMIT ?1,?2";

		logger.debug("sql=" + sql);
		Query query = em.createNativeQuery(sql);
		query.setParameter(1, offset);
		query.setParameter(2, showPerPage);

		List<Object[]> resultList = query.getResultList();

		for (Object row[] : resultList) {
			ProductD productD = new ProductD();

			productD.setProductId((Integer) row[0]);
			productD.setCode((String) row[1]);
			productD.setName((String) row[2]);
			productD.setUnitType((String) row[3]);
			productD.setStockLevel(Double.parseDouble("" + row[4]));
			productD.setCost((Double) row[5]);
			productD.setPrice((Double) row[6]);
			productD.setCategory((String) row[7]);
			if (row[8] != null)
				productD.setPacketSize((Double) row[8]);

			if (row[9] != null)
				productD.setMinimumStockLevel(Double.parseDouble("" + row[9]));

			if (row[10] != null)
				productD.setLastPrice((Double) row[10]);

			if (row[11] != null)
				productD.setUnit((String) row[11]);

			if (row[12] != null)
				productD.setAttachedFileId((Integer) row[12]);

			productDs.add(productD);
		}

		return productDs;
	}

	@Override
	public List<ProductD> findStockPaginateByStockId(Integer stockId, Integer offset, Integer showPerPage) {
		List<ProductD> productDs = new ArrayList<>();

		String sql = "SELECT A.I_PRODUCT,PRODUCT_CODE,PRODUCT_NAME,UNIT_TYPE_NAME,IFNULL(ROUND(PSTOCK.AMOUNT,3),0) STOCK_LEVEL,COST,PRICE,CATEGORY,PACKET_SIZE,MINIMUM_STOCK_LEVEL,ROUND(LP.PAYMENT_AMOUNT/LP.QUANTITY,3) AS LAST_PRICE,UNIT,I_ATTACHED_FILE \n"
				+ "FROM( SELECT P.I_PRODUCT,P.PRODUCT_CODE,P.PRODUCT_NAME,PUT.UNIT_TYPE_NAME,IFNULL(SUM(QUANTITY-SOLD_QUANTITY),0) AS STOCK_LEVEL,ROUND(SUM(PAYMENT_AMOUNT)/SUM(QUANTITY),3) as COST,P.PRICE,PC.CATEGORY_NAME AS CATEGORY,PACKET_SIZE,MINIMUM_STOCK_LEVEL,MAX(I_PRODUCT_STEPUP) AS LAST_PURCHASE,UNIT,I_ATTACHED_FILE \n"
				+ "FROM PRODUCTS P LEFT OUTER JOIN PRODUCT_UNIT_TYPES PUT USING(I_PRODUCT_UNIT_TYPE)\n"
				+ " LEFT OUTER JOIN PRODUCT_CATEGORIES PC USING(I_PRODUCT_CATEGORY) \n"
				+ " LEFT OUTER JOIN PRODUCT_STEPUPS PS ON P.I_PRODUCT=PS.I_PRODUCT \n"
				+ " GROUP BY P.I_PRODUCT ORDER BY PRODUCT_CODE) A \n"
				+ "LEFT OUTER JOIN PRODUCT_STEPUPS LP ON LP.I_PRODUCT_STEPUP=LAST_PURCHASE\n"
				+ "LEFT OUTER JOIN PRODUCT_STOCKS PSTOCK ON  A.I_PRODUCT=PSTOCK.I_PRODUCT\n"
				+ "AND PSTOCK.I_STOCK=?1 LIMIT ?2,?3";

		logger.debug("sql=" + sql);

		Query query = em.createNativeQuery(sql);

		query.setParameter(1, stockId);
		query.setParameter(2, offset);
		query.setParameter(3, showPerPage);

		List<Object[]> resultList = query.getResultList();

		for (Object row[] : resultList) {
			ProductD productD = new ProductD();

			productD.setProductId((Integer) row[0]);
			productD.setCode((String) row[1]);
			productD.setName((String) row[2]);
			productD.setUnitType((String) row[3]);
			productD.setStockLevel(Double.parseDouble("" + row[4]));
			productD.setCost((Double) row[5]);
			productD.setPrice((Double) row[6]);
			productD.setCategory((String) row[7]);
			if (row[8] != null)
				productD.setPacketSize((Double) row[8]);

			if (row[9] != null)
				productD.setMinimumStockLevel(Double.parseDouble("" + row[9]));

			if (row[10] != null)
				productD.setLastPrice((Double) row[10]);

			if (row[11] != null)
				productD.setUnit((String) row[11]);

			if (row[12] != null)
				productD.setAttachedFileId((Integer) row[12]);

			productDs.add(productD);
		}
		return productDs;
	}

	@Override
	public List<SearchInStockD> findByProductCode(String keyword) {
		return SearchQuery(keyword,
				"SELECT I_PRODUCT,PRODUCT_CODE FROM PRODUCTS WHERE PRODUCT_CODE LIKE CONCAT('%',?1,'%')");
	}

	@Override
	public List<SearchInStockD> findByProductName(String name) {
		return SearchQuery(name,
				"SELECT I_PRODUCT,PRODUCT_NAME FROM PRODUCTS WHERE PRODUCT_NAME LIKE CONCAT('%',?1,'%')");
	}

	@Override
	public List<SearchInStockD> findByProductCategory(String category) {
		return SearchQuery(category,
				"SELECT I_PRODUCT_CATEGORY,CATEGORY_NAME FROM PRODUCT_CATEGORIES WHERE CATEGORY_NAME LIKE CONCAT('%',?1,'%')");
	}

	@Override
	public ProductD findByProductIdAndStockId(Integer productId, Integer stockId) {
		Query query = em.createNativeQuery(
				"SELECT  A.I_PRODUCT, PRODUCT_CODE,PRODUCT_NAME,UNIT_TYPE_NAME,IFNULL(ROUND(PSTOCK.AMOUNT,3),0) STOCK_LEVEL,COST, PRICE,CATEGORY,PACKET_SIZE,MINIMUM_STOCK_LEVEL,ROUND(LP.PAYMENT_AMOUNT/LP.QUANTITY, 3) AS LAST_PRICE, UNIT,I_ATTACHED_FILE\n"
						+ " FROM ( SELECT P.I_PRODUCT,P.PRODUCT_CODE,P.PRODUCT_NAME, PUT.UNIT_TYPE_NAME,IFNULL(SUM(QUANTITY-SOLD_QUANTITY),0) AS STOCK_LEVEL,ROUND(SUM(PAYMENT_AMOUNT)/SUM(QUANTITY), 3) as COST,P.PRICE, PC.CATEGORY_NAME AS CATEGORY, PACKET_SIZE,MINIMUM_STOCK_LEVEL,MAX(I_PRODUCT_STEPUP) AS LAST_PURCHASE, UNIT,I_ATTACHED_FILE\n"
						+ " FROM PRODUCTS P LEFT OUTER JOIN PRODUCT_UNIT_TYPES PUT USING(I_PRODUCT_UNIT_TYPE)\n"
						+ " LEFT OUTER JOIN PRODUCT_CATEGORIES PC USING(I_PRODUCT_CATEGORY)\n"
						+ " LEFT OUTER JOIN PRODUCT_STEPUPS PS ON P.I_PRODUCT=PS.I_PRODUCT AND (EXPIRATION_DATE>CURDATE() OR EXPIRATION_DATE IS NULL) \n"
						+ " WHERE P.I_PRODUCT =?1\n" + " GROUP BY P.I_PRODUCT ORDER BY PRODUCT_CODE) A \n"
						+ "LEFT OUTER JOIN PRODUCT_STEPUPS LP ON LP.I_PRODUCT_STEPUP=LAST_PURCHASE\n"
						+ "LEFT OUTER JOIN PRODUCT_STOCKS PSTOCK ON  A.I_PRODUCT=PSTOCK.I_PRODUCT\n"
						+ "AND PSTOCK.I_STOCK=?2");
		query.setParameter(1, productId);
		query.setParameter(2, stockId);

		List<Object[]> resultList = query.getResultList();

		ProductD productD = new ProductD();

		for (Object row[] : resultList) {

			productD.setProductId((Integer) row[0]);
			productD.setCode((String) row[1]);
			productD.setName((String) row[2]);
			productD.setUnitType((String) row[3]);
			productD.setStockLevel(Double.parseDouble("" + row[4]));
			productD.setCost((Double) row[5]);
			productD.setPrice((Double) row[6]);
			productD.setCategory((String) row[7]);
			if (row[8] != null)
				productD.setPacketSize((Double) row[8]);

			if (row[9] != null)
				productD.setMinimumStockLevel(Double.parseDouble("" + row[9]));

			if (row[10] != null)
				productD.setLastPrice((Double) row[10]);

			if (row[11] != null)
				productD.setUnit((String) row[11]);

			if (row[12] != null)
				productD.setAttachedFileId((Integer) row[12]);

		}
		return productD;
	}

	@Override
	public ProductD findByProductId(Integer productId) {
		Query query = em.createNativeQuery(
				"SELECT A.I_PRODUCT,PRODUCT_CODE,PRODUCT_NAME,UNIT_TYPE_NAME,STOCK_LEVEL,COST,PRICE,CATEGORY,PACKET_SIZE,MINIMUM_STOCK_LEVEL,ROUND(LP.PAYMENT_AMOUNT/LP.QUANTITY,3) AS LAST_PRICE,UNIT,I_ATTACHED_FILE\n"
						+ "FROM ( SELECT P.I_PRODUCT,P.PRODUCT_CODE,P.PRODUCT_NAME,PUT.UNIT_TYPE_NAME,\n"
						+ "IFNULL(SUM(PS.QUANTITY-PS.SOLD_QUANTITY),0) AS STOCK_LEVEL,ROUND(SUM(PAYMENT_AMOUNT)/SUM(QUANTITY),3) as COST,P.PRICE,PC.CATEGORY_NAME AS CATEGORY,PACKET_SIZE,MINIMUM_STOCK_LEVEL,MAX(I_PRODUCT_STEPUP) AS LAST_PURCHASE,UNIT,I_ATTACHED_FILE\n"
						+ "FROM PRODUCTS P LEFT OUTER JOIN PRODUCT_UNIT_TYPES PUT USING(I_PRODUCT_UNIT_TYPE) LEFT OUTER JOIN PRODUCT_CATEGORIES PC USING(I_PRODUCT_CATEGORY) \n"
						+ "LEFT OUTER JOIN PRODUCT_STEPUPS PS ON P.I_PRODUCT=PS.I_PRODUCT\n" + "WHERE P.I_PRODUCT =?1\n"
						+ "GROUP BY P.I_PRODUCT ORDER BY PRODUCT_CODE) A\n"
						+ "LEFT OUTER JOIN PRODUCT_STEPUPS LP ON LP.I_PRODUCT_STEPUP=LAST_PURCHASE;");
		query.setParameter(1, productId);

		List<Object[]> resultList = query.getResultList();

		ProductD productD = new ProductD();

		for (Object row[] : resultList) {

			productD.setProductId((Integer) row[0]);
			productD.setCode((String) row[1]);
			productD.setName((String) row[2]);
			productD.setUnitType((String) row[3]);
			productD.setStockLevel(Double.parseDouble("" + row[4]));
			productD.setCost((Double) row[5]);
			productD.setPrice((Double) row[6]);
			productD.setCategory((String) row[7]);
			if (row[8] != null)
				productD.setPacketSize((Double) row[8]);

			if (row[9] != null)
				productD.setMinimumStockLevel(Double.parseDouble("" + row[9]));

			if (row[10] != null)
				productD.setLastPrice((Double) row[10]);

			if (row[11] != null)
				productD.setUnit((String) row[11]);

			if (row[12] != null)
				productD.setAttachedFileId((Integer) row[12]);

		}
		return productD;
	}

	@Override
	public List<ProductD> findByProductCategoryAndStockId(Integer categoryId, Integer stockId) {
		List<ProductD> productDS = new ArrayList<>();

		Query query = em.createNativeQuery(
				"SELECT  A.I_PRODUCT, PRODUCT_CODE,PRODUCT_NAME,UNIT_TYPE_NAME,IFNULL(ROUND(PSTOCK.AMOUNT,3),0) STOCK_LEVEL,COST, PRICE,CATEGORY,PACKET_SIZE,MINIMUM_STOCK_LEVEL,ROUND(LP.PAYMENT_AMOUNT/LP.QUANTITY, 3) AS LAST_PRICE, UNIT,I_ATTACHED_FILE\n"
						+ " FROM ( SELECT P.I_PRODUCT,P.PRODUCT_CODE,P.PRODUCT_NAME, PUT.UNIT_TYPE_NAME,IFNULL(SUM(QUANTITY-SOLD_QUANTITY),0) AS STOCK_LEVEL,ROUND(SUM(PAYMENT_AMOUNT)/SUM(QUANTITY), 3) as COST,P.PRICE, PC.CATEGORY_NAME AS CATEGORY, PACKET_SIZE,MINIMUM_STOCK_LEVEL,MAX(I_PRODUCT_STEPUP) AS LAST_PURCHASE, UNIT,I_ATTACHED_FILE\n"
						+ " FROM PRODUCTS P LEFT OUTER JOIN PRODUCT_UNIT_TYPES PUT USING(I_PRODUCT_UNIT_TYPE)\n"
						+ " LEFT OUTER JOIN PRODUCT_CATEGORIES PC USING(I_PRODUCT_CATEGORY)\n"
						+ " LEFT OUTER JOIN PRODUCT_STEPUPS PS ON P.I_PRODUCT=PS.I_PRODUCT AND (EXPIRATION_DATE>CURDATE() OR EXPIRATION_DATE IS NULL) \n"
						+ " WHERE PC.I_PRODUCT_CATEGORY=?1\n" + " GROUP BY P.I_PRODUCT ORDER BY PRODUCT_CODE) A \n"
						+ "LEFT OUTER JOIN PRODUCT_STEPUPS LP ON LP.I_PRODUCT_STEPUP=LAST_PURCHASE\n"
						+ "LEFT OUTER JOIN PRODUCT_STOCKS PSTOCK ON  A.I_PRODUCT=PSTOCK.I_PRODUCT\n"
						+ "AND PSTOCK.I_STOCK=?2");
		query.setParameter(1, categoryId);
		query.setParameter(2, stockId);

		List<Object[]> resultList = query.getResultList();

		for (Object row[] : resultList) {
			ProductD productD = new ProductD();
			productD.setProductId((Integer) row[0]);
			productD.setCode((String) row[1]);
			productD.setName((String) row[2]);
			productD.setUnitType((String) row[3]);
			productD.setStockLevel(Double.parseDouble("" + row[4]));
			productD.setCost((Double) row[5]);
			productD.setPrice((Double) row[6]);
			productD.setCategory((String) row[7]);
			if (row[8] != null)
				productD.setPacketSize((Double) row[8]);

			if (row[9] != null)
				productD.setMinimumStockLevel(Double.parseDouble("" + row[9]));

			if (row[10] != null)
				productD.setLastPrice((Double) row[10]);

			if (row[11] != null)
				productD.setUnit((String) row[11]);

			if (row[12] != null)
				productD.setAttachedFileId((Integer) row[12]);

			productDS.add(productD);

		}
		return productDS;
	}

	@Override
	public List<ProductD> findByProductCategoryId(Integer categoryId) {
		List<ProductD> productDS = new ArrayList<>();

		Query query = em.createNativeQuery(
				"SELECT A.I_PRODUCT,PRODUCT_CODE,PRODUCT_NAME,UNIT_TYPE_NAME,STOCK_LEVEL,COST,PRICE,CATEGORY,PACKET_SIZE,MINIMUM_STOCK_LEVEL,ROUND(LP.PAYMENT_AMOUNT/LP.QUANTITY,3) AS LAST_PRICE,UNIT,I_ATTACHED_FILE\n"
						+ "FROM ( SELECT P.I_PRODUCT,P.PRODUCT_CODE,P.PRODUCT_NAME,PUT.UNIT_TYPE_NAME,\n"
						+ "IFNULL(SUM(PS.QUANTITY-PS.SOLD_QUANTITY),0) AS STOCK_LEVEL,ROUND(SUM(PAYMENT_AMOUNT)/SUM(QUANTITY),3) as COST,P.PRICE,PC.CATEGORY_NAME AS CATEGORY,PACKET_SIZE,MINIMUM_STOCK_LEVEL,MAX(I_PRODUCT_STEPUP) AS LAST_PURCHASE,UNIT,I_ATTACHED_FILE\n"
						+ "FROM PRODUCTS P LEFT OUTER JOIN PRODUCT_UNIT_TYPES PUT USING(I_PRODUCT_UNIT_TYPE) LEFT OUTER JOIN PRODUCT_CATEGORIES PC USING(I_PRODUCT_CATEGORY) \n"
						+ "LEFT OUTER JOIN PRODUCT_STEPUPS PS ON P.I_PRODUCT=PS.I_PRODUCT\n"
						+ "WHERE PC.I_PRODUCT_CATEGORY=?1\n" + "GROUP BY P.I_PRODUCT ORDER BY PRODUCT_CODE) A\n"
						+ "LEFT OUTER JOIN PRODUCT_STEPUPS LP ON LP.I_PRODUCT_STEPUP=LAST_PURCHASE;");
		query.setParameter(1, categoryId);

		List<Object[]> resultList = query.getResultList();

		for (Object row[] : resultList) {
			ProductD productD = new ProductD();
			productD.setProductId((Integer) row[0]);
			productD.setCode((String) row[1]);
			productD.setName((String) row[2]);
			productD.setUnitType((String) row[3]);
			productD.setStockLevel(Double.parseDouble("" + row[4]));
			productD.setCost((Double) row[5]);
			productD.setPrice((Double) row[6]);
			productD.setCategory((String) row[7]);
			if (row[8] != null)
				productD.setPacketSize((Double) row[8]);

			if (row[9] != null)
				productD.setMinimumStockLevel(Double.parseDouble("" + row[9]));

			if (row[10] != null)
				productD.setLastPrice((Double) row[10]);

			if (row[11] != null)
				productD.setUnit((String) row[11]);

			if (row[12] != null)
				productD.setAttachedFileId((Integer) row[12]);

			productDS.add(productD);

		}
		return productDS;
	}

	@Override
	public List<SearchD> findByProductNameOrCode(String keyword) {
		List<SearchD> searchDS = new ArrayList<>();
		keyword = "'%" + keyword.replaceAll(" ", "%") + "%'";
		logger.info("keyword=" + keyword);
		Query query = em
				.createNativeQuery("SELECT I_PRODUCT,PRODUCT_CODE,PRODUCT_NAME FROM PRODUCTS WHERE (PRODUCT_CODE LIKE "
						+ keyword + " || PRODUCT_NAME LIKE  " + keyword + " )");

		List<Object[]> resultList = query.getResultList();

		for (Object row[] : resultList) {
			SearchD searchD = new SearchD();
			searchD.setId((Integer) row[0]);
			searchD.setKeyword((String) row[1]);
			searchD.setSecondKeyword((String) row[2]);
			searchDS.add(searchD);
		}
		logger.info("searchDS=" + searchDS);
		return searchDS;
	}

	@Override
	public List<ProductD> findStockByKeyword(String keyword) {
		List<ProductD> productDs = new ArrayList<>();

		Query query = em.createNativeQuery(
				"SELECT A.I_PRODUCT,PRODUCT_CODE,PRODUCT_NAME,UNIT_TYPE_NAME,STOCK_LEVEL,COST,PRICE,CATEGORY,PACKET_SIZE,MINIMUM_STOCK_LEVEL,ROUND(LP.PAYMENT_AMOUNT/LP.QUANTITY,3) AS LAST_PRICE,UNIT,I_ATTACHED_FILE\n"
						+ "\t\t\tFROM ( SELECT P.I_PRODUCT,P.PRODUCT_CODE,P.PRODUCT_NAME,PUT.UNIT_TYPE_NAME,\n"
						+ "\t\t\t\tIFNULL(SUM(PS.QUANTITY-PS.SOLD_QUANTITY),0) AS STOCK_LEVEL,ROUND(SUM(PAYMENT_AMOUNT)/SUM(QUANTITY),3) as COST,P.PRICE,PC.CATEGORY_NAME AS CATEGORY,PACKET_SIZE,MINIMUM_STOCK_LEVEL,MAX(I_PRODUCT_STEPUP) AS LAST_PURCHASE,UNIT,I_ATTACHED_FILE\n"
						+ "\t\t\t\tFROM PRODUCTS P LEFT OUTER JOIN PRODUCT_UNIT_TYPES PUT USING(I_PRODUCT_UNIT_TYPE) LEFT OUTER JOIN PRODUCT_CATEGORIES PC USING(I_PRODUCT_CATEGORY) \n"
						+ "\t\t\t\tLEFT OUTER JOIN PRODUCT_STEPUPS PS ON P.I_PRODUCT=PS.I_PRODUCT\n"
						+ "\t\t\t\tGROUP BY P.I_PRODUCT ORDER BY PRODUCT_CODE) A\n"
						+ "\t\t\t\tLEFT OUTER JOIN PRODUCT_STEPUPS LP ON LP.I_PRODUCT_STEPUP=LAST_PURCHASE  WHERE (PRODUCT_CODE LIKE CONCAT('%',?1,'%') || PRODUCT_NAME LIKE CONCAT('%',?1,'%'));");
		query.setParameter(1, keyword);

		List<Object[]> resultList = query.getResultList();

		for (Object row[] : resultList) {
			ProductD productD = new ProductD();

			productD.setProductId((Integer) row[0]);
			productD.setCode((String) row[1]);
			productD.setName((String) row[2]);
			productD.setUnitType((String) row[3]);
			productD.setStockLevel(Double.parseDouble("" + row[4]));
			productD.setCost((Double) row[5]);
			productD.setPrice((Double) row[6]);
			productD.setCategory((String) row[7]);
			if (row[8] != null)
				productD.setPacketSize((Double) row[8]);

			if (row[9] != null)
				productD.setMinimumStockLevel(Double.parseDouble("" + row[9]));

			if (row[10] != null)
				productD.setLastPrice((Double) row[10]);

			if (row[11] != null)
				productD.setUnit((String) row[11]);

			if (row[12] != null)
				productD.setAttachedFileId((Integer) row[12]);

			productDs.add(productD);
		}
		return productDs;
	}

	@Override
	public List<ProductD> findStockByStockIdAndKeyword(Integer stockId, String keyword) {
		List<ProductD> productDs = new ArrayList<>();

		Query query = em.createNativeQuery(
				"SELECT A.I_PRODUCT,PRODUCT_CODE,PRODUCT_NAME,UNIT_TYPE_NAME,IFNULL(ROUND(PSTOCK.AMOUNT,3),0) STOCK_LEVEL,COST,PRICE,CATEGORY,PACKET_SIZE,MINIMUM_STOCK_LEVEL,ROUND(LP.PAYMENT_AMOUNT/LP.QUANTITY,3) AS LAST_PRICE,UNIT,I_ATTACHED_FILE \n"
						+ "FROM( SELECT P.I_PRODUCT,P.PRODUCT_CODE,P.PRODUCT_NAME,PUT.UNIT_TYPE_NAME,IFNULL(SUM(QUANTITY-SOLD_QUANTITY),0) AS STOCK_LEVEL,ROUND(SUM(PAYMENT_AMOUNT)/SUM(QUANTITY),3) as COST,P.PRICE,PC.CATEGORY_NAME AS CATEGORY,PACKET_SIZE,MINIMUM_STOCK_LEVEL,MAX(I_PRODUCT_STEPUP) AS LAST_PURCHASE,UNIT,I_ATTACHED_FILE \n"
						+ "FROM PRODUCTS P LEFT OUTER JOIN PRODUCT_UNIT_TYPES PUT USING(I_PRODUCT_UNIT_TYPE)\n"
						+ " LEFT OUTER JOIN PRODUCT_CATEGORIES PC USING(I_PRODUCT_CATEGORY) \n"
						+ " LEFT OUTER JOIN PRODUCT_STEPUPS PS ON P.I_PRODUCT=PS.I_PRODUCT \n"
						+ " GROUP BY P.I_PRODUCT ORDER BY PRODUCT_CODE) A \n"
						+ "LEFT OUTER JOIN PRODUCT_STEPUPS LP ON LP.I_PRODUCT_STEPUP=LAST_PURCHASE\n"
						+ "LEFT OUTER JOIN PRODUCT_STOCKS PSTOCK ON  A.I_PRODUCT=PSTOCK.I_PRODUCT\n"
						+ "AND PSTOCK.I_STOCK=?1 WHERE (PRODUCT_CODE LIKE CONCAT('%',?2,'%') || PRODUCT_NAME LIKE CONCAT('%',?2,'%')) ;");

		query.setParameter(1, stockId);
		query.setParameter(2, keyword);

		List<Object[]> resultList = query.getResultList();

		for (Object row[] : resultList) {
			ProductD productD = new ProductD();

			productD.setProductId((Integer) row[0]);
			productD.setCode((String) row[1]);
			productD.setName((String) row[2]);
			productD.setUnitType((String) row[3]);
			productD.setStockLevel(Double.parseDouble("" + row[4]));
			productD.setCost((Double) row[5]);
			productD.setPrice((Double) row[6]);
			productD.setCategory((String) row[7]);
			if (row[8] != null)
				productD.setPacketSize((Double) row[8]);

			if (row[9] != null)
				productD.setMinimumStockLevel(Double.parseDouble("" + row[9]));

			if (row[10] != null)
				productD.setLastPrice((Double) row[10]);

			if (row[11] != null)
				productD.setUnit((String) row[11]);

			if (row[12] != null)
				productD.setAttachedFileId((Integer) row[12]);

			productDs.add(productD);
		}
		return productDs;
	}

	private List<SearchInStockD> SearchQuery(String keyword, String statement) {
		List<SearchInStockD> searchInStockDS = new ArrayList<>();

		Query query = em.createNativeQuery(statement);
		query.setParameter(1, keyword);

		List<Object[]> resultList = query.getResultList();

		for (Object row[] : resultList) {
			SearchInStockD searchInStockD = new SearchInStockD();
			searchInStockD.setProductId((Integer) row[0]);
			searchInStockD.setKeyword((String) row[1]);

			searchInStockDS.add(searchInStockD);
		}
		return searchInStockDS;

	}
}
