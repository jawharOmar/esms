package com.joh.esms.controller;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joh.esms.dao.ProductStepUpDAO;
import com.joh.esms.model.CustomerOrder;
import com.joh.esms.model.VenderReturnsDetail;
import com.joh.esms.model.VendorReturn;
import com.joh.esms.service.ProductService;
import com.joh.esms.service.ProductStepUpService;
import com.joh.esms.service.ProductStockService;
import com.joh.esms.service.StockService;
import com.joh.esms.service.VenderReturnService;
import com.joh.esms.service.VendorService;


@Controller
@RequestMapping(path = "/venderReturns")
public class VendorReturnController {

	private static final Logger logger = Logger.getLogger(VendorReturnController.class);

	@Autowired
	private ProductService productService;

    @Autowired
    private VendorService vendorService;

    @Autowired
    private ProductStepUpDAO productStepUpService;

    @Autowired
    private ProductStepUpService pstep;

    @Autowired
    private StockService stockService;

    @Autowired
    private ProductStockService productStockService;

    @Autowired
    private VenderReturnService venderReturnService;

	@GetMapping(path = "/add")
	private String addreturn(Model model) throws JsonProcessingException {


	    model.addAttribute("products",productService.findAll());

	    VendorReturn returns=new VendorReturn();
	    VenderReturnsDetail detail=new VenderReturnsDetail();
        ObjectMapper mapper = new ObjectMapper();
        model.addAttribute("vendorreturn", mapper.writeValueAsString(returns));
        model.addAttribute("detail", mapper.writeValueAsString(detail));
        model.addAttribute("Vendors",vendorService.findAll());
        model.addAttribute("Stocks",stockService.findAll());
        model.addAttribute("Products",productService.findAll());

		return "addVenderReturn";
	}

    @GetMapping(path = "/view")
    private String viewReturn(@RequestParam() @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
                              @RequestParam() @DateTimeFormat(pattern = "yyyy-MM-dd") Date to,Model model)  {


	    model.addAttribute("verdorReturns",venderReturnService.findAllByTimeBetween(from,to));
        model.addAttribute("from",from);
        model.addAttribute("to",to);
        return "VendorReturns";
    }

    @GetMapping(path = "/get")
    private String getstockuups(Model model,@RequestParam(required = true) int id) throws JsonProcessingException {


        model.addAttribute("products",productService.findAll());

        CustomerOrder order=new CustomerOrder();
        ObjectMapper mapper = new ObjectMapper();
        model.addAttribute("customerOrder", mapper.writeValueAsString(order));

        return "addVenderReturn";
    }

    @PostMapping(path = "/add",produces="application/json;charset=UTF-8")
    private String get(@RequestBody VendorReturn vendorReturn,Model model) throws JsonProcessingException {
        logger.info("Add Vendor Return->fired");


        venderReturnService.save(vendorReturn);
        return "success";
    }

    @ResponseBody
    @GetMapping(path = "/getVender/{id}",produces="application/json;charset=UTF-8")
    public String getvendor(@PathVariable int id) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
	    return mapper.writeValueAsString(vendorService.findOne(id));
    }


    @ResponseBody
    @GetMapping(path = "/getProduct/{id}/stock/{istock}",produces="application/json;charset=UTF-8")
    public String ProductStock(@PathVariable int id,@PathVariable int istock) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(productStockService.findOne(istock,id));
    }

    @ResponseBody
    @GetMapping(path = "/getPrice/{id}",produces="application/json;charset=UTF-8")
    public String getProduct(@PathVariable int id) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        int pid=productStepUpService.findTopByProductIdOrderByIdDesc(id).getId();
        double price=pstep.findLastPrice(pid);
        return mapper.writeValueAsString(price);
    }



    @GetMapping(path = "/{id}/print")
    private String printvendorReturn(@PathVariable int id, Model model) {

	    model.addAttribute("VendorReturn",venderReturnService.findone(id));
        return "printVendorReturn";
    }


    @GetMapping(path = "/delete/{id}")
    private String delete(@PathVariable int id) {

        logger.info("Delete Vendor Return->fired");

        logger.info("id=" + id);

        venderReturnService.delete(id);

        return "success";
    }
}

