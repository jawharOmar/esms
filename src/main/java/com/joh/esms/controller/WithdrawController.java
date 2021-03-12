package com.joh.esms.controller;


import com.joh.esms.model.Withdraw;
import com.joh.esms.model.WithdrawCategory;
import com.joh.esms.service.AccountService;
import com.joh.esms.service.WithdrawCategoryService;
import com.joh.esms.service.WithdrawService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.DecimalFormat;
import java.util.Date;

@Controller()
@RequestMapping(path = "/withdraws")
public class WithdrawController {


    private static final Logger logger = Logger.getLogger(ExpenseController.class);

    @Autowired
    private WithdrawService withdrawService;

    @Autowired
    private WithdrawCategoryService withdrawCategoryService;

    @Autowired
    private AccountService accountService;

    private static final DecimalFormat df = new DecimalFormat("#.###");

    @GetMapping()
    public String getAllWithdraws(@RequestParam(required = false, defaultValue = "-1") int cat, @RequestParam() @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
                                  @RequestParam() @DateTimeFormat(pattern = "yyyy-MM-dd") Date to, Model model) {
        logger.info("getAllWithdraws->fired");
        Iterable<Withdraw> withdraws;
        if (cat == -1) {
            withdraws = withdrawService.findAll(from, to);
        } else {
            withdraws = withdrawService.findByCat(from, to, cat);
        }

        logger.info("withdraws=" + withdraws);
        model.addAttribute("category", withdrawCategoryService.findAll());
        model.addAttribute("withdraws", withdraws);
        model.addAttribute("from", from);
        model.addAttribute("to", to);

        return "adminWithdraws";
    }


    @GetMapping(path = "/add")
    private String getAddingWithdraw(Model model) {
        logger.info("getAddingWithdraw->fired");

        Iterable<WithdrawCategory> withdrawCategories = withdrawCategoryService.findAll();


        model.addAttribute("withdrawCategories", withdrawCategories);
        model.addAttribute("accountBalance", df.format(accountService.findOne(1).getBalance()));

        model.addAttribute("withdraw", new Withdraw());
        return "withdraw/addWithdraw";
    }


    @PostMapping(path = "/add")
    private String addWithdraw(@RequestBody @Valid Withdraw withdraw, BindingResult result, Model model) {

        logger.info("addWithdraw->fired");
        logger.info("withdraw=" + withdraw);
        double accountBalance = accountService.findOne(1).getBalance();
        if (accountBalance < withdraw.getAmount()) {
            result.rejectValue("amount", null, "Amount must be less than Account Balance.");
        }

        logger.info("error=" + result.getAllErrors());
        if (result.hasErrors()) {
            Iterable<WithdrawCategory> withdrawCategories = withdrawCategoryService.findAll();

            model.addAttribute("withdrawCategories", withdrawCategories);
            model.addAttribute("accountBalance",  df.format(accountBalance));
            model.addAttribute("withdraw", withdraw);
            return "withdraw/addWithdraw";
        } else {

            withdrawService.save(withdraw);
            return "success";
        }
    }


    @PostMapping(path = "/delete/{id}")
    private String deleteWithdraw(@PathVariable int id) {
        logger.info("deleteWithdraw->fired");
        withdrawService.delete(id);
        return "success";
    }

    @GetMapping(path = "/edit/{id}")
    private String editingWithdraw(@PathVariable int id, Model model) {
        logger.info("editingWithdraw->fired");
        logger.info("id=" + id);
        Withdraw withdraw = withdrawService.findOne(id);
        logger.info("withdraw=" + withdraw);

        Iterable<WithdrawCategory> withdrawCategories = withdrawCategoryService.findAll();

        model.addAttribute("withdrawCategories", withdrawCategories);
        model.addAttribute("accountBalance",  df.format(accountService.findOne(1).getBalance()));
        model.addAttribute("withdraw", withdraw);

        return "withdraw/editWithdraw";
    }

    @PostMapping(path = "/update")
    private String updateWithdraw(@RequestBody Withdraw withdraw, BindingResult result, Model model) {
        logger.info("updateWithdraw->fired");

        logger.info("withdraw=" + withdraw);

        double lastWithdraw = withdrawService.findOne(withdraw.getId()).getAmount();
        double accountBalance = accountService.findOne(1).getBalance();

        if ((accountBalance + lastWithdraw) < withdraw.getAmount()) {
            result.rejectValue("amount", null, "Amount must be less than Account Balance.");
        }
        logger.info("errors=" + result.getAllErrors());
        if (result.hasErrors()) {
            Iterable<WithdrawCategory> withdrawCategories = withdrawCategoryService.findAll();

            model.addAttribute("withdrawCategories", withdrawCategories);
            model.addAttribute("accountBalance",  df.format(accountBalance));
            return "withdraw/editWithdraw";
        } else {

            withdrawService.update(withdraw);
            return "success";
        }
    }


}
