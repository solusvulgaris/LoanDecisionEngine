package com.ak.loanengine.loanengine.services;

import com.ak.loanengine.loanengine.util.Decision;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/*
 * Loan Webservice Controller
 */
@RestController
@RequestMapping("/loanengineapi")
public class LoanWebserviceController {
    private final LoanService loanService;

    @Autowired
    public LoanWebserviceController(LoanService loanService) {
        this.loanService = loanService;
    }

    /**
     * Get Decision
     *
     * @param personalCode      user personal code
     * @param desiredLoanAmount desired loan amount specified by user
     * @param loanPeriod        loan period (month) specified by user
     *
     * @return Decision
     */
    @GetMapping
    public Decision getDecision(
            @RequestParam(value = "personalCode") String personalCode,
            @RequestParam(value = "desiredLoanAmount") String desiredLoanAmount,
            @RequestParam(value = "loanPeriod") String loanPeriod) {
        BigDecimal calculateLoanAmount = loanService.calculateLoan(personalCode, desiredLoanAmount, loanPeriod);
        return new Decision(calculateLoanAmount);
    }
}
