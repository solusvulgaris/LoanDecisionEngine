package com.ak.loanengine.loanengine.services;

import com.ak.loanengine.loanengine.data.SegmentRepository;
import com.ak.loanengine.loanengine.data.UserRepository;
import com.ak.loanengine.loanengine.util.AppStartupEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.logging.Logger;

/**
 * Loan Service
 */
@Service
public class LoanService {
    private static final Logger logger = Logger.getLogger(LoanService.class.getName());

    private final SegmentRepository segmentRepository;
    private final UserRepository userRepository;

    @Autowired
    public LoanService(SegmentRepository segmentRepository, UserRepository userRepository) {
        this.segmentRepository = segmentRepository;
        this.userRepository = userRepository;
    }

    public BigDecimal calculateLoan(String personalCode, String desiredLoanAmount, String loanPeriod) {
        BigDecimal calculatedLoanAmount = new BigDecimal(desiredLoanAmount);
        //TODO: implement calculation
        return calculatedLoanAmount;
    }
}
