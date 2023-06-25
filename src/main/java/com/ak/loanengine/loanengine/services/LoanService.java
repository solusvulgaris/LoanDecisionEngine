package com.ak.loanengine.loanengine.services;

import com.ak.loanengine.loanengine.data.SegmentRepository;
import com.ak.loanengine.loanengine.data.User;
import com.ak.loanengine.loanengine.data.UserRepository;
import com.ak.loanengine.loanengine.exceptions.UserNotFoundException;
import com.ak.loanengine.loanengine.util.Decision;
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

    public Decision calculateLoan(String personalCode, int desiredLoanAmount, int loanPeriod)
            throws UserNotFoundException {
        Optional<User> user = userRepository.findByCode(personalCode);
        if (user.isEmpty()) {
            throw new UserNotFoundException(String.format("There is no user with personal code: %s", personalCode));
        } else {
            if (user.get().isDebt()) {
                return new Decision();
            } else {
                int creditModifier = segmentRepository.findById(user.get().getSegmentId()).get().getModifier();
                float creditScore = ((float) creditModifier / (float) desiredLoanAmount) * loanPeriod;
                if (creditScore < 1) {
                    new Decision(); //If the result is less than 1 then we would not approve such sum
                } else {
                    //if the result is larger or equal than 1 then we would approve this sum.
                    return new Decision(new BigDecimal(desiredLoanAmount));
                }
            }
        }
        return new Decision();
    }

}
