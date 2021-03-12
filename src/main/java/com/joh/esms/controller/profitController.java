package com.joh.esms.controller;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.joh.esms.dao.ProfitDAO;
import com.joh.esms.model.Customer;
import com.joh.esms.model.CustomerPayment;
import com.joh.esms.model.profit;
import com.joh.esms.service.CustomerPaymentService;
import com.joh.esms.service.CustomerService;

@Controller()
@RequestMapping(path = "/profit")
public class profitController {

    @Autowired
    CustomerService cservice;

    @Autowired
    private ProfitDAO service;

    @Autowired
    private CustomerPaymentService customerPaymentService;

	@GetMapping()
	public String reports(Model model, Locale locale) throws JsonProcessingException {

        Iterable<Customer> customers=cservice.findAll();
        model.addAttribute("customers",customers);

        return "profit";
	}


    @RequestMapping(path = "/calc",method = RequestMethod.POST)
    public String vget(HttpServletRequest request,@RequestParam @DateTimeFormat(pattern = "MM/dd/yyyy") Date from,
                       @RequestParam @DateTimeFormat(pattern = "MM/dd/yyyy") Date to,
                       Model model) {

        double discount = 0.0;

        if (!request.getParameter("Customer").toString().contains("all")) {
            List<CustomerPayment> customerPayments;
            if (to == null || from == null) {
                List<profit> p = service.findAllBycustomer(Integer.valueOf(request.getParameter("Customer")));
                customerPayments = customerPaymentService.findAllByCustomerId(Integer.valueOf(request.getParameter("Customer")));

                model.addAttribute("profit", p);
            } else {
                List<profit> p = service.findAllBycustomerAndTimeBetween(Integer.valueOf(request.getParameter("Customer")), from, to);
                customerPayments = customerPaymentService.findAllByCustomerIdAndTimeBetween(Integer.valueOf(request.getParameter("Customer")), from, to);

                model.addAttribute("profit", p);
            }

            for (CustomerPayment customerPayment : customerPayments) {
                if (customerPayment.getDiscount() != null) {
                    discount += customerPayment.getDiscount();
                }
            }
            model.addAttribute("discount", discount);
        } else {

            Iterable<CustomerPayment> customerPayments;
            if (to != null && from != null) {
                List<profit> p = service.findAllByTimeBetween(from, to);
                customerPayments = customerPaymentService.findAllByTimeBetween(from, to);
                model.addAttribute("profit", p);
            } else {
                List<profit> p = service.findAll();
                customerPayments = customerPaymentService.findAll();
                model.addAttribute("profit", p);
            }
            for (CustomerPayment customerPayment : customerPayments) {
                if (customerPayment.getDiscount() != null) {
                    discount += customerPayment.getDiscount();
                }
            }
            model.addAttribute("discount", discount);
        }
        Iterable<Customer> customers = cservice.findAll();
        model.addAttribute("customers", customers);

        return "profit";
    }



}
