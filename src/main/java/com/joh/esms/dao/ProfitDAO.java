package com.joh.esms.dao;

import com.joh.esms.model.profit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ProfitDAO extends JpaRepository<profit, Integer> {

	List<profit> findAllBycustomerOrderByTime(int i_customer);

	List<profit> findAllByTimeBetween(Date from, Date to);

	List<profit> findAllBycustomer(int i_customer);



	List<profit> findAllBycustomerAndTimeBetween(int i_customer,Date from,Date to);

}
