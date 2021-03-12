package com.joh.esms.controller;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joh.esms.dao.ProjectStatementDAO;
import com.joh.esms.dao.RepDAO;
import com.joh.esms.dao.VendorRepDAO;
import com.joh.esms.domain.model.ProjectStatementD;
import com.joh.esms.model.Customer;
import com.joh.esms.model.Vendor;
import com.joh.esms.model.reports;
import com.joh.esms.model.vendor_reports;
import com.joh.esms.service.CustomerService;
import com.joh.esms.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;

@Controller()
@RequestMapping(path = "/report")
public class reportController {

    @Autowired
    private RepDAO service;

    @Autowired
    private VendorRepDAO vrservice;

    @Autowired
    CustomerService cservice;

    @Autowired
    VendorService vservice;

    @Autowired
    private ProjectStatementDAO projectStatementDAO;

    @GetMapping()
    public String reports(Model model, Locale locale) throws JsonProcessingException {
        Iterable<Customer> customers = cservice.findAll();
        model.addAttribute("customers", customers);
        Iterable<Vendor> vendors = vservice.findAll();
        model.addAttribute("vendors", vendors);
        return "report";
    }

    @RequestMapping(path = "/get", method = RequestMethod.POST)
    public String aupdate(HttpServletRequest request,
                          Model model) {
        int id = Integer.valueOf(request.getParameter("id"));

        Iterable<Vendor> vendors = vservice.findAll();
        model.addAttribute("vendors", vendors);
        Iterable<Customer> customers = cservice.findAll();
        Customer selected = cservice.findOne(id);

        if (!request.getParameter("pay").toString().matches("all")) {
            List<reports> rep = service.findAllByIdAndTypeOrderByTime(id, request.getParameter("pay"));
            model.addAttribute("report", rep);
        } else {
            List<reports> rep = service.findAllByIdOrderByTime(id);
            model.addAttribute("report", rep);
        }

        model.addAttribute("isVendor", false);
        model.addAttribute("customers", customers);
        model.addAttribute("selected", selected);
        return "report";
    }


    @RequestMapping(path = "/vget", method = RequestMethod.POST)
    public String vget(HttpServletRequest request,
                       Model model) {

        System.out.println(request.getParameter("pay"));

        int id = Integer.valueOf(request.getParameter("id"));

        Iterable<Vendor> vendors = vservice.findAll();
        model.addAttribute("vendors", vendors);


        Iterable<Customer> customers = cservice.findAll();
        Vendor selected = vservice.findOne(id);
        if (!request.getParameter("pay").toString().matches("all")) {
            List<vendor_reports> rep = vrservice.findAllByIdAndTypeOrderByTime(id, request.getParameter("pay"));
            model.addAttribute("report", rep);

        } else {
            List<vendor_reports> rep = vrservice.findAllByIdOrderByTime(id);
            model.addAttribute("report", rep);
        }
        model.addAttribute("isVendor", true);
        model.addAttribute("customers", customers);
        model.addAttribute("selected", selected);
        return "report";
    }

    @GetMapping("/projectStatement")
    public String projectStatement(Model model,@RequestParam(value = "customerId",required = false) Integer customerId
            , @RequestParam(value = "projectId",required = false) Integer projectId
            , @RequestParam(value = "actionType",required = false,defaultValue = "-1") String actionType) throws JsonProcessingException {
        Iterable<Customer> customers = cservice.findAll();

        ObjectMapper objectMapper = new ObjectMapper();
        model.addAttribute("jsonCustomers", objectMapper.writeValueAsString(customers));

        if(customerId!=null && projectId!=null && !actionType.matches("-1")){
            List<ProjectStatementD> projectStatementDS;
            if (actionType.matches("all")) {
                projectStatementDS = projectStatementDAO.findPSByProject(customerId, projectId);
            } else {
                projectStatementDS = projectStatementDAO.findPSByProjectAndType(customerId, projectId, actionType);
            }
            Customer selected = cservice.findOne(customerId);
            model.addAttribute("report", projectStatementDS);
            model.addAttribute("selected", selected);
        }
        return "projectStatement";
    }
}
