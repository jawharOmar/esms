package com.joh.esms.controller;


import com.joh.esms.dao.NetProfitDAO;
import com.joh.esms.domain.model.NetProfitD;
import com.joh.esms.model.Account;
import com.joh.esms.model.AccountTransaction;
import com.joh.esms.service.AccountService;
import com.joh.esms.service.AccountTransactionService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;
import java.util.Locale;

@Controller()
@RequestMapping(path = "/netProfit")
public class NetProfitController {

    private static final Logger logger = Logger.getLogger(NetProfitController.class);

    @Autowired
    private NetProfitDAO netProfitDAO;

    @Autowired
    private AccountTransactionService accountTransactionService;

    @Autowired
    private AccountService accountService;

    @GetMapping(path = "/main")
    private String getNetProfit(@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
                                @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date to, Model model, Locale locale) {
        logger.info("getNetProfit->fired");

        Double oldBalance = 0.0;

        List<NetProfitD> netProfitDS;
        List<AccountTransaction> accountTransaction = accountTransactionService.findAllByAccountId(1);

//        Different mean different between Account and Account Transactions
        double different = accountTransaction.stream().mapToDouble(e -> e.getAmount()).sum() - accountService.findOne(1).getBalance();


        if (from != null && to != null) {

            netProfitDS = netProfitDAO.findNetProfitByDate(from, to, locale);

            List<NetProfitD> findOldBalance = netProfitDAO.findNetProfitOldBalance(from, locale);
            oldBalance = findOldBalance.stream().mapToDouble(e -> e.getAmount()).sum();

            oldBalance -= different;

            model.addAttribute("from", from);
            model.addAttribute("to", to);
        } else {
            netProfitDS = netProfitDAO.findNetProfit(locale);

//            2 means the account Balance of System(that selected in query)
            netProfitDS.get(2).setAmount(netProfitDS.get(2).getAmount() - different);
        }

        model.addAttribute("oldBalance", oldBalance);
        model.addAttribute("netProfitDS", netProfitDS);
        return "netProfit";
    }
}
