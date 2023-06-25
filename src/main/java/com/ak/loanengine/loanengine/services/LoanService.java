package com.ak.loanengine.loanengine.services;

import com.ak.loanengine.loanengine.data.SegmentRepository;
import com.ak.loanengine.loanengine.data.User;
import com.ak.loanengine.loanengine.data.UserRepository;
import com.ak.loanengine.loanengine.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Loan Service
 */
@Service
public class LoanService {
    private final SegmentRepository segmentRepository;
    private final UserRepository userRepository;

    @Autowired
    public LoanService(SegmentRepository segmentRepository, UserRepository userRepository) {
        this.segmentRepository = segmentRepository;
        this.userRepository = userRepository;
    }

    public BigDecimal calculateLoan(String personalCode, String desiredLoanAmount, String loanPeriod)
            throws UserNotFoundException {
        Optional<User> user = userRepository.findByCode(personalCode);
        if (user.isEmpty()) {
            throw new UserNotFoundException(String.format("There is no user with personal code: %s", personalCode));
        } else {
            if (user.get().isDebt()) {
                return new BigDecimal(0);
            } else {
                BigDecimal calculatedLoanAmount = new BigDecimal(desiredLoanAmount);
                //TODO: implement calculation
                return calculatedLoanAmount;
            }
        }
    }

}
