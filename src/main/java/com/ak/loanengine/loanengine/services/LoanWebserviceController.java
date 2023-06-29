package com.ak.loanengine.loanengine.services;

import com.ak.loanengine.loanengine.util.Loan;
import com.ak.loanengine.loanengine.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Loan Webservice Controller
 */
@Controller
@RequestMapping("/api")
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
     * @param loan loan DTO object - gets params to calculate loan value
     * @return result form name to redirect
     */
    @PostMapping("/loan")
    public String loanSubmit(@ModelAttribute("loan") Loan loan, Model model) {
        try {
            loan.setDecision(loanService.calculateLoan(loan));
            model.addAttribute("loan", loan);
            return "result";
        } catch (UserNotFoundException e) {
            logger.log(Level.WARNING, e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

}
