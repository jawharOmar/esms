package com.joh.esms.controller;

import com.joh.esms.dao.*;
import com.joh.esms.model.*;
import com.joh.esms.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller()
@RequestMapping(path = "/psearch")
public class productdetcontroller {

    @Autowired
    ProductDAO pservice;

    @Autowired
    ReportDAO repdao;

    @Autowired
    StockService sservice;

    @Autowired
    ProductStockDAO pstock;

    @Autowired
    pRepDAO prep;

    @GetMapping()
    public String reports(Model model) {


        Iterable<Product> products = pservice.findAll();
        model.addAttribute("products", products);
        Iterable<Stock> stocks = sservice.findAll();

        model.addAttribute("stocks", stocks);


        return "psearch";
    }


    @RequestMapping(path = "/find", method = RequestMethod.POST)
    public String vget(HttpServletRequest request,
                       Model model) {


        if (request.getParameter("stocks").toString().contains("all")) {
            List<product_reports> report = prep.findAllByproductOrderByTime(Integer.valueOf(request.getParameter("product")));
            model.addAttribute("report", report);
            model.addAttribute("selected", pservice.findOne(Integer.valueOf(request.getParameter("product"))));
            model.addAttribute("sumval", repdao.sumproduct(Integer.valueOf(request.getParameter("product"))));
            model.addAttribute("selected_stock", -1);


        } else {
            List<product_reports> reportt = prep.findthem(
                    Integer.valueOf(request.getParameter("product")),
                    Integer.valueOf(request.getParameter("stocks")),
                    request.getParameter("stocks"));
            model.addAttribute("report", reportt);
            model.addAttribute("selected_stock", Integer.valueOf(request.getParameter("stocks")));
            model.addAttribute("pval", pstock.findByStockIdAndProductId(
                    Integer.valueOf(request.getParameter("stocks")),
                    Integer.valueOf(request.getParameter("product"))

            ));

            model.addAttribute("selected", pservice.findOne(Integer.valueOf(request.getParameter("product"))));


        }


        Iterable<Product> products = pservice.findAll();
        model.addAttribute("products", products);

        Iterable<Stock> stocks = sservice.findAll();

        model.addAttribute("stocks", stocks);


        return "psearch";
    }


}
