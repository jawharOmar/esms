package com.joh.esms.controller;

import com.joh.esms.dao.BoxAccountingDAO;
import com.joh.esms.domain.model.BoxAccountingD;
import com.joh.esms.model.Account;
import com.joh.esms.service.AccountService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

@Controller()
@RequestMapping(path = "/boxAccounting")
public class BoxAccountingController {

    private static final Logger logger = Logger.getLogger(BoxAccountingController.class);

    @Autowired
    private BoxAccountingDAO boxAccountingDAO;

    @Autowired
    private AccountService accountService;


    @Autowired
    private MessageSource messageSource;

    @GetMapping("/main")
    public String getBoxAccounting(@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
                                   @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date to, Model model, Locale locale) {
        logger.info("getBoxAccounting->fired");
        List<BoxAccountingD> boxAccountingDS;

        if (from != null && to != null) {
            Account account = accountService.findOne(1);

//            Find Old Balance If difference Between Account And Box Account
            boxAccountingDS = boxAccountingDAO.findBalance(locale);
            double incomes = boxAccountingDS.stream().mapToDouble(e -> e.getIncome()).sum();
            double expenses = boxAccountingDS.stream().mapToDouble(e -> e.getExpense()).sum();

            boxAccountingDS = boxAccountingDAO.findBalanceByDate(from, to, locale);
            HashMap<String, Double> incExpValues = boxAccountingDAO.findIncomesAndExpensesByDate(from);

            Double difference_balance = account.getBalance() - (incomes - expenses);


            if (difference_balance != 0) {
                if (difference_balance < 0) {
                    incExpValues.put("expenses", incExpValues.get("expenses") - difference_balance);
                } else {
                    incExpValues.put("incomes", incExpValues.get("incomes") + difference_balance);
                }
            }

            for (BoxAccountingD boxAccountingD : boxAccountingDAO.findLoanByDate(to, locale)) {
                boxAccountingDS.add(boxAccountingD);
            }
            model.addAttribute("incExpValues", incExpValues);
            model.addAttribute("from", from);
            model.addAttribute("to", to);
        } else {
            boxAccountingDS = boxAccountingDAO.findBalance(locale);
            for (BoxAccountingD boxAccountingD : boxAccountingDAO.findLoan(locale)) {
                boxAccountingDS.add(boxAccountingD);
            }
        }

        Double wastedCost = 0.0;

        if (boxAccountingDAO.findWastedCost() != null) {
            wastedCost = boxAccountingDAO.findWastedCost();
        }
        model.addAttribute("boxAccounting", boxAccountingDS);
        model.addAttribute("dateFilterUrl", "/main");
        model.addAttribute("wastedCost", wastedCost);
        return "boxAccounting";
    }


    @GetMapping("/filter")
    public String getBoxAccountingByActionType(@RequestParam(value = "actionTypeId") Integer actionTypeId,
                                               @RequestParam(value = "from") @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
                                               @RequestParam(value = "to") @DateTimeFormat(pattern = "yyyy-MM-dd") Date to, Model model, Locale locale) {

        logger.info("getBoxAccountingByActionType->fired");
        List<BoxAccountingD> boxAccountingDS;
        HashMap<String, Double> incExpValues;

        String actionType;

        switch (actionTypeId) {
            case 1:
                boxAccountingDS = boxAccountingDAO.findVendorOrder(from, to, locale);
                incExpValues = boxAccountingDAO.findVendorOrderExpenseByDate(from);
                actionType = messageSource.getMessage("boxAccounting.vendorOrders", null, locale);
                break;
            case 2:
                boxAccountingDS = boxAccountingDAO.findVendorPayment(from, to, locale);
                incExpValues = boxAccountingDAO.findVendorPaymentExpenseByDate(from);
                actionType = messageSource.getMessage("boxAccounting.vendorPayment", null, locale);
                break;
            case 3:
                boxAccountingDS = boxAccountingDAO.findVendorReturn(from, to, locale);
                incExpValues = boxAccountingDAO.findVendorReturnExpenseByDate(from);
                actionType = messageSource.getMessage("boxAccounting.vendorReturn", null, locale);
                break;
            case 4:
                boxAccountingDS = boxAccountingDAO.findCustomerOrder(from, to, locale);
                incExpValues = boxAccountingDAO.findCustomerOrderIncomesByDate(from);
                actionType = messageSource.getMessage("boxAccounting.customerOrders", null, locale);
                break;
            case 5:
                boxAccountingDS = boxAccountingDAO.findCustomerPayment(from, to, locale);
                incExpValues = boxAccountingDAO.findCustomerPaymentIncomesByDate(from);
                actionType = messageSource.getMessage("boxAccounting.customerPayment", null, locale);
                break;
            case 6:
                boxAccountingDS = boxAccountingDAO.findCustomerReturn(from, to, locale);
                incExpValues = boxAccountingDAO.findCustomerReturnIncomesByDate(from);
                actionType = messageSource.getMessage("boxAccounting.customerReturn", null, locale);
                break;
            case 7:
                boxAccountingDS = boxAccountingDAO.findExpenses(from, to, locale);
                incExpValues = boxAccountingDAO.findOldExpenses(from);
                actionType = messageSource.getMessage("boxAccounting.expenses", null, locale);
                break;
            case 8:
                boxAccountingDS = boxAccountingDAO.findIncomes(from, to, locale);
                incExpValues = boxAccountingDAO.findOldIncomes(from);
                actionType = messageSource.getMessage("boxAccounting.incomes", null, locale);
                break;
            case 9:
                boxAccountingDS = boxAccountingDAO.findWithdraws(from, to, locale);
                incExpValues = boxAccountingDAO.findOldWithdraws(from);
                actionType = messageSource.getMessage("boxAccounting.withdraws", null, locale);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + actionTypeId);
        }

        model.addAttribute("incExpValues", incExpValues);
        model.addAttribute("from", from);
        model.addAttribute("to", to);

        model.addAttribute("boxAccounting", boxAccountingDS);
        model.addAttribute("dateFilterUrl", "/filter");
        model.addAttribute("actionType", actionType);
        model.addAttribute("actionTypeId", actionTypeId);


        return "boxAccounting";
    }


}
