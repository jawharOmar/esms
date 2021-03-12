package com.joh.esms.commons;

public class Test {
	public static void main(String[] args) {
		System.out.println("SELECT PS.* FROM PRODUCTS P INNER JOIN  PRODUCT_STEPUPS PS USING(I_PRODUCT)\n"
				+ "WHERE  QUANTITY-SOLD_QUANTITY>= ?2 \n" + "AND PRODUCT_CODE= ?1 "
				+ "ORDER BY EXPIRATION_DATE LIMIT 1;");
	}
}
