package com.ak.loanengine.loanengine.webservice;

import com.ak.loanengine.loanengine.util.Decision;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/*
 * Webservice Loan Controller
 */
@RestController
@RequestMapping("/loanengineapi")
public class LoanService {
    /**
     * Get Decision
     *
     * @param personalCode user personal code
     * @param desiredLoanAmount desired loan amount specified by user
     * @param loanPeriod loan period (month) specified by user
     *
     * @return Decision
     */
    @GetMapping
    public Decision getDecision(
            @RequestParam(value = "personalCode", required = true) String personalCode,
            @RequestParam(value = "desiredLoanAmount", required = true) String desiredLoanAmount,
            @RequestParam(value = "loanPeriod", required = true) String loanPeriod) {
        //TODO: implement service choosing the decision value
        BigDecimal calculateLoanAmount = new BigDecimal(desiredLoanAmount);
        return new Decision(calculateLoanAmount);
    }
}
