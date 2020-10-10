package com.joh.esms.controller;


import com.joh.esms.model.WithdrawCategory;
import com.joh.esms.service.WithdrawCategoryService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller()
@RequestMapping(path = "/withdrawCategories")
public class WithdrawCategoryController {
    private static final Logger logger = Logger.getLogger(ExpenseCategoryController.class);

    @Autowired
    private WithdrawCategoryService withdrawCategoryService;

    @GetMapping()
    public String getAllWithdrawCategories(Model model){
        logger.info("getAllWithdrawCategories->fired");

        Iterable<WithdrawCategory> withdrawCategories = withdrawCategoryService.findAll();

        logger.info("withdrawCategories=" + withdrawCategories);
        model.addAttribute("withdrawCategories", withdrawCategories);

        return "withdrawCategories";
    }

    @GetMapping("/add")
    private String getAddingWithdrawCategory(Model model){
        logger.info("getAddingWithdrawCategory->fired");
        model.addAttribute("withdrawCategory", new WithdrawCategory());

        return "withdrawCategory/addWithdrawCategory";
    }


    @PostMapping("/add")
    private String addWithdrawCategory(@RequestBody @Valid WithdrawCategory withdrawCategory, BindingResult result,Model model){
        logger.info("addWithdrawCategory->fired");
        logger.info("withdrawCategory=" + withdrawCategory);

        logger.info("error=" + result.getAllErrors());
        if (result.hasErrors()) {
            model.addAttribute("withdrawCategory", withdrawCategory);
            return "withdrawCategory/addWithdrawCategory";
        } else {
            withdrawCategoryService.save(withdrawCategory);
            return "success";
        }
    }

    @PostMapping(path = "/delete/{id}")
    private String deleteWithdrawCategory(@PathVariable int id) {
        logger.info("deleteWithdrawCategory->fired");
        withdrawCategoryService.delete(id);
        return "success";
    }

    @GetMapping(path = "/edit/{id}")
    private String editingWithdrawCategory(@PathVariable int id, Model model) {
        logger.info("editingWithdrawCategory->fired");
        logger.info("id=" + id);
        WithdrawCategory withdrawCategory = withdrawCategoryService.findOne(id);
        logger.info("withdrawCategory=" + withdrawCategory);

        model.addAttribute("withdrawCategory", withdrawCategory);

        return "withdrawCategory/editWithdrawCategory";
    }

    @PostMapping(path = "/update")
    private String updateWithdrawCategory(@RequestBody WithdrawCategory withdrawCategory, BindingResult result) {
        logger.info("updateWithdrawCategory->fired");

        logger.info("withdrawCategory=" + withdrawCategory);

        logger.info("errors=" + result.getAllErrors());
        if (result.hasErrors()) {
            return "withdrawCategory/editWithdrawCategory";
        } else {

            withdrawCategoryService.update(withdrawCategory);
            return "success";
        }
    }


}
