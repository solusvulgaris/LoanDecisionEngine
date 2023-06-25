package com.ak.loanengine.loanengine.services;

import com.ak.loanengine.loanengine.exceptions.UserNotFoundException;
import com.ak.loanengine.loanengine.util.Decision;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Loan Webservice Controller
 */
@RestController
@RequestMapping("/loanengineapi")
public class LoanWebserviceController {
    private static final Logger logger = Logger.getLogger(LoanWebserviceController.class.getName());
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
     * @return Decision
     */
    @GetMapping
    public Decision getDecision(
            @RequestParam(value = "personalCode") String personalCode,
            @RequestParam(value = "desiredLoanAmount") String desiredLoanAmount,
            @RequestParam(value = "loanPeriod") String loanPeriod) {
        try {
            BigDecimal calculateLoanAmount = loanService.calculateLoan(personalCode, desiredLoanAmount, loanPeriod);
            return new Decision(calculateLoanAmount);
        } catch (UserNotFoundException e) {
            logger.log(Level.WARNING, e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
