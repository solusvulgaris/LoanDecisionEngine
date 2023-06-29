package com.ak.loanengine.loanengine.services;

import com.ak.loanengine.loanengine.util.Loan;
import com.ak.loanengine.loanengine.data.SegmentRepository;
import com.ak.loanengine.loanengine.data.User;
import com.ak.loanengine.loanengine.data.UserRepository;
import com.ak.loanengine.loanengine.exceptions.UserNotFoundException;
import com.ak.loanengine.loanengine.util.Decision;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Loan Service
 */
@Getter
@Setter
@Service
public class LoanService {
    private final SegmentRepository segmentRepository;
    private final UserRepository userRepository;

    protected float initialLoanAmount;
    protected float initialLoanPeriod;
    protected float calculatedLoanAmount = 0;
    protected float calculatedLoanPeriod = 0;
    List<Decision> decisions = new ArrayList<>();

    @Autowired
    public LoanService(SegmentRepository segmentRepository, UserRepository userRepository) {
        this.segmentRepository = segmentRepository;
        this.userRepository = userRepository;
    }

    public Decision calculateLoan(Loan loan)
            throws UserNotFoundException {
        Optional<User> user = userRepository.findByCode(loan.getPersonalCode());
        if (user.isEmpty()) {
            throw new UserNotFoundException(String.format("There is no user with personal code: %s", loan.getPersonalCode()));
        } else {
            if (user.get().isDebt()) {
                return new Decision();
            } else {
                int creditModifier = segmentRepository.findById(user.get().getSegmentId()).get().getModifier();
                initialLoanAmount = loan.getLoanAmount();
                initialLoanPeriod = loan.getLoanPeriod();
                decisions = new ArrayList<>();
                return calculate(creditModifier, initialLoanAmount, initialLoanPeriod);
            }
        }
    }

    protected Decision calculate(float creditModifier, float inputAmount, float inputPeriod) {
        float creditScoreMax = (creditModifier / inputAmount) * inputPeriod;
        if (creditScoreMax < 1) {
            if (inputAmount <= 2000) {
                if (inputPeriod >= 60) {
                    //If the result is less than 1 in all cases - then we would not approve such sum
                    return new Decision(BigDecimal.valueOf(calculatedLoanAmount), (int) calculatedLoanPeriod);
                } else {
                    // try to prolog loanPeriod
                    return calculate(creditModifier, initialLoanAmount, inputPeriod + 1);
                }
            } else {
                if (inputAmount != initialLoanAmount) {
                    if (inputPeriod >= 60) {
                        if (inputAmount <= 2000) {
                            //If the result is less than 1 in all cases - then we would not approve such sum
                            return new Decision(BigDecimal.valueOf(calculatedLoanAmount), (int) calculatedLoanPeriod);
                        } else {
                            // try to find min sum to be approved
                            return calculate(creditModifier, inputAmount - 100, inputPeriod);
                        }
                    } else {
                        // try to prolog loanPeriod
                        return calculate(creditModifier, inputAmount, inputPeriod + 1);
                    }
                } else {
                    // try to find min sum to be approved
                    return calculate(creditModifier, inputAmount - 100, inputPeriod);
                }
            }
        } else {
            Decision calculatedDecision = new Decision(BigDecimal.valueOf(inputAmount), (int) inputPeriod);
            if (decisions.contains(calculatedDecision)) {
                Collections.sort(decisions);
                return decisions.get(decisions.size() - 1);
            } else {
                decisions.add(calculatedDecision);
            }
            //if the result is larger or equal than 1 then we would approve this sum.
            if (inputAmount == 10000) {
                if (inputPeriod <= 12) {
                    return new Decision(BigDecimal.valueOf(inputAmount), (int) inputPeriod);
                } else {
                    //have to reduce period
                    return calculate(creditModifier, inputAmount, inputPeriod - 1);
                }
            } else {
                //try the other options, to find max sum that can be approved
                return calculate(creditModifier, inputAmount + 100, inputPeriod);
            }
        }
    }
}
